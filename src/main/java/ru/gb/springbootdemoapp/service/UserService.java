package ru.gb.springbootdemoapp.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.springbootdemoapp.model.RegistrationToken;
import ru.gb.springbootdemoapp.repository.AuthorityRepository;
import ru.gb.springbootdemoapp.repository.RegistrationTokenRepository;
import ru.gb.springbootdemoapp.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final AuthorityRepository authorityRepository;
  private final RegistrationTokenRepository registrationTokenRepository;
  private final EmailService emailService;

  public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
                     AuthorityRepository authorityRepository, RegistrationTokenRepository registrationTokenRepository,
                     EmailService emailService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.authorityRepository = authorityRepository;
    this.registrationTokenRepository = registrationTokenRepository;
    this.emailService = emailService;
  }

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByEmail(username)
        .map(user -> new User(
            user.getEmail(),
            user.getPassword(),
            user.getEnabled(), true, true, true,
            user.getAuthorities().stream().map(
                authority -> new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toSet())
            )
        ).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
  }

  //Todo: RegisterService
  @Transactional
  public String sighUp(String email, String password) {
    boolean userExist = userRepository.findByEmail(email).isPresent();
    if (userExist){
      throw new IllegalStateException("User already exist");
    }
    var user = new ru.gb.springbootdemoapp.model.User();
    user.setEmail(email);
    user.setPassword(passwordEncoder.encode(password));
    user.setEnabled(false);
    user.setAuthorities(Set.of(authorityRepository.findByName("ROLE_USER")));
    userRepository.save(user);

    String tokenUid = UUID.randomUUID().toString();
    registrationTokenRepository.save(new RegistrationToken(tokenUid, LocalDateTime.now().plusMinutes(15), user));

    emailService.sendVerificationLink(email, tokenUid);

    return tokenUid;
  }

  @Transactional
  public boolean confirmRegistration(String token){
    var user = registrationTokenRepository.findUserByToken(LocalDateTime.now(), token);
    if (user.isEmpty()){
      return false;
    }
    user.ifPresent(user1 -> user1.setEnabled(true));
    return false;
  }
}
