package br.dev.diego.springsecurity.services;

import br.dev.diego.springsecurity.entities.User;
import br.dev.diego.springsecurity.records.login.TokenInfo;

public interface TokenService {

    TokenInfo gerarToken(User user);

    String getSubject(String token);
}
