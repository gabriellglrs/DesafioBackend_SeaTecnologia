package br.com.sea.tecnologia.desafioBackend.domain.product.repositories;

import br.com.sea.tecnologia.desafioBackend.domain.product.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
