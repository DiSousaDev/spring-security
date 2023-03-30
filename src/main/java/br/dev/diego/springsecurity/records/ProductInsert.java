package br.dev.diego.springsecurity.records;

public record ProductInsert(
        String name,
        Double price,
        String description
) {
}
