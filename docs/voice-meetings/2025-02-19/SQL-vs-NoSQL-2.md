W projekcie **Smart-route** wybór bazy danych powinien zależeć od kilku czynników, takich jak:

- **Rodzaj danych** (strukturalne vs niestrukturalne)
- **Wydajność i skalowalność**
- **Rodzaj zapytań, jakie będą wykonywane**

### SQL vs. NoSQL – analiza pod kątem Twojego projektu

#### **Opcja 1: Relacyjna baza danych (SQL – np. PostgreSQL, MySQL)**
**Kiedy warto?**
- Jeśli dane mają **strukturę relacyjną**, np. tabele `przystanki`, `linie_autobusowe`, `rozkłady_jazdy`.
- Jeśli chcesz zapewnić **spójność danych (ACID)**, np. nie chcesz sytuacji, w której autobus "znika" z systemu w trakcie aktualizacji.
- Jeśli planujesz wykonywać **skomplikowane zapytania SQL** łączące różne tabele (JOIN-y).
- Jeśli dane nie będą się bardzo dynamicznie zmieniać (np. nie masz ogromnej ilości zapisów na sekundę).

**Przykładowa struktura bazy w SQL (PostgreSQL)**

<details>
  <summary>Pokaż kod</summary>

  <pre><code class="language-sql">
    CREATE TABLE przystanki (
        id SERIAL PRIMARY KEY,
        nazwa VARCHAR(255) NOT NULL,
        latitude DECIMAL(9,6),
        longitude DECIMAL(9,6)
    );
    
    CREATE TABLE linie_autobusowe (
        id SERIAL PRIMARY KEY,
        numer VARCHAR(10) NOT NULL
    );
    
    CREATE TABLE rozklady_jazdy (
        id SERIAL PRIMARY KEY,
        przystanek_id INT REFERENCES przystanki(id),
        linia_id INT REFERENCES linie_autobusowe(id),
        czas_odjazdu TIME NOT NULL
    );
  </code></pre>
</details>

➡ **Rekomendacja:** PostgreSQL – wspiera JSONB, co pozwoli na przechowywanie dynamicznych danych, np. tras.

---

#### **Opcja 2: NoSQL (np. MongoDB, Redis, Neo4j)**
**Kiedy warto?**
- Jeśli dane są **bardziej dynamiczne** i zmieniają swoją strukturę (np. dynamiczne zmiany tras, nowe pola).
- Jeśli potrzebujesz **szybkiego dostępu do danych** bez kosztownych operacji JOIN.
- Jeśli chcesz przechowywać dane w formie **dokumentów JSON** zamiast tabel.

**Kiedy MongoDB?**
- Gdy trasy, przystanki i rozkłady można przechowywać jako dokumenty JSON, np.:
```json
{
  "nazwa": "Metro Centrum",
  "linie": [
    {"numer": "175", "godziny_odjazdow": ["08:30", "09:00", "09:30"]}
  ],
  "lokalizacja": {"lat": 52.2298, "lng": 21.0118}
}
```
➡ **MongoDB sprawdzi się, jeśli chcesz łatwo przechowywać i przetwarzać dynamiczne dane.**

**Kiedy Neo4j?**
- Jeśli głównym wyzwaniem będzie **optymalizacja tras** (grafy!), gdzie węzły = przystanki, a krawędzie = połączenia.
- Możesz łatwo znajdować najkrótsze drogi między punktami:
```cypher
MATCH (start:Przystanek {nazwa: "A"})-[:LINIA]->(end:Przystanek {nazwa: "B"})
RETURN shortestPath((start)-[:LINIA*]->(end))
```
➡ **Neo4j będzie świetny, jeśli aplikacja mocno polega na analizie tras i grafowych relacjach.**

---

### **Ostateczna rekomendacja**
- **Jeśli system ma być klasycznym rozkładem jazdy → PostgreSQL**
- **Jeśli dane są dynamiczne i często zmieniają strukturę → MongoDB**
- **Jeśli kluczowa jest optymalizacja tras (grafy) → Neo4j**
- **Jeśli potrzebujesz cache do szybkich odpowiedzi → Redis jako dodatek**

Jeśli zależy na elastyczności, **możesz połączyć SQL + NoSQL**:
- PostgreSQL dla relacyjnych danych
- Redis do cache’owania zapytań
- Neo4j do znajdowania najlepszych tras
