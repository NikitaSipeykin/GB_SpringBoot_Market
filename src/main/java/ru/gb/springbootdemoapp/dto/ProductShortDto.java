package ru.gb.springbootdemoapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ProductShortDto {

  @NotNull
  @NotEmpty
  private String title;

  @NotNull
  private Float price;

  @NotNull
  private String category;

  private String imageUrl;
}
