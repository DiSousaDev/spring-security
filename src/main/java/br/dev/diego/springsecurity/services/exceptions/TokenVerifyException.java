package br.dev.diego.springsecurity.services.exceptions;

public class TokenVerifyException extends RuntimeException {

    public TokenVerifyException(String message) {
        super(message);
    }
}
