package com.sun.ray.hrdomain.entity.jobposition

import com.sun.ray.hrdomain.entity.common.converter.BooleanToYNConverter
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "job_position")
@SequenceGenerator(
    name = "job_position_generator",
    sequenceName = "JOB_POSITION_SEQ", // DB에 실제 생성될 시퀀스 이름
    initialValue = 1,
    allocationSize = 1
)
class JobPosition(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_position_generator")
    @Column(name = "POS_ID")
    val id: Long? = null,

    @Column(name = "POS_CD", nullable = false, unique = true, length = 20)
    var positionCode: String, // 예: POS-001

    @Column(name = "POS_NM", nullable = false, length = 100)
    var name: String?,

    @Enumerated(EnumType.STRING)
    @Column(name = "JOB_GROUP", length = 20)
    var jobGroup: JobGroup,

    @Enumerated(EnumType.STRING)
    @Column(name = "JOB_LEVEL", length = 20)
    var jobLevel: JobLevel,

    /* 사용여부 */
    @Column(name = "USE_YN", length = 1)
    @Convert(converter = BooleanToYNConverter::class)
    var isActive: Boolean = true,

    /* 시스템등록정보 */
    @Column(name = "SYS_REG_DTIME", updatable = false)
    var registrationDateTime: LocalDateTime = LocalDateTime.now(),

    @Column(name = "SYS_REGR_ID", updatable = false)
    var registrationId: String,

    /* 시스템수정정보 */
    @Column(name = "SYS_MOD_DTIME")
    var modificationDateTime: LocalDateTime = LocalDateTime.now(),

    @Column(name = "SYS_MODR_ID")
    var modificationId: String
)

enum class JobGroup {
    TECH, ADMIN, SALES, MANAGEMENT
}

enum class JobLevel(
    val description: String,
    val sequence: Int
) {
    CEO("대표이사", 1),
    DIRECTOR("본부장/이사", 2),
    DEPARTMENT_MANAGER("부장", 3),
    TEAM_LEADER("차장", 4),
    MANAGER("과장", 5),
    ASSISTANT_MANAGER("대리", 6),
    STAFF("사원", 7);
}