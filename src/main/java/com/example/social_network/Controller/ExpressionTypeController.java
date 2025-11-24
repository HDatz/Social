package com.example.social_network.Controller;

import com.example.social_network.Dto.request.ExpressionTypeRequest;
import com.example.social_network.Dto.response.ApiResponse;
import com.example.social_network.Dto.response.ExpressionTypeResponse;
import com.example.social_network.Service.ExpressionTypeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expressionType")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExpressionTypeController {
    ExpressionTypeService expressionTypeService;

    @PostMapping
    ApiResponse<ExpressionTypeResponse> create(@RequestBody ExpressionTypeRequest request){
        return ApiResponse.<ExpressionTypeResponse>builder()
                .result(expressionTypeService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<ExpressionTypeResponse>> getAll(){
        return ApiResponse.<List<ExpressionTypeResponse>>builder()
                .result(expressionTypeService.getAll())
                .build();
    }

    @DeleteMapping("/{expressionTypeName}")
    ApiResponse<String> delete (@PathVariable("expressionTypeName") String expression){
        expressionTypeService.deleteExpressionType(expression);
        return ApiResponse.<String>builder()
                .result("Delete has been success")
                .build();
    }

}
