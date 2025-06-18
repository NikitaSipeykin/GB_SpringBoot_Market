package ru.gb.springbootdemoapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.springbootdemoapp.model.Order;
import ru.gb.springbootdemoapp.service.OrderService;

import java.security.Principal;

@Controller
@RequestMapping("/order")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping
  public String getOrderPage(){
    return "order";
  }

  @GetMapping("/success")
  public String getOrderSuccessPage(){
    return "order-success";
  }

  @PostMapping
  public String createOrder(@RequestParam String address, @RequestParam String email, Principal principal, Model model){
    try {
      Order order = orderService.placeOrder(address, email, principal);
      return "redirect:/order/success";
    } catch (IllegalStateException e) {
      model.addAttribute("illegalStateException", e);
      return "order";
    }
  }
}
