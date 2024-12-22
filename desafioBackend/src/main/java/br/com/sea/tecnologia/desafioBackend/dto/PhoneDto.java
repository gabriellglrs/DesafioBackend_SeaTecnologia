package br.com.sea.tecnologia.desafioBackend.dto;

import br.com.sea.tecnologia.desafioBackend.entities.Phone;
import br.com.sea.tecnologia.desafioBackend.enums.PhoneType;
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

     public PhoneDto(Long id, String numero, PhoneType phoneType) {
          this.id = id;
          this.numero = numero;
          this.phoneType = phoneType;
     }

     public PhoneDto(Phone phone) {
          id = phone.getId();
          numero = phone.getNumero();
          phoneType = phone.getPhoneType();
     }

     public Long getId() {
          return id;
     }

     public String getNumero() {
          if (numero != null) {
               if (phoneType == PhoneType.CELULAR) {
                    return "(**)*****-****";
               } else {
                    return "(**)****-****";
               }
          }
          return numero;
     }

     public PhoneType getPhoneType() {
          return phoneType;
     }


}
