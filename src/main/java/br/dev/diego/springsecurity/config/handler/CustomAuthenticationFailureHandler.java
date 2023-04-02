package br.dev.diego.springsecurity.config.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class CustomAuthenticationFailureHandler extends AuthorizationAuthenticationHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) {

        if (!response.isCommitted()) {

            status = HttpStatus.UNAUTHORIZED.value();
            error = "Unhautorized";
            errorMessage = "Token invalid or expired";

            responseClient(request, response, status, error, errorMessage);

        }

    }

}
