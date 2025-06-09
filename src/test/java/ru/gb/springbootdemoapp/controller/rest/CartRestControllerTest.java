package ru.gb.springbootdemoapp.controller.rest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import ru.gb.springbootdemoapp.dto.Cart;
import ru.gb.springbootdemoapp.service.CartService;

public class CartRestControllerTest {
  private CartRestController cartRestController;
  private Cart cart = new Cart();

  @BeforeEach
  void setUp(){
    CartService cartService = mock(CartService.class);
    when(cartService.getCartForCurrentUser()).thenReturn(cart);

    cartRestController = new CartRestController(cartService);
  }

  @Test
  void getCartForCurrentUser(){
    Cart currentCart = cartRestController.getCart();
    assertSame(currentCart, cart);
  }
}
