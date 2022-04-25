#컨테이너 베이스 이미지
FROM openjdk:11-jre-slim

#app 디렉토리 생성
RUN mkdir /app

#빌드된 jar 파일 -> 컨테이너 내부 app.jar로 복사
COPY build/libs/*.jar /app/app.jar

#컨테이너 띄울시 실행 될 shell 명령어
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod", "/app/app.jar"]