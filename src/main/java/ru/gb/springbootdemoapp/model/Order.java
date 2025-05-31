package ru.gb.springbootdemoapp.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column
  private Double price;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private User customer;

  @Column
  private String address;

  @Column
  private String details;

  @Column(name = "contact_email")
  private String contactEmail;

  @Enumerated
  @Column(columnDefinition = "smallint")
  private OrderStatus orderStatus;

  @Enumerated
  @Column(columnDefinition = "smallint")
  private ShippingMethod shippingMethod;

  @Column(name = "creation_time")
  private LocalDateTime creationTime;

  @Column(name = "deliver_time")
  private LocalDateTime deliverTime;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private List<OrderItem> orderItems;
}
