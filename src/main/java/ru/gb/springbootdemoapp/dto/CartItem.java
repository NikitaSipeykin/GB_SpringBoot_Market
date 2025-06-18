package ru.gb.springbootdemoapp.dto;

import lombok.Data;

@Data
public class CartItem {
  private Long productId;
  private String title;
  private Float pricePerOne;
  private Float price;
  private int count = 1;

  public void incrementCount(){
    count++;
    price = pricePerOne * count;
  }

  public void decrementCount(){
    count--;
    price = pricePerOne * count;
  }
}
