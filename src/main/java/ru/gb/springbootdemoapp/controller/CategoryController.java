package ru.gb.springbootdemoapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.springbootdemoapp.model.Category;
import ru.gb.springbootdemoapp.repository.CategoryRepository;

@Controller
@RequestMapping("/category")
public class CategoryController {

  private final CategoryRepository categoryRepository;

  public CategoryController(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @GetMapping
  public String getAllCategories(Model model) {
    model.addAttribute("categories", categoryRepository.findAll());
    return "categories";
  }

  @GetMapping
  @RequestMapping("/{id}")
  public String getAllCategories(@PathVariable Long id, Model model) {
    Category category = categoryRepository.findByIdFetchProducts(id);
    model.addAttribute("title", category.getTitle());
    model.addAttribute("products", category.getProducts());
    return "product_list";
  }
}
