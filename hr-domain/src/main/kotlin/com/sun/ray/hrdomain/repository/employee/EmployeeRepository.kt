package com.sun.ray.hrdomain.repository.employee

import com.sun.ray.hrdomain.entity.employee.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : JpaRepository<Employee, String>