package com.dpls.permit;

import com.dpls.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/permits")
@RequiredArgsConstructor
public class PermitController {

    private final PermitService permitService;

    @PostMapping("/{applicationId}/issue")
    @PreAuthorize("hasAuthority('ROLE_OFFICER')")
    public ResponseEntity<ApiResponse<PermitResponse>> issue(@PathVariable Long applicationId) {
        PermitResponse response = permitService.issue(applicationId);
        return ResponseEntity.ok(ApiResponse.success("Permit issued successfully", response));
    }

    @GetMapping("/application/{applicationId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<PermitResponse>> getByApplicationId(
            @PathVariable Long applicationId) {
        PermitResponse response = permitService.getByApplicationId(applicationId);
        return ResponseEntity.ok(ApiResponse.success("Permit retrieved", response));
    }

    @GetMapping("/verify/{code}")
    public ResponseEntity<ApiResponse<PermitResponse>> verify(@PathVariable String code) {
        PermitResponse response = permitService.verify(code);
        return ResponseEntity.ok(ApiResponse.success("Permit is valid", response));
    }
}