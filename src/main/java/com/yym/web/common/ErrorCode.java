package com.yym.web.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
  // Common
  INVALID_UNIT("001", HttpStatus.BAD_REQUEST, "단위값은 0 이상 입니다."),
  INVALID_URL("002", HttpStatus.BAD_REQUEST, "잘못된 URL 주소 형식 입니다."),
  INVALID_INPUT("003", HttpStatus.BAD_REQUEST, "잘못된 요청 형식 입니다."),
  UNKNOWN_ERROR("004", HttpStatus.INTERNAL_SERVER_ERROR, "알수없는 오류 발생");

  private final String code;
  private final HttpStatus httpStatus;
  private final String message;
}
