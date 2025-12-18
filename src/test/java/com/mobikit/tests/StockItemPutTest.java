package com.mobikit.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class StockItemPutTest extends BaseTest {

    @Test
    public void testUpdateExistingItem() {
        System.out.println("=== TEST 1: Mevcut ürünü güncelle (PUT) ===");

        // Önce yeni bir ürün oluştur
        String createBody = "{\n" +
                "  \"name\": \"Güncellenecek Ürün\",\n" +
                "  \"description\": \"Eski açıklama\",\n" +
                "  \"location\": \"Eski Konum\",\n" +
                "  \"unit\": \"Adet\",\n" +
                "  \"storageArea\": \"Eski Raf\"\n" +
                "}";

        Response createResponse = given()
                .contentType(ContentType.JSON)
                .body(createBody)
                .when()
                .post(getItemsEndpoint())
                .then()
                .statusCode(201)
                .extract().response();

        String itemId = createResponse.path("id");
        System.out.println("  ✓ Ürün oluşturuldu, ID: " + itemId);

        // Şimdi güncelle
        String updateBody = "{\n" +
                "  \"name\": \"GÜNCELLENMIŞ ÜRÜN\",\n" +
                "  \"description\": \"YENİ AÇIKLAMA - Test başarılı!\",\n" +
                "  \"location\": \"Yeni Test Deposu\",\n" +
                "  \"unit\": \"Kutu\",\n" +
                "  \"storageArea\": \"Yeni Raf B\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(updateBody)
                .log().all()
                .when()
                .put(getItemEndpoint(itemId))  // PUT isteği
                .then()
                .log().all()
                .statusCode(200)  // ✅ Success
                .time(lessThan(getMaxResponseTime()))  // ✅ Response time
                .body("name", equalTo("GÜNCELLENMIŞ ÜRÜN"))  // ✅ Body kontrolü
                .body("description", containsString("YENİ AÇIKLAMA"))
                .body("location", equalTo("Yeni Test Deposu"))
                .body("unit", equalTo("Kutu"));

        System.out.println("✓ Ürün başarıyla güncellendi!");
    }

    @Test
    public void testUpdateOnlyName() {
        System.out.println("=== TEST 2: Sadece isim güncelle ===");

        // Mevcut bir ürünü güncelle (ID: 1)
        String updateBody = "{\n" +
                "  \"name\": \"Rigol DS1054Z - GÜNCELLENDİ " + System.currentTimeMillis() + "\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(updateBody)
                .log().all()
                .when()
                .put(getItemEndpoint("1"))
                .then()
                .log().all()
                .statusCode(200)
                .time(lessThan(getMaxResponseTime()))
                .body("name", containsString("GÜNCELLENDİ"))
                .body("id", equalTo("1"));

        System.out.println("✓ Ürün ismi güncellendi");
    }

    @Test
    public void testUpdateWithFullData() {
        System.out.println("=== TEST 3: Tüm alanları güncelle ===");

        // Yeni ürün oluştur
        String createBody = "{\n" +
                "  \"name\": \"Tam Güncellenecek\",\n" +
                "  \"description\": \"İlk hali\",\n" +
                "  \"location\": \"A1\",\n" +
                "  \"unit\": \"Adet\",\n" +
                "  \"storageArea\": \"Raf 1\"\n" +
                "}";

        String itemId = given()
                .contentType(ContentType.JSON)
                .body(createBody)
                .when()
                .post(getItemsEndpoint())
                .then()
                .statusCode(201)
                .extract().path("id");

        System.out.println("  ✓ Test ürünü oluşturuldu: " + itemId);

        // Tüm alanları güncelle
        String updateBody = "{\n" +
                "  \"name\": \"Tamamen Yeni İsim\",\n" +
                "  \"description\": \"Tamamen yeni açıklama - Test\",\n" +
                "  \"location\": \"Yeni Lokasyon Z9\",\n" +
                "  \"unit\": \"Paket\",\n" +
                "  \"storageArea\": \"Yeni Raf 99\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(updateBody)
                .when()
                .put(getItemEndpoint(itemId))
                .then()
                .statusCode(200)
                .body("name", equalTo("Tamamen Yeni İsim"))
                .body("description", containsString("Tamamen yeni"))
                .body("location", equalTo("Yeni Lokasyon Z9"))
                .body("unit", equalTo("Paket"))
                .body("storageArea", equalTo("Yeni Raf 99"));

        System.out.println("✓ Tüm alanlar başarıyla güncellendi!");
    }
}