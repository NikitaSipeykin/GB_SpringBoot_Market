package ru.gb.springbootdemoapp.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.webjars.NotFoundException;
import ru.gb.springbootdemoapp.dto.ProductDto;
import ru.gb.springbootdemoapp.dto.ProductShortDto;
import ru.gb.springbootdemoapp.model.Category;
import ru.gb.springbootdemoapp.model.Product;
import ru.gb.springbootdemoapp.repository.CategoryRepository;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

  @Autowired
  protected CategoryRepository categoryRepository;

  @Mapping(source = "category", target = "category", qualifiedByName = "titleToCategory")
  public abstract Product productShortDtoToProduct(ProductShortDto productShortDto);

  @Mapping(source = "category.title", target = "category")
  public abstract ProductDto productToProductDto(Product product);

  @Named("titleToCategory")
  public Category categoryTitleToCategory(String categoryTitle) {
    return categoryRepository.findByTitle(categoryTitle).orElseThrow(() -> new NotFoundException("Категория не найдена"));
  }
}
