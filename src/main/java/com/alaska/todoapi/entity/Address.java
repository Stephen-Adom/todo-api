package com.alaska.todoapi.entity;

import com.alaska.todoapi.entity.validationInterface.EditUserValidationInterface;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Column(name = "city", columnDefinition = "VARCHAR(255) default 'N/A'")
    @NotBlank(message = "User city is not available", groups = EditUserValidationInterface.class)
    private String city;

    @Column(name = "zipcode", columnDefinition = "VARCHAR(255) default 'N/A'")
    private String zipCode;

    @Column(name = "country", columnDefinition = "VARCHAR(255) default 'N/A'")
    @NotBlank(message = "User country is not available", groups = EditUserValidationInterface.class)
    private String country;
}
