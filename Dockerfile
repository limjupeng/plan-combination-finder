FROM eclipse-temurin:19
EXPOSE 8080
ARG JAR_FILE=target/plan-combination-finder-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
