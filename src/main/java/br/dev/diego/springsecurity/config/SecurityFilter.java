package br.dev.diego.springsecurity.config;

import br.dev.diego.springsecurity.records.login.TokenInfo;
import br.dev.diego.springsecurity.services.TokenService;
import br.dev.diego.springsecurity.services.exceptions.TokenVerifyException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(SecurityFilter.class);
    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;

    public SecurityFilter(TokenService tokenService, UserDetailsService userDetailsService) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            TokenInfo tokenInfo = recoverToken(request);
            UserDetails userDetails = getUserDetails(tokenInfo);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
        } catch (TokenVerifyException e) {
            log.info("Endpoint {} - {}", request.getRequestURI(), e.getMessage());
            filterChain.doFilter(request, response);
        }
    }

    private UserDetails getUserDetails(TokenInfo tokenInfo) {
        String subject = tokenService.getSubject(tokenInfo.token());
        return userDetailsService.loadUserByUsername(subject);
    }

    private TokenInfo recoverToken(HttpServletRequest request) { // Método recupera o token passado no header da request.
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return new TokenInfo(authorizationHeader.replace("Bearer ", ""));
        }
        throw new TokenVerifyException("Authorization header token not found.");
    }

}
