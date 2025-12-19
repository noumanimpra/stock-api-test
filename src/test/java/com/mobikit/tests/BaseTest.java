package com.mobikit.tests;

import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.RestAssured;
import org.junit.BeforeClass;

public class BaseTest {

    // .env dosyasından değerleri yükle
    protected static Dotenv dotenv;
    protected static String baseUri;
    protected static String itemsEndpoint;
    protected static long maxResponseTime;

    @BeforeClass
    public static void setup() {
        // .env dosyasını yükle
        dotenv = Dotenv.configure()
                .directory("./")  // Proje kök dizini
                .ignoreIfMissing()  // Dosya yoksa hata verme
                .load();

        // Environment variable read
        baseUri = dotenv.get("BASE_URI", "https://694440897dd335f4c35fce43.mockapi.io");
        itemsEndpoint = dotenv.get("ITEMS_ENDPOINT", "/items");
        maxResponseTime = Long.parseLong(dotenv.get("MAX_RESPONSE_TIME", "3000"));

        // Rest Assured'ı yapılandırma
        RestAssured.baseURI = baseUri;

        // Test başlangıç bilgisi
        System.out.println("=================================");
        System.out.println("🚀 Test Ortamı Hazırlandı");
        System.out.println("📍 Base URI: " + baseUri);
        System.out.println("🔗 Endpoint: " + itemsEndpoint);
        System.out.println("⏱️  Max Response Time: " + maxResponseTime + "ms");
        System.out.println("=================================\n");
    }

    // Helper metodlar
    protected static String getItemsEndpoint() {
        return itemsEndpoint;
    }

    protected static String getItemEndpoint(String id) {
        return itemsEndpoint + "/" + id;
    }

    protected static long getMaxResponseTime() {
        return maxResponseTime;
    }
}