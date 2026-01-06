package com.sun.ray.hrdomain.system

import com.sun.ray.hrdomain.entity.common.codes.WorkType
import com.sun.ray.hrdomain.entity.department.Department
import com.sun.ray.hrdomain.entity.employee.Employee
import com.sun.ray.hrdomain.entity.employee.EmployeeAssignment

import com.sun.ray.hrdomain.entity.jobposition.JobGroup
import com.sun.ray.hrdomain.entity.jobposition.JobLevel
import com.sun.ray.hrdomain.entity.jobposition.JobPosition
import com.sun.ray.hrdomain.entity.projectsite.ProjectSite
import com.sun.ray.hrdomain.repository.department.DepartmentRepository
import com.sun.ray.hrdomain.repository.employee.EmployeeRepository
import com.sun.ray.hrdomain.repository.jobposition.JobPositionRepository
import com.sun.ray.hrdomain.repository.projectsite.ProjectSiteRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Component
@Profile("dev", "local")
class DataInitializer(
    private val employeeRepository: EmployeeRepository,
    private val departmentRepository: DepartmentRepository,
    private val jobPositionRepository: JobPositionRepository,
    private val projectSiteRepository: ProjectSiteRepository
) : ApplicationRunner {

    @Transactional
    override fun run(args: ApplicationArguments?) {
        if (employeeRepository.count() > 0) return

        println("======= [System] 초기 데이터 생성 시작 =======")

        // 1. 부서 생성
        val deptPlatform = departmentRepository.save(Department(
            departmentCode = "DEPT_01", departmentName = "플랫폼개발본부"
        ))
        val deptManagement = departmentRepository.save(Department(
            departmentCode = "DEPT_02", departmentName = "경영지원팀"
        ))

        // 2. 직급 생성 (CEO부터 STAFF까지 5개 레벨)
        val posCEO = jobPositionRepository.save(JobPosition(
            positionCode = "POS_001", name = "대표이사",
            jobGroup = JobGroup.MANAGEMENT, jobLevel = JobLevel.CEO,
            registrationId = "SYSTEM", modificationId = "SYSTEM"
        ))
        val posDeptManager = jobPositionRepository.save(JobPosition(
            positionCode = "POS_002", name = "부장",
            jobGroup = JobGroup.TECH, jobLevel = JobLevel.DEPARTMENT_MANAGER,
            registrationId = "SYSTEM", modificationId = "SYSTEM"
        ))
        val posManager = jobPositionRepository.save(JobPosition(
            positionCode = "POS_003", name = "과장",
            jobGroup = JobGroup.TECH, jobLevel = JobLevel.MANAGER,
            registrationId = "SYSTEM", modificationId = "SYSTEM"
        ))
        val posSenior = jobPositionRepository.save(JobPosition(
            positionCode = "POS_004", name = "대리",
            jobGroup = JobGroup.TECH, jobLevel = JobLevel.ASSISTANT_MANAGER,
            registrationId = "SYSTEM", modificationId = "SYSTEM"
        ))
        val posJunior = jobPositionRepository.save(JobPosition(
            positionCode = "POS_005", name = "사원",
            jobGroup = JobGroup.ADMIN, jobLevel = JobLevel.STAFF,
            registrationId = "SYSTEM", modificationId = "SYSTEM"
        ))

        // 3. 현장 생성
        val siteHQ = projectSiteRepository.save(ProjectSite(
            siteCode = "SITE_01", siteName = "강남본사", departmentName = "본사",
            registrationId = "SYSTEM", modificationId = "SYSTEM"
        ))

        // 4. 사원 데이터 생성 (5명)
        // 사원 1: 대표이사
        saveEmployeeWithAssignment(
            num = "20260001_ceo", name = "김대표", email = "ceo@company.com",
            dept = deptManagement, pos = posCEO, site = siteHQ, salary = 200000000
        )

        // 사원 2: 부장
        saveEmployeeWithAssignment(
            num = "20260101_park", name = "박부장", email = "park@company.com",
            dept = deptPlatform, pos = posDeptManager, site = siteHQ, salary = 95000000
        )

        // 사원 3: 과장
        saveEmployeeWithAssignment(
            num = "20260102_kim", name = "김과장", email = "kim@company.com",
            dept = deptPlatform, pos = posManager, site = siteHQ, salary = 70000000
        )

        // 사원 4: 대리
        saveEmployeeWithAssignment(
            num = "20260103_lee", name = "이대리", email = "lee@company.com",
            dept = deptPlatform, pos = posSenior, site = siteHQ, salary = 55000000
        )

        // 사원 5: 사원
        saveEmployeeWithAssignment(
            num = "20260201_choi", name = "최사원", email = "choi@company.com",
            dept = deptManagement, pos = posJunior, site = siteHQ, salary = 38000000
        )

        println("======= [System] 초기 데이터 생성 완료 (5명) =======")
    }

    private fun saveEmployeeWithAssignment(
        num: String, name: String, email: String,
        dept: Department, pos: JobPosition, site: ProjectSite, salary: Int
    ) {
        val hireDate = LocalDate.now()

        val employee = Employee(
            startDate = hireDate,
            employeeName = name,
            email = email,
            registrationId = "SYSTEM",
            modificationId = "SYSTEM"
        ).apply {
            this.employeeNumber = num
        }

        val assignment = EmployeeAssignment(
            workType = WorkType.REGULAR,
            salary = salary,
            registrationId = "SYSTEM",
            modificationId = "SYSTEM"
        ).apply {
            this.startDate = hireDate
            updateAssignmentInfo(dept, pos, site)
        }

        employee.addAssignment(assignment)
        employeeRepository.save(employee)
    }
}