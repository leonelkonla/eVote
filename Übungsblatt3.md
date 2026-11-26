# 5. eVote: Online-Absmmungssystem für Bürgerbefragungen

	Eine sichere Plattform für Online-Absmmungen, bei der Bürger über städtische Entscheidungen oder 	Projekte absmmen können.

# Funktionale Anforderungen:

- Nutzerregistrierung und Authenfizierung: Registrierung für Bürger mit gesicherter Authentisierung (z. B. durch E-Mail-Bestätigung oder 2-Faktor-Authenfizierung).
- Abstimmungsübersicht: Bürger können sich eine Liste der aktuellen Abstimmungen ansehen, inklusive  Beschreibung und Frist.
- Abstimmungsmechanismus: Bürger können an den Abstimmungen teilnehmen und ihre Stimme abgeben  (einfacher Voting-Mechanismus).
- Ergebnisübersicht: Nach Abschluss der Abstimmung können Bürger die Ergebnisse einsehen.
- Abstimmungssicherheit: Sicherstellen, dass jeder Bürger nur 1x abstimmt (z. B. durch eindeutige Kennung)

# Aufgabe 1 – Event Storming

Zuerst fachliche Ereignisse indentifizieren, die bei eVote stattfinden


| Nr. | Event | Bedeutung |
|---|---|---|
| 1 | BuergerRegistriert | Bürgerkonto wurde angelegt |
| 2 | BuergerVerifiziert | Bürgeridentität wurde bestätigt |
| 3 | AbstimmungErstellt | Neue Abstimmung wurde vorbereitet |
| 4 | AbstimmungGestartet | Abstimmung wurde aktiviert |
| 5 | StimmeAbgegeben | Gültiger Stimmzettel wurde angenommen |
| 6 | AbstimmungGeschlossen | Abstimmung wurde beendet |
| 7 | ErgebnisVeroeffentlicht | Resultate wurden freigegeben |

	
Zwei wichtigste Events für das Projekt: `StimmeAbgegeben` und `ErgebnisVeroeffentlicht`.

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

	Diagram im Diagramm-Ordner

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

# Aufgabe 6

Auf Basis des in Aufgaben 1–5 entwickelten Domain-Driven-Design-Modells soll die Implementierung von eVote in kleinen, testbaren Schritten beginnen.


## 7. Technische Entscheidung

- Java 25 als Programmiersprache.
- Modularer Monolith: zunächst eine Anwendung mit klar abgegrenzten fachlichen Modulen, keine Microservices.
- DDD: Regeln in Entities/Value Objects/Aggregate Roots, orchestrierende Abläufe in Application Services.
- Repository-Interfaces: Trennung der Domain-Logik von In-Memory- bzw. späterer Datenbankpersistenz.
- Git und CI: kleinere Commits/PRs, automatisches Kompilieren und Ausführen von Tests; konkretes CI-Werkzeug wird im Team festgelegt.

## 8. Module / Bounded Contexts

| Kontext | Domain Model | Zuständigkeit |
|---|---|---|
| Identität & Berechtigung | `Nutzer`, `Buerger`, `Administrator` | Identität, Verifikation, Rollen |
| Abstimmungsverwaltung | `Abstimmung`, `Abstimmungsoption`, `Abstimmungszeitraum`, `Abstimmungsstatus` | Erstellung, Optionen, Laufzeit, Status |
| Stimmabgabe | `Teilnahme`, `Stimmzettel` | Prüfen und genau einmal abstimmen |
| Ergebnisauswertung | `Abstimmungsergebnis` | Stimmen aggregieren und Ergebnisse freigeben |

	Ein Bounded Context ist eine fachliche Modellgrenze
	
## 9. Teststrategie (TDD)

(Wird beschrieben...)

## 10. Ordnerstruktur

	Wird kontinuierlich aufgebaut.

## 11. Architektur

Für eVote verwenden wir einen modularen Monolithen mit einer DDD-orientierten Schichtenarchitektur. Die Anwendung ist in die Bereiche UI, Application, Domain und Infrastructure gegliedert. Repository-Schnittstellen entkoppeln die Fachlogik von der Datenspeicherung und folgen dem Ports-&-Adapters-Prinzip der hexagonalen Architektur. Dadurch bleibt das System übersichtlich, modular und gut testbar.

| Schicht| Verantwortung | Beispiel in der Anwendung eVote |
|---|---|---|
| UI | Stellt Informationen dar und nimmt Benutzeraktionen entgegen | Bürger klickt auf „Abstimmen“ |
| Application | Koordiniert die Anwendungsfälle wie Registrierung, Abstimmung und Ergebnisanzeige| StimmabgabeService koordiniert die Stimmabgabe |
| Domain | Enthält die fachlichen Objekte und Geschäftsregeln | Abstimmung prüft, ob die Abstimmung aktiv ist |
| Infrastructure | Implementiert die technische Speicherung über Repository-Schnittstellen | Repository speichert Teilnahme und Stimmzettel |
| Qualitätssicherung| Testet und baut die Anwendung automatisiert| ... |

	Grafische Darstelung im Diagrammenordner

