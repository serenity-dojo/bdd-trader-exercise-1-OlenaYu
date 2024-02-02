package net.bddtrader.unittests.clients;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.bddtrader.clients.Client;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

public class WhenCreatingANewClient {
    @Before
    public void baseUrlSetup() {
        baseURI =  "http://localhost:9000/api/";
    }
    @Test
    public void each_new_client_should_get_a_unique_id () {
        Client aNewClient = Client.withFirstName("Olena").andLastName("Yurova").andEmail("testemail@g.com");
        RestAssured.given().contentType(ContentType.JSON)
                .body(aNewClient)
                .when()
                .post("/client")
                .then().statusCode(200)
                .and().body("id", not(equalTo(0)))
                .and().body("email", equalTo("testemail@g.com"))
                .and().body("firstName", equalTo("Olena"))
                .and().body("lastName", equalTo("Yurova"));
        }
        /*
         Exercise 1
            Write a test to create a new client with the following details using the Client class:
            First name: Jim
            Last name: Halpert
            Email: jim@halpert.com
     */
    @Test
    public void create_a_new_client_with_specified_data_using_Client() {
        Client theNewClient = Client.withFirstName("Jim").andLastName("Halpert").andEmail("jim@halpert.com");
        given().contentType(ContentType.JSON).body(theNewClient)
                .when().post("/client")
                .then().statusCode(200)
                .and().body("firstName", equalTo("Jim"))
                .and().body("lastName", equalTo("Halpert"))
                .and().body("email", equalTo("jim@halpert.com"));

    }
    /*
        Exercise 2
        Write another test to create a new client using a map with the following details:
        First name: Kevin
        Last name: Malone
        Email: kevin@malone.com
     */
    @Test
    public void create_a_new_client_with_specified_data_using_Map() {
        Map<String, Object> clientData = new HashMap<>();
        clientData.put("firstName", "Kevin");
        clientData.put("lastName", "Malone");
        clientData.put("email", "kevin@malone.com");

        given().contentType(ContentType.JSON).body(clientData)
                .when().post("/client")
                .then().statusCode(200)
                .and().body("firstName", equalTo("Kevin"))
                .and().body("lastName", equalTo("Malone"))
                .and().body("email", equalTo("kevin@malone.com"));
    }


    }

