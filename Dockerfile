FROM openjdk:10-jre

RUN mkdir /app

WORKDIR /app

ADD target/api_interview-0.1.0.jar app.jar

ENTRYPOINT ["java","-server","-jar","app.jar"]