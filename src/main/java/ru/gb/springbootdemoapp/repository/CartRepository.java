package ru.gb.springbootdemoapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.gb.springbootdemoapp.dto.Cart;

@Repository
public interface CartRepository extends CrudRepository<Cart, String> {
}
