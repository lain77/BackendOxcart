package com.oxcart.project.controller;

import com.oxcart.project.entity.Battle;
import com.oxcart.project.service.BattleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/battle")
@Tag(name="Battle", description="API para gerenciamento de battles")
public class BattleController {
    private BattleService battleService;

    public BattleController(BattleService battleService) {
        this.battleService = battleService;
    }

    @GetMapping("/listar")
    @Operation(summary="Listar jogos", description = "Endpoint para listar todos os jogos")
    public ResponseEntity<List<Battle>> listarBattles(){
        return ResponseEntity.ok(battleService.listarBattles());
    }
}
