package com.ngts.auth.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//for Angular Client (withCredentials)
//@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials="true")
//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@CrossOrigin(origins = "/**", maxAge = 3600)
@RestController
@RequestMapping("/auth/test")
public class TestController {

  @GetMapping(value = "/all", produces = "application/html")
  public String allAccess() {
    return "Public Content.";
  }

  @GetMapping("/user")
  public String userAccess() {
    return "User Content.";
  }

  @GetMapping("/mod")
  public String moderatorAccess() {
    return "Moderator Board.";
  }

  @GetMapping("/admin")
  public String adminAccess() {
    return "Admin Board.";
  }
}
