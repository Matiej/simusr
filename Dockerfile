FROM eclipse-temurin:18-jdk-alpine as builder
WORKDIR /opt/app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
# clean up the file --- super important.  (depends on system jdk version etc)
RUN sed -i 's/\r$//' mvnw
RUN /bin/sh mvnw dependency:go-offline
COPY ./src ./src
RUN /bin/sh mvnw clean package -Dmaven.test.skip
CMD ["java", "-jar", "/opt/app/*.jar"]

FROM eclipse-temurin:18-jre
WORKDIR /opt/app
COPY --from=builder /opt/app/target/*.jar  /opt/app/*.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar"]
