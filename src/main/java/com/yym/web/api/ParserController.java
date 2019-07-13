package com.yym.web.api;

import com.yym.web.common.ErrorCode;
import com.yym.web.exception.ApiException;
import com.yym.web.model.Parser;
import com.yym.web.services.ParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/parser")
public class ParserController {

  private ParserService parserService;

  @Autowired
  public ParserController(ParserService parserService){
    this.parserService = parserService;
  }

  @GetMapping
  public Parser convert(@Valid @ModelAttribute Parser parser, BindingResult bindingResult){
    if(bindingResult.hasErrors()){
      throw new ApiException(ErrorCode.INVALID_INPUT);
    }
    return parserService.convert(parser);
  }
}
