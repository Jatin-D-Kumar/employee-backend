package com.jatin.usermodule.common;

import com.jatin.usermodule.payload.ApiResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.time.OffsetDateTime;

@RestControllerAdvice(basePackages = "com.jatin.usermodule.controller")
public class ApiResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true; // Apply to all responses
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        // Avoid wrapping these cases
        if (body instanceof ApiResponse || body instanceof String || body instanceof byte[] || body instanceof ResponseEntity) {
            return body;
        }

        return ApiResponse.builder()
                .success(true)
                .message("Success")
                .data(body)
                .timestamp(OffsetDateTime.now())
                .build();
    }
}
