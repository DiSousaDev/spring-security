package br.dev.diego.springsecurity.config.handler;

import br.dev.diego.springsecurity.services.exceptions.AuthenticationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public abstract class AuthorizationAuthenticationHandler {

    protected int status;
    protected String error;
    protected String errorMessage;
    private final ObjectMapper objectMapper = new ObjectMapper();

    protected void responseClient(HttpServletRequest request, HttpServletResponse response, int status, String error, String errorMessage) {

        try {
            objectMapper.registerModule(new JavaTimeModule());
            response.setStatus(status);
            response.setContentType("application/json");
            String uri = request.getRequestURI();
            StandardError err = new StandardError(status, error, errorMessage, uri);
            response.getWriter().write(objectMapper.writeValueAsString(err));
        } catch (IOException e) {
            throw new AuthenticationException(e.getMessage());
        }

    }

}
