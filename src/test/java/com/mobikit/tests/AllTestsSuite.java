package com.mobikit.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Tüm test sınıflarını toplu çalıştıran Test Suite
 *
 * Çalıştırma:
 * - IntelliJ'den: Bu dosyaya sağ tık -> Run 'AllTestsSuite'
 * - Maven'den: mvn clean test
 *
 * Test Sırası:
 * 1. GET testleri (StockItemGetTest)
 * 2. POST testleri (StockItemPostTest)
 * 3. PUT testleri (StockItemPutTest)
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        StockItemGetTest.class,
        StockItemPostTest.class,
        StockItemPutTest.class
})
public class AllTestsSuite {
    // Bu sınıf boş kalır - sadece anotasyonlar çalışır
    // Tüm testleri sırayla çalıştırır
}