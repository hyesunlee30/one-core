package com.sun.ray.hrdomain.entity.employee

import com.sun.ray.hrdomain.entity.common.codes.WorkType
import com.sun.ray.hrdomain.entity.common.converter.BooleanToYNConverter
import com.sun.ray.hrdomain.entity.department.Department
import com.sun.ray.hrdomain.entity.jobposition.JobPosition
import com.sun.ray.hrdomain.entity.projectsite.ProjectSite
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@IdClass(EmployeeKeys::class)
@Table(name = "employee_assignment")
class EmployeeAssignment(

    @Column(name = "END_DATE", nullable = false)
    var endDate: LocalDate = LocalDate.of(9999, 12, 31),

    @Column(name = "DEPT_NM", length = 100)
    var departmentName: String? = null,

    @Column(name = "POS_NM", length = 100)
    var positionName: String? = null,

    @Column(name = "SITE_NM", length = 100)
    var siteName: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPT_ID")
    var department: Department? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POS_ID")
    var position: JobPosition? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SITE_ID")
    var projectSite: ProjectSite? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "WORK_TYPE")
    var workType: WorkType,

    @Column(name = "SALARY")
    var salary: Int = 0,

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
    var modificationId: String
) {

    @Id
    var employeeNumber: String? = null

    @Id
    var startDate: LocalDate? = null
    /**
     * 발령 시 명칭 정보를 함께 업데이트하는 편의 메서드
     */
    fun updateAssignmentInfo(dept: Department, pos: JobPosition, site: ProjectSite) {
        this.department = dept
        this.departmentName = dept.departmentName

        this.position = pos
        this.positionName = pos.name

        this.projectSite = site
        this.siteName = site.siteName
    }

    fun close(newStartDate: LocalDate, modifierId: String) {
        this.endDate = newStartDate.minusDays(1)
        this.modificationId = modifierId
        this.modificationDateTime = LocalDateTime.now()
    }
}