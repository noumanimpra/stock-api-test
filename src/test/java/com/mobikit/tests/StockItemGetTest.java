package com.mobikit.tests;

import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class StockItemGetTest extends BaseTest {

    @Test
    public void testGetAllItems() {
        System.out.println("=== TEST 1: Tüm ürünleri getir ===");

        given()
                .log().all()
                .when()
                .get(getItemsEndpoint())  // .env'den geliyor
                .then()
                .log().all()
                .statusCode(200)
                .time(lessThan(getMaxResponseTime()))  // .env'den geliyor
                .body("size()", greaterThan(0))
                .body("[0].id", notNullValue())
                .body("[0].name", notNullValue());

        System.out.println("✓ Test başarılı!");
    }

    @Test
    public void testGetSingleItem() {
        System.out.println("=== TEST 2: İlk ürünü getir ===");

        given()
                .log().all()
                .when()
                .get(getItemEndpoint("1"))  // .env'den geliyor
                .then()
                .log().all()
                .statusCode(200)
                .time(lessThan(getMaxResponseTime()))
                .body("id", equalTo("1"))
                .body("name", notNullValue());

        System.out.println("✓ Test başarılı!");
    }

    @Test
    public void testResponseTimeCheck() {
        System.out.println("=== TEST 3: Response time kontrolü ===");

        long startTime = System.currentTimeMillis();

        given()
                .when()
                .get(getItemsEndpoint())
                .then()
                .statusCode(200)
                .time(lessThan(getMaxResponseTime()));

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("✓ API " + duration + "ms'de yanıt verdi (Limit: " + getMaxResponseTime() + "ms)");
    }
}