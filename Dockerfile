FROM openjdk:17-oracle
COPY target/grocery-web-app-0.0.1-SNAPSHOT.jar grocery.jar
COPY src/main/resources/application-prod.properties application.properties
COPY src/main/resources/log4j2-prod.xml log4j2.xml
CMD ["java", "-jar", "grocery.jar" ]