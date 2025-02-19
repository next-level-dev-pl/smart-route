### UW Warszawa API Informacje NR 6

Problem z odpytywaniem API

```json
{
    "result": null
}
```

Wygląda na to, że API może wymagać dodatkowych parametrów lub innego sposobu odwoływania się do przystanku. Spróbujmy podejść do tego systematycznie.

---

### **1. Sprawdzenie poprawnej nazwy przystanku i słupka**
Zamiast wpisywać ręcznie nazwę przystanku, pobierz listę przystanków wraz z ich numerami słupków za pomocą tego zapytania:

```
https://api.um.warszawa.pl/api/action/dbstore_get/?id=27c49cfe-1e88-4d80-9c8e-c0dd3a6e50d5&apikey=d17c9caf-0bfa-4e5f-a86a-9368320df800
```

To zapytanie powinno zwrócić wszystkie dostępne przystanki, dzięki czemu będziesz mógł znaleźć dokładną nazwę "KINO FEMINA" oraz dostępne słupki.

---

### **2. Pobranie identyfikatora przystanku**
Jeśli API wymaga identyfikatora zamiast nazwy przystanku, możesz spróbować pobrać listę ID przystanków:

```
https://api.um.warszawa.pl/api/action/dbstore_get/?id=ab75c33d-3a26-4342-b36a-6e5fef0a3ac3&apikey=d17c9caf-0bfa-4e5f-a86a-9368320df800
```

W wynikach wyszukaj **"KINO FEMINA"** i sprawdź, jakie ID ma przypisane.

---

### **3. Pobranie rozkładu dla konkretnego ID**
Gdy znajdziesz poprawne **ID przystanku**, spróbuj użyć go w zapytaniu o rozkład:

```
https://api.um.warszawa.pl/api/action/dbtimetable_get/?id=b27f4c17-5c50-4a5b-89dd-236b282bc499&busstopId=TWOJE_ID_PRZYSTANKU&apikey=d17c9caf-0bfa-4e5f-a86a-9368320df800
```

---

### **4. Inne możliwe przyczyny błędu**
- **API może nie zawierać rozkładu dla tego przystanku** – niektóre przystanki mogą nie mieć zapisanych rozkładów.
- **Błędna struktura zapytania** – upewnij się, że klucz API działa poprawnie, a nazwa przystanku jest zgodna z danymi w API.
- **Problem po stronie API** – możliwe, że dane są nieaktualne lub występują błędy po stronie serwera.
