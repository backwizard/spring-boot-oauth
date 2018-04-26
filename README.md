# Spring Boot With JWT


# Checking out and Building

To check out the project and build from source, do the following:

```
git clone https://github.com/backwizard/spring-boot-oauth.git

cd spring-boot-oauth

./gradlew build && java -jar build/libs/spring-boot-oauth-0.1.0.jar
```

# Usage

```
* registers a new user

    curl -H "Content-Type: application/json" -X POST -d '{
            "username": "username",
            "password": "password",
            "address": "address",
            "salary": 50000,
            "phone": "0123456789"
    }'  [http://localhost:8080/user/register]

* get user detail

    curl -H "Content-Type: application/json" \
         -H "Authorization: Bearer header.payload.signature" \
        [http://localhost:8080/api/v1/user?username=xxxx]

```
# Design Concept

    (media/design.png)

## Running the tests

Explain how to run the automated tests for this system

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```
