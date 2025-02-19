### UW Warszawa API Informacje NR 5

Problem z odpytywaniem API

```json
{
    "result": null
}
```

Aby skutecznie pobrać rozkład jazdy dla przystanku "KINO FEMINA" za pomocą API m.st. Warszawy, zalecam skorzystanie z poniższego podejścia:

1. **Pobranie listy przystanków z numerami słupków**: Najpierw należy uzyskać pełną listę przystanków wraz z ich numerami słupków. Można to zrobić, wykonując zapytanie do odpowiedniego zasobu API.

2. **Wyszukanie konkretnego przystanku**: Po otrzymaniu listy przystanków, przeszukaj ją, aby znaleźć dokładną nazwę przystanku "KINO FEMINA" wraz z odpowiadającymi jej numerami słupków (np. "KINO FEMINA 01", "KINO FEMINA 02").

3. **Pobranie rozkładu jazdy dla wybranego słupka**: Mając dokładną nazwę przystanku z numerem słupka, wykonaj zapytanie do API, używając tej nazwy. Przykładowe zapytanie może wyglądać następująco:

   ```
   https://api.um.warszawa.pl/api/action/dbtimetable_get/?id=b27f4c17-5c50-4a5b-89dd-236b282bc499&name=KINO%20FEMINA%2001&apikey=d17c9caf-0bfa-4e5f-a86a-9368320df800
   ```

   Upewnij się, że nazwa przystanku jest dokładnie taka, jak w danych źródłowych, łącznie z numerem słupka i ewentualnymi znakami diakrytycznymi.

Jeśli mimo to nadal otrzymujesz pusty wynik, możliwe, że problem leży po stronie API lub danych. W takim przypadku warto skontaktować się bezpośrednio z administratorem serwisu lub sprawdzić dokumentację API pod kątem ewentualnych zmian czy aktualizacji.

