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
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class WhenUpdatingAndDeletingAClient {
    @Before
    public void baseUrlSetup() {
        baseURI =  "http://localhost:9000/api/";
    }
    //DELETE
    @Test
    public void should_be_able_to_delete_a_client() {
        //Given an existing client (setting up the data)
        String id = aClientExists(Client.withFirstName("Tom").andLastName("Berton").andEmail("tom@berton.com"));

        //When I delete the client
        //DELETE http://localhost:9000/api/2343
        given().delete("/client/{id}", id);

        //Then the client should no longer exist
        given()
                .get("/client/{id}", id)
                .then()
                .statusCode(404);
    }
    //PUT
    @Test
    public void should_be_able_to_update_the_client() {
        Client tom = Client.withFirstName("Tom").andLastName("Berton").andEmail("tom@berton.com");
        //Given an existing client (setting up the data)
        String id = aClientExists(tom);

        //When I update the client's email
        //Client tomUpdated = Client.withFirstName("Tom").andLastName("Berton").andEmail("tom_berton@gmail.com");
        //Instead of creating the whole structure, for bigger projects, we can use Map
        Map<String, Object> updates = new HashMap<>();
        updates.put("email","tom_berton@gmail.com");
        given().contentType(ContentType.JSON).and().body(updates)
                .when().put("/client/{id}", id).then().statusCode(200);

        //Then the email should be updated
        when().get("/client/{id}", id)
                .then().body("email", Matchers.equalTo("tom_berton@gmail.com"));
    }

    private static String aClientExists(Client existingClient) {
        return given().contentType(ContentType.JSON)
                .body(existingClient)
                .when()
                .post("/client")//we need to get the jsonPath for the object
                .jsonPath().getString("id");//getting id of the client to be deleted later on


    }
}
