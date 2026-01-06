package com.sun.ray.hrinterface.controller.employee

import com.sun.ray.hrdomain.entity.employee.dto.EmployeeResponse
import com.sun.ray.hrdomain.service.EmployeeRequest
import com.sun.ray.hrdomain.service.EmployeeService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@Tag(name = "Employee", description = "사원 관리 API")
@RestController
@RequestMapping("/api/v1/employees")
class EmployeeController(
    private val service: EmployeeService
) {

    @Operation(summary = "사원 목록 조회", description = "전체 사원 리스트를 조회합니다. 데이터가 없으면 빈 배열을 반환합니다.")
    @GetMapping("")
    fun getEmployeeList(): ResponseEntity<List<EmployeeResponse>> {
        val list = service.findEmployeeList() ?: emptyList()
        return ResponseEntity.ok(list)
    }

    @Operation(summary = "사원 상세 조회", description = "사원 번호를 기반으로 특정 사원의 정보를 조회합니다.")
    @GetMapping("/{employeeNumber}")
    fun getEmployee(
        @Parameter(description = "사원 번호", example = "EMP001")
        @PathVariable employeeNumber: String
    ): ResponseEntity<EmployeeResponse> {
        val employee = service.findByEmployeeNumber(employeeNumber)
            ?: throw RuntimeException("사원을 찾을 수 없습니다.")

        return ResponseEntity.ok(employee)
    }

    @Operation(
        summary = "사원 추가",
        description = "새로운 사원의 정보를 등록합니다.",
    )
    @PostMapping("")
    fun createEmployee(
        @RequestBody request: EmployeeRequest
    ): ResponseEntity<Unit> {
        service.saveEmployee(request)

        return ResponseEntity.ok().build()
    }

    @Operation(
        summary = "사원 삭제",

        )
    @DeleteMapping("/{employeeNumber}/{endDate}")
    fun deleteEmployee(
        @Parameter(description = "사원 번호", example = "EMP001")
        @PathVariable employeeNumber: String,
        @Parameter(description = "퇴사 날짜", example = "20261201")
        @PathVariable endDate: String
    ): ResponseEntity<Unit> {
        service.deleteEmployee(employeeNumber, endDate)

        return ResponseEntity.ok().build()
    }

}