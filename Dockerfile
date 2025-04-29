FROM openjdk:17-jdk-slim
COPY lockerAuth-0.0.1-SNAPSHOT.jar app_locker.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "app_locker.jar" ]
