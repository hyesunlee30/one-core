package com.sun.ray.hrdomain.entity.department

import com.sun.ray.hrdomain.entity.common.converter.BooleanToYNConverter
import javax.persistence.*

@Entity
@Table(name = "department")
@SequenceGenerator(
    name = "department_generator",
    sequenceName = "DEPARTMENT_SEQ",
    initialValue = 1,
    allocationSize = 1
)
class Department(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_generator")
    @Column(name = "ID")
    val id: Long? = null,

    @Column(name = "DEPT_CODE", nullable = false, unique = true, length = 20)
    var departmentCode: String,

    @Column(name = "DEPT_NM", nullable = false, length = 100)
    var departmentName: String?,

    @Column(name = "PARENT_DEPT_NM", length = 100)
    var parentDepartmentName: String? = null,

    @Column(name = "DEPT_PATH", length = 500)
    var departmentPath: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    var parentDepartment: Department? = null,

    @Column(name = "USE_YN", length = 1)
    @Convert(converter = BooleanToYNConverter::class)
    var isActive: Boolean = true,

    @Column(name = "DISPLAY_ORDER")
    var displayOrder: Int = 0
) {
    /**
     * 상위 부서 변경 시 반정규화 필드도 함께 업데이트
     */
    fun updateParent(parent: Department?) {
        this.parentDepartment = parent
        this.parentDepartmentName = parent?.departmentName
        // 경로 업데이트 로직 예시
        this.departmentPath = if (parent != null) "${parent.departmentPath} > ${this.departmentName}" else this.departmentName
    }
}