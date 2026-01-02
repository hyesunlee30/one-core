
# 📑 One-Core HR Project

**One-Core HR**은 Oracle Database를 기반으로 한 **급여 및 근태 관리 중심의 인사 관리(HR) 시스템**입니다.
Kotlin과 Spring Boot 3.3을 활용하여 견고한 Backend API를 구축하고, Docker Compose를 통해 인프라 설정 없이 즉시 실행 가능한 환경을 제공합니다.

## 📌 Project Goals

* **Kotlin-Native REST API**: 코틀린의 간결함과 안전성을 활용한 API 개발.
* **Database Integration**: Oracle DB XE 환경에서의 데이터 모델링 및 JPA 연동.
* **Containerization**: Docker 기반의 "One-Click" 실행 환경 구축.
* **Scalability**: 향후 회계(Finance), 구매(Procurement) 등으로 확장 가능한 모듈형 아키텍처 지향.

---

## 🛠 Tech Stack

### Framework & Language

* **Language**: Kotlin 1.9.24
* **Framework**: Spring Boot 3.3.5
* **Build Tool**: Gradle (Groovy DSL)
* **JDK**: 17

### Data & Security

* **Database**: Oracle Database XE 21c (Docker)
* **Persistence**: Spring Data JPA
* **Security**: Spring Security (REST API Protection)
* **API Documentation**: SpringDoc OpenAPI (Swagger UI) 2.6.0

---

## 🧩 Core Features (Scope)

* **인사(HR)**: 사원 기본 정보 관리 및 부서/직급 체계화.
* **근태(Attendance)**: 실시간 출퇴근 기록 및 월별 근무 시간 집계.
* **급여(Payroll)**: 근태 데이터를 기반으로 한 기본 급여 계산 로직.

---

## 🐳 Docker Infrastructure

본 프로젝트는 Docker Compose를 통해 데이터베이스와 애플리케이션을 통합 관리합니다.

### 1. Database (Oracle XE)

* **Image**: `gvenzl/oracle-xe:21-slim`
* **Port**: `1521`
* **User/Pass**: `onecore` / `onecore`

### 2. Application

* **Java Version**: Eclipse Temurin 17 (JDK)
* **Port**: `8080`

---

## ▶️ Getting Started (실행 방법)

### 1️⃣ 빌드 (Build)

```bash
./gradlew clean build

```

### 2️⃣ 실행 (Run with Docker)

```bash
docker-compose up --build -d

```

### 3️⃣ 접속 정보 (Access)

* **Swagger API Docs**: [http://localhost:8080/swagger-ui/index.html](https://www.google.com/search?q=http://localhost:8080/swagger-ui/index.html)
* **Oracle DB**: `localhost:1521` (SID/Service Name: `FREE`)

---

## ⚙️ Key Configuration (build.gradle 핵심)

* **Compiler Options**: Kotlin의 Null 안정성을 위해 `-Xjsr305=strict` 옵션을 적용하였습니다.
* **All-Open Plugin**: JPA 엔티티의 지연 로딩(Lazy Loading)을 위해 `@Entity` 클래스들을 자동으로 개방(Open) 설정하였습니다.
* **Security & Validation**: RESTful API의 데이터 검증 및 보안 강화를 위한 스타터 팩이 포함되어 있습니다.

---

## 🎬 시연 시나리오 (Checklist)

1. **Container Health Check**: Oracle 컨테이너가 정상 기동된 후 Spring Boot가 기동되는지 확인합니다.
2. **Schema Auto Generation**: JPA `ddl-auto: update` 설정을 통해 테이블이 자동 생성됩니다.
3. **API Testing**: Swagger UI를 통해 사원 등록 및 근태 기록 API를 테스트합니다.

