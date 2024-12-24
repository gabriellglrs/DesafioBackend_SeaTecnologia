package br.com.sea.tecnologia.desafioBackend.domain.user.dto;

import br.com.sea.tecnologia.desafioBackend.domain.user.entities.Phone;
import br.com.sea.tecnologia.desafioBackend.domain.user.enums.PhoneType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class PhoneDto {
     private Long id;
     @NotBlank(message = "O número do telefone é obrigatório.")
     @Pattern(regexp = "^[0-9]{10,11}$", message = "O número do telefone deve ter entre 10 e 11 dígitos.")

     private String numero;
     @NotNull(message = "O tipo de telefone é obrigatório.")
     private PhoneType phoneType;

     public PhoneDto() {
     }

     public PhoneDto(Phone phone) {
          id = phone.getId();
          phoneType = phone.getPhoneType();
          if (phoneType == PhoneType.CELULAR) {
               numero = "(**)*****-****";
          } else {
               numero = "(**)****-****";
          }

     }

     public Long getId() {
          return id;
     }

     public String getNumero() {
          return numero;
     }

     public PhoneType getPhoneType() {
          return phoneType;
     }


}
