package com.yym.web.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ParserTypeEnum {
  T("T", "TEXT"),
  A("A", "ALL");

  private String code;
  private String value;
}
