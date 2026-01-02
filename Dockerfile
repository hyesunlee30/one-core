# 1단계: JDK 17 환경 사용
FROM eclipse-temurin:17-jdk

# 2단계: 작업 디렉토리 설정
WORKDIR /app

# 3단계: 빌드된 jar 파일을 컨테이너 내부로 복사
COPY build/libs/*-SNAPSHOT.jar app.jar

# 4단계: 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]