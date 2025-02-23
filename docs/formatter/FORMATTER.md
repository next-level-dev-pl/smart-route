# Formatter – Jak Korzystać z Narzędzia Automatycznego Formatowania Kodów
W naszym projekcie używamy Spotless do automatycznego formatowania kodu.
Poniżej znajdziesz instrukcje, jak korzystać z tego narzędzia zarówno lokalnie, jak i w ramach CI/CD.

## Lokalnie

**Sprawdzanie formatu kodu:**

Aby sprawdzić, czy kod jest sformatowany zgodnie z ustalonymi zasadami, uruchom poniższe polecenie:

./gradlew spotlessCheck

* **Efekt**:

  Jeśli zadanie zakończy się niepowodzeniem, oznacza to, że niektóre pliki nie spełniają standardów formatowania.

    W takim przypadku należy uruchomić automatyczne formatowanie lub poprawić kod ręcznie.


**Automatyczne formatowanie:**

Aby automatycznie sformatować kod, uruchom polecenie:

./gradlew spotlessApply

* **Efekt:**

  To polecenie przekształci wszystkie pliki zgodnie z naszą konfiguracją:

  - Usunie zbędne białe znaki (np. na końcu linii)
 
  - Dopasuje wcięcia (zgodnie z trybem AOSP Google Java Format, czyli 4-spacowy indent)

  - Uporządkuje importy, usuwając te nieużywane.


## W CI/CD

* **Integracja z pipeline:**

W naszej konfiguracji GitHub Actions zadanie spotlessCheck jest uruchamiane jako część pipeline.

W przypadku niezgodności kodu ze standardami, build zostanie przerwany, co wymusza poprawne sformatowanie przed scaleniem PR.
