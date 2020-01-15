# race-server
Server implementation for a racing system. The server accepts inputs from timing mats and saves the athletes' scores to the database.
It is a simple SpringBoot application that is using Postgres database.

You can GET and POST events to the server.
GET race/events returns all the events. 

For example:
GET http://localhost:8080/race/events

POST race/event creates a new event or updates the existing event with the new time.

body:rfid - id of an athlete (chip code), readerId - id of the timing point, time - time when the athlete crossed the timing point

For example:
POST http://localhost:8080/race/event
{
  "rfid": 1,
  "readerId": 100,
  "time": "2020-10-10T10:10:10.0"
}

To develop (for IntelliJ) you need: Java (min 8), IntelliJ, PostgreSQL server (for Mac postgres.app was enough https://postgresapp.com/)

Instructions:

1) clone the repository

2) open project in IntelliJ

3) you can add the data source in Database sidebar, then: + > Data Source > PostgreSQL :

url=jdbc:postgresql://localhost:5432/postgres, username=postgres, password=admin

click test connection to make sure everything is installed properly and you can connect
 
4) if prompt to install PostgreSQL JDBC Driver, do that

5) run the RaceServerApplication (this should create and initialize the db); double check from the data source that tables participant and event exist

6) you can switch off the initialization once you have the db in application.properties to spring.datasource.initialization-mode=never

7) run the tests in RaceServerApplicationTests


Note: In real world application there would be probably multiple races 
so there would be another table race in the db and the endpoints would be GET race/{id}/events and POST race/{id}/event where id is the race id. 
