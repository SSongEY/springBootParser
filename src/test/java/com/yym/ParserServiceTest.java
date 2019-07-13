package com.yym;

import com.yym.web.exception.ApiException;
import com.yym.web.model.Parser;
import com.yym.web.services.ParserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ParserServiceTest {

  @Autowired
  private ParserService parserService;

  @Test
  public void 요청URL_나머지값_테스트(){

    //TODO 요청된 묶음 단위보다 parser의 나머지값 length는 항상 작아야 한다.
    Parser parser = new Parser();
    parser.setUrl("http://google.co.kr");
    parser.setUnit(10);

    parser = parserService.convert(parser);
    Assert.assertTrue(parser.getRemainderString().length() < parser.getUnit());
  }

  @Test(expected = ApiException.class)
  public void 요청URL_유효성_테스트(){

    //TODO 요청된 URI 형식을 유지
    Parser parser = new Parser();
    parser.setUrl("google.co.kr");
    parserService.convert(parser);
  }

  @Test(expected = ApiException.class)
  public void 요청_묶음단위_유효성_테스트(){

    //TODO 요청된 URI 형식을 유지
    Parser parser = new Parser();
    parser.setUrl("http://google.co.kr");
    parser.setUnit(0);
    parserService.convert(parser);
  }

  @Test
  public void 영어정렬하기(){
    String input = "CaDAbcBd";
    String result = "";
    String[] arr = input.split("");

    Arrays.sort(arr, (o1, o2) -> {
      String o1Upper = o1.toUpperCase();
      String o2Upper = o2.toUpperCase();
      int comp = o1Upper.compareTo(o2Upper);

      if(comp != 0)
        return comp;
      else{
        return o1.compareTo(o2);
      }
    });

    for(String str : arr){
      result = result.concat(str);
    }
    Assert.assertEquals("AaBbCcDd", result);
  }
}
