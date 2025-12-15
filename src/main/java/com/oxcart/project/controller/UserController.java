package com.oxcart.project.controller;

import com.oxcart.project.dto.extra.CreateUserDto;
import com.oxcart.project.dto.extra.LoginUserDto;
import com.oxcart.project.dto.extra.RecoveryJwtTokenDto;
import com.oxcart.project.dto.extra.UpdateNameDTO;
// Importações não utilizadas (UserDTORequest, UserDTOResponse, User, List) foram removidas para clareza
import com.oxcart.project.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Tag(name = "Users & Authentication", description = "Endpoints para Login, Cadastro e Gerenciamento de Dados do Usuário.")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // --- 1. AUTENTICAÇÃO E CADASTRO ---

    @Operation(summary = "Realiza o Login e retorna o Token JWT", description = "Endpoint público para autenticação.")
    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenDto> authenticateUser(@RequestBody LoginUserDto loginUserDto) {
        RecoveryJwtTokenDto token = userService.authenticateUser(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @Operation(summary = "Cria um novo usuário no sistema", description = "Endpoint público para registro.")
    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDto createUserDto) {
        userService.createUser(createUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // --- 2. ATUALIZAÇÃO DE DADOS (Requer Autenticação) ---

    @Operation(summary = "Atualiza o nome (Callsign) do usuário logado",
            description = "Extrai o email do Token JWT (Principal) para garantir que apenas o próprio usuário seja editado.")
    @PutMapping("/name") // Usando '/name' (ou '/username') para seguir a convenção REST
    public ResponseEntity<Void> editUsername(
            @RequestBody UpdateNameDTO dto,
            Principal principal // Captura o usuário logado via JWT
    ) {
        // principal.getName() é o email/username injetado pelo Spring Security
        userService.updateName(principal.getName(), dto.name());

        // Retorna 204 No Content (padrão HTTP para PUT/UPDATE sem corpo de retorno)
        return ResponseEntity.noContent().build();
    }

    // --- 3. TESTES DE PERMISSÃO ---

    @Operation(summary = "Teste de Autenticação Geral", description = "Requer JWT válido.")
    @GetMapping("/test")
    public ResponseEntity<String> getAuthenticationTest() {
        return ResponseEntity.ok("Autenticado com sucesso");
    }

    @Operation(summary = "Teste de Permissão de Cliente", description = "Requer Role de Cliente.")
    @GetMapping("/test/customer")
    public ResponseEntity<String> getCustomerAuthenticationTest() {
        return ResponseEntity.ok("Cliente autenticado com sucesso");
    }

    @Operation(summary = "Teste de Permissão de Administrador", description = "Requer Role de Administrador.")
    @GetMapping("/test/administrator")
    public ResponseEntity<String> getAdminAuthenticationTest() {
        return ResponseEntity.ok("Administrador autenticado com sucesso");
    }
}