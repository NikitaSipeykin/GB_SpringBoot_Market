package ru.gb.springbootdemoapp.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
@Data
public class Authority {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;
  @Column(name = "name")
  private String name;
}
