package com.oxcart.project.controller;

import com.oxcart.project.dto.request.CompanyDTORequest;
import com.oxcart.project.dto.response.CompanyDTOResponse;
import com.oxcart.project.entity.Company;
import com.oxcart.project.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/company")
@Tag(name = "Company", description = "API para gerenciamento de empresas")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar empresas", description = "Endpoint para listar todas as empresas")
    public ResponseEntity<List<Company>> listarEmpresas() {
        List<Company> empresas = companyService.listarEmpresas();
        if (empresas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/{companyId}")
    @Operation(summary = "Obter empresa por ID", description = "Endpoint para obter uma empresa pelo seu ID")
    public ResponseEntity<Company> obterCompanyPorId(@PathVariable Integer companyId) {
        Company company = companyService.obterPorId(companyId);
        if (company == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(company);
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar nova empresa", description = "Endpoint para criar uma nova empresa")
    public ResponseEntity<CompanyDTOResponse> criarCompany(
            @Valid @RequestBody CompanyDTORequest companyRequest) {
        CompanyDTOResponse response = companyService.criarCompany(companyRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/atualizar/{companyId}")
    @Operation(summary = "Atualizar empresa", description = "Endpoint para atualizar todos os dados da empresa")
    public ResponseEntity<CompanyDTOResponse> atualizarCompany(
            @PathVariable Integer companyId,
            @Valid @RequestBody CompanyDTORequest companyRequest) {
        CompanyDTOResponse response = companyService.atualizarCompany(companyId, companyRequest);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/atualizar-status/{companyId}")
    @Operation(summary = "Atualizar status da empresa", description = "Endpoint para atualizar apenas o status da empresa")
    public ResponseEntity<CompanyDTOResponse> atualizarStatus(
            @PathVariable Integer companyId,
            @RequestParam String status) {
        CompanyDTOResponse response = companyService.atualizarStatus(companyId, status);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/apagar/{companyId}")
    @Operation(summary = "Apagar empresa", description = "Endpoint para apagar uma empresa pelo ID")
    public ResponseEntity<Void> deleteCompany(@PathVariable Integer companyId) {
        companyService.deleteCompany(companyId);
        return ResponseEntity.noContent().build();
    }
}
