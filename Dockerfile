FROM maven:3.6 AS builder
RUN mkdir -p /app
EXPOSE 8080
COPY hismetag /app
WORKDIR /app
RUN mvn package


FROM tomcat:8.0
COPY --from=builder /app/target/api.war /usr/local/tomcat/webapps/api.war
