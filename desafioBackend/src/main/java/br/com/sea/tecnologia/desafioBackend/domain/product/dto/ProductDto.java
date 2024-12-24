package br.com.sea.tecnologia.desafioBackend.domain.product.dto;

import br.com.sea.tecnologia.desafioBackend.domain.product.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductDto {
     private Long id;
     @NotBlank(message = "O nome do produto é obrigatório.")
     @Size(min = 3, max = 100, message = "O nome do produto deve ter entre 3 e 100 caracteres.")
     private String name;

     private String description;

     @Positive(message = "O preço deve ser um valor maior que 0.")
     private Double price;

     public ProductDto() {
     }

     public ProductDto(Product product) {
          id = product.getId();
          name = product.getName();
          description = product.getDescription();
          price = product.getPrice();
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

     public Long getId() {
          return id;
     }
}
