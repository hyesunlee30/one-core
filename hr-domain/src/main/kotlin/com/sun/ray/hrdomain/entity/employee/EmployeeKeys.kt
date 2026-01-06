package com.sun.ray.hrdomain.entity.employee

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import java.io.Serializable
import java.time.LocalDate
import javax.persistence.Column

class EmployeeKeys: Serializable {

    @Column(name = "EMP_NUM")
    var employeeNumber: String? = null


    @Column(name = "START_DATE")
    val startDate: LocalDate? = null

    companion object {
        private val properties = arrayOf(
            EmployeeKeys::employeeNumber,
            EmployeeKeys::startDate
        )
    }

    override fun equals(other: Any?): Boolean {
        return kotlinEquals(other = other, properties = properties)
    }

    override fun hashCode(): Int {
        return kotlinHashCode(properties = properties)
    }
}