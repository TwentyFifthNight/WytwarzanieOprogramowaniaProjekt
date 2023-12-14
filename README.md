# Spring School Management System

## Overview

This project aims to provide a solution for student, grade and teacher management. The project is built using the Spring framework.

## Requirements

For building and running the application you need:

- [JDK 17.0.2](https://jdk.java.net/archive)
- Maven

## REST API

### Get list of Students

#### Request

`GET /api/student`

#### Response

    HTTP/1.1 200 OK
    Date: Thu, 14 Dec 2023 23:23:15 GMT
    Status: 200 OK
    Keep-Alive: timeout=60
    Connection: keep-alive
    Content-Type: application/json
    Transfer-Encoding: chunked

    [{"id":1,"name":"Jan","surname":"Kowalik","pesel":"02221565579","scores":[],"grade":null}]

## Create a new Student

#### Request

`POST /api/student`

#### Body

    {
      "name":"Jan",
      "surname":"Kowalik",
      "pesel":"02221565579"
    }


#### Response

    HTTP/1.1 201 Created
    Date: Thu, 14 Dec 2023 23:15:50 GMT
    Status: 201 Created
    Keep-Alive: timeout=60
    Connection: keep-alive
    Content-Type: application/json
    Transfer-Encoding: chunked

    {"id":1,"name":"Jan","surname":"Kowalik","pesel":"02221565579","scores":[],"grade":null}
