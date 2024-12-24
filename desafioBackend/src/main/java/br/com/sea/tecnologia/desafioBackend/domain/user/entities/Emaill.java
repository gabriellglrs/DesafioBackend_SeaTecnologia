package br.com.sea.tecnologia.desafioBackend.domain.user.entities;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name = "tb_email")
public class Emaill {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     private String email;

     @ManyToOne
     @JoinColumn(name = "client_id")
     private User client;

     public Emaill() {
     }

     public Emaill(Long id, String email, User client) {
          this.id = id;
          this.email = email;
          this.client = client;
     }

     public Long getId() {
          return id;
     }

     public void setId(Long id) {
          this.id = id;
     }

     public String getEmail() {
          return email;
     }

     public void setEmail(String email) {
          this.email = email;
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
          Emaill emaill = (Emaill) o;
          return Objects.equals(id, emaill.id);
     }

     @Override
     public int hashCode() {
          return Objects.hashCode(id);
     }
}
