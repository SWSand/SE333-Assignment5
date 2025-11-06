
# SE333 - Assignment 5 - Unit, Mocking and Integration Testing
## Workflow Status Badge
[![.github/workflows/SE333_CI.yml](https://github.com/SWSand/SE333-Assignment5/actions/workflows/SE333_CI.yml/badge.svg?branch=main&event=push)](https://github.com/SWSand/SE333-Assignment5/actions/workflows/SE333_CI.yml)
## Project Overview

This repository contains the starter code for Assignment 5 (Unit, Mocking and Integration Testing).
It includes two sample modules:

- BarnesAndNoble — example code and tests demonstrating specification-based and structural-based testing.
- Amazon — code that will later receive unit and integration tests (see Part 3 of the assignment).

## Tests and CI

- Barnes specification tests: `src/test/java/org/example/Barnes/BarnesSpecificationTest.java` (@DisplayName("specification-based"))
- Barnes structural tests: `src/test/java/org/example/Barnes/BarnesStructuralTest.java` (@DisplayName("structural-based"))
- CI workflow (GitHub Actions) runs Checkstyle and the test suite and will be extended to publish JaCoCo (`jacoco.xml`) and Checkstyle artifacts.

## Quickstart:

1. Make sure you have a compatible JDK installed (I used Java 21 for compatibility with JaCoCo and other dependencies).
2. From the project root run:

```bash
mvn -B clean test
```

This runs the test suite. CI runs Checkstyle during the `validate` phase and uploads reports.

## Also Included:
- Automated Testing with GitHub Actions Workflows
- Tests run on `push` event to `main` branch
