FROM maven:3.9.9-amazoncorretto-21-alpine AS build
COPY . .
RUN mvn clean package -DskipTests
FROM amazoncorretto:21-alpine
COPY --from=build target/Oxcart.jar app.jar
EXPOSE 8088
CMD ["java", "-jar", "/*.jar"]