FROM openjdk:17-oracle

ADD target/coupon-service-0.0.1-SNAPSHOT.jar coupon-service-0.0.1-SNAPSHOT.jar

LABEL authors="med"

# first thing should be executable when container launched
ENTRYPOINT ["java","-jar","coupon-service-0.0.1-SNAPSHOT.jar"]