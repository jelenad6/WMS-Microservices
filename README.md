# WMS Mikroservisni Sistem

Sistem za upravljanje magacinom (WMS) realizovan kroz mikroservisnu arhitekturu korišćenjem Java Spring Boot okruženja, RabbitMQ sistema za razmenu poruka i MySQL baza podataka.

## Arhitektura sistema

Sistem se sastoji od tri mikroservisa:

* **Inventory Service** - upravljanje artiklima, zalihama, prijemom robe, rezervacijama i otpremnicama.
* **Orders Service** - obrada porudžbina, zahteva za isporuku, faktura i Saga orkestracije.
* **Billing Service** - obrada uplata i potvrda plaćanja.

Komunikacija između servisa realizovana je asinhrono putem RabbitMQ sistema za razmenu poruka.

Svaki mikroservis koristi sopstvenu bazu podataka.

## Funkcionalnosti

* Upravljanje artiklima
* Evidencija stanja zaliha po magacinima
* Prijem robe
* Kreiranje i potvrda porudžbina
* Rezervacija zaliha
* Implementacija Saga obrasca
* Obrada uplata
* Automatsko otkazivanje isteklih rezervacija
* Kreiranje faktura
* Kreiranje otpremnica
* Razmena događaja između mikroservisa putem RabbitMQ

## Pokretanje sistema

Pokretanje kompletnog sistema:

```bash
docker compose up
```


