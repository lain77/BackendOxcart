package com.oxcart.project.service;

import com.oxcart.project.config.SecurityConfiguration;
import com.oxcart.project.dto.extra.CreateUserDto;
import com.oxcart.project.dto.extra.LoginUserDto;
import com.oxcart.project.dto.extra.RecoveryJwtTokenDto;
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

    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }

    public void createUser(CreateUserDto createUserDto) {
        User newUser = new User();
        newUser.setNome(createUserDto.username());
        newUser.setEmail(createUserDto.email());

        // Mantendo o MD5 por causa do limite de 45 caracteres do seu banco
        newUser.setPassword(securityConfiguration.passwordEncoder().encode(createUserDto.password()));

        // --- LÓGICA DE ROLE PROTEGIDA ---
        Role role = new Role();
        try {
            String roleInput = String.valueOf(createUserDto.role());
            if (roleInput == null || roleInput.isEmpty()) {
                role.setName(RoleName.ROLE_USUARIO);
            } else {
                // Se o front mandar "USUARIO", vira "ROLE_USUARIO". Se mandar "ROLE_USUARIO", mantém.
                String roleName = roleInput.startsWith("ROLE_") ? roleInput : "ROLE_" + roleInput;
                role.setName(RoleName.valueOf(roleName));
            }
        } catch (Exception e) {
            // Se der qualquer erro no Enum, define como USUARIO padrão
            role.setName(RoleName.ROLE_USUARIO);
        }

        newUser.setRoles(List.of(role));
        userRepository.save(newUser);
    }

    public void updateName(String email, String newName) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setNome(newName);
        userRepository.save(user);
    }

    public UserStatsDTOResponse getUserStats(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        return new UserStatsDTOResponse(
                (long) user.getId(),
                user.getNome(),
                user.getEmail(),
                0, 0, 0
        );
    }
}