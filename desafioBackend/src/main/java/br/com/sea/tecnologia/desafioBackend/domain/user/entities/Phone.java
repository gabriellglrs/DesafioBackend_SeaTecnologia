package br.com.sea.tecnologia.desafioBackend.domain.user.entities;

import br.com.sea.tecnologia.desafioBackend.domain.user.enums.PhoneType;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tb_phone")
public class Phone {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     private String numero;
     private PhoneType phoneType;

     @ManyToOne
     @JoinColumn(name = "client_id")
     private User client;

     public Phone() {
     }

     public Phone(Long id, String numero, PhoneType phoneType, User client) {
          this.id = id;
          this.numero = numero;
          this.phoneType = phoneType;
          this.client = client;
     }

     public Long getId() {
          return id;
     }

     public void setId(Long id) {
          this.id = id;
     }

     public String getNumero() {
          return numero;
     }

     public void setNumero(String numero) {
          this.numero = numero;
     }

     public PhoneType getPhoneType() {
          return phoneType;
     }

     public void setPhoneType(PhoneType phoneType) {
          this.phoneType = phoneType;
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
          Phone phone = (Phone) o;
          return Objects.equals(numero, phone.numero);
     }

     @Override
     public int hashCode() {
          return Objects.hashCode(numero);
     }
}
