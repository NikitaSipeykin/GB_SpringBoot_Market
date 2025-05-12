package ru.gb.springbootdemoapp.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column(name = "login")
  private String login;

  @Column(name = "password")
  private String password;

  @ManyToMany
  @JoinTable(
      name = "users_authorities",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "authority_id")
  )
  private Set<Authority> authorities;
}
