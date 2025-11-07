package com.oxcart.project.service;

import com.oxcart.project.dto.request.CompanyDTORequest;
import com.oxcart.project.dto.response.CompanyDTOResponse;
import com.oxcart.project.entity.Company;
import com.oxcart.project.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> listarEmpresas() {
        return companyRepository.findAll();
    }

    public Company obterPorId(Integer id) {
        return companyRepository.findById(id).orElse(null);
    }

    public CompanyDTOResponse criarCompany(CompanyDTORequest request) {
        Company company = new Company();
        company.setName(request.getName());
        company.setDescription(request.getDescription());
        company.setCountry(request.getCountry());

        Company saved = companyRepository.save(company);
        return toResponse(saved);
    }

    public CompanyDTOResponse atualizarCompany(Integer id, CompanyDTORequest request) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        company.setName(request.getName());
        company.setDescription(request.getDescription());
        company.setCountry(request.getCountry());

        Company updated = companyRepository.save(company);
        return toResponse(updated);
    }

    public CompanyDTOResponse atualizarStatus(Integer id, String status) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        company.setDescription(status); // exemplo: status armazenado em description

        Company updated = companyRepository.save(company);
        return toResponse(updated);
    }

    public void deleteCompany(Integer id) {
        companyRepository.deleteById(id);
    }

    private CompanyDTOResponse toResponse(Company company) {
        CompanyDTOResponse dto = new CompanyDTOResponse();
        dto.setId(company.getId());
        dto.setName(company.getName());
        dto.setDescription(company.getDescription());
        dto.setCountry(company.getCountry());
        return dto;
    }
}
