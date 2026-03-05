FROM amazoncorretto:17-alpine-jdk

EXPOSE 8080

COPY ./target/java-maven-app-jma-1.0-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app

ENTRYPOINT ["java", "-jar", "java-maven-app-jma-1.0-SNAPSHOT.jar"]