package ru.gb.springbootdemoapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.springbootdemoapp.service.UserService;

@Controller
public class AuthController {
  private final UserService userService;

  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/login")
  public String loginForm() {
    return "login";
  }

  @GetMapping("/register")
  public String registerForm() {
    return "register";
  }

  @PostMapping("/register")
  public String register(@RequestParam String username, @RequestParam String password, Model model) {
    // Todo: accept 2 passwords and compare it
    // Todo: email and regexp validation

    String token = userService.sighUp(username, password); //Todo: get the error and send to user
    model.addAttribute("token", token);

    return "register-confirm";
  }

  @GetMapping("/register/confirm")
  public String registerConfirm(@RequestParam String token){
    if (userService.confirmRegistration(token)){
      return "register-complete";
    }
    //Todo: response something logical
    return "redirect:/";
  }
}
