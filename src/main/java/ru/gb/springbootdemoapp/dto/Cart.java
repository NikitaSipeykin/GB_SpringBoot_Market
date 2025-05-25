package ru.gb.springbootdemoapp.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {
  private List<CartItem> items = new ArrayList<>();
  private double price;

  public void addItem(CartItem cartItem){
    items.stream().filter(items -> items.getProductId().equals(cartItem.getProductId())).findFirst()
        .ifPresentOrElse(CartItem::incrementCount, () -> items.add(cartItem));

    recalculate();
  }

  public void removeItem(Long id){
    items.stream().filter(items -> items.getProductId().equals(id)).findFirst()
        .ifPresent(item -> {
          item.decrementCount();
          if (item.getCount() == 0){
            items.remove(item);
          }
        });

    recalculate();
  }

  private void recalculate(){
    price = items.stream().mapToDouble(CartItem::getPrice).sum();
  }
}
