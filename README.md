# **MEDISCREEN-NOTES**
#### OpenClassrooms project number 9

This API REST is a part of the Mediscreen project.

The Mediscreen project involves developing a system to detect and anticipate diabetes in patients. </br>
The development was divided into three sprints :
each sprint corresponds to one of the three REST APIs necessary for the operation of this application.

Sprint 1 : **mediscreen-patient**
https://github.com/G-jonathan/Mediscreen-patient </br>
Sprint 2 : **mediscreen-notes**
https://github.com/G-jonathan/Medicreen-notes </br>
Sprint 3 : **mediscreen-report**
https://github.com/G-jonathan/Mediscreen-report </br>
UI Web application : **mediscreen-client** :
https://github.com/G-jonathan/Mediscreen-client </br>

## Built with

- JAVA 17
  https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html </br>
- Gradle -Kotlin 7.6.1
  https://docs.gradle.org/7.6.1/release-notes.html </br>
- SpringBoot 3.1.0
  https://spring.io/blog/2023/05/18/spring-boot-3-1-0-available-now </br>
- MongoDB 6.0.6 
  https://www.mongodb.com/fr-fr
- MySQL 8.0.32
  https://www.mysql.com/fr/
- Git 2.34.1
  https://git-scm.com/downloads </br>
- Docker 24.0.1
  https://www.docker.com/ </br>
- Docker-compose 2.18.1
  https://docs.docker.com/compose/install/ </br>
- Bootstrap 5.2.3
  https://mvnrepository.com/artifact/org.webjars/bootstrap/5.2.3 </br>
- OpenFeign
  https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-openfeign/4.0.3 </br>
- Thymeleaf 3.1.1
  https://www.thymeleaf.org/ </br>
- JaCoco
  https://www.jacoco.org/jacoco/trunk/doc/ </br>
- Swagger
  https://swagger.io/specification/ </br>
- JUnit 5 Jupiter
  https://junit.org/junit5/docs/current/user-guide/ </br>
- TestContainers 1.17.6
  https://testcontainers.com/

## Architecture diagram
![p9_architecture_diagram.png](documentation%2Fp9_architecture_diagram.png)

## Installation and Getting Started
### Requirements
- Docker 24.0.1 https://www.docker.com/ </br>
- Docker-compose 2.18.1 https://docs.docker.com/compose/install/ </br>

### Docker global installation
1. Clone the repositories </br>
   ```shell
    git clone git@github.com:G-jonathan/Mediscreen-client.git;
      ``` 
   ```shell
    git clone git@github.com:G-jonathan/Mediscreen-patient.git;
      ``` 
   ```shell
    git clone git@github.com:G-jonathan/Mediscreen-notes.git;
      ``` 
   ```shell
    git clone git@github.com:G-jonathan/Mediscreen-report.git
      ``` 

2. Build the applications </br>
   ```shell
    cd ../Mediscreen-client; ./gradlew bootRun
      ``` 
   ```shell
    cd ../Mediscreen-patient; ./gradlew bootRun
      ``` 
   ```shell
    cd ../Mediscreen-notes; ./gradlew bootRun
      ``` 
   ```shell
    cd ../Mediscreen-report; ./gradlew bootRun
      ``` 

3. Create the Docker images </br>
   ```shell
    cd ../Mediscreen-client; docker build -t mediscreen-client .
      ``` 
   ```shell
    cd ../Mediscreen-patient; docker build -t mediscreen-patient .
      ``` 
   ```shell
    cd ../Mediscreen-notes; docker build -t mediscreen-notes .
      ``` 
   ```shell
    cd ../Mediscreen-report; docker build -t mediscreen-report .
      ``` 

   3.1. Docker database images : </br>
   ```shell
    cd ../Mediscreen-notes/database; docker build -t mediscreen-notes-db .
      ``` 
   ```shell
    cd ../Mediscreen-patient/database; docker build -t mediscreen-patient-db .
      ```

4. Execute the Docker-compose </br>
   ```shell
    docker-compose up
      ``` 

