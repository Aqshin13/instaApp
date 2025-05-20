FROM openjdk:18
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} instaApp-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/instaApp-0.0.1-SNAPSHOT.jar"]
