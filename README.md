# formp-proxy-performance-tests

Performance test suite for the `formp-proxy`, using [performance-test-runner](https://github.com/hmrc/performance-test-runner) under the hood.

## Pre-requisites

### Services

Setup Oracle Database for FORMP Locally (using Docker or Lima as per preference)

Start Mongo Docker container following instructions from the [MDTP Handbook](https://docs.tax.service.gov.uk/mdtp-handbook/documentation/developer-set-up/set-up-mongodb.html).

Start `FORMP_PROXY` services as follows:

```bash
sm2 --start FORMP_PROXY
```

### Adding and configuring the tests for different services

This is API only performance test repo for formp-proxy microservice and its interaction with FORMP Oracle DB

To setup your tests:
1) Add your service's auth login stub payload in AuthLoginRequests
2) Create new folder in parent folder uk/gov/hmrc/perftests/ for your service and create Object with the API requests
3) Create Simulation Trait for your service in uk/gov/hmrc/perftests/simulation and update FormpSimulation.scala
4) Update BaseRequests, journeys.conf as per requirement 

### Logging

The default log level for all HTTP requests is set to `WARN`. Configure [logback.xml](src/test/resources/logback.xml) to update this if required.

### WARNING :warning:

Do **NOT** run a full performance test against staging from your local machine. Please [implement a new performance test job](https://docs.tax.service.gov.uk/mdtp-handbook/documentation/mdtp-test-approach/performance-testing/performance-test-a-microservice/index.html) and execute your job from the dashboard in [Performance Jenkins](https://performance.tools.staging.tax.service.gov.uk).

## Tests

Run smoke test (locally) as follows:

```bash
sbt -Dperftest.runSmokeTest=true -DrunLocal=true gatling:test
```

Run full performance test (locally) as follows:

```bash
sbt -DrunLocal=true gatling:test
```

Run smoke test (staging) as follows:

```bash
sbt -Dperftest.runSmokeTest=true -DrunLocal=false gatling:test
```

## Scalafmt

Check all project files are formatted as expected as follows:

```bash
sbt scalafmtCheckAll scalafmtCheck
```

Format `*.sbt` and `project/*.scala` files as follows:

```bash
sbt scalafmtSbt
```

Format all project files as follows:

```bash
sbt scalafmtAll
```

## License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
