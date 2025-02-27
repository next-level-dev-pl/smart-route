# Formatter – Jak Korzystać z Narzędzia Automatycznego Formatowania Kodów
W naszym projekcie używamy Spotless do automatycznego formatowania kodu.
Poniżej znajdziesz instrukcje, jak korzystać z tego narzędzia zarówno lokalnie, jak i w ramach CI/CD.

## Lokalnie

**Sprawdzanie formatu kodu:**

Aby sprawdzić, czy kod jest sformatowany zgodnie z ustalonymi zasadami, uruchom poniższe polecenie:

`./gradlew spotlessCheck`

* **Efekt**:

  Jeśli zadanie zakończy się niepowodzeniem, oznacza to, że niektóre pliki nie spełniają standardów formatowania.

    W takim przypadku należy uruchomić automatyczne formatowanie lub poprawić kod ręcznie.


**Automatyczne formatowanie:**

Aby automatycznie sformatować kod, uruchom polecenie:

`./gradlew spotlessApply`

* **Efekt:**

  To polecenie przekształci wszystkie pliki zgodnie z naszą konfiguracją:

  - Usunie zbędne białe znaki (np. na końcu linii)
 
  - Dopasuje wcięcia (zgodnie z trybem AOSP Google Java Format, czyli 4-spacowy indent)

  - Uporządkuje importy, usuwając te nieużywane.


## W CI/CD

* **Integracja z pipeline:**

W naszej konfiguracji GitHub Actions zadanie spotlessCheck jest uruchamiane jako część pipeline.

W przypadku niezgodności kodu ze standardami, build zostanie przerwany, co wymusza poprawne sformatowanie przed scaleniem PR.


## Integracja ze środowiskiem IntelliJ Idea:

**1. Utwórz konfigurację External Tool:**
* otwórz **Settings/Preferences** (Ctrl+Alt+S)
* przejdź do sekcji **Tools → External Tools**
* kliknij **"+"** aby dodać nowe narzędzie

  Wypełnij pola:

  **Name**: np. Spotless Apply

  **Program**: wpisz `./gradlew` (lub pełną ścieżkę do skryptu, jeśli potrzebne).
  
  **Arguments**: spotlessApply

  **Working directory**: ustaw na `$ProjectFileDir$`


**2. Przypisz skrót klawiszowy do nowego narzędzia:**

* w ustawieniach przejdź do **Keymap** 
* wyszukaj nazwę utworzonego narzędzia (np. Spotless Apply) w sekcji **External Tools** 
* kliknij prawym przyciskiem myszy na wpis i wybierz **Add Keyboard Shortcut** 
* ustaw preferowaną kombinację 
(np. Ctrl+Alt+L – ale pamiętaj, że domyślnie ta kombinacja jest przypisana do wbudowanego formatera IntelliJ, więc możesz ją zmodyfikować lub wybrać inny skrót)
