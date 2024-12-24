package br.com.sea.tecnologia.desafioBackend.domain.user.entities;

import br.com.sea.tecnologia.desafioBackend.domain.user.enums.RoleType;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_role")
public class Role implements GrantedAuthority {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     @Enumerated(EnumType.STRING)
     private RoleType authority;

     @ManyToMany(mappedBy = "roles")
     private Set<User> users = new HashSet<>();

     public Role() {
     }

     public Role(Long id, RoleType authority) {
          this.id = id;
          this.authority = authority;
     }

     public Long getId() {
          return id;
     }

     public void setId(Long id) {
          this.id = id;
     }


     public void setAuthority(RoleType authority) {
          this.authority = authority;
     }

     public Set<User> getUsers() {
          return users;
     }

     @Override
     public String getAuthority() {
          return authority.name();
     }

     @Override
     public boolean equals(Object o) {
          if (o == null || getClass() != o.getClass()) return false;
          Role role = (Role) o;
          return Objects.equals(id, role.id) && authority == role.authority;
     }

     @Override
     public int hashCode() {
          return Objects.hash(id, authority);
     }
}
