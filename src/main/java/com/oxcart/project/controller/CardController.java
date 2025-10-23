package com.oxcart.project.controller;

import com.oxcart.project.entity.Card;
import com.oxcart.project.service.CardService;
import com.oxcart.project.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/card")
@Tag(name = "Card", description = "API para gerenciamento de cartas")
public class CardController {

    private final CompanyService companyService;
    private CardService cardService;

    public CardController(CardService cardService, CompanyService companyService) {
        this.cardService = cardService;
        this.companyService = companyService;
    }

    @GetMapping("/listar")
    @Operation(summary="Listar cartas", description = "Endpoint para listar todos as cartas")
    public ResponseEntity<List<Card>> listarCards(){
        return ResponseEntity.ok(cardService.listarCartas());
    }
}
