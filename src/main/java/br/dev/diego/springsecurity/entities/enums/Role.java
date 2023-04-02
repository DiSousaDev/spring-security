package br.dev.diego.springsecurity.entities.enums;


import static java.lang.String.format;

public enum Role {

    ADMIN(1, "ROLE_ADMIN"),
    USER(2, "ROLE_USER");

    private final int cod;
    private final String descricao;

    Role(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Role toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }
        for (Role t : Role.values()) {
            if (cod.equals(t.getCod())) {
                return t;
            }
        }
        throw new IllegalArgumentException(format("Invalid enum code >>> [%s] ", cod));
    }
}
