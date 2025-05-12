package ru.gb.springbootdemoapp.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="categories")
@Data
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column
  private String title;

  @OneToMany(mappedBy = "category")
  private List<Product> products;
}
