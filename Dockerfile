FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/lockerauth-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_locker.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "app_locker.jar" ]