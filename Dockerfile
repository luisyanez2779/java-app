FROM amazoncorretto:17-alpine-jdk

EXPOSE 8080

RUN mkdir /usr/app
COPY ./target/java-maven-app-*.jar /usr/app
WORKDIR /usr/app

ENTRYPOINT ["java", "-jar", "java-maven-app-jma-2.0-SNAPSHOT.jar"]