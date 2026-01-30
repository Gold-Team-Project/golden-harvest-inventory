# Java 21 Amazon Corretto 베이스 이미지 사용
FROM amazoncorretto:21-alpine-jdk

# 작업 디렉토리 설정
WORKDIR /app

# 빌드된 jar 파일을 컨테이너로 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# 실행 권한 부여 및 실행
ENTRYPOINT ["java", "-jar", "app.jar"]