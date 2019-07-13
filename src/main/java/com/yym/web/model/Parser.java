package com.yym.web.model;

import com.yym.web.common.ParserTypeEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Parser {

  private String url;
  private ParserTypeEnum type;
  private int unit;
  private String quotientString;
  private String remainderString;
  List<String> stringList = new ArrayList<>();
  List<Integer> integerList = new ArrayList<>();

}
