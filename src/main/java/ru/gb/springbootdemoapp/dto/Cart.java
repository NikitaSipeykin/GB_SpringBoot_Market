package ru.gb.springbootdemoapp.dto;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;

@Data
@RedisHash(value = "Cart", timeToLive = 200)
public class Cart {
  private String id;
  private List<CartItem> items = new ArrayList<>();
  private double price;

  public Cart(String id) {
    this.id = id;
  }

  public Cart() {
  }

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
