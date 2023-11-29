FROM openjdk:17

EXPOSE 5050

COPY ./target/mancala.war mancala.war

ENTRYPOINT ["java","-jar","mancala.war"]