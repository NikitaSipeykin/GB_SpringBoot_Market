package ru.gb.springbootdemoapp.controller.rest;

import org.springframework.web.bind.annotation.*;
import ru.gb.springbootdemoapp.dto.Cart;
import ru.gb.springbootdemoapp.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartRestController {
  private CartService cartService;

  public CartRestController(CartService cartService) {
    this.cartService = cartService;
  }

  @GetMapping
  public Cart getCart(){
    return cartService.getCartForCurrentUser();
  }

  @PostMapping("/product/{id}")
  public Cart addProduct(@PathVariable Long id){
    return cartService.addProductById(id);
  }

  @DeleteMapping("/product/{id}")
  public Cart deleteProduct(@PathVariable Long id){
    return cartService.removeProductById(id);
  }
}
