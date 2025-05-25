package ru.gb.springbootdemoapp.service;

import org.springframework.stereotype.Service;
import ru.gb.springbootdemoapp.model.Product;
import ru.gb.springbootdemoapp.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public List<Product> getAll() {
    return productRepository.findAll();
  }

  public void save(Product product) {
    productRepository.save(product);
  }

  public Optional<Product> findById(Long id) {
    return productRepository.findById(id);
  }

  public void deleteById(Long id) {
    productRepository.deleteById(id);
  }
}
