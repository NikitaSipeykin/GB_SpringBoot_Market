package ru.gb.springbootdemoapp.service;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import ru.gb.springbootdemoapp.converter.ProductMapper;
import ru.gb.springbootdemoapp.dto.Cart;
import ru.gb.springbootdemoapp.repository.CartRepository;

import java.security.Principal;

@Service
public class CartService {
  private ProductService productService;
  private ProductMapper productMapper;
  private CartRepository cartRepository;

  public CartService(ProductService productService, ProductMapper productMapper, CartRepository cartRepository) {
    this.productService = productService;
    this.productMapper = productMapper;
    this.cartRepository = cartRepository;
  }

  public Cart getCartForCurrentUser(){
    Principal principal = SecurityContextHolder.getContext().getAuthentication();
    String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
    Cart cart = getCart(principal, sessionId);
    return cart;
  }

  public void removeCartForCurrentUser(){
    cartRepository.delete(getCartForCurrentUser());
  }

  private Cart getCart(Principal principal, String sessionId) {
    //Todo: what will we do if anonymous add something to cart and login after
    if (principal instanceof AnonymousAuthenticationToken){
      return cartRepository.findById(sessionId).orElse(new Cart(sessionId));
    }
    return cartRepository.findById(principal.getName()).orElse(new Cart(principal.getName()));
  }

  public Cart addProductById(Long id){
    Cart cart = getCartForCurrentUser();
    productService.findById(id).ifPresent(product -> cart.addItem(productMapper.productToCartItem(product)));
    cartRepository.save(cart);
    return cart;
  }

  public Cart removeProductById(Long id){
    Cart cart = getCartForCurrentUser();
    cart.removeItem(id);
    cartRepository.save(cart);
    return cart;
  }
}
