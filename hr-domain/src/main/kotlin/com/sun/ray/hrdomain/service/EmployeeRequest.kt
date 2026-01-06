package com.sun.ray.hrdomain.service

data class EmployeeRequest(

    val name: String,

    val departmentCode: String,

    val email: String,

    val positionCode: String,

    val siteCode: String,

    val hireDate: String,

    val salary: Int = 0
)