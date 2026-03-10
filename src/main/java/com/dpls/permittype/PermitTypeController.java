package com.dpls.permittype;

import com.dpls.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permit-types")
@RequiredArgsConstructor
public class PermitTypeController {

    private final PermitTypeService permitTypeService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PermitTypeResponse>> create(
            @Valid @RequestBody PermitTypeRequest request) {
        PermitTypeResponse response = permitTypeService.create(request);
        return ResponseEntity.ok(ApiResponse.success("Permit type created", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PermitTypeResponse>>> getAll() {
        List<PermitTypeResponse> response = permitTypeService.getAll();
        return ResponseEntity.ok(ApiResponse.success("Permit types retrieved", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PermitTypeResponse>> getById(@PathVariable Long id) {
        PermitTypeResponse response = permitTypeService.getById(id);
        return ResponseEntity.ok(ApiResponse.success("Permit type retrieved", response));
    }
}
