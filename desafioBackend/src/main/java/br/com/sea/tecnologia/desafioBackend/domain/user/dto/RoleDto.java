package br.com.sea.tecnologia.desafioBackend.domain.user.dto;

import br.com.sea.tecnologia.desafioBackend.domain.user.entities.Role;
import br.com.sea.tecnologia.desafioBackend.domain.user.enums.RoleType;

public class RoleDto {
     private Long id;
     private RoleType authority;

     public RoleDto() {
     }

     public RoleDto(Role role) {
          id = role.getId();
          this.authority = role.getAuthority() != null ? RoleType.valueOf(role.getAuthority()) : null;
     }

     public Long getId() {
          return id;
     }

     public RoleType getAuthority() {
          return authority;
     }
}
