package ru.gb.springbootdemoapp.controller.repository;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.gb.springbootdemoapp.model.Order;
import ru.gb.springbootdemoapp.model.OrderStatus;
import ru.gb.springbootdemoapp.repository.OrderRepository;

@DataJpaTest
public class OrderRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;
  @Autowired
  private OrderRepository orderRepository;

  @Test
  void testGetOrdersWithStatusNew() {
    Order order1 = new Order();
    order1.setOrderStatus(OrderStatus.NEW);
    Order order2 = new Order();
    order2.setOrderStatus(OrderStatus.NEW);
    Order order3 = new Order();
    order3.setOrderStatus(OrderStatus.SHIPPED);

    List.of(order1, order2, order3).forEach(
        order -> {
          order.setPrice(0.);
          order.setContactEmail("");
        }
    );

    entityManager.persist(order1);
    entityManager.persist(order2);
    entityManager.persist(order3);

    List<Order> orders = orderRepository.findAllByOrderStatusEquals(OrderStatus.NEW);

    assertEquals(2, orders.size());
    assertTrue(orders.stream().allMatch(o -> o.getOrderStatus() == OrderStatus.NEW));
  }
}
