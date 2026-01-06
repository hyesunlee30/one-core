package com.sun.ray.hrdomain.entity.employee

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import com.sun.ray.hrdomain.entity.common.converter.BooleanToYNConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.persistence.*

@Entity
@Table(name = "employee_master")
class Employee(

    @Column(name = "START_DATE")
    var startDate: LocalDate, // 시작일 (PK Part 2)

    @Column(name = "EMP_NM", nullable = false)
    var employeeName: String,

    @Column(name = "EMAIL", nullable = false, unique = true)
    var email: String,

    @Column(name = "USE_YN", length = 1)
    @Convert(converter = BooleanToYNConverter::class)
    var isActive: Boolean = true,

    @Column(name = "SYS_REG_DTIME", updatable = false)
    var registrationDateTime: LocalDateTime = LocalDateTime.now(),

    @Column(name = "SYS_REGR_ID", updatable = false)
    var registrationId: String,

    @Column(name = "SYS_MOD_DTIME")
    var modificationDateTime: LocalDateTime = LocalDateTime.now(),

    @Column(name = "SYS_MODR_ID")
    var modificationId: String,

    @OneToMany(mappedBy = "employeeNumber", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var assignments: MutableList<EmployeeAssignment> = mutableListOf()

) {

    @Id
    @Column(name = "EMP_NUM", length = 20)
    var employeeNumber: String? = null

    companion object {
        private val properties = arrayOf(
            Employee::startDate,
            Employee::employeeName,
            Employee::email,
            Employee::isActive,
            Employee::registrationDateTime,
            Employee::registrationId,
            Employee::modificationDateTime,
            Employee::modificationId,
        )
    }

    override fun equals(other: Any?): Boolean {
        return kotlinEquals(other = other, properties = properties)
    }

    override fun hashCode(): Int {
        return kotlinHashCode(properties = properties)
    }

    override fun toString(): String {
        return kotlinToString(properties = properties)
    }

    /**
     * 양방향 연관관계 편의 메서드
     */
    fun addAssignment(assignment: EmployeeAssignment) {
        // 자식 엔티티의 식별자 중 하나인 employeeNumber를 부모와 동기화
        assignment.employeeNumber = this.employeeNumber
        this.assignments.add(assignment)
    }

    fun terminate(endDate: String) {
        this.isActive = false
        this.assignments.forEach {
            it.isActive = false
            it.endDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyyMMdd"))
        }
        this.modificationDateTime = LocalDateTime.now()
    }
}