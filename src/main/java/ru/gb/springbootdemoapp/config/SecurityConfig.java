package ru.gb.springbootdemoapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.gb.springbootdemoapp.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserService userService;

  public SecurityConfig(UserService userService) {
    this.userService = userService;
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(new BCryptPasswordEncoder());
    provider.setUserDetailsService(userService);
    return provider;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
          .antMatchers("/admin/**").hasRole( "ADMIN")
        .and()
          .formLogin()
          .loginPage("/login")
        .and()
          .logout()
          .logoutSuccessUrl("/");

    // для консольи h2 - так не делают в продакшене
    http.csrf().disable()
        .headers().frameOptions().disable();
  }
}
