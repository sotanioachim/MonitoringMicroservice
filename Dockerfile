FROM openjdk:17
COPY target/monitoring-manager.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]