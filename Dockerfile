FROM openjdk:21-jdk-slim as builder
WORKDIR /app
COPY . .
RUN ./gradlew clean build

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/ProyectoFinal-ProgramacionAvanzada-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]