package com.dpls.permittype;


import com.dpls.department.Department;
import com.dpls.department.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermitTypeService {

    private final PermitTypeRepository permitTypeRepository;
    private final DepartmentRepository departmentRepository;

    public PermitTypeResponse create(PermitTypeRequest request) {
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        PermitType permitType = PermitType.builder()
                .name(request.getName())
                .description(request.getDescription())
                .validityPeriodDays(String.valueOf(request.getValidityPeriodDays()))
                .department(department)
                .build();

        permitTypeRepository.save(permitType);
        return mapToResponse(permitType);
    }

    public List<PermitTypeResponse> getAll() {
        return permitTypeRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public PermitTypeResponse getById(Long id) {
        PermitType permitType = permitTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permit type not found"));
        return mapToResponse(permitType);
    }

    private PermitTypeResponse mapToResponse(PermitType permitType) {
        return PermitTypeResponse.builder()
                .id(permitType.getId())
                .name(permitType.getName())
                .description(permitType.getDescription())
                .validityPeriodDays(Integer.valueOf(permitType.getValidityPeriodDays()))
                .departmentName(permitType.getDepartment().getName())
                .build();
    }
}