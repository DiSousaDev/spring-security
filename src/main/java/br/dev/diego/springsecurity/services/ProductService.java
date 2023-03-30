package br.dev.diego.springsecurity.services;

import br.dev.diego.springsecurity.records.product.ProductDetail;
import br.dev.diego.springsecurity.records.product.ProductInsert;

import java.util.List;

public interface ProductService {

    List<ProductDetail> buscarTodos();
    ProductDetail buscarPorId(Long id);
    ProductDetail salvar(ProductInsert product);

}
