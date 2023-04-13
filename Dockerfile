FROM maven:3-alpine
WORKDIR /app
COPY pom.xml /app/
RUN mvn dependency:go-offline
COPY src /app/src
RUN mvn -DskipTests package
EXPOSE 8080
CMD ["java", "-jar", "target/train-0.0.1-SNAPSHOT.jar"]