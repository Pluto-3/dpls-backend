package com.dpls.permittype;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PermitTypeRequest {

    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    @NotNull(message = "Validity period is required")
    private Integer validityPeriodDays;

    @NotNull(message = "Department is required")
    private Long departmentId;
}