### insert test data </br>
The MySQL database schema and the test data are also inserted automatically when docker image is created. </br>
MongoDB database test notes must be inserted by hand either using the interface or using the following cURL commands:
- ```shell
  curl --location --request POST 'http://localhost:8082/api/note' --header 'Content-Type: application/json' --data-raw '{
  "patientId": 1,
  "comment":": TestNone Practitioner'\''s notes/recommendations: Patient states that they are '\''feeling terrific'\'' Weight at or below recommended level"
  }' 
  ```
- ```shell
  curl --location --request POST 'http://localhost:8082/api/note' --header 'Content-Type: application/json' --data-raw '{
  "patientId": 2,
  "comment":"Patient: TestBorderline Practitioner'\''s notes/recommendations: Patient states that they are feeling a great deal of stress at work Patient also complains that their hearing seems Abnormal as of late"
  }'
  ```
- ```shell
  curl --location --request POST 'http://localhost:8082/api/note' --header 'Content-Type: application/json' --data-raw '{
  "patientId": 2, 
  "comment":"Patient: TestBorderline Practitioner'\''s notes/recommendations: Patient states that they have had a Reaction to medication within last 3 months Patient also complains that their hearing continues to be problematic"
  }'
  ```
- ```shell
   curl --location --request POST 'http://localhost:8082/api/note' --header 'Content-Type: application/json' --data-raw '{
  "patientId": 3,
  "comment":"Patient: TestInDanger Practitioner'\''s notes/recommendations: Patient states that they are short term Smoker"
  }'
  ```
- ```shell
   curl --location --request POST 'http://localhost:8082/api/note' --header 'Content-Type: application/json' --data-raw '{
  "patientId": 3,
  "comment":"Patient: TestInDanger Practitioner'\''s notes/recommendations: Patient states that they quit within last year Patient also complains that of Abnormal breathing spells Lab reports Cholesterol LDL high"
  }'
  ```
- ```shell
   curl --location --request POST 'http://localhost:8082/api/note' --header 'Content-Type: application/json' --data-raw '{
  "patientId": 4,
  "comment":"Patient: TestEarlyOnset Practitioner'\''s notes/recommendations: Patient states that walking up stairs has become difficult Patient also complains that they are having shortness of breath Lab results indicate Antibodies present elevated Reaction to medication"
  }'
  ```
- ```shell
   curl --location --request POST 'http://localhost:8082/api/note' --header 'Content-Type: application/json' --data-raw '{
  "patientId": 4,
  "comment":"Patient: TestEarlyOnset Practitioner'\''s notes/recommendations: Patient states that they are experiencing back pain when seated for a long time"
  }'
  ```
- ```shell
   curl --location --request POST 'http://localhost:8082/api/note' --header 'Content-Type: application/json' --data-raw '{
  "patientId": 4,
  "comment":"Patient: TestEarlyOnset Practitioner'\''s notes/recommendations: Patient states that they are a short term Smoker Hemoglobin A1C above recommended level"
  }'
  ```
- ```shell 
   curl --location --request POST 'http://localhost:8082/api/note' --header 'Content-Type: application/json' --data-raw '{
  "patientId": 4,
  "comment":"Patient: TestEarlyOnset Practitioner'\''s notes/recommendations: Patient states that Body Height, Body Weight, Cholesterol, Dizziness and Reaction"
  }'
  ```

### Then start at : </br>

http://localhost:8080/

## Tests coverage
The application is covered in integration test and unit test up to 90%. </br></br>
![Screenshot_Jacoco_mediscreen-note.png](documentation%2FScreenshot_Jacoco_mediscreen-note.png)

## Swagger UI
Swagger UI available at http://localhost:8082/swagger-ui/index.html </br></br>
![Screenshot_swagger_mediscreen-notes.png](documentation%2FScreenshot_swagger_mediscreen-notes.png)

## Spécifications API REST
List of available endpoints at : </br></br>
[spécifications_API_REST_medicscreen-notes.odt](documentation%2Fspe%CC%81cifications_API_REST_medicscreen-notes.odt)