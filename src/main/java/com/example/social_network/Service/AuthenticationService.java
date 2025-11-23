package com.example.social_network.Service;


import com.example.social_network.Dto.request.AuthenticationRequest;
import com.example.social_network.Dto.request.LogoutRequest;
import com.example.social_network.Dto.request.RefreshRequest;
import com.example.social_network.Dto.response.AuthenticationResponse;
import com.example.social_network.Entity.InvalidatedToken;
import com.example.social_network.Entity.User;
import com.example.social_network.Exception.AppException;
import com.example.social_network.Exception.ErrorCode;
import com.example.social_network.Repository.InvalidatedTokenRepository;
import com.example.social_network.Repository.UserReponsitory;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserReponsitory userReponsitory;
    InvalidatedTokenRepository invalidatedTokenRepository;
    PasswordEncoder passwordEncoder;
    TokenVerificationService tokenVerificationService;


    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;


    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    // ---------------- authenticate ----------------
    public AuthenticationResponse authenticate(AuthenticationRequest request){

        var user = userReponsitory.findByemail(request.getIdentifier())
                .or(() -> userReponsitory.findByphoneNumber(request.getIdentifier()))
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if(!authenticated) throw  new AppException(ErrorCode.UNAUTHENTICATED);

        var token = generaToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();

    }

    // ---------------- generate token ----------------
    private String generaToken(User user){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUserId())
                .issuer("social.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("email", user.getEmail())
                .claim("phoneNumber", user.getPhoneNumber())
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        }catch (JOSEException e){
            log.error("Cannot create token", e);
            throw  new RuntimeException(e);
        }

    }

    // ---------------- logout ----------------
    public void logout(LogoutRequest request) throws ParseException, JOSEException {
       try{
           var signToken = tokenVerificationService.verifyToken(request.getToken(), true);

           String jit = signToken.getJWTClaimsSet().getJWTID();;
           Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();


           InvalidatedToken invalidatedToken =
                   InvalidatedToken.builder()
                           .id(jit)
                           .expriTime(expiryTime)
                           .build();
           invalidatedTokenRepository.save(invalidatedToken);
       }catch (AppException exception){
           log.info("Token already expired");
       }
    }

    // ---------------- refresh ----------------
    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        var signedJWT = tokenVerificationService.verifyToken(request.getToken(), true);

        var jit = signedJWT.getJWTClaimsSet().getJWTID();

        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken =
                InvalidatedToken.builder().id(jit).expriTime(expiryTime).build();

        invalidatedTokenRepository.save(invalidatedToken);

        // get subject and claims
        var userId = signedJWT.getJWTClaimsSet().getSubject();

        var phone = signedJWT.getJWTClaimsSet().getStringClaim("phoneNumber");

        var email = signedJWT.getJWTClaimsSet().getStringClaim("email");


        var user = userReponsitory.findById(userId)
                .or(() -> userReponsitory.findByphoneNumber(phone))
                .or(() -> userReponsitory.findByemail(email))
                .orElseThrow(()-> new AppException(ErrorCode.UNAUTHENTICATED));

        var token = generaToken(user);

        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getRoleName());
                if (!CollectionUtils.isEmpty(role.getPermissions())) {
                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getPermissionName()));
                }
            });
        }
        return stringJoiner.toString();
    }

}
