package me.xu.controller.advice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.xu.common.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Map;

/**
 * Description 响应增强
 * Date 2022/3/24 13:44
 * Version 1.0.1
 *
 * @author Wen
 */
@RestControllerAdvice
public class ResultAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 放行Swagger
        if (request.getURI().getPath().matches("(.*)swagger(.*)|(.*)api-docs(.*)")) {
            return body;
        }
        // 放行404响应
        if (body instanceof Map && ((Map<?, ?>) body).get("status").equals(404)) {
            return body;
        }
        // 若原返回结果为String，需要生成响应体、转换为JSON，再返回
        if (body instanceof String || String.class.equals(returnType.getGenericParameterType())) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            try {
                return mapper.writeValueAsString(Result.success(body));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        // 如果返回Boolean值，则生成对应的响应体再返回
        if (body instanceof Boolean) {
            if ((Boolean) body) {
                return Result.success();
            } else {
                return Result.failed();
            }
        }
        // 如果已经包装为响应体，直接返回
        if (body instanceof Result) {
            return body;
        }
        return Result.success(body);
    }
}
