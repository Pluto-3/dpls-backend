package com.dpls.application;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApplicationRequest {
    @NotNull(message = "Permit type is required")
    private Long permitTypeId;
}
