package ru.gb.springbootdemoapp.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="products")
@Data
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column
  private String title;

  @Column
  private Float price;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;
}
