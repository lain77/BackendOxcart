package com.oxcart.project.dto.request;


import jakarta.validation.constraints.NotBlank;
import org.antlr.v4.runtime.misc.NotNull;

public class AircraftDTORequest {

    @NotBlank(message = "O nome da aeronave é obrigatório")
    private String nome;

    @NotBlank(message = "O papel (role) da aeronave é obrigatório")
    private String role;

    private String natoName;

    private String description;

    @NotBlank(message = "O país da aeronave é obrigatório")
    private String country;

    @NotNull
    private Integer userId;

    @NotNull
    private Integer companyId;

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNatoName() {
        return natoName;
    }

    public void setNatoName(String natoName) {
        this.natoName = natoName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
