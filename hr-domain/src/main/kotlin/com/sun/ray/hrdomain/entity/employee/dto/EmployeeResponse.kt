package com.sun.ray.hrdomain.entity.employee.dto

import com.sun.ray.hrdomain.entity.employee.Employee
import com.sun.ray.hrdomain.entity.employee.EmployeeAssignment
import java.time.LocalDate

data class EmployeeResponse(
    val employeeNumber: String?,
    val employeeName: String,
    val isActive: Boolean,
    val assignments: List<AssignmentDto>,
) {
    companion object {
        fun from(employee: Employee): EmployeeResponse {
            return EmployeeResponse(
                employeeNumber = employee.employeeNumber,
                employeeName = employee.employeeName,
                isActive = employee.isActive,
                assignments = employee.assignments.map { AssignmentDto.from(it) } ?: emptyList(),
            )
        }
    }
}

data class AssignmentDto(
    val assignmentDate: LocalDate?,
    val positionName: String?,
    val departmentName: String?,
    val projectSite: String?,
) {
    companion object {
        fun from(entity: EmployeeAssignment): AssignmentDto {
            return AssignmentDto(
                assignmentDate = entity.startDate,
                positionName = entity.position?.name,
                departmentName = entity.department?.departmentName,
                projectSite = entity.siteName
            )
        }
    }
}