# Generate Synthea Data

## About

This is a script that is used to populate Summit Health with realistic synthetic patient data. The bash script works by cloning [Synthea](https://github.com/synthetichealth/synthea), running Synthea to generate data, converting the Synthea data output to a JSON file, and sending the `apidata.json` JSON file to Summit Health API where it is processed and stored in the database.

## Prerequisites

* NPM: Install [here](https://www.npmjs.com/get-npm)
* Install dependencies:

```bash
npm install
```

* Java 1.8 or above: Install [here](https://www.oracle.com/technetwork/java/javase/downloads/index.html)

## Running

```bash
./generate.sh -p [population] -u [url]
```

**Flags**:
* p
    * The population of the generated Synthea data. If flag is not used, defaults to 500.
* u
    * **REQUIRED**: The API url for Summit Health.
