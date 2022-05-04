FROM openjdk:8-jdk-slim
COPY --from=build "/target/banking-account-service-0.1.jar" "account-service.jar"
ENTRYPOINT ["java","-jar","account-service.jar"]