# Tworzenie lub generowanie dokumentacji API

Tworzenie dokumentacji API oraz uzyskiwanie rozpiski endpointów dla istniejącego API to dwa różne zagadnienia. Omówię obie kwestie.

---

## **1. Tworzenie dokumentacji API**
Dobra dokumentacja API powinna zawierać kilka kluczowych elementów:

### **A. Podstawowe informacje**
- **Opis API** – do czego służy, kto może z niego korzystać.
- **BASE_URL** – główny adres API.
- **Format danych** – np. `JSON` czy `XML`.
- **Autoryzacja** – czy API wymaga klucza (`API Key`), OAuth 2.0, JWT, itp.

### **B. Opis endpointów**
Każdy endpoint powinien być opisany w następujący sposób:
- **Ścieżka** (`GET /api/users`)
- **Opis działania** (`Zwraca listę użytkowników`)
- **Metoda HTTP** (`GET`, `POST`, `PUT`, `DELETE`)
- **Parametry wejściowe** (query params, body params)
- **Przykładowe zapytanie** (`curl`, `Postman`, `JavaScript fetch`)
- **Przykłowa odpowiedź** (statusy 200, 400, 500)
- **Błędy** – jakie mogą się pojawić i co oznaczają

### **C. Narzędzia do dokumentacji**
- **Swagger (OpenAPI)** – popularny standard, interaktywna dokumentacja
- **Postman API Documentation** – generowanie dokumentacji na podstawie requestów
- **Redoc** – czytelna dokumentacja w formacie OpenAPI
- **ReadMe.io** – dedykowane narzędzie do dokumentacji API

Jeśli tworzysz dokumentację dla własnego API, warto rozważyć **Swagger UI**, który umożliwia testowanie endpointów na żywo.

---

## **2. Jak uzyskać rozpiskę endpointów dla istniejącego API, jeśli mamy tylko BASE_URL?**
Jeśli nie masz dokumentacji API, ale masz **BASE_URL**, oto sposoby na jego zbadanie:

### **A. Sprawdzenie Swagger/OpenAPI**
1. Spróbuj otworzyć typowe ścieżki:
   ```
   BASE_URL/swagger
   BASE_URL/swagger-ui/
   BASE_URL/api/docs
   BASE_URL/openapi.json
   BASE_URL/openapi.yaml
   ```
2. Jeśli API ma Swaggera, dostaniesz interaktywną dokumentację.

### **B. Wykorzystanie Postmana**
1. Otwórz Postmana i wpisz `BASE_URL`
2. Jeśli serwer zwraca dane, sprawdź nagłówki i odpowiedzi.
3. Przetestuj typowe endpointy (`/users`, `/auth`, `/data`, `/status`).

### **C. Przeglądanie nagłówków w przeglądarce**
1. Otwórz stronę korzystającą z API.
2. Włącz `DevTools` (`F12` → `Network`).
3. Filtruj ruch po `XHR/Fetch`, zobacz jakie endpointy są wywoływane.

### **D. Automatyczne eksplorowanie API**

```diff
- Ostrzeżenie!
```
Należy zwrócić szczególną uwagę w kwestiach legalności skanowania zasobów sieciowych i ew. grożących z tego powodu konsekwencji! 

Jeśli API nie jest dobrze zabezpieczone, możesz spróbować narzędzi:
- `dirsearch` (Python) – skanuje katalogi API
- `Burp Suite` – przechwytuje ruch API
- `Postman API Explorer` – sprawdza możliwe ścieżki

### **E. Sprawdzenie kodu źródłowego aplikacji**
Jeśli API jest używane przez aplikację webową/mobilną, możesz:
- Sprawdzić pliki `JavaScript` (`app.js`, `api.js`).
- Zanalizować pliki `.json` w kodzie frontendu.
- Przechwycić ruch HTTP (`Fiddler`, `Wireshark`).

---

### **Podsumowanie**
- Jeśli tworzysz **własne API**, użyj **Swaggera** lub **Postmana** do dokumentacji.
- Jeśli chcesz **odkryć istniejące API**, sprawdź `BASE_URL/swagger`, przeanalizuj ruch w `DevTools`, lub użyj `dirsearch`.
