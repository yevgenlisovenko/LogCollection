FROM openjdk:11-jdk
ENV LOG_DIR=/logs
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]