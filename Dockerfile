FROM openjdk:19-slim

COPY target/*.jar /app/commissioning.jar

ENV POSTGRES_DB commissioning_of_ventilation_system
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD password

CMD ["java", "-jar", "/app/commissioning.jar"]
