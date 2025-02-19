# ACID 

#### ACID to zestaw właściwości, które zapewniają niezawodność operacji w bazach danych. Akronim oznacza:

🔹 A – Atomicity (Atomowość)  
🔹 C – Consistency (Spójność)  
🔹 I – Isolation (Izolacja)  
🔹 D – Durability (Trwałość)

Zapewnia, że bazy danych są niezawodne, bezpieczne i spójne, co jest kluczowe np. dla banków, e-commerce czy systemów rezerwacyjnych.

---

### 🔹 Atomicity (Atomowość)
Transakcja jest niepodzielna – albo wykonuje się w całości, albo wcale. Jeśli coś pójdzie nie tak, wszystkie zmiany są cofane.

<details>
  <summary>Przykład</summary>

🔹 Przelew bankowy: Jeśli wysyłasz 100 zł z konta A na konto B, operacja musi:  

🔹 Odjąć 100 zł od konta A

🔹 Dodać 100 zł do konta B

Jeśli jedno się nie powiedzie, cała transakcja jest cofana.
</details>

---

### 🔹 Consistency (Spójność)
Baza zawsze przechodzi ze stanu poprawnego do poprawnego. Jeśli jakaś operacja narusza reguły integralności, jest cofana.

<details>
  <summary>Przykład</summary>
🔹 Jeśli na koncie nie może być ujemnego salda, baza nie pozwoli na wypłatę 200 zł, jeśli masz tylko 100 zł.
</details>

---

### 🔹 Isolation (Izolacja)
Równoczesne transakcje nie wpływają na siebie nawzajem, tak jakby były wykonywane pojedynczo.

<details>
  <summary>Przykład</summary>
🔹 Dwie osoby próbują kupić ostatni bilet na koncert. Dzięki izolacji tylko jedna transakcja zostanie zatwierdzona, druga dostanie komunikat o braku miejsc.
</details>

---

### 🔹 Durability (Trwałość)
Gdy transakcja zostanie zatwierdzona, dane są trwałe i nie zostaną utracone – nawet w razie awarii systemu.

<details>
  <summary>Przykład</summary>
🔹 Jeśli bank potwierdził przelew, nawet po awarii serwera dane nie znikną – zapisano je na dysku lub w logach transakcyjnych.
</details>

---
