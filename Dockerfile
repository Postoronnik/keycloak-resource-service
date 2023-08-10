FROM openjdk:17
ARG JAR_FILE=*.jar
COPY build/libs/${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
