package com.dpls.department;

import com.dpls.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Department>> create(@RequestBody Map<String, String> body) {
        Department department = departmentService.create(
                body.get("name"),
                body.get("description")
        );
        return ResponseEntity.ok(ApiResponse.success("Department created", department));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Department>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Departments retrieved", departmentService.getAll()));
    }
}