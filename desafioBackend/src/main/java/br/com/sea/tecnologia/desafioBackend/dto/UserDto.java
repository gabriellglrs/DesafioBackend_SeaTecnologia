package br.com.sea.tecnologia.desafioBackend.dto;

import br.com.sea.tecnologia.desafioBackend.entities.Role;
import br.com.sea.tecnologia.desafioBackend.entities.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

public class UserDto {
     private Long id;

     @NotBlank(message = "O nome é obrigatório.")
     @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres.")
     @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "O nome só pode conter letras, números e espaços.")
     private String nome;

     @NotBlank(message = "O CPF é obrigatório.")
     @Pattern(regexp = "^[0-9]{11}$", message = "O CPF deve conter exatamente 11 dígitos numéricos.")
     private String cpf;

     @NotBlank(message = "A senha é obrigatória.")
     @Size(min = 8, max = 20, message = "A senha deve ter entre 8 e 20 caracteres.")
     @Pattern(
             regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,20}$",
             message = "A senha deve conter pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial."
     )
     private String password;

     @Valid
     private AddressDto address;

     @Valid
     private Set<PhoneDto> phones = new HashSet<>();

     @Valid
     private Set<EmailDto> emails = new HashSet<>();

     private Set<RoleDto> roles = new HashSet<>();


     public UserDto() {
     }

     public UserDto(Long id, String nome, String cpf, String password, AddressDto address) {
          this.id = id;
          this.nome = nome;
          this.cpf = cpf;
          this.password = password;
          this.address = address;
     }

     public UserDto(User user) {
          id = user.getId();
          nome = user.getNome();
          cpf = "***-***-***-**";
          password = "********";
          address = new AddressDto(user);

          // Transformando a lista de telefones em PhoneDto
          user.getPhones().forEach(phone -> this.phones.add(new PhoneDto(phone)));

          // Transformando a lista de emails em EmailDto
          user.getEmails().forEach(email -> this.emails.add(new EmailDto(email)));

          user.getRoles().forEach(role -> this.roles.add(new RoleDto(role)));
     }

     public Long getId() {
          return id;
     }

     public String getNome() {
          return nome;
     }

     public String getCpf() {
          return cpf;
     }

     public String getPassword() {
          return password;
     }

     public AddressDto getAddress() {
          return address;
     }

     public Set<PhoneDto> getPhones() {
          return phones;
     }

     public Set<EmailDto> getEmails() {
          return emails;
     }

     public Set<RoleDto> getRoles() {
          return roles;
     }
}