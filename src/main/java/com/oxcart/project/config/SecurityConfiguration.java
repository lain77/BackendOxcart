package com.oxcart.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;

    // ROTAS PÚBLICAS (Sem /oxcart)
    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "/users/login",
            "/users",
            "/api/users/criar",
            "/h2-console/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/api/aircraft/listar"
    };

    // ROTAS DE USUARIO COMUM
    public static final String [] ENDPOINTS_CUSTOMER = {
//            "/api/aircraft/listar" // Exemplo
    };

    // ROTAS DE ADMINISTRADOR (Lider Comite)
    public static final String [] ENDPOINTS_ADMIN = {
            "/users/test/administrator",
            "/api/aircraft/criar",
            "/api/aircraft/apagar/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                        // hasRole("LIDERCOMITE") procura por "ROLE_LIDERCOMITE" no UserDetails
                        .requestMatchers(ENDPOINTS_ADMIN).hasRole("LIDERCOMITE")
                        .requestMatchers(ENDPOINTS_CUSTOMER).hasRole("USUARIO")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //BASICAMENTE O PROFESSOR BOTOU 45 CARACTERES NO MEU USER_PASSWORD ENTÃO O BCrypt É GRANDE DEMAIS PARA SALVAR
    //QUANDO ELE MUDAR, APAGUE O MÉTODO QUE ESTÁ EMBAIXO DESTE E DESCOMENTE O METODO ORIGINAL

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Implementação manual de MD5 para caber na coluna de 45 caracteres do banco
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return md5(rawPassword.toString());
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                // Verifica se a senha digitada (hashada) bate com a do banco
                return md5(rawPassword.toString()).equals(encodedPassword);
            }

            // Função auxiliar para gerar o Hash MD5 (32 caracteres)
            private String md5(String s) {
                try {
                    java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
                    byte[] array = md.digest(s.getBytes());
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < array.length; ++i) {
                        sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
                    }
                    return sb.toString();
                } catch (java.security.NoSuchAlgorithmException e) {
                    throw new RuntimeException("Erro ao gerar MD5", e);
                }
            }
        };
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Libera tudo
                        .allowedOrigins("*") // Libera para qualquer origem
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT");
            }
        };
    }
}