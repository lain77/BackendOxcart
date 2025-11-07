package com.oxcart.project.service;

import com.oxcart.project.dto.request.AircraftDTORequest;
import com.oxcart.project.dto.response.AircraftDTOResponse;
import com.oxcart.project.entity.Aircraft;
import com.oxcart.project.entity.Company;
import com.oxcart.project.entity.User;
import com.oxcart.project.repository.AircraftRepository;
import com.oxcart.project.repository.CompanyRepository;
import com.oxcart.project.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AircraftService {

    private final AircraftRepository aircraftRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public AircraftService(AircraftRepository aircraftRepository,
                           UserRepository userRepository,
                           CompanyRepository companyRepository) {
        this.aircraftRepository = aircraftRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }

    public List<Aircraft> listarAeronaves() {
        return aircraftRepository.findAll();
    }

    public Aircraft obterPorId(Integer id) {
        return aircraftRepository.findById(id).orElse(null);
    }

    public AircraftDTOResponse criarAircraft(AircraftDTORequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        Aircraft aircraft = new Aircraft();
        aircraft.setNome(request.getNome());
        aircraft.setRole(request.getRole());
        aircraft.setNatoName(request.getNatoName());
        aircraft.setDescription(request.getDescription());
        aircraft.setCountry(request.getCountry());
        aircraft.setUser(user);
        aircraft.setCompany(company);

        Aircraft saved = aircraftRepository.save(aircraft);

        return toResponse(saved);
    }

    public AircraftDTOResponse atualizarAircraft(Integer id, AircraftDTORequest request) {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aeronave não encontrada"));

        aircraft.setNome(request.getNome());
        aircraft.setRole(request.getRole());
        aircraft.setNatoName(request.getNatoName());
        aircraft.setDescription(request.getDescription());
        aircraft.setCountry(request.getCountry());

        // atualizar vínculos se necessário
        if (request.getUserId() != null) {
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            aircraft.setUser(user);
        }

        if (request.getCompanyId() != null) {
            Company company = companyRepository.findById(request.getCompanyId())
                    .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
            aircraft.setCompany(company);
        }

        Aircraft updated = aircraftRepository.save(aircraft);

        return toResponse(updated);
    }

    public AircraftDTOResponse atualizarStatus(Integer id, String status) {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aeronave não encontrada"));

        aircraft.setRole(status); // exemplo: se o status for armazenado no campo `role`

        Aircraft updated = aircraftRepository.save(aircraft);
        return toResponse(updated);
    }

    public void apagarAircraft(Integer id) {
        aircraftRepository.deleteById(id);
    }

    private AircraftDTOResponse toResponse(Aircraft aircraft) {
        AircraftDTOResponse dto = new AircraftDTOResponse();
        dto.setId(aircraft.getId());
        dto.setNome(aircraft.getNome());
        dto.setRole(aircraft.getRole());
        dto.setNatoName(aircraft.getNatoName());
        dto.setDescription(aircraft.getDescription());
        dto.setCountry(aircraft.getCountry());
        dto.setUserName(aircraft.getUser().getNome());
        dto.setCompanyName(aircraft.getCompany().getName());
        return dto;
    }
}
