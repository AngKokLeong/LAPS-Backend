FROM amazoncorretto:21-alpine

RUN apk add --no-cache curl

WORKDIR /app

COPY target/*.war app.war

EXPOSE 8100

ENTRYPOINT ["java", "-jar", "app.war"]
