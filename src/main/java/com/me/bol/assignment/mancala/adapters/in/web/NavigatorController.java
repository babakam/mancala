package com.me.bol.assignment.mancala.adapters.in.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavigatorController {

  private static final String MAIN_PAGE = "main";

  @GetMapping("/")
  public String mainPageHandler() {
    return MAIN_PAGE;
  }
}
