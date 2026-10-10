package com.bankingsystem.dto;

import javax.validation.constraints.NotBlank;

public class FormDTO {

    @NotBlank(message = "Form name is required")
    private String formName;

    @NotBlank(message = "Form type is required")
    private String type; // NATIONAL / INTERNATIONAL

    private String description;

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    
    
}
