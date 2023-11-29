FROM openjdk:17

EXPOSE 5050

COPY ./target/mancala-0.0.1-SNAPSHOT.war game.war

ENTRYPOINT ["java","-jar","game.war"]