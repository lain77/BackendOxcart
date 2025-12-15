package com.oxcart.project.service;

import com.oxcart.project.config.SecurityConfiguration;
import com.oxcart.project.dto.extra.CreateUserDto;
import com.oxcart.project.dto.extra.LoginUserDto;
import com.oxcart.project.dto.extra.RecoveryJwtTokenDto;
import com.oxcart.project.dto.request.UserDTORequest;
import com.oxcart.project.dto.response.UserDTOResponse;
import com.oxcart.project.dto.response.UserStatsDTOResponse;
import com.oxcart.project.entity.Role;
import com.oxcart.project.entity.RoleName;
import com.oxcart.project.entity.User;
import com.oxcart.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    // Método responsável por autenticar um usuário e retornar um token JWT
    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
        // Cria um objeto de autenticação com o email e a senha do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Gera um token JWT para o usuário autenticado
        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }

    // Método responsável por criar um usuário
    public void createUser(CreateUserDto createUserDto) {
        Role role = new Role();

        // Tenta converter a String (ex: "ROLE_USUARIO") para o Enum.
        // Se o front enviar apenas "USUARIO", você precisa adicionar o "ROLE_" antes.
        try {
            role.setName(RoleName.valueOf(String.valueOf(createUserDto.role())));
        } catch (IllegalArgumentException e) {
            // Fallback caso o front mande sem o prefixo
            role.setName(RoleName.valueOf("ROLE_" + createUserDto.role()));
        }

        User newUser = new User();
        newUser.setNome(createUserDto.username());
        newUser.setEmail(createUserDto.email());
        newUser.setPassword(securityConfiguration.passwordEncoder().encode(createUserDto.password()));
        newUser.setRoles(List.of(role));

        userRepository.save(newUser);
    }

    public void updateName(String email, String newName) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found via token credential"));

        // 2. Atualiza o nome (usando setNome() conforme seu código anterior)
        user.setNome(newName);

        // 3. Salva no banco
        userRepository.save(user);
    }

    public UserStatsDTOResponse getUserStats(String email) {
        // 1. Busca o usuário no banco
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        // 2. Retorna o DTO preenchido
        // Nota: Certifique-se que sua entidade User tem o método getNome() ou getName()
        return new UserStatsDTOResponse(
                Long.valueOf(user.getId()), // Garante que seja Long
                user.getNome(),             // O nome que será exibido no App
                user.getEmail(),
                0, // total_flights (placeholder)
                0, // aircraft_count (placeholder)
                0  // credits (placeholder)
        );
    }
}
