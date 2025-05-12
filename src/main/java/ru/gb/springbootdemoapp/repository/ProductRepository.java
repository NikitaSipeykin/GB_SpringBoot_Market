package ru.gb.springbootdemoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gb.springbootdemoapp.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  @Query("select p from Product p left join fetch p.category")
  List<Product> findAll();
}
