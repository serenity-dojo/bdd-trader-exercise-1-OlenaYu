package net.bddtrader.acceptancetests;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class JSONPathAssertionExercises {
    /*
     * Exercise 1 - Read a single field value
     * Read and verify the "industry" field for AAPL
     * The result should be "Telecommunications Equipment"
     */
    @Before
    public void prepare_rest_config() {
        baseURI = "http://localhost:9000/api/";
    }
    @Test
    public void find_a_simple_field_value() {
        given().pathParam("symbol", "aapl")
                .when()
                .get("/stock/{symbol}/company")
                .then()
                .body("industry", equalTo("Telecommunications Equipment"));
    }

    /*
     * Exercise 2 - Read a single field value
     * Read and verify the "description" field for AAPL
     * The result should contain the string "smartphones"
     */
    @Test
    public void check_that_a_field_value_contains_a_given_string() {
        given().pathParam("symbol", "aapl")
                .when()
                .get("/stock/{symbol}/company")
                .then()
                .body("description", containsString("smartphones"));
    }
    /*
     * Exercise 3 - Read a single nested field value
     * Read the 'symbol' field inside the 'quote' entry in the Apple stock book
     * The result should be 'AAPL'
     */
    @Test
    public void find_a_nested_field_value() {
        given().pathParam("symbol", "aapl")
                .when().get("/stock/{symbol}/book")
                .then().body("quote.symbol", equalTo("AAPL"));
    }
    /*
     * Exercise 4 - Find a list of values
     * Find the list of symbols recently traded from  https://bddtrader.herokuapp.com/api/tops/last
     * Find the list of symbols recently traded and check that the list contains "PTN", "PINE" and "TRS"
     */
    @Test
    public void find_a_list_of_values() {
        when().get("/tops/last").then().body("symbol", hasItems("PTN", "PINE", "TRS"));
    }
    /*
     * Exercise 5 - Check that there is at least one price that is greater than 100.
     * https://bddtrader.herokuapp.com/api/tops/last
     */
    @Test
    public void check_at_least_one_price_matches_a_given_condition() {
        when().get("/tops/last").then().body("price", hasItems(greaterThan(100.0f)));
    }
    /*
     * Exercise 6 - check the value of a specific item in a list
     * SAMPLE QUERY: https://bddtrader.herokuapp.com/api/stock/aapl/book
     * Check that price of the first trade in the Apple stock book is 319.59
     */
    @Test
    public void check_the_value_of_a_specific_item_in_a_list() {
        given().pathParam("symbol", "aapl").when().get("stock/{symbol}/book")
                .then().body("trades[0].price", equalTo(319.59f));
    }
    /*
     * Exercise 7 - check the value of a specific item in a list
     * SAMPLE QUERY: https://bddtrader.herokuapp.com/api/stock/aapl/book
     * Check that price of the last trade in the Apple stock book is 319.54
     */
    @Test
    public void check_the_last_value_of_a_specific_item_in_a_list() {
        given().pathParam("symbol", "aapl").when().get("stock/{symbol}/book")
                .then().body("trades[-1].price", equalTo(319.54f));
    }
    /*
    Exercise 8 - check for number of elements in a collection
    SAMPLE QUERY: https://bddtrader.herokuapp.com/api/stock/aapl/book
    Check that precisely 20 trades are returned per query.
     */
    @Test
    public void check_the_number_of_elements_in_a_collection() {
        given().pathParam("symbol", "aapl").when().get("stock/{symbol}/book")
                .then().body("trades", hasSize(20));
        //OR solution from John
        // .then().body("trades.size()", equalTo(20));
    }
    /*
     * Exercise 9 - check for minimum or maximum
     * SAMPLE QUERY: https://bddtrader.herokuapp.com/api/stock/aapl/book
     * Check that the minimum price of any trade in the Apple stock book is 319.38
     */
    @Test
    public void check_the_minimum_price() {
        given().pathParam("symbol", "aapl").when().get("stock/{symbol}/book")
                .then().body("trades.min{trade->trade.price}.price", equalTo(319.38f));
                // OR
                //.then().body("trades.min{it.price}.price", equalTo(319.38f));
                //OR solution from John
                //.then().body("trades.price.min()", equalTo(319.38f));
    }
    /*
     * Exercise 10 - find a collection of objects matching a specified criteria
     * SAMPLE QUERY: https://bddtrader.herokuapp.com/api/stock/aapl/book
     * Check that there are 13 trades with prices greater than 319.50
     */
    @Test
    public void find_a_collection_of_objects_matching_specific_criteria() {
        given().pathParam("symbol", "aapl").when().get("stock/{symbol}/book")
                .then().body("trades.findAll{it.price > 319.50}", hasSize(13));
    }

}
