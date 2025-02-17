# UW Warszawa API Informacje NR 2

Wynik (`"result": null`) podczas próby pobrania rozkładu jazdy dla przystanku o nazwie "CENTRUM". Może to wynikać z niepoprawnej nazwy przystanku lub jej formatu. Aby znaleźć poprawne nazwy przystanków, możesz skorzystać z następujących źródeł:

1. **Mapa przystanków Warszawskiego Transportu Publicznego (WTP)**: Interaktywna mapa dostępna pod adresem [https://www.arcgis.com/apps/instant/sidebar/index.html?appid=ea9abe87a62845a2a580a2d337fd428e](https://www.arcgis.com/apps/instant/sidebar/index.html?appid=ea9abe87a62845a2a580a2d337fd428e) umożliwia przeglądanie lokalizacji oraz nazw przystanków. Możesz na niej znaleźć interesujący Cię przystanek i sprawdzić jego dokładną nazwę.

2. **Dane rozkładowe udostępniane przez Zarząd Transportu Miejskiego (ZTM) w Warszawie**: ZTM udostępnia pliki z danymi rozkładowymi, które zawierają informacje o przystankach, liniach oraz rozkładach jazdy. Pliki te można pobrać ze strony [https://www.ztm.waw.pl/pliki-do-pobrania/dane-rozkladowe/](https://www.ztm.waw.pl/pliki-do-pobrania/dane-rozkladowe/). Po pobraniu odpowiednich plików (np. w formacie GTFS), możesz przeszukać je w celu znalezienia dokładnej nazwy przystanku.

Upewnij się, że w zapytaniu do API używasz dokładnej nazwy przystanku, zgodnej z danymi ZTM, uwzględniając wielkość liter oraz polskie znaki diakrytyczne. Jeśli po weryfikacji nazwy problem nadal występuje, warto skonsultować się z dokumentacją API lub skontaktować się bezpośrednio z administratorem serwisu w celu uzyskania dalszej pomocy. 