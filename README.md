# DHIS2 metaCache

This is just a simple Sprint Boot demo application.

## What's in the box

- At startup, the app will read application.properties, connect to the mentioned server, authenticate with the given credentials and fetch the meta data
- metadata will be cached in an in memory H2 instance
- The metadata fetcher will run regularly at a configurable interval
- Provide a REST API to export the meta data to authenticated clients

### Build

`./mvnw clean package`

### Run

- run directly: `./mvnw clean spring-boot:run`
- run JAR: `java -jar target/metaCache-0.0.1-SNAPSHOT.jar`