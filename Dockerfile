FROM openjdk:17-jdk-slim
MAINTAINER vijithakn@gmail.com

ARG env_name

ENV env_name_val $env_name

COPY target/eagriservice-0.0.1-SNAPSHOT.jar eagriservice-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=${env_name_val}", "-jar","/eagriservice-0.0.1-SNAPSHOT.jar"]