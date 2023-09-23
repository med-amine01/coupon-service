FROM openjdk:17-oracle
ADD target/coupon-service-0.0.1-SNAPSHOT.jar /app/target/coupon-service-0.0.1-SNAPSHOT.jar
LABEL authors="med"
EXPOSE 9091
ENTRYPOINT ["java", "-jar", "/app/target/coupon-service-0.0.1-SNAPSHOT.jar"]