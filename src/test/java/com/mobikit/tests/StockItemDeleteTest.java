package com.mobikit.tests;

import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class StockItemDeleteTest extends BaseTest {

    @Test
    public void testDeleteMultipleItems() {
        System.out.println("=== TOPLU SİLME TESTİ ===");

        int deletedCount = 0;

        for (int i = 1; i <= 3; i++) {
            // Ürün oluştur
            String body = "{\n" +
                    "  \"name\": \"Toplu Silme Test " + i + "\",\n" +
                    "  \"unit\": \"Adet\"\n" +
                    "}";

            String id = given()
                    .contentType(ContentType.JSON)
                    .body(body)
                    .when()
                    .post(getItemsEndpoint())
                    .then()
                    .statusCode(201)
                    .time(lessThan(getMaxResponseTime()))  // ✅ Response time eklendi
                    .extract()
                    .path("id");

            System.out.println("  ✓ Ürün " + i + " oluşturuldu (ID: " + id + ")");

            // Sil
            given()
                    .when()
                    .delete(getItemEndpoint(id))
                    .then()
                    .statusCode(200)
                    .time(lessThan(getMaxResponseTime()));  // ✅ Response time eklendi

            deletedCount++;
            System.out.println("  ✓ Ürün " + i + " silindi");
        }

        System.out.println("✅ Toplu silme tamamlandı: " + deletedCount + "/3");
    }

}