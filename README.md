# Task Manager - End-to-End Test Suite

This repository contains the End-to-End (E2E) test suite for the Task Manager application. It uses Docker Compose to create a self-contained environment for running automated tests against the application.

## Overview

The test environment is composed of several services orchestrated by Docker Compose:
- **PostgreSQL Database (`db`)**: The application's database.
- **Task Manager Application (`app`)**: The backend application being tested.
- **Selenoid (`selenoid`)**: A robust implementation of the Selenium Hub for running browser-based tests in Docker containers.
- **Browser Image Puller (`browser-image-puller`)**: An init-container that automatically pulls the required browser images (e.g., Chrome) specified in `browsers.json` before the tests start.
- **Test Runner (`tests`)**: The container that executes the E2E tests using Gradle.

## Prerequisites

Before you begin, ensure you have the following installed:
- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/install/)
- [jq](https://stedolan.github.io/jq/download/) (a lightweight command-line JSON processor)

## Running the Tests

To build the Docker images, start the services, and run the entire test suite, execute the following command from the root of the project:

```bash
docker-compose up --build --exit-code-from tests
```

This command will:
1.  Build the Docker images for the application, Selenoid, and the test runner.
2.  Start all services in the correct order.
3.  The `browser-image-puller` service will pull the necessary browser images.
4.  The `tests` service will run the E2E tests against the application.
5.  After the tests complete, all containers will automatically stop. The command will exit with the test runner's exit code (0 for success, non-zero for failure).

## Test Reports

After the test run is complete, an Allure test report is generated in the `build/allure-report` directory. To view the report, you can open the `index.html` file from that directory in your web browser.