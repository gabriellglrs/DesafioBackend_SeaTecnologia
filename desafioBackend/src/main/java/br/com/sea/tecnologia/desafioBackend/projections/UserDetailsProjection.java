package br.com.sea.tecnologia.desafioBackend.projections;

public interface UserDetailsProjection {
     String getUsername();
     String getPassword();
     Long getRoleId();
     String getAuthority();
}
