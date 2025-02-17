# UW Warszawa API Informacje NR 1

Aby poprawnie skorzystać z endpointu API „Dostęp do rozkładów jazdy” udostępnianego przez m.st. Warszawa, należy wykonać następujące kroki:

1. **Rejestracja i uzyskanie klucza API**: Jeśli jeszcze tego nie zrobiłeś, zarejestruj się na stronie [api.um.warszawa.pl](https://api.um.warszawa.pl/), aby uzyskać unikalny klucz API (apikey).

2. **Identyfikator zasobu (resource_id)**: Każdy zasób w API posiada unikalny identyfikator. W przypadku rozkładów jazdy, identyfikator ten to `b27f4c17-5c50-4a5b-89dd-236b282bc499`.

3. **Parametry zapytania**:
    - `id`: identyfikator zasobu (resource_id).
    - `name`: nazwa przystanku, dla którego chcesz pobrać rozkład.
    - `apikey`: Twój unikalny klucz API.

4. **Przykładowe wywołanie**: Zakładając, że Twój klucz API to `d17c9caf-0bfa-4e5f-a86a-9368320df800` i chcesz pobrać rozkład dla przystanku o nazwie `CENTRUM`, zapytanie powinno wyglądać następująco:

   ```
   https://api.um.warszawa.pl/api/action/dbtimetable_get/?id=b27f4c17-5c50-4a5b-89dd-236b282bc499&name=CENTRUM&apikey=d17c9caf-0bfa-4e5f-a86a-9368320df800
   ```

   Upewnij się, że nazwa przystanku jest wpisana dokładnie tak, jak występuje w systemie, uwzględniając wielkość liter oraz polskie znaki diakrytyczne.

5. **Obsługa odpowiedzi**: API zwróci dane w formacie JSON zawierające informacje o rozkładzie jazdy dla danego przystanku. Upewnij się, że Twoja aplikacja potrafi poprawnie przetworzyć i wyświetlić te dane.

Pamiętaj, że korzystanie z API może podlegać ograniczeniom dotyczącym liczby zapytań w określonym czasie. Szczegółowe informacje znajdziesz w dokumentacji dostępnej na stronie [api.um.warszawa.pl](https://api.um.warszawa.pl/).

Dodatkowo, od 12 sierpnia 2024 roku, Zarząd Transportu Miejskiego w Warszawie udostępnia dane rozkładowe w formacie GTFS. Aktualne pliki można pobrać z [https://gtfs.ztm.waw.pl/last](https://gtfs.ztm.waw.pl/last). Więcej informacji na ten temat znajdziesz na stronie [ZTM Warszawa](https://www.ztm.waw.pl/pliki-do-pobrania/dane-rozkladowe/).

Jeśli napotkasz problemy lub masz dodatkowe pytania, warto skonsultować się z dokumentacją API lub skontaktować się bezpośrednio z administratorem serwisu. 