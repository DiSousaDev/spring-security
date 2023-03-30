package br.dev.diego.springsecurity.services.impl;

import br.dev.diego.springsecurity.repositories.UserRepository;
import br.dev.diego.springsecurity.services.exceptions.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    public AuthenticationServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username).orElseThrow(() ->
                new EntityNotFoundException(String.format("Entidade login: [%s] não encontrada.", username)));
    }
}
