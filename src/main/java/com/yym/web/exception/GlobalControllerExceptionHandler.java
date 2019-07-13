package com.yym.web.exception;

import com.yym.web.common.ErrorCode;
import com.yym.web.dto.ApiErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice(basePackages = {"com.yym.web.api"})
public class GlobalControllerExceptionHandler implements ResponseBodyAdvice<Object> {

  private Logger logger = LoggerFactory.getLogger(getClass());

  @ExceptionHandler
  @ResponseBody
  public ApiErrorResponse handleConflict(final Exception e, final HttpServletResponse response) {
    logger.error(e.getMessage());
    if (e instanceof ApiException) {
      final ApiException ae = (ApiException) e;
      response.setStatus(ae.getHttpStatus().value());

      return new ApiErrorResponse(ae);
    }
    return new ApiErrorResponse(ErrorCode.UNKNOWN_ERROR);
  }

  @Override
  public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
    return body;
  }
}
