package br.dev.diego.springsecurity.services.exceptions;

public class TokenGenerationException extends RuntimeException {

    public TokenGenerationException(String message) {
        super(message);
    }
}
