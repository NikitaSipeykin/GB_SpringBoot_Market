package ru.gb.springbootdemoapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.springbootdemoapp.model.AppUser;
import static ru.gb.springbootdemoapp.model.EmailType.USER_REGISTRATION;
import ru.gb.springbootdemoapp.model.RegistrationToken;
import ru.gb.springbootdemoapp.repository.AuthorityRepository;
import ru.gb.springbootdemoapp.repository.RegistrationTokenRepository;
import ru.gb.springbootdemoapp.repository.UserRepository;

@Service
public class RegistrationService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final AuthorityRepository authorityRepository;
  private final RegistrationTokenRepository registrationTokenRepository;
  private final EmailService emailService;

  public RegistrationService(UserRepository userRepository,
                             BCryptPasswordEncoder bCryptPasswordEncoder,
                             AuthorityRepository authorityRepository,
                             RegistrationTokenRepository registrationTokenRepository,
                             EmailService emailService) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.authorityRepository = authorityRepository;
    this.registrationTokenRepository = registrationTokenRepository;
    this.emailService = emailService;
  }

  @Transactional
  public String sighUp(String email, String password) {
    boolean userExist = userRepository.findByEmail(email).isPresent();
    if (userExist) {
      throw new IllegalStateException("Пользователь уже существует");
    }
    var user = new AppUser();
    user.setEmail(email);
    user.setPassword(bCryptPasswordEncoder.encode(password));
    user.setEnabled(false);
    user.setAuthorities(Set.of(authorityRepository.findByName("ROLE_USER")));
    userRepository.save(user);

    String tokenUid = UUID.randomUUID().toString();
    registrationTokenRepository.save(new RegistrationToken(tokenUid, LocalDateTime.now().plusMinutes(15), user));

    emailService.sendMail(USER_REGISTRATION, Map.of("token", tokenUid), List.of(email));

    return tokenUid;
  }

  @Transactional
  public boolean confirmRegistration(String token) {
    var user = registrationTokenRepository.findUserByToken(LocalDateTime.now(), token);
    if (user.isEmpty()) {
      return false;
    }
    user.ifPresent(u -> u.setEnabled(true));
    return true;
  }
}
