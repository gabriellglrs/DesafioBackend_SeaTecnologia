package br.com.sea.tecnologia.desafioBackend.dto;

import br.com.sea.tecnologia.desafioBackend.entities.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AddressDto {
     private Long id;
     @NotBlank(message = "O CEP é obrigatório.")
     @Pattern(regexp = "^[0-9]{5}-[0-9]{3}$", message = "O CEP deve ser no formato 00000-000.")
     private String cep;

     @NotBlank(message = "O logradouro é obrigatório.")
     @Size(min = 3, max = 100, message = "O logradouro deve ter entre 3 e 100 caracteres.")
     private String logradouro;

     @NotBlank(message = "O bairro é obrigatório.")
     private String bairro;

     @NotBlank(message = "A cidade é obrigatória.")
     private String cidade;

     @NotBlank(message = "O estado (UF) é obrigatório.")
     @Size(min = 2, max = 2, message = "A UF deve ter exatamente 2 caracteres.")
     private String uf;

     private String complemento; // Complemento não é obrigatório;

     public AddressDto() {
     }

     public AddressDto(User user) {
          id = user.getAddress().getId();
          cep = "*****-***";
          logradouro = user.getAddress().getLogradouro();
          bairro = user.getAddress().getBairro();
          cidade = user.getAddress().getCidade();
          uf = user.getAddress().getUf();
          complemento = user.getAddress().getComplemento();
     }

     public AddressDto(Long id, String cep, String logradouro, String bairro, String cidade, String uf, String complemento) {
          this.id = id;
          this.cep = cep;
          this.logradouro = logradouro;
          this.bairro = bairro;
          this.cidade = cidade;
          this.uf = uf;
          this.complemento = complemento;
     }

     public Long getId() {
          return id;
     }

     public String getCep() {
          return cep;
     }

     public String getLogradouro() {
          return logradouro;
     }

     public String getBairro() {
          return bairro;
     }

     public String getCidade() {
          return cidade;
     }

     public String getUf() {
          return uf;
     }

     public String getComplemento() {
          return complemento;
     }
}

