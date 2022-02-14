# Url Shortener Service

Url Shortener is a [Spring Boot](https://spring.io/projects/spring-boot) application built using Maven  
This proof of concept application exposes client RESTful endpoints to accept a URL and provide a unique 
short-url in response. The short-url can be used to return the original long url.  

This application currently has two database implementations. The first is a fake database useful for local development,
it is implemented via a List to store the URLs. The second implementation utilises a Redis cache data structure store.
If ran locally will require a redis server to be running, alternatively docker-compose can be used to spin up a redis
container configured to talk to the url-shortener application.
To switch between the database implementations simply change the `@Qualifier` annotation value in the 
`UrlServiceImplementation` class to match the fakeUrlDao `"fakeDao"` or 
RedisDao `"redisDao"` `@Repository` annotation.

## Requirements

For building and running the application :

- [Open JDK 11](https://openjdk.java.net/projects/jdk/11/)
- [Maven 3](https://maven.apache.org/)
- [Docker](https://www.docker.com/)

### Running the application locally
There are several ways to run the application locally. 

#### IDE
Execute the `main` method in the `UrlShortenerApplication` class from your IDE.

#### Maven
Use Maven in the terminal to run the application
```shell
mvn spring-boot:run
```

#### Docker
You can utilise docker-compose to build and run both the url-shortener and redis containers
```shell
docker-compose up -d --build
```

## Future Development
Future development should consider following:
- User accounts
- Expiration dates associated to urls stored
- Front end and registered domain to redirect to original url following short-url submission.
- Persisted Database
