package ru.gb.springbootdemoapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void testMainPageAllowed() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().isOk());
  }

  @Test
  void testAdminAnonimusTest() throws Exception {
    mockMvc.perform(get("/admin"))
        .andExpect(redirectedUrlPattern("**/login"));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void testAdminAdminTest() throws Exception {
    mockMvc.perform(get("/admin"))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "USER")
  void testAdminUserTest() throws Exception {
    mockMvc.perform(get("/admin"))
        .andExpect(status().isForbidden());
  }
}
