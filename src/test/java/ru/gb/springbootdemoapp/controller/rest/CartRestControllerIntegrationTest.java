package ru.gb.springbootdemoapp.controller.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.gb.springbootdemoapp.dto.Cart;
import ru.gb.springbootdemoapp.dto.CartItem;
import ru.gb.springbootdemoapp.service.CartService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CartRestControllerIntegrationTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void getCartForCurrentUser(){
    ResponseEntity<Cart> entity = restTemplate.getForEntity("/cart", Cart.class);
    assertSame(entity.getStatusCode(), HttpStatus.OK);
  }

  @MockBean
  private CartService cartService;

  @BeforeEach
  void setUp(){
    Cart cart = new Cart();
    CartItem cartItem = new CartItem();
    cartItem.setPrice(10.f);
    cart.addItem(cartItem);

    given(cartService.getCartForCurrentUser()).willReturn(cart);
  }

  @Test
  void getCartItemsTest(){
    ResponseEntity<Cart> entity = restTemplate.getForEntity("/cart", Cart.class);
    var cartItems = entity.getBody().getItems();
    assertSame(cartItems.size(), 1);
    assertEquals(cartItems.get(0).getPrice(), 10.f);
  }

  void addItemsTest(){
    restTemplate.postForEntity("/cart/product/1", null, Cart.class);
    verify(cartService).addProductById(1L);
  }
}
