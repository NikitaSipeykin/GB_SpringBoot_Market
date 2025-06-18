package ru.gb.springbootdemoapp.model;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name="orders")
@Data
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column(name = "price")
  private Double price;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private AppUser customer;

  @Column(name = "contact_email")
  private String contactEmail;

  @Column(name = "details")
  private String details;

  @Column(name = "address")
  private String address;

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
