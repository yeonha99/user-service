FROM openjdk:11-jdk
VOLUME /tmp
ARG JAR_FILE=./build/libs/user-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 1000
ENTRYPOINT ["java","-jar","/app.jar"]
