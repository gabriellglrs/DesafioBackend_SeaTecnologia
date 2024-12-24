package br.com.sea.tecnologia.desafioBackend.domain.user.repositories;

import br.com.sea.tecnologia.desafioBackend.domain.user.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepositories extends JpaRepository<Role, Long> {
}
