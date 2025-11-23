package com.example.social_network.Controller;


import com.example.social_network.Dto.request.AuthenticationRequest;
import com.example.social_network.Dto.request.IntrospectRequest;
import com.example.social_network.Dto.request.LogoutRequest;
import com.example.social_network.Dto.request.RefreshRequest;
import com.example.social_network.Dto.response.ApiResponse;
import com.example.social_network.Dto.response.AuthenticationResponse;
import com.example.social_network.Dto.response.IntrospectResponse;
import com.example.social_network.Service.AuthenticationService;
import com.example.social_network.Service.TokenVerificationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService  authenticationService;

    TokenVerificationService tokenVerificationService;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder()
                .build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> refreshtoken(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
       var result = authenticationService.refreshToken(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();

    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var result = tokenVerificationService.introspectResponse(request);

        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }


}
