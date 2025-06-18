package ru.gb.springbootdemoapp.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.springbootdemoapp.model.AppUser;
import ru.gb.springbootdemoapp.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByEmail(username)
        .map(user -> new User(
                user.getEmail(),
                user.getPassword(),
                user.getEnabled(), true, true, true,
                user.getAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toSet())
            )
        ).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
  }

  @Transactional(readOnly = true)
  public List<AppUser> getActiveManagers() {
    return userRepository.findAllFetchAuthority().stream()
        .filter(AppUser::getEnabled)
        .filter(user -> user.getAuthorities().stream().anyMatch(authority -> authority.getName().equals("ROLE_MANAGER")))
        .collect(Collectors.toList());
  }
}
