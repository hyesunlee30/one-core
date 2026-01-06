package com.sun.ray.hrdomain.repository.employee

import com.querydsl.jpa.impl.JPAQueryFactory
import com.sun.ray.hrdomain.entity.employee.Employee
import com.sun.ray.hrdomain.entity.employee.QEmployee.employee
import com.sun.ray.hrdomain.entity.employee.QEmployeeAssignment.employeeAssignment
import com.sun.ray.hrdomain.entity.jobposition.QJobPosition.jobPosition
import org.springframework.stereotype.Repository


@Repository
class EmployeeCustomRepositoryImpl(
    val jpaQueryFactory: JPAQueryFactory
) : EmployeeCustomRepository {

    override fun findByEmployeeNumber(employeeNumber: String): Employee? {
        return jpaQueryFactory
            .selectFrom(employee)
            // 1. Employee -> EmployeeAssignment 조인 (fetchJoin으로 N+1 방지)
            // 연관 관계 필드(employee.assignments)
            .leftJoin(employee.assignments, employeeAssignment).fetchJoin()

            // 2. EmployeeAssignment -> JobPosition 조인
            // assignment 내부의 position 필드와 조인 대상을 연결
            .leftJoin(employeeAssignment.position, jobPosition).fetchJoin()

            .where(employee.employeeNumber.eq(employeeNumber))
            .fetchOne()
    }

    override fun findEmployeeList(): List<Employee>? {
        return jpaQueryFactory
            .selectFrom(employee)
            // 1. Employee -> EmployeeAssignment 조인 (fetchJoin으로 N+1 방지)
            // 연관 관계 필드(employee.assignments)
            .leftJoin(employee.assignments, employeeAssignment).fetchJoin()

            // 2. EmployeeAssignment -> JobPosition 조인
            // assignment 내부의 position 필드와 조인 대상을 연결
            .leftJoin(employeeAssignment.position, jobPosition).fetchJoin()
            .fetch()
    }


}