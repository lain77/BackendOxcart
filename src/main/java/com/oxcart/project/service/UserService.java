package com.oxcart.project.service;

import com.oxcart.project.config.SecurityConfiguration;
import com.oxcart.project.dto.extra.CreateUserDto;
import com.oxcart.project.dto.extra.LoginUserDto;
import com.oxcart.project.dto.extra.RecoveryJwtTokenDto;
import com.oxcart.project.dto.request.UserDTORequest;
import com.oxcart.project.dto.response.UserDTOResponse;
import com.oxcart.project.entity.Role;
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

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Listar todos os usuários
    public List<User> listarUsuarios() {
        return userRepository.findAll();
    }

    // Buscar usuário por ID
    public User listarPorUsuarioId(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    // Buscar usuário por email
    public User buscarPorEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    // Criar novo usuário
    public UserDTOResponse criarUsuario(UserDTORequest request) {
        User user = new User();
        user.setNome(request.getNome());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    // Atualizar usuário completo
    public UserDTOResponse atualizarUsuario(Integer id, UserDTORequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.setNome(request.getNome());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        User updated = userRepository.save(user);
        return toResponse(updated);
    }

    // Apagar usuário
    public void apagarUsuario(Integer id) {
        userRepository.deleteById(id);
    }

    // Conversão para DTO de resposta
    private UserDTOResponse toResponse(User user) {
        UserDTOResponse dto = new UserDTOResponse();
        dto.setId(user.getId());
        dto.setNome(user.getNome());
        dto.setEmail(user.getEmail());
        return dto;
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

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
        role.setName(createUserDto.roleName);

        User newUser = new User();
        newUser.setEmail(createUserDto.email);
        newUser.setPassword(securityConfiguration.passwordEncoder().encode(createUserDto.password));
        newUser.setRoles(List.of(role));

        // Cria um novo usuário com os dados fornecidos
        /*
        User newUser = User.builder()
                .email(createUserDto.email())
                // Codifica a senha do usuário com o algoritmo bcrypt
                .password(securityConfiguration.passwordEncoder().encode(createUserDto.password()))
                // Atribui ao usuário uma permissão específica
                .roles(List.of(Role.builder().name(createUserDto.role()).build()))
                .build();
         */
        // Salva o novo usuário no banco de dados
        userRepository.save(newUser);
    }
}
