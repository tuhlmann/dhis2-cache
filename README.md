# DHIS2 metaCache

This is just a simple Sprint Boot demo application.

## What's in the box

- At startup, the app will read application.properties, connect to the mentioned server, authenticate with the given credentials and fetch the meta data
- metadata will be cached in an in memory H2 instance
- The metadata fetcher will run regularly at a configurable interval
- Provide a REST API to export the meta data to authenticated clients

### Build

```
  ./mvnw clean package
```

### Test

Run included tests with:

```
  ./mvnw test
```

The test package also includes integration tests. These use [Wiremock](http://wiremock.org/) to create a backend service from
which we fetch the source meta data- instead of accessing the real service. Everything else uses the production logic to
test as much of production code as possible. Using this approach we test:

- authentication at the backend service
- transmission of data from backend service to our app, parsing and reading this data and caching it
- accessing our own REST api with a client and fetching data as json and xml
- testing we got indeed the data we expected.

Integration tests are excluded from these tests, they run with a separate profile:

```
  ./mvnw verify -Pintegration-tests
```


### Run

- run directly: `./mvnw clean spring-boot:run`
- run JAR: `java -jar target/metaCache-0.0.1-SNAPSHOT.jar`

### Accessing the API

The service creates two users, `user / password` and `admin / password`.
The service supports basic authentication and gives access to all provided end points for these users.

The directory `target/generated-docs/index.html` contains api documentation generated by Spring Rest Docs.
To generate the documentation, run:

```
  ./mvnw package
```
 
### Content Negotiation

The service can emit either XML or JSON data depending on your request setting.

You can control the output type through these 3 different controls:

1. add a path suffix extension to your request URL, for instance `/dataElementGroups.json` or `/dataElements.xml`.
2. Add the `mediaType` request parameter to the URL, like so: `/dataElementGroups?mediaType=xml`
3. Accept Header: Add an `Accept` header to your request with `application/xml` or `application/json`

If no output control is found the return value format will default to JSON.
