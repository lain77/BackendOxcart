package com.oxcart.project.controller;

import com.oxcart.project.dto.request.AircraftDTORequest;
import com.oxcart.project.dto.response.AircraftDTOResponse;
import com.oxcart.project.entity.Aircraft;
import com.oxcart.project.service.AircraftService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/aircraft")
@Tag(name = "Aircraft", description = "API para gerenciamento de aeronaves")
public class AircraftController {

    private final AircraftService aircraftService;

    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar aeronaves", description = "Endpoint para listar todas as aeronaves")
    public ResponseEntity<List<Aircraft>> listarAeronaves() {
        List<Aircraft> aeronaves = aircraftService.listarAeronaves();
        if (aeronaves.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(aeronaves);
    }

    @GetMapping("/{aircraftId}")
    @Operation(summary = "Obter aeronave por ID", description = "Endpoint para obter uma aeronave pelo seu ID")
    public ResponseEntity<Aircraft> obterAircraftPorId(@PathVariable Integer aircraftId) {
        Aircraft aircraft = aircraftService.obterPorId(aircraftId);
        if (aircraft == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(aircraft);
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar nova aeronave", description = "Endpoint para criar uma nova aeronave")
    public ResponseEntity<AircraftDTOResponse> criarAircraft(
            @Valid @RequestBody AircraftDTORequest aircraftRequest) {
        AircraftDTOResponse response = aircraftService.criarAircraft(aircraftRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/atualizar/{aircraftId}")
    @Operation(summary = "Atualizar aeronave", description = "Endpoint para atualizar todos os dados da aeronave")
    public ResponseEntity<AircraftDTOResponse> atualizarAircraft(
            @PathVariable Integer aircraftId,
            @Valid @RequestBody AircraftDTORequest aircraftRequest) {
        AircraftDTOResponse response = aircraftService.atualizarAircraft(aircraftId, aircraftRequest);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/atualizar-status/{aircraftId}")
    @Operation(summary = "Atualizar status da aeronave", description = "Endpoint para atualizar apenas o status da aeronave")
    public ResponseEntity<AircraftDTOResponse> atualizarStatus(
            @PathVariable Integer aircraftId,
            @RequestParam String status) {
        AircraftDTOResponse response = aircraftService.atualizarStatus(aircraftId, status);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/apagar/{aircraftId}")
    @Operation(summary = "Apagar aeronave", description = "Endpoint para apagar uma aeronave pelo ID")
    public ResponseEntity<Void> apagarAircraft(@PathVariable Integer aircraftId) {
        aircraftService.apagarAircraft(aircraftId);
        return ResponseEntity.noContent().build();
    }
}
