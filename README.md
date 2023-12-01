# Mancala

This application has been created based on an assignment from bol.com.
Mancala is a two-player strategy board game where the objective is to capture more stones than the opponent. This application is a web version of the game


## Getting started

These instructions will give you a copy of the project up and running on your local machine for evaluation and testing purposes.

#### It's important to note that the default port for this application is 5050. To change the port, please refer to the 'application.properties' file in the project path.

### Prerequisites / Test and Deploy

Requirements for the software and other tools to build and test.
- [JDK 17] (https://www.openlogic.com/openjdk-downloads). 
  - After downloading and installing the JDK, make sure to add your JAVA_HOME to your path
  - To set your JAVA_HOME follow this link https://www.baeldung.com/java-home-on-windows-7-8-10-mac-os-x-linux
- [Maven] (https://maven.apache.org/download.cgi). Installing the maven on your system is mandatory. With this, you are unable to compile the application
  - To setup your maven environment follow this link https://www.baeldung.com/install-maven-on-windows-linux-mac
```
cd ${project_path}/mancala/
mvn clean install
java -jar ./target/mancala.war
```
  - To play the game follow this link http://localhost:5050/

- [Docker] (https://www.docker.com/products/docker-desktop/)
  - FYI, if you choose this manner you can ignore the previous one.
```
cd ${project_path}/mancala/
mvn clean install
docker build --tag 'mancala' .
docker run -d -p [your_proper_port]:5050 mancala
```
  - To play the game follow this link http://localhost:your_proper_port/
  - To stop the process or other related stuff to Docker you can use this link https://docs.docker.com/get-started/docker_cheatsheet.pdf, or read more about docker concepts

## Technologies & Design
- Hexagonal Architecture has been used to design and package the layers.
- This application has been written in Java (17) language, except the UI part that is based on Javasctipt, Jquery and CSS/HTML
- Spring Boot 3 is the framework that has been used to handle 
  - IoC
  - Test
  - WEB
- Lombok 

## Usage
The project is just an assignment for bol.com

## Support
If you face the problem or have any questions please contact me via babak.azarmi@yahoo.com

## Authors and acknowledgment
The application has been created by Babak Azarmi. (babak.azarmi@yahoo.com)

## License
This project is free for everyone. 

## Project status
It's an assignment, and I don't think there is a plan to improve and extend the project
