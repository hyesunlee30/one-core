package com.sun.ray.hrdomain.repository.department

import com.sun.ray.hrdomain.entity.department.Department
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DepartmentRepository : JpaRepository<Department, Long> {
    fun findByDepartmentCode(departmentCode: String): Department
}