# Mancala

This application has been created based on an assignment from bol.com.
Mancala is a two-player strategy board game where the objective is to capture more stones than the opponent. This application is a web version of the game


## Getting started

These instructions will give you a copy of the project up and running on your local machine for evaluating and testing purposes.

#### It's important to note that the default port for this application is 5050. To change the port, please refer to the 'application.properties' file in the project path.

### Prerequisites / Test and Deploy

Requirements for the software and other tools to build and test. Choosing one of them could cause you to ignore the other one.
- [JDK 17] (https://www.openlogic.com/openjdk-downloads). 
  - After downloading and installing the JDK, make sure to add your JAVA_HOME to your path
  - To set your JAVA_HOME follow this link https://www.baeldung.com/java-home-on-windows-7-8-10-mac-os-x-linux
- [Maven] (https://maven.apache.org/download.cgi)
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
docker build --tag 'mancala' .
docker run -d -p [your_proper_port]:5050 mancala
```
  - To play the game follow this link http://localhost:your_proper_port/
  - To stop the process or other related stuff to Docker you can use this link https://docs.docker.com/get-started/docker_cheatsheet.pdf, or read more about docker concepts


## Name
Choose a self-explaining name for your project.

## Description
Let people know what your project can do specifically. Provide context and add a link to any reference visitors might be unfamiliar with. A list of Features or a Background subsection can also be added here. If there are alternatives to your project, this is a good place to list differentiating factors.

## Usage
The project is just an assigment for bol.com

## Support
If you face the problem or have any questions please contact me via babak.azarmi@yahoo.com

## Authors and acknowledgment
The application has been created by Babak Azarmi. (babak.azarmi@yahoo.com)

## License
This project is free for every one. 

## Project status
It's an assignment, and I don't think so there is a plan to improve and extend the project