package ru.gb.springbootdemoapp.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ProductShortDtoTest {

  @Autowired
  private JacksonTester<ProductShortDto> json;

  @Test
  void jsonTest() throws IOException {
    ProductShortDto dto = new ProductShortDto();
    dto.setPrice(77.f);
    dto.setCategory("test category");
    dto.setTitle("test title");

    JsonContent<ProductShortDto> jsonDto = json.write(dto);

    assertThat(jsonDto).extractingJsonPathStringValue("@.title").isEqualTo("test title");

    assertThat(jsonDto)
        .extractingJsonPathStringValue("@.category")
        .isEqualTo("test category");
  }
}
