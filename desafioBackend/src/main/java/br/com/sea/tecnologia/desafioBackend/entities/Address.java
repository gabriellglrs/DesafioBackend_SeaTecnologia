package br.com.sea.tecnologia.desafioBackend.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tb_address")
public class Address {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     private String cep;
     private String logradouro;
     private String bairro;
     private String cidade;
     private String uf;
     private String complemento;

     @OneToOne(mappedBy = "address")
     private User client;

     public Address() {
     }

     public Address(Long id, String cep, String logradouro, String bairro, String cidade, String uf, String complemento, User client) {
          this.id = id;
          this.cep = cep;
          this.logradouro = logradouro;
          this.bairro = bairro;
          this.cidade = cidade;
          this.uf = uf;
          this.complemento = complemento;
          this.client = client;
     }

     public Long getId() {
          return id;
     }

     public void setId(Long id) {
          this.id = id;
     }

     public String getCep() {
          return cep;
     }

     public void setCep(String cep) {
          this.cep = cep;
     }

     public String getLogradouro() {
          return logradouro;
     }

     public void setLogradouro(String logradouro) {
          this.logradouro = logradouro;
     }

     public String getBairro() {
          return bairro;
     }

     public void setBairro(String bairro) {
          this.bairro = bairro;
     }

     public String getCidade() {
          return cidade;
     }

     public void setCidade(String cidade) {
          this.cidade = cidade;
     }

     public String getUf() {
          return uf;
     }

     public void setUf(String uf) {
          this.uf = uf;
     }

     public String getComplemento() {
          return complemento;
     }

     public void setComplemento(String complemento) {
          this.complemento = complemento;
     }

     public User getClient() {
          return client;
     }

     public void setClient(User client) {
          this.client = client;
     }

     @Override
     public boolean equals(Object o) {
          if (o == null || getClass() != o.getClass()) return false;
          Address address = (Address) o;
          return Objects.equals(id, address.id);
     }

     @Override
     public int hashCode() {
          return Objects.hashCode(id);
     }
}
