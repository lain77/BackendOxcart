package com.oxcart.project.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CompanyDTORequest {

    @NotBlank(message = "O nome da empresa é obrigatório")
    private String name;

    private String description;

    @NotBlank(message = "O país da empresa é obrigatório")
    private String country;

    // Getters e Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
