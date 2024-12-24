package br.com.sea.tecnologia.desafioBackend.domain.user.repositories;

import br.com.sea.tecnologia.desafioBackend.domain.user.entities.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {

     boolean existsByNumero(String numero);
}
