package br.com.sea.tecnologia.desafioBackend.domain.user.repositories;

import br.com.sea.tecnologia.desafioBackend.domain.user.entities.Emaill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Emaill, Long> {

     boolean existsByEmail(String email);
}
