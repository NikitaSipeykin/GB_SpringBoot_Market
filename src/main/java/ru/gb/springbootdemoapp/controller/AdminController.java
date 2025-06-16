package ru.gb.springbootdemoapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import ru.gb.springbootdemoapp.converter.ProductMapper;
import ru.gb.springbootdemoapp.dto.ProductDto;
import ru.gb.springbootdemoapp.dto.ProductShortDto;
import ru.gb.springbootdemoapp.service.CategoryService;
import ru.gb.springbootdemoapp.service.ProductService;
import ru.gb.springbootdemoapp.service.StorageService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

  private final ProductService productService;
  private final CategoryService categoryService;
  private final ProductMapper productMapper;
  private final StorageService storageService;

  public AdminController(ProductService productService, CategoryService categoryService, ProductMapper productMapper, StorageService storageService) {
    this.productService = productService;
    this.categoryService = categoryService;
    this.productMapper = productMapper;
    this.storageService = storageService;
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
  public String saveStudent(@Valid ProductShortDto productShortDto, @RequestParam MultipartFile image,
                            BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      return "admin/add_product_form";
    }
    try {
      storageService.store(image);
      productShortDto.setImageUrl("/media/" + image.getOriginalFilename());
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
