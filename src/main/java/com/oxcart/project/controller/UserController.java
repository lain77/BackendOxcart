package com.oxcart.project.controller;



import com.oxcart.project.dto.extra.CreateUserDto;
import com.oxcart.project.dto.extra.LoginUserDto;
import com.oxcart.project.dto.extra.RecoveryJwtTokenDto;
import com.oxcart.project.dto.request.UserDTORequest;
import com.oxcart.project.dto.response.UserDTOResponse;
import com.oxcart.project.entity.User;
import com.oxcart.project.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
@Tag(name = "User", description = "API para gerenciamento de usuários")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar usuários", description = "Endpoint para listar todos os usuários")
    public ResponseEntity<List<User>> listarUsuarios() {
        return ResponseEntity.ok(userService.listarUsuarios());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter usuário por ID", description = "Endpoint para obter usuário pelo seu ID")
    public ResponseEntity<User> obterUsuarioPorId(@PathVariable Integer id) {
        User usuario = userService.listarPorUsuarioId(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Obter usuário por Email", description = "Endpoint para obter usuário pelo email")
    public ResponseEntity<User> obterUsuarioPorEmail(@PathVariable String email) {
        User usuario = userService.buscarPorEmail(email);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar novo usuário", description = "Endpoint para criar um novo usuário")
    public ResponseEntity<UserDTOResponse> criarUsuario(@Valid @RequestBody UserDTORequest request) {
        return ResponseEntity.ok(userService.criarUsuario(request));
    }

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar usuário", description = "Endpoint para atualizar todos os dados do usuário")
    public ResponseEntity<UserDTOResponse> atualizarUsuario(
            @PathVariable Integer id,
            @Valid @RequestBody UserDTORequest request) {
        return ResponseEntity.ok(userService.atualizarUsuario(id, request));
    }

    @DeleteMapping("/apagar/{id}")
    @Operation(summary = "Apagar usuário", description = "Endpoint para apagar um usuário pelo ID")
    public ResponseEntity<Void> apagarUsuario(@PathVariable Integer id) {
        userService.apagarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenDto> authenticateUser(@RequestBody LoginUserDto loginUserDto) {
        RecoveryJwtTokenDto token = userService.authenticateUser(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDto createUserDto) {
        userService.createUser(createUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public ResponseEntity<String> getAuthenticationTest() {
        return new ResponseEntity<>("Autenticado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/test/customer")
    public ResponseEntity<String> getCustomerAuthenticationTest() {
        return new ResponseEntity<>("Cliente autenticado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/test/administrator")
    public ResponseEntity<String> getAdminAuthenticationTest() {
        return new ResponseEntity<>("Administrador autenticado com sucesso", HttpStatus.OK);
    }
}
