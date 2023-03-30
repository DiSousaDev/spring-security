package br.dev.diego.springsecurity.records.product;

public record ProductInsert(
        String name,
        Double price,
        String description
) {
}
