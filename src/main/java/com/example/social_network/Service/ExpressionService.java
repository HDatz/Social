//package com.example.social_network.Service;
//
//import com.example.social_network.Dto.request.ExpressionCreateRequest;
//import com.example.social_network.Dto.response.ExpressionResponse;
//import com.example.social_network.Exception.AppException;
//import com.example.social_network.Exception.ErrorCode;
//import com.example.social_network.Mapper.ExpressionMapper;
//import com.example.social_network.Repository.ExpressionRepository;
//import com.example.social_network.Security.SecurityUtils;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//public class ExpressionService {
//
//    ExpressionRepository expressionRepository;
//    ExpressionMapper expressionMapper;
//    UserRepository userRepository;
//
//    public ExpressionResponse createExpression(ExpressionCreateRequest request){
//       var expression = expressionMapper.toResponse(request);
//
//       String userId = SecurityUtils.getCurrentUserId();
//
//       var user = userRepository.findById(userId)
//               .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
//
//       expression.setU
//    }
//}
