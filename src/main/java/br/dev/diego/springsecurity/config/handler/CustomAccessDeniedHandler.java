package br.dev.diego.springsecurity.config.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler extends AuthorizationAuthenticationHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {

        if (!response.isCommitted()) {

            status = HttpStatus.FORBIDDEN.value();
            error = "Forbidden";
            errorMessage = "Access Denied";

            responseClient(request, response, status, error, errorMessage);

        }

    }

}
