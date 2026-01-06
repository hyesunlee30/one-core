package com.sun.ray.hrdomain.repository.employee

import com.sun.ray.hrdomain.entity.employee.Employee


interface EmployeeCustomRepository {

    fun findByEmployeeNumber(
        employeeNumber: String,
    ): Employee?

    fun findEmployeeList(
    ): List<Employee>?

}