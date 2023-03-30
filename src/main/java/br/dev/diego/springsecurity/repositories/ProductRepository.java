package br.dev.diego.springsecurity.repositories;

import br.dev.diego.springsecurity.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
