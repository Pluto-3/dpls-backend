package com.dpls.permittype;

import com.dpls.department.Department;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "permit_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermitType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    private String validityPeriodDays;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
