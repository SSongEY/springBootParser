package com.yym.web.services;

import com.yym.web.common.ErrorCode;
import com.yym.web.common.ParserTypeEnum;
import com.yym.web.exception.ApiException;
import com.yym.web.model.Parser;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ParserServiceImpl implements ParserService {

  private RestTemplate restTemplate;

  @PostConstruct
  private void init(){
    restTemplate = new RestTemplate();
  }

  @Override
  public Parser convert(Parser parser) {
    String html;

    //TODO 요청 묶음 단위 체크
    validUnit(parser.getUnit());
    //TODO parser의 url호출 후 fetch html
    html = fetchHtmlForRequestUrl(parser.getUrl());
    //TODO 정규식을 통한 html parsing
    html = convertHtmlByTypeFromRegex(html, parser);

    makeIntAndStringListForParser(parser, html);

    //TODO stringList Sorting ex) AaBbCcDd....Zz
    Collections.sort(parser.getStringList(), (o1, o2) -> {
      String o1Upper = o1.toUpperCase();
      String o2Upper = o2.toUpperCase();
      int result = o1Upper.compareTo(o2Upper);

      if(result != 0)
        return result;
      else{
        return o1.compareTo(o2);
      }
    });
    //TODO integerList Sorting ex) 012345..9
    Collections.sort(parser.getIntegerList());

    //TODO 출력묶음 단위로 몫/나머지 데이터 생성
    makeParseData(parser);

    return parser;
  }

  private void validUnit(int unit) {
    if(unit == 0) throw new ApiException(ErrorCode.INVALID_UNIT);
  }

  private String fetchHtmlForRequestUrl(String requestUrl) {
    String html = null;
    ResponseEntity<String> response;
    try {
      response = restTemplate.exchange(requestUrl, HttpMethod.GET, null, String.class);
      html = response.getBody();
    } catch (IllegalArgumentException argException){
      throw new ApiException(ErrorCode.INVALID_URL);
    }
    return html;
  }

  private void makeParseData(Parser parser){
    StringBuilder sb = new StringBuilder();
    List<Integer> integerList = parser.getIntegerList();
    List<String> stringList = parser.getStringList();

    int i = 0;
    //TODO 각 리스트 최대 size 만큼 stringBuilder append ex) A0a1B2b3C4.......
    while (i < integerList.size() || i < stringList.size()){
      if(i < stringList.size()) sb.append(stringList.get(i));
      if(i < integerList.size()) sb.append(integerList.get(i));
      i ++;
    }

    int splitNumber = sb.toString().length() / parser.getUnit() * parser.getUnit();
    parser.setQuotientString(sb.toString().substring(0, splitNumber));
    parser.setRemainderString(sb.toString().substring(splitNumber));
  }

  private String convertHtmlByTypeFromRegex(String html, Parser parser){
    String parsedHtml = html;

    if(parser.getType() == ParserTypeEnum.T){
      //TODO HTML 태그 제외
      parsedHtml = parsedHtml.replaceAll("\\<.*?\\>", "");
    }
    //TODO 영어,숫자 제외 나머지 문자 빈스트링 처리
    parsedHtml = parsedHtml.replaceAll("[^a-zA-Z0-9]", "");

    return parsedHtml;
  }

  private void makeIntAndStringListForParser(Parser parser, String html){
    List<Integer> integerList = new ArrayList<>();
    List<String> stringList = new ArrayList<>();
    //TODO 영어 숫자 구분
    Pattern isNumber = Pattern.compile("[0-9]");
    for (String value : html.split("")){
      Matcher matched = isNumber.matcher(value);
      if(matched.matches()){
        integerList.add(Integer.valueOf(value));
      }else{
        stringList.add(value);
      }
    }
    parser.setIntegerList(integerList);
    parser.setStringList(stringList);
  }
}
