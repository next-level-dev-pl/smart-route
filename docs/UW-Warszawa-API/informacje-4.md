### UW Warszawa API Informacje NR 4


Problem z odpytywaniem API

```json
{
    "result": null
}
```


Spróbuj użyć innego sposobu identyfikacji przystanku. API może wymagać nie tylko nazwy, ale także numeru słupka (`słupek`).

### **1. Pobranie listy słupków dla przystanku**
Najpierw sprawdź, jakie słupki są przypisane do przystanku "KINO FEMINA", wykonując zapytanie:

```
https://api.um.warszawa.pl/api/action/dbtimetable_get/?id=88cd555f-6f31-43ca-9de4-66c479ad5942&apikey=d17c9caf-0bfa-4e5f-a86a-9368320df800
```

To powinno zwrócić listę przystanków z ich numerami słupków.

### **2. Pobranie rozkładu dla konkretnego słupka**
Jeśli API wymaga dodatkowego parametru, spróbuj podać zarówno nazwę przystanku, jak i numer słupka, np.:

```
https://api.um.warszawa.pl/api/action/dbtimetable_get/?id=b27f4c17-5c50-4a5b-89dd-236b282bc499&name=KINO%20FEMINA&slupek=01&apikey=d17c9caf-0bfa-4e5f-a86a-9368320df800
```

Sprawdź też, czy API nie wymaga użycia ID przystanku (`708507`) zamiast jego nazwy. Jeśli tak, spróbuj takiego zapytania:

```
https://api.um.warszawa.pl/api/action/dbtimetable_get/?id=b27f4c17-5c50-4a5b-89dd-236b282bc499&busstopId=708507&apikey=d17c9caf-0bfa-4e5f-a86a-9368320df800
```
