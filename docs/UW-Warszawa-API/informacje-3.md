# UW Warszawa API Informacje NR 3

Aby uzyskać poprawną nazwę przystanku celem użycia nazwy w API. Aby to zrobić, możesz skorzystać z poniższego zapytania, które zwróci listę dostępnych przystanków:

```
https://api.um.warszawa.pl/api/action/dbstore_get/?id=27c49cfe-1e88-4d80-9c8e-c0dd3a6e50d5&apikey=d17c9caf-0bfa-4e5f-a86a-9368320df800
```

To zapytanie zwróci listę przystanków wraz z ich szczegółami. Przykładowa nazwa przystanku, którą możesz użyć w swoim zapytaniu, to "CENTRUM 01". Upewnij się, że używasz dokładnej nazwy przystanku, uwzględniając numer słupka (np. "01", "02") oraz polskie znaki diakrytyczne.

Po zidentyfikowaniu odpowiedniej nazwy przystanku, możesz użyć jej w swoim zapytaniu do API, na przykład:

```
https://api.um.warszawa.pl/api/action/dbtimetable_get/?id=b27f4c17-5c50-4a5b-89dd-236b282bc499&name=CENTRUM%2001&apikey=d17c9caf-0bfa-4e5f-a86a-9368320df800
```

Pamiętaj, że nazwa przystanku musi być dokładnie taka, jak w danych źródłowych, aby API zwróciło poprawne wyniki. 