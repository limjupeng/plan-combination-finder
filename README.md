# Build

This application can be built using maven.

openJDK 19.0.2 is used to develop this application.

To build,

1. cd to the working directory that contains pom.xml
2. run the command

   ```mvn package```

# Run

To start the API server,

- in the same directory, run the command

  ```java -jar ".\target\plan-combination-finder-0.0.1-SNAPSHOT.jar"```

# Usage

To use the API,

- go to http://localhost:8080/ to access the frontend, OR
- access the REST API using the http://localhost:8080/plan endpoint

# OpenAPI Documentation

To access the OpenAPI documentation,

- go to http://localhost:8080/swagger-ui.html

# Docker

To start the server via container,

- run the command

  ```docker run -p 8080:8080 jupeng/plan-combination-finder:0.0.1```

# Tests

- JUnit:
    - run the command
      ```mvn test```
- Postman
    1. Copy `Example1.txt` to your Postman working directory (default is `%USERPROFILE%\Postman\files`)
    2. In Postman, import the file `plan-combination-finder-postman.postman_collection.json`
    3. Start the API Server
    4. Send the POST request in Postman and check the test results pass