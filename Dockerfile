FROM openjdk:17
ADD target/my-cooking-buddy-app.jar my-cooking-buddy-app.jar
EXPOSE 8047
ENTRYPOINT ["java","-jar","my-cooking-buddy-app.jar"]