package br.com.sea.tecnologia.desafioBackend.dto;

import br.com.sea.tecnologia.desafioBackend.entities.Emaill;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailDto {
     private Long id;

     @NotBlank(message = "O e-mail é obrigatório.")
     @Email(message = "O e-mail deve ter um formato válido.")
     private String email;

     public EmailDto() {
     }

     public EmailDto(String email, Long id) {
          this.email = email;
          this.id = id;
     }

     public EmailDto(Emaill emaill) {
          id = emaill.getId();
          email = emaill.getEmail();
     }

     public String getEmail() {
          return email;
     }

     public Long getId() {
          return id;
     }
}