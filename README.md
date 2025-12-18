# Stock API Test Automation

Bu proje, **Rest Assured** kütüphanesi kullanılarak geliştirilmiş bir **API Test Otomasyon** projesidir. MockAPI üzerinde çalışan bir stok yönetim sistemi için **GET, POST, PUT ve DELETE** operasyonlarının otomatik testlerini içerir.

---

## Proje Amacı

Bu proje, **Yazılım Test Mühendisliği** dersi kapsamında hazırlanmıştır. Amaç, RESTful API servislerinin:

* Doğru HTTP status code döndürmesini
* Beklenen response body yapısını sağlamasını
* Performans kriterlerini (response time) karşılamasını

otomatik testler ile doğrulamaktır.

---

## Kullanılan Teknolojiler

* **Java 8+**
* **Maven** – Bağımlılık yönetimi
* **JUnit 4** – Test framework
* **Rest Assured** – API test kütüphanesi
* **Dotenv** – Environment variable yönetimi
* **Hamcrest** – Assertion kütüphanesi

---

## Proje Yapısı

```
stock-api-test-automation/
├── src/
│   └── test/
│       └── java/
│           └── com/mobikit/tests/
│               ├── AllTestsSuite.java          # Tüm testleri toplu çalıştırır
│               ├── BaseTest.java               # Ortak test yapılandırması
│               ├── StockItemGetTest.java       # GET testleri
│               ├── StockItemPostTest.java      # POST testleri
│               ├── StockItemPutTest.java       # PUT testleri
│               └── StockItemDeleteTest.java    # DELETE testleri
├── .env                                        # API endpoint yapılandırması
├── pom.xml                                     # Maven bağımlılıkları
└── README.md                                   # Proje dokümantasyonu
```

---

## Kurulum

### 1. Gereksinimler

* Java 8 veya üzeri
* Maven 
* IntelliJ IDEA / Eclipse 

---

### 2. Projeyi Klonlayın

```bash
git clone https://github.com/noumanimpra/stock-api-test.git
cd stock-api-test
```

---

### 3. `.env` Dosyasını Yapılandırın

Proje kök dizininde `.env` dosyası oluşturun:

```env
BASE_URI=https://mockapiurl.mockapi.io
ITEMS_ENDPOINT=/items
MAX_RESPONSE_TIME=3000
```

---

### 4. Bağımlılıkları Yükleyin

```bash
mvn clean install
```

---

## ▶️ Testleri Çalıştırma

### Tüm Testleri Çalıştırma

```bash
mvn clean test
```

---

### IntelliJ IDEA Üzerinden Çalıştırma

1. `AllTestsSuite.java` dosyasına sağ tıklayın
2. **Run 'AllTestsSuite'** seçeneğini seçin

---

### Tek Bir Test Sınıfını Çalıştırma

```bash
mvn test -Dtest=StockItemGetTest
```

---

## Test Senaryoları

### GET Testleri – `StockItemGetTest`

| Test                    | Açıklama             | Kontroller                      |
| ----------------------- | -------------------- | ------------------------------- |
| `testGetAllItems`       | Tüm ürünleri getirir | Status 200, Response time, Body |
| `testGetSingleItem`     | Tek ürün detayı      | ID kontrolü, Name varlığı       |
| `testResponseTimeCheck` | Yanıt süresi ölçümü  | Max 3000ms                      |

---

### POST Testleri – `StockItemPostTest`

| Test                      | Açıklama                | Kontroller        |
| ------------------------- | ----------------------- | ----------------- |
| `testCreateNewItem`       | Yeni ürün oluşturur     | Status 201        |
| `testCreateMultipleItems` | Birden fazla ürün ekler | Her biri için 201 |

---

### PUT Testleri – `StockItemPutTest`

| Test                     | Açıklama         | Kontroller      |
| ------------------------ | ---------------- | --------------- |
| `testUpdateExistingItem` | Ürün güncelleme  | Status 200      |
| `testUpdateOnlyName`     | Kısmi güncelleme | Alan doğrulama  |
| `testUpdateWithFullData` | Tam güncelleme   | Body validation |

---

### DELETE Testleri – `StockItemDeleteTest`

| Test             | Açıklama   | Kontroller               |
| ---------------- | ---------- | ------------------------ |
| `testDeleteItem` | Ürün silme | Create → Delete → Verify |

> 🔎 **Not:**
> DELETE işleminden sonra aynı ürün için yapılan **GET isteğinin 404 (Not Found)** dönmesi **beklenen ve doğru davranıştır**. Bu durum testlerde özellikle doğrulanmaktadır.

---

##  Örnek Test Çıktısı

