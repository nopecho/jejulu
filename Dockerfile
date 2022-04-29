#컨테이너 베이스 이미지
FROM openjdk:11-jre-slim

ENV CONFIG_APP /home/ec2-user/app/config/jejulu/application
ENV CONFIG_FIREBASE /home/ec2-user/app/config/jejulu/firebase

#app 디렉토리 생성
RUN mkdir /app

#config 설정파일 디렉토리 생성
RUN mkdir CONFIG_APP
RUN mkdir CONFIG_FIREBASE

#빌드된 jar 파일 -> 컨테이너 내부 app.jar로 복사
COPY build/libs/*.jar /app/app.jar

#컨테이너 띄울시 실행 될 shell 명령어
ENTRYPOINT ["java","-jar", "/app/app.jar"]