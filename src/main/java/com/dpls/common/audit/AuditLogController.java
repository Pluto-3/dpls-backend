package com.dpls.common.audit;

import com.dpls.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping("/{id}/timeline")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<AuditLogResponse>>> getTimeline(
            @PathVariable Long id) {
        List<AuditLogResponse> timeline = auditLogService.getTimeline(id);
        return ResponseEntity.ok(ApiResponse.success("Timeline retrieved", timeline));
    }
}