```
=================================
🚀 Test Ortamı Hazırlandı
📍 Base URI: https://mymockapi.mockapi.io
🔗 Endpoint: /items
⏱️  Max Response Time: 3000ms
=================================
=== TEST : İlk ürünü getir ===
Request method:	GET

{
    "id": "1",
    "name": "Rigol DS1054Z - GÜNCELLENDİ 1766087262190",
    "description": "4 Kanallı, 50 MHz bant genişliği, 1 GSa/s örnekleme hızı, USB bağlantılı, hacklenebilir.",
    "location": "Test Tezgahı 1",
    "unit": "Adet",
    "storageArea": "Ölçüm Cihazları Rafı",
    "createdAt": 1766081000
}
✓ Test başarılı!
=== TEST : Tüm ürünleri getir ===
Request method:	GET

[
    {
        "id": "1",
        "name": "Rigol DS1054Z - GÜNCELLENDİ 1766087262190",
        "description": "4 Kanallı, 50 MHz bant genişliği, 1 GSa/s örnekleme hızı, USB bağlantılı, hacklenebilir.",
        "location": "Test Tezgahı 1",
        "unit": "Adet",
        "storageArea": "Ölçüm Cihazları Rafı",
        "createdAt": 1766081000
    },
	.
	.
	.

    {
        "name": "Tamamen Yeni İsim",
        "description": "Tamamen yeni açıklama - Test",
        "location": "Yeni Lokasyon Z9",
        "unit": "Paket",
        "storageArea": "Yeni Raf 99",
        "createdAt": 1766083422,
        "id": "64"
    }
]
✓ Test başarılı!

=== TEST : Response time kontrolü ===
✓ API 430ms'de yanıt verdi (Limit: 3000ms)

=== TEST : Birden fazla ürün oluştur ===
  	✓ Kapasitör eklendi
  	✓ IC 555 eklendi
  	✓ LM358 eklendi
✓ Tüm ürünler eklendi!

=== TEST : Yeni ürün oluştur (POST) ===
Request method:	POST

{
    "name": "Test Ürünü - 1766087780494",
    "description": "Rest Assured ile otomatik oluşturuldu",
    "location": "Test Deposu",
    "unit": "Adet",
    "storageArea": "Test Rafı",
    "createdAt": 1766083701,
    "id": "68"
}
✓ Yeni ürün oluşturuldu!


=== TEST : Mevcut ürünü güncelle (PUT) ===
  ✓ Ürün oluşturuldu, ID: 69
Request method:	PUT

{
    "name": "GÜNCELLENMIŞ ÜRÜN",
    "description": "YENİ AÇIKLAMA - Test başarılı!",
    "location": "Yeni Test Deposu",
    "unit": "Kutu",
    "storageArea": "Yeni Raf B",
    "createdAt": 1766083641,
    "id": "69"
}
✓ Ürün başarıyla güncellendi!

=== TEST : Tüm alanları güncelle ===
  ✓ Test ürünü oluşturuldu: 70
✓ Tüm alanlar başarıyla güncellendi!

=== TEST : Sadece isim güncelle ===
Request method:	PUT

{
    "id": "1",
    "name": "Rigol DS1054Z - GÜNCELLENDİ 1766087782899",
    "description": "4 Kanallı, 50 MHz bant genişliği, 1 GSa/s örnekleme hızı, USB bağlantılı, hacklenebilir.",
    "location": "Test Tezgahı 1",
    "unit": "Adet",
    "storageArea": "Ölçüm Cihazları Rafı",
    "createdAt": 1766081000
}
✓ Ürün ismi güncellendi


=== TOPLU SİLME TESTİ ===
  ✓ Ürün 1 oluşturuldu (ID: 71)
  ✓ Ürün 1 silindi
  ✓ Ürün 2 oluşturuldu (ID: 71)
  ✓ Ürün 2 silindi
  ✓ Ürün 3 oluşturuldu (ID: 71)
  ✓ Ürün 3 silindi
✅ Toplu silme tamamlandı: 3/3

```

---


## 🔍 Test Edilen API Endpoint’leri

| Method | Endpoint      | Açıklama             |
| ------ | ------------- | -------------------- |
| GET    | `/items`      | Tüm ürünleri listele |
| GET    | `/items/{id}` | Tek ürün getir       |
| POST   | `/items`      | Yeni ürün oluştur    |
| PUT    | `/items/{id}` | Ürün güncelle        |
| DELETE | `/items/{id}` | Ürün sil             |

---


MTH2526-G50 Sektörde Yazılım Test Mühendisliği Dersi – 2025

Nouman NTELI IMPRAIM

---
Not: Test edilecek servis olarak izole bir test ortamında çalışmak adına MockAPI altyapısı tercih edilmiştir.