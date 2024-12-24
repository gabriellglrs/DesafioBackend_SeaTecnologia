package br.com.sea.tecnologia.desafioBackend.domain.user.entities;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "tb_user")
public class User implements UserDetails {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     private String nome;

     private String cpf;

     private String password;

     @OneToOne(cascade = CascadeType.ALL)
     @JoinColumn(name = "address_id")
     private Address address;

     @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
     private Set<Phone> phones = new HashSet<>();

     @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
     private Set<Emaill> emails = new HashSet<>();

     @ManyToMany
     @JoinTable(
             name = "tb_user_role",
             joinColumns = @JoinColumn(name = "user_id"),
             inverseJoinColumns = @JoinColumn(name = "role_id")
     )
     private Set<Role> roles = new HashSet<>();

     public User() {
     }

     public User(Long id, String nome, String cpf, String password, Address address) {
          this.id = id;
          this.nome = nome;
          this.cpf = cpf;
          this.password = password;
          this.address = address;
     }


     public Long getId() {
          return id;
     }

     public void setId(Long id) {
          this.id = id;
     }

     public String getNome() {
          return nome;
     }

     public void setNome(String nome) {
          this.nome = nome;
     }

     public String getCpf() {
          return cpf;
     }

     public void setCpf(String cpf) {
          this.cpf = cpf;
     }

     @Override
     public Collection<? extends GrantedAuthority> getAuthorities() {
          return roles;
     }

     public String getPassword() {
          return password;
     }

     @Override
     public String getUsername() {
          return cpf;
     }

     @Override
     public boolean isAccountNonExpired() {
          return true;
     }

     @Override
     public boolean isAccountNonLocked() {
          return true;
     }

     @Override
     public boolean isCredentialsNonExpired() {
          return true;
     }

     @Override
     public boolean isEnabled() {
          return true;
     }

     public void setPassword(String password) {
          this.password = password;
     }

     public Address getAddress() {
          return address;
     }

     public void setAddress(Address address) {
          this.address = address;
     }

     public Set<Phone> getPhones() {
          return phones;
     }

     public Set<Emaill> getEmails() {
          return emails;
     }

     public void setPhones(Set<Phone> phones) {
          this.phones = phones;
     }

     public void setEmails(Set<Emaill> emails) {
          this.emails = emails;
     }

     public void setRoles(Set<Role> roles) {
          this.roles = roles;
     }

     public Set<Role> getRoles() {
          return roles;
     }

     public void addRole(Role role) {
          roles.add(role);
     }

     public boolean hasRole(String roleName) {
          for (Role role : roles) {
               if (role.getAuthority().equals(roleName)) {
                    return true;
               }
          }
          return false;
     }

     @Override
     public boolean equals(Object o) {
          if (o == null || getClass() != o.getClass()) return false;
          User user = (User) o;
          return Objects.equals(cpf, user.cpf) && Objects.equals(password, user.password);
     }

     @Override
     public int hashCode() {
          return Objects.hash(cpf, password);
     }
}
