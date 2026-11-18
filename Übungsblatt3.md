# 5. eVote: Online-Absmmungssystem für Bürgerbefragungen

	Eine sichere Plattform für Online-Absmmungen, bei der Bürger über städtische Entscheidungen oder 	Projekte absmmen können.

# Funktionale Anforderungen:

- Nutzerregistrierung und Authenfizierung: Registrierung für Bürger mit gesicherter Authentisierung (z. B. durch E-Mail-Bestätigung oder 2-Faktor-Authenfizierung).
- Abstimmungsübersicht: Bürger können sich eine Liste der aktuellen Abstimmungen ansehen, inklusive  Beschreibung und Frist.
- Abstimmungsmechanismus: Bürger können an den Abstimmungen teilnehmen und ihre Stimme abgeben  (einfacher Voting-Mechanismus).
- Ergebnisübersicht: Nach Abschluss der Abstimmung können Bürger die Ergebnisse einsehen.
- Abstimmungssicherheit: Sicherstellen, dass jeder Bürger nur 1x abstimmt (z. B. durch eindeutige Kennung)

# Aufgabe 1 – Event Storming

- Zuerst fachliche Ereignisse indentifizieren, die bei eVote stattfinden


| Nr. | Event | Bedeutung |
|---|---|---|
| 1 | BuergerRegistriert | Bürgerkonto wurde angelegt |
| 2 | BuergerVerifiziert | Bürgeridentität wurde bestätigt |
| 3 | AbstimmungErstellt | Neue Abstimmung wurde vorbereitet |
| 4 | AbstimmungGestartet | Abstimmung wurde aktiviert |
| 5 | StimmeAbgegeben | Gültiger Stimmzettel wurde angenommen |
| 6 | AbstimmungGeschlossen | Abstimmung wurde beendet |
| 7 | ErgebnisVeroeffentlicht | Resultate wurden freigegeben |

	
- Zwei wichtigste Events für das Projekt: `StimmeAbgegeben` und `ErgebnisVeroeffentlicht`.

Für Event-Storming, Diagramm-Ordner schauen.

# Aufgabe 2 – Domänenmodell erstellen


| Nr. | Element | DDD-Typ |
|---|---|---|
| 1 | Bürger | Entity / Aggregate Root |
| 2 | Teilnahme | Entity für einmalige Teilnahme |
| 3 | Abstimmung | Entity / Aggregate Root |
| 4 | Abstimmungsoption | Entity innerhalb des Abstimmungs-Aggregates |
| 5 | Abstimmungszeitraum | Value Object |
| 6 | Stimmzettel | Eigenständige Entity |
| 7 | Abstimmungsergebnis | Read Model |
| 8 | Abstimmungsstatus | Entity |
| 9 | Admin | Entity / Aggregate Root |
| 10 | Nutzer | Entity |


# Aufgabe 3 – Bounded Contexts identifizieren

- Der Identitätskontext verwaltet Bürgerkonten.
- Der Abstimmungskontext verwaltet die Lebenszyklen der Abstimmungen.
- Die Stimmabgabe überprüft die fachlichen Bedingungen und nimmt gültige Stimmen an.
- Die Ergebnisauswertung berechnet und veröffentlicht die Resultate.

--> Diagram im Diagramm-Ordner

# Aufgabe 4 – Entitäten und Aggregate definieren


## 1. Verwaltung

**Aggregate Root:** Abstimmung

**Attribute:**
- id
- titel
- beschreibung
- status
- zeitraum
- optionen

**Methoden:**
- starten()
- schliessen()
- pruefeStimmabgabe()

**Geschäftsregeln:**
- Mindestens zwei Optionen.
- Nur aktive Abstimmungen akzeptieren Stimmen.
- Ungültige Optionen werden abgewiesen.

---

## 2. Identität

**Aggregate Root:** Buerger

**Attribute:**
- id
- email
- verifiziert

**Methoden:**
- verifizieren()
- pruefeBerechtigung()

**Geschäftsregeln:**
- Nur verifizierte Bürger können eine Stimme abgeben.

---

## 3. Stimmabgabe

### Entity: Teilnahme

**Attribute:**
- abstimmungId
- buergerId
- teilgenommenAm

**Methoden:**
- Keine Methoden angegeben.

### Entity: Stimmzettel

**Attribute:**
- id
- abstimmungId
- optionId
- abgegebenAm

**Methoden:**
- Keine Methoden angegeben.

**Geschäftsregeln:**
- Höchstens eine Teilnahme pro Bürger und Abstimmung.
- Teilnahme und Stimmzettel werden gemeinsam gespeichert.


# Aufgabe 5 – Domain Services und Repositories



## 4. Domain Services

| Domain Service | Aufgabe |
|---|---|
| TeilnahmeberechtigungsService | Prüft die fachliche Berechtigung eines Bürgers |
| ErgebnisBerechnungsService | Ermittelt die Anzahl gültiger Stimmen je Option |

---

## 5. Application Services

| Application Service | Methoden |
|---|---|
| StimmabgabeService | stimmeAbgeben(...) |
| AbstimmungsApplicationService | erstelle(), starten(), schliessen() |
| ErgebnisApplicationService | veroeffentlichen() |

---

## 6. Repositories

| Repository | Methoden |
|---|---|
| BuergerRepository | findById(), save() |
| AbstimmungRepository | findById(), findAktive(), save() |
| TeilnahmeRepository | exists(), insertUnique() |
| StimmzettelRepository | save(), findByAbstimmung() |
| ErgebnisRepository | findByAbstimmung(), save() |

# 


