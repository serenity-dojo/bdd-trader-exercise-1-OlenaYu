package net.bddtrader.unittests.clients;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.bddtrader.clients.Client;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

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
        String newClient = "{\n" +
                "  \"email\": \"testemail@g.com\",\n" +
                "  \"firstName\": \"Olena\",\n" +
                "  \"lastName\": \"Yurova\"\n" +
                "}";
        RestAssured.given().contentType(ContentType.JSON)
                .body(newClient)
                .when()
                .post("/client")
                .then().statusCode(200)
                .and().body("id", not(equalTo(0)))
                .and().body("email", equalTo("testemail@g.com"))
                .and().body("firstName", equalTo("Olena"))
                .and().body("lastName", equalTo("Yurova"));
        }
    }

