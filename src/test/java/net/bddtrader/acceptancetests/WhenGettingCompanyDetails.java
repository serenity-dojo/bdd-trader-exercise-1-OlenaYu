package net.bddtrader.acceptancetests;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

public class WhenGettingCompanyDetails {
    @Before
    public void prepare_rest_config() {
        RestAssured.baseURI = "http://localhost:9000/api/";
    }
//    @Test
//    public void should_return_name_and_sector_in_production() {
//    RestAssured.get("stock/aapl/company").then()
//            .body("companyName", Matchers.equalTo("Apple, Inc."))
//            .body("sector", Matchers.equalTo("Electronic Technology"));
//    }
    @Test
    public void should_return_name_and_sector_locally() {
        RestAssured.given()
                .pathParam("symbol", "aapl")
                .when()
                .get("stock/{symbol}/company").then()
                .body("companyName", equalTo("Apple, Inc."))
                .body("sector", equalTo("Electronic Technology"));
    }
    @Test
    public void should_return_news_for_a_requested_company() {
        RestAssured.given()
                .queryParam("symbols", "GOOGL")
                .when()
                .get("/news")
                .then()
                .body("related", everyItem(containsString("GOOGL")));
    }
}
