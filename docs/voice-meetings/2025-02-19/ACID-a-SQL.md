# ACID a SQL?

### **Czy bazy SQL i NoSQL zapewniają ACID?**

✅ **Bazy SQL (relacyjne)** **zazwyczaj zapewniają pełne ACID**, ponieważ są zaprojektowane do obsługi transakcji i spójności danych.

⚠️ **Bazy NoSQL (nierelacyjne)** mogą, ale nie zawsze zapewniają pełne ACID – zależy od typu bazy i jej architektury. Wiele baz NoSQL stawia na **wydajność i skalowalność kosztem spójności** (tzw. **eventual consistency**).

---

### 🔹 **ACID a SQL**
Relacyjne bazy danych (PostgreSQL, MySQL, Oracle, SQL Server) **gwarantują ACID**, ponieważ:
- Mają **mechanizm transakcji** (`BEGIN`, `COMMIT`, `ROLLBACK`).
- Wykorzystują **blokady** i mechanizmy kontroli współbieżności (MVCC).
- Dane są **trwale zapisane** w logach transakcyjnych.

 **Przykład transakcji ACID w PostgreSQL:**
```sql
BEGIN;

UPDATE konto SET saldo = saldo - 100 WHERE id = 1;
UPDATE konto SET saldo = saldo + 100 WHERE id = 2;

COMMIT;
```
Jeśli którykolwiek z tych kroków się nie powiedzie, można wykonać `ROLLBACK`, a stan bazy wróci do poprzedniego.

---

## 🔹 **NoSQL a ACID**
Nie wszystkie bazy NoSQL zapewniają pełne ACID! Zależy to od konkretnej technologii:

| Baza NoSQL | ACID? | Uwagi |
|------------|-------|-------|
| **MongoDB** | ✅ (od wersji 4.0) | Obsługuje transakcje, ale tylko na poziomie dokumentów. |
| **Cassandra** | ❌ (BASE) | Stawia na **wydajność** kosztem spójności. |
| **DynamoDB** | ⚠️ Częściowe | Obsługuje atomiczne operacje na pojedynczych rekordach. |
| **Neo4j** | ✅ Tak | Jako baza grafowa wspiera pełne ACID. |
| **Redis** | ❌ Nie | Jest in-memory, nie gwarantuje trwałości. |

 **MongoDB od wersji 4.0 obsługuje transakcje ACID** na wielu dokumentach:
```javascript
const session = db.getMongo().startSession();
session.startTransaction();

db.konta.updateOne({ id: 1 }, { $inc: { saldo: -100 } });
db.konta.updateOne({ id: 2 }, { $inc: { saldo: 100 } });

session.commitTransaction();
session.endSession();
```
To działa podobnie jak transakcje w SQL!

---

## ** Podsumowanie**
| **Cecha** | **SQL (PostgreSQL, MySQL, etc.)** | **NoSQL (MongoDB, Cassandra, etc.)** |
|-----------|--------------------------------|-------------------------------|
| **ACID** | ✅ Tak, pełne wsparcie | ⚠️ Czasami, zależy od bazy |
| **Atomicity (A)** | ✅ Tak | ❌ Nie zawsze |
| **Consistency (C)** | ✅ Tak | ⚠️ Zazwyczaj *eventual consistency* |
| **Isolation (I)** | ✅ Tak | ❌ Ograniczone |
| **Durability (D)** | ✅ Tak | ⚠️ Może wymagać konfiguracji |

 **Wybór zależy od projektu!**
- Jeśli **spójność danych jest kluczowa → SQL**
- Jeśli **wydajność i skalowalność** są ważniejsze → NoSQL
