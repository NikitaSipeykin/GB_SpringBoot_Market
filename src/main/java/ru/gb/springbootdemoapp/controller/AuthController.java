package ru.gb.springbootdemoapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.springbootdemoapp.service.RegistrationService;

@Controller
public class AuthController {

  private final RegistrationService registrationService;

  public AuthController(RegistrationService registrationService) {
    this.registrationService = registrationService;
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
    // TODO принимать два пароля и сравнивать
    // TODO валидация email regexp
    String token = registrationService.sighUp(username, password); // TODO обработать ошибку и вывести пользователю
    model.addAttribute("token", token);
    return "register-confirm";
  }

  @GetMapping("/register/confirm")
  public String registerConfirm(@RequestParam String token) {
    // TODO токен истек - что делать
    if (registrationService.confirmRegistration(token)) {
      return "register-complete";
    }
    // TODO что-то выдавать разумное
    return "redirect:/";
  }
}
