package ru.gb.springbootdemoapp.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="order_items")
@Data
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column(name = "price")
  private Float price;

  @Column(name = "quantity")
  private Integer quantity;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;
}
