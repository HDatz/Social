package com.example.social_network.Service;

import com.example.social_network.Dto.request.ExpressionTypeRequest;
import com.example.social_network.Dto.response.ExpressionTypeResponse;
import com.example.social_network.Entity.ExpressionType;
import com.example.social_network.Mapper.ExpressionTypeMapper;
import com.example.social_network.Repository.ExpressionTypeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExpressionTypeService {
    ExpressionTypeMapper expressionTypeMapper;
    ExpressionTypeRepository expressionTypeRepository;

    public ExpressionTypeResponse create(ExpressionTypeRequest request){
        ExpressionType expressionType = expressionTypeMapper.toExpressionType(request);
        expressionType = expressionTypeRepository.save(expressionType);

        return expressionTypeMapper.toExpressionTypeResponse(expressionType);
    }

    public List<ExpressionTypeResponse> getAll(){
        var expressionType = expressionTypeRepository.findAll();
        return expressionType.stream().map(expressionTypeMapper::toExpressionTypeResponse).toList();
    }

    public void deleteExpressionType(String expressionType){
        expressionTypeRepository.deleteById(expressionType);
    }
}
