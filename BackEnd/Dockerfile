FROM gradle:7.5-jdk17-alpine

WORKDIR /app

COPY ./BackEnd/ ./

RUN gradle clean build --no-daemon


FROM openjdk:17-alpine
WORKDIR /app
COPY --from=0 /app/build/libs/ ./
COPY BackEnd/properties/ ./
CMD ["java", "-jar", "BackEnd-0.0.1-SNAPSHOT.jar"]
#CMD ["ping", "localhost:80"]