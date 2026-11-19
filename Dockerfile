FROM maven:3.9.8-eclipse-temurin-21-jammy
LABEL authors="yohanna.kreusch"

WORKDIR /app

COPY .idea .

RUN mvn clean package -DskipTests

WORKDIR /app

COPY --from=builder /app/target/solidaryAction-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar","solidaryAction-0.0.1-SNAPSHOT.jar"]