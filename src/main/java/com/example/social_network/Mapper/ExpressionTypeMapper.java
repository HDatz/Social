package com.example.social_network.Mapper;

import com.example.social_network.Dto.request.ExpressionTypeRequest;
import com.example.social_network.Dto.response.ExpressionTypeResponse;
import com.example.social_network.Entity.ExpressionType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExpressionTypeMapper {

    ExpressionType toExpressionType(ExpressionTypeRequest request);

    ExpressionTypeResponse toExpressionTypeResponse(ExpressionType expressionType);
}
