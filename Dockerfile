FROM openjdk:8-jdk-slim
COPY "./target/banking-account-service-0.1.jar" "account-service.jar"
ENTRYPOINT ["java","-jar","account-service.jar"]