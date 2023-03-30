package br.dev.diego.springsecurity.records.product;

import br.dev.diego.springsecurity.entities.Product;

public record ProductDetail(
        Long id,
        String name,
        Double price,
        String description
) {
    public ProductDetail(Product entity) {
        this(entity.getId(), entity.getName(), entity.getPrice(), entity.getDescription());
    }
}
