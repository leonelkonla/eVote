# eVote
Wilkommen in unserem Projekt dieses Semester! 

eVote ist unser Semesterprojekt im Modul Moderne Softwareentwicklung.

## Projektbeschreibung
'eVote': Online-Absmmungssystem für Bürgerbefragungen: 
Eine sichere Plattform für Online-Absmmungen, bei der Bürger über städsche Entscheidungen oder Projekte abstimmen können. 

### Funktioonale Anforderungen aus dem Kurs:
- Nutzerregistrierung und Authen fizierung: Registrierung für Bürger mit gesicherter Authen fizierung (z. 
B. durch E-Mail-Bestä gung oder 2-Faktor-Authen fizierung). 
- Absmmungsübersicht: Bürger können sich eine Liste der aktuellen Absmmungen ansehen, inklusive 
Beschreibung und Frist. 
- Absmmungsmechanismus: Bürger können an den Absmmungen teilnehmen und ihre Smme abgeben 
(einfacher Vo ng-Mechanismus). 
- Ergebnisübersicht: Nach Abschluss der Absmmung können Bürger die Ergebnisse einsehen. 
- Absmmungssicherheit: Sicherstellen, dass jeder Bürger nur 1x absmmt (z. B. durch eindeu ge Kennung)


## Technologien
- Java 25
- Eclipse als IDE 
- Git für das Versionskontrollsystem und GitHub, die webbasierte Cloud-Plattform für die Git-Workflows.
- usw.

## Entwicklung
Die Entwicklung erfolgt über eigene Git-Branches. Wird aber im main-branch als stabile version des Projekts verschoben.



### Was ist Git?
Kurz gesagt ein Versionskontrollsystem, es speichert Entwicklungszustände von Datein über den Projektverlauf hinweg. Es gehört mittlerweile zu der grundlegenden Arbeitsweise der Entwicklungsbranche(besonders im Open Source Bereich) und wurde speziell für die Zusammenarbeit von vielen Entwickler:innen von Linus Torvalds entwickelt. 
Dabei wird auf ein dezentrales System gesetzt, jede Person hat eine vollständige Kopie des Projekts oder "Repositories" und auch den gesamten Verlauf lokal auf dem Rechner. Es braucht einen zentralen Computer nur zum Austausch der eingereichten Arbeit. 

Prominente implementierungen von Git sind GitHub, GitLab oder Bitbucket. 

### Warum sollte Git verwendet werden?
Die Verwendung einer Versionskontrolle hilft dabei den Komplexen und zum Teil schwierig zu durchschauenden Entwicklungsprozess zu organisieren und besser nachvollziehbar zu machen. Es verbessert die Möglichkeit das verscheidene Personen parallel an dem gleichen Projekt arbeiten können und Fehler nachträglich rückgängig gemacht werden können. 
Entwickler haben eigene Umgebungen(Branches) in denen ausprobiert und experimentiert werden kann ohne das Hauptprojekt zu gefährden. 

### Wichtige Grundbefehle von Git (aus der Vorlesung)
create project (mit *git init* und *git clone* und *git help* verwenden)
*git status* and *git diff* (Dateiänderungen anzeigen und wann welcher Befehl zu verwenden ist)
*git add .* and *git commit -m "..."* (Dateien dem nächsten Commit hinzufügen und finale Änderungen dem Repository hinzufügen)
*git push* and *git pull* (Austausch mit externen Repositories)
*git remove* and *git move* (Dateien entfernen und verschieben)
More diff commands (effektiver Vergleich mit anderen Diff-Befehlen)
*git log* (Projektverlauf anzeigen)
*git branch "..."* and *git merge "..."* (mit verschiedenen Entwicklungszweigen arbeiten und Inhalte aus Entwicklungszweigen zusammenführen)

### Branches und Merge-Konflikte
Mit den Branches können unterschiedliche Personen und Kontexte oder Aufgaben getrennt werden. 
Mergekonflikte können auftauchen wenn Inhalte verschiedener Branches zusammengefügt(gemerged) werden. Konflikt bedeutet das an gleichen Stellen in der selben Datei unterschiedliche Inhalte stehen. Beim Lösen der Konflikte helfen moderne IDE's oder Anwendungen wie Github Desktop, die die Differenzbereiche hervorheben sodass dann entschieden werden kann welche Änderung gemacht weden soll.

### Lokal Repository und Remote Repository
**Working Directory:** Lokal auf deinem Rechner, alle deine Änderungen werden hier gespeichert.
**Staging Area:** Mit *git add* wählst du aus, welche Änderungen in den nächsten Commit sollen.
**Repository:** Mit *git commit* werden die vorgemerkten Änderungen dauerhaft als Version gespeichert.

### Github als Austauschplattform und Backup des Repositories
Github bietet als Plattform das Zuhause und die Zentrale Verwaltungsstelle für Projektdateien und Projektmanagement, hier werden committe Änderungen angezeigt und andere Personen können diese bei sich runterladen um sie zu Testen oder daran weiterzuarbeiten. 

### Beiträge im Team 
- **Leonel:** Init des Projekts, Abschnitte "Projektbeschreibung", "Funktioonale Anforderungen aus dem Kurs", "Technologien", "Entwicklung"

- **Jannis:** "Was ist Git?", "Warum sollte Git verwendet werden?", "Wichtige Grundbefehle von Git", "Branches und Merge-Konflikte", "Lokal Repository und Remote Repository", "Github als Austauschplattform "
