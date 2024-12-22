package br.com.sea.tecnologia.desafioBackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductDto {
     @NotBlank(message = "O nome do produto é obrigatório.")
     @Size(min = 3, max = 100, message = "O nome do produto deve ter entre 3 e 100 caracteres.")
     private String name;

     private String description;

     @Positive(message = "O preço deve ser um valor maior que 0.")
     private Double price;

     public ProductDto() {
     }

     public ProductDto(String name, String description, Double price) {
          this.name = name;
          this.description = description;
          this.price = price;
     }

     public String getName() {
          return name;
     }

     public String getDescription() {
          return description;
     }

     public Double getPrice() {
          return price;
     }
}
