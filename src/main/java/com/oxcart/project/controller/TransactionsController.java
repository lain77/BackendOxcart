package com.oxcart.project.controller;

import com.oxcart.project.dto.request.TransactionDTORequest;
import com.oxcart.project.entity.Transactions;
import com.oxcart.project.service.TransactionsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/transactions")
@Tag(name="Transactions", description="API para gerenciamento de transações")
public class TransactionsController {

    private TransactionsService transactionsService;

    public TransactionsController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @GetMapping("/listar")
    @Operation(summary="Listar transações", description = "Endpoint para listar todos as transações")
    public ResponseEntity<List<Transactions>> listarTransacoes(){
        return ResponseEntity.ok(transactionsService.listarTransacoes());
    }
    @GetMapping("/{transactionId}")
    @Operation(summary = "Obter transação por ID", description = "Endpoint para obter uma transação por ID")
    public ResponseEntity<Transactions> obterTransactionPorId(@PathVariable Integer transactionId){
        Transactions transactions = transactionsService.obterPorId(transactionId);
        if (transactions == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transactions);
    }
    @PostMapping("/criar")
    @Operation(summary = "Criar nova transação", description = "Endpoint para criar uma nova transação")
    public ResponseEntity<Transactions> criarTransaction(
            @Valid @RequestBody TransactionDTORequest transactionRequest) {
        Transactions response = transactionsService.criarTransaction(transactionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
