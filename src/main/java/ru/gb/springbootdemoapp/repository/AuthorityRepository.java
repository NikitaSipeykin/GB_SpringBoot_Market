package ru.gb.springbootdemoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.gb.springbootdemoapp.model.Authority;
import ru.gb.springbootdemoapp.model.OrderStatus;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

  Authority findByName(String name);
}
