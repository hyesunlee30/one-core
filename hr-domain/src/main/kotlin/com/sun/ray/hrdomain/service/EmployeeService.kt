package com.sun.ray.hrdomain.service

import com.sun.ray.hrdomain.entity.common.codes.WorkType
import com.sun.ray.hrdomain.entity.employee.Employee
import com.sun.ray.hrdomain.entity.employee.EmployeeAssignment
import com.sun.ray.hrdomain.entity.employee.dto.EmployeeResponse
import com.sun.ray.hrdomain.repository.department.DepartmentRepository
import com.sun.ray.hrdomain.repository.employee.EmployeeCustomRepository
import com.sun.ray.hrdomain.repository.employee.EmployeeRepository
import com.sun.ray.hrdomain.repository.jobposition.JobPositionRepository
import com.sun.ray.hrdomain.repository.projectsite.ProjectSiteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Service
class EmployeeService(
    private val employeeCustomRepository: EmployeeCustomRepository,
    private val employeeRepository: EmployeeRepository,
    private val departmentRepository: DepartmentRepository,
    private val jobPositionRepository : JobPositionRepository,
    private val projectSiteRepository: ProjectSiteRepository
) {

    @Transactional(readOnly = true)
    fun findByEmployeeNumber(employeeNumber: String): EmployeeResponse? {
        val employee = employeeCustomRepository.findByEmployeeNumber(employeeNumber)
            ?: throw RuntimeException("사원을 찾을 수 없습니다.")

        return EmployeeResponse.from(employee)
    }

    @Transactional(readOnly = true)
    fun findEmployeeList(): List<EmployeeResponse>? {
        return employeeCustomRepository.findEmployeeList()?.map { EmployeeResponse.from(it) }
    }


    @Transactional
    fun saveEmployee(request: EmployeeRequest): String {
        // 1. 필요한 기초 마스터 정보 일괄 조회
        val department = departmentRepository.findByDepartmentCode(request.departmentCode)
            ?: throw RuntimeException("존재하지 않는 부서 코드입니다: ${request.departmentCode}")
        val jobPosition = jobPositionRepository.findByPositionCode(request.positionCode)
            ?: throw RuntimeException("존재하지 않는 직급 코드입니다: ${request.positionCode}")
        val projectSite = projectSiteRepository.findBySiteCode(request.siteCode)
            ?: throw RuntimeException("존재하지 않는 현장 코드입니다: ${request.siteCode}")

        // 2. 사번 생성 (오늘날짜 + 이메일 아이디)
        val todayStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        val emailPrefix = request.email.substringBefore("@")
        val generatedEmployeeNumber = "${todayStr}_$emailPrefix"


        // 3. 사원 마스터 엔티티 생성
        val employee = Employee(
            startDate = LocalDate.parse( request.hireDate, DateTimeFormatter.ofPattern("yyyyMMdd")), // 입사일
            employeeName = request.name,
            email = request.email,
            registrationId = "SYSTEM",
            modificationId = "SYSTEM"
        ).apply {
            this.employeeNumber = generatedEmployeeNumber
        }

        // 4. 초기 발령 정보 생성
        val initialAssignment = EmployeeAssignment(
            workType = WorkType.REGULAR,
            salary = request.salary,
            registrationId = "SYSTEM",
            modificationId = "SYSTEM"
        ).apply {

            this.employeeNumber = employee.employeeNumber
            this.startDate = employee.startDate // 입사일짜

            updateAssignmentInfo(department, jobPosition, projectSite)
        }

        // 5. 연관관계 설정 및 저장
        // addAssignment 내부에서 assignment.employeeNumber = this.employeeNumber 가 처리됨
        employee.addAssignment(initialAssignment)

        return employeeRepository.save(employee).employeeNumber ?: generatedEmployeeNumber
    }

    @Transactional
    fun deleteEmployee(
        employeeNumber: String,
        endDate: String,
    ) {
        val employee = employeeCustomRepository.findByEmployeeNumber(employeeNumber)
        employee?.let { employee.terminate(endDate) }

    }
}