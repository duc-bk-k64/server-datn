FROM openjdk:16-jdk-alpine
 
COPY target/project3-0.0.1-SNAPSHOT.jar project3-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/project3-0.0.1-SNAPSHOT.jar"]