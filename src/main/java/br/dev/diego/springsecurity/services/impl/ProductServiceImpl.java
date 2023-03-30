package br.dev.diego.springsecurity.services.impl;

import br.dev.diego.springsecurity.entities.Product;
import br.dev.diego.springsecurity.records.ProductDetail;
import br.dev.diego.springsecurity.records.ProductInsert;
import br.dev.diego.springsecurity.repositories.ProductRepository;
import br.dev.diego.springsecurity.services.ProductService;
import br.dev.diego.springsecurity.services.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDetail> buscarTodos() {
        return repository.findAll().stream().map(ProductDetail::new).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDetail buscarPorId(Long id) {
        return new ProductDetail(repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Entidade %s não encontrada.", id))));
    }

    @Override
    @Transactional
    public ProductDetail salvar(ProductInsert product) {
        Product entity = repository.save(new Product(product));
        return new ProductDetail(entity);
    }

}
