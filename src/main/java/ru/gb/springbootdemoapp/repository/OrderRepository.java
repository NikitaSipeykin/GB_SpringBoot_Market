package ru.gb.springbootdemoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.springbootdemoapp.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
