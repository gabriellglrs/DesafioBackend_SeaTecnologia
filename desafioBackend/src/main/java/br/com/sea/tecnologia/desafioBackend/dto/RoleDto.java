package br.com.sea.tecnologia.desafioBackend.dto;

import br.com.sea.tecnologia.desafioBackend.entities.Role;
import br.com.sea.tecnologia.desafioBackend.enums.RoleType;

public class RoleDto {
     private Long id;
     private RoleType authority;

     public RoleDto() {
     }

     public RoleDto(Role role) {
          id = role.getId();
          authority = role.getAuthority();
     }

     public Long getId() {
          return id;
     }

     public RoleType getAuthority() {
          return authority;
     }
}
