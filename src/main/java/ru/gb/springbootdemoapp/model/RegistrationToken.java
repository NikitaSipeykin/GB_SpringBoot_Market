package ru.gb.springbootdemoapp.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "registration_tokens")
@Data
@NoArgsConstructor
public class RegistrationToken {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column(name = "token")
  private String token;

  @Column(name = "expired_at")
  private LocalDateTime expiredAt;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private AppUser appUser;

  public RegistrationToken(String token, LocalDateTime expiredAt, AppUser appUser) {
    this.token = token;
    this.expiredAt = expiredAt;
    this.appUser = appUser;
  }
}
