package com.mobikit.tests;

import io.restassured.http.ContentType;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class StockItemPostTest extends BaseTest {

    @Test
    public void testCreateNewItem() {
        System.out.println("=== TEST 1: Yeni ürün oluştur (POST) ===");

        String requestBody = "{\n" +
                "  \"name\": \"Test Ürünü - " + System.currentTimeMillis() + "\",\n" +
                "  \"description\": \"Rest Assured ile otomatik oluşturuldu\",\n" +
                "  \"location\": \"Test Deposu\",\n" +
                "  \"unit\": \"Adet\",\n" +
                "  \"storageArea\": \"Test Rafı\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .log().all()
                .when()
                .post(getItemsEndpoint())  // .env'den geliyor
                .then()
                .log().all()
                .statusCode(201)
                .time(lessThan(getMaxResponseTime()))
                .body("name", containsString("Test Ürünü"))
                .body("id", notNullValue());

        System.out.println("✓ Yeni ürün oluşturuldu!");
    }

    @Test
    public void testCreateMultipleItems() {
        System.out.println("=== TEST 2: Birden fazla ürün oluştur ===");

        String[] itemNames = {"Kapasitör", "IC 555", "LM358"};

        for (String itemName : itemNames) {
            String requestBody = "{\n" +
                    "  \"name\": \"" + itemName + "\",\n" +
                    "  \"description\": \"Test ürünü\",\n" +
                    "  \"location\": \"Raf A\",\n" +
                    "  \"unit\": \"Adet\",\n" +
                    "  \"storageArea\": \"Elektronik\"\n" +
                    "}";

            given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .when()
                    .post(getItemsEndpoint())  // .env'den geliyor
                    .then()
                    .statusCode(201)
                    .body("name", equalTo(itemName));

            System.out.println("  ✓ " + itemName + " eklendi");
        }

        System.out.println("✓ Tüm ürünler eklendi!");
    }
}