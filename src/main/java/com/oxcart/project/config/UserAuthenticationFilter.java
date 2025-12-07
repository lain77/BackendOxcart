package com.oxcart.project.config;

import com.oxcart.project.entity.User;
import com.oxcart.project.repository.UserRepository;
import com.oxcart.project.service.JwtTokenService;
import com.oxcart.project.service.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = recoveryToken(request); // Tenta recuperar o token

        if (token != null) {
            try {
                String subject = jwtTokenService.getSubjectFromToken(token);
                // Dica: Usar findByEmail retorna Optional, trate caso não exista
                Optional<User> userOptional = userRepository.findByEmail(subject);

                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    UserDetailsImpl userDetails = new UserDetailsImpl(user);

                    Authentication authentication =
                            new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                // Se o token for inválido, apenas logamos (opcional) e não autenticamos.
                // O Spring Security vai barrar o acesso lá na frente se a rota for privada.
                System.out.println("Token inválido ou erro na autenticação: " + e.getMessage());
            }
        }

        // SEMPRE continua o filtro.
        // Se a rota for pública, passa.
        // Se for privada e não tiver user no Contexto, o Spring Security lança 403.
        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}