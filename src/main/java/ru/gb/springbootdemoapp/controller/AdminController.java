package ru.gb.springbootdemoapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.webjars.NotFoundException;
import ru.gb.springbootdemoapp.converter.ProductMapper;
import ru.gb.springbootdemoapp.dto.ProductDto;
import ru.gb.springbootdemoapp.dto.ProductShortDto;
import ru.gb.springbootdemoapp.service.CategoryService;
import ru.gb.springbootdemoapp.service.ProductService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

  private final ProductService productService;
  private final CategoryService categoryService;
  private final ProductMapper productMapper;

  public AdminController(ProductService productService, CategoryService categoryService, ProductMapper productMapper) {
    this.productService = productService;
    this.categoryService = categoryService;
    this.productMapper = productMapper;
  }

  @GetMapping
  public String getAllStudents(Model model) {
    List<ProductDto> students =  productService.getAll().stream()
        .map(productMapper::productToProductDto).collect(Collectors.toList());
    model.addAttribute("products", students);
    return "admin/index";
  }


  @GetMapping("/add")
  public String getStudentAddFrom(Model model) {
    model.addAttribute("productShortDto", new ProductShortDto());
    model.addAttribute("categories", categoryService.getAllTitles());
    return "admin/add_product_form";
  }

  @PostMapping("/add")
  @Transactional
  public String saveStudent(@Valid ProductShortDto productShortDto, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      return "admin/add_product_form";
    }
    try {
      productService.save(productMapper.productShortDtoToProduct(productShortDto));
    } catch (NotFoundException ex) {
      model.addAttribute("notFound", ex);
      return "admin/add_product_form";
    }
    return "redirect:/admin";
  }

  @PostMapping("/delete/{id}")
  public String saveStudent(@PathVariable Long id) {
    productService.deleteById(id);
    return "redirect:/admin";
  }
}
