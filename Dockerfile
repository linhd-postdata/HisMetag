FROM maven:3.6 AS builder
RUN mkdir -p /app
EXPOSE 8080
COPY Hismetag /app
WORKDIR /app
RUN mvn package


FROM tomcat:8.0
COPY --from=builder /app/target/ServicioWebRest-*.war /usr/local/tomcat/webapps/ServicioWebRest-1.0-SNAPSHOT.war
