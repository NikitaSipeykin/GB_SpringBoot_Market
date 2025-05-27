package ru.gb.springbootdemoapp.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Table()
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;
}
