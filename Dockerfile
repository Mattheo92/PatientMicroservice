FROM openjdk:21-jdk
MAINTAINER Mattheo92
COPY target/PatientMicroservice-0.0.1-SNAPSHOT.jar PatientMicroservice.jar
ENTRYPOINT ["java", "-jar", "PatientMicroservice.jar"]