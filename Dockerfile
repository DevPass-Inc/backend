FROM bellsoft/liberica-openjdk-debian:17 AS builder
WORKDIR /builder

COPY ./gradlew /builder/gradlew
COPY ./gradle /builder/gradle
RUN chmod +x ./gradlew

COPY ./ /builder/

RUN ./gradlew clean build -x test

FROM bellsoft/liberica-openjre-debian:17-cds
WORKDIR /application

ARG JAR_FILE=build/libs/*.jar
COPY --from=builder /builder/${JAR_FILE} application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]