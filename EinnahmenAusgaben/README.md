# Einnahmen-Ausgaben-Verwaltung

Dieses Projekt ist eine Java-basierte Desktop-Anwendung zur Verwaltung von Einnahmen und Ausgaben. Die Benutzeroberfläche wird mit Swing implementiert und ermöglicht das Hinzufügen, Löschen und Filtern von Buchungen. Die Anwendung stellt außerdem eine Verbindung zu einer MySQL-Datenbank her, um die Buchungsdaten zu speichern und übersichtlich darzustellen.

## Features

- **Hinzufügen von Buchungen**: Einnahmen oder Ausgaben können über ein benutzerfreundliches Dialogfenster hinzugefügt werden. Der Benutzer wählt die Kategorie, gibt den Betrag sowie zusätzliche Informationen ein.
- **Buchungen löschen**: Markierte Einträge können leicht aus der Tabelle und der Datenbank entfernt werden, um veraltete oder falsche Buchungen zu korrigieren.
- **Filterfunktion**: Die Filterfunktion ermöglicht eine flexible Anzeige der Daten. Es ist möglich, nach Datum, Einnahmen oder Ausgaben zu filtern, um nur die relevanten Einträge anzuzeigen.
- **Datenbankanbindung**: Die Anwendung speichert alle Buchungen in einer MySQL-Datenbank, sodass die Daten auch nach einem Neustart der Anwendung erhalten bleiben.
- **Zusammenfassungsanzeige**: Die Gesamteinnahmen, Gesamtausgaben sowie die Anzahl der Buchungen werden automatisch berechnet und angezeigt, um dem Benutzer eine schnelle Übersicht über die finanzielle Lage zu geben.

## Voraussetzungen

- **Java 8 oder höher**: Die Anwendung erfordert eine Java-Laufzeitumgebung in Version 8 oder höher.
- **MySQL-Datenbank**: Eine MySQL-Datenbank wird benötigt, um die Buchungen persistent zu speichern.
- **JDBC-Treiber für MySQL**: Der JDBC-Treiber muss vorhanden sein, damit die Anwendung mit der MySQL-Datenbank kommunizieren kann.

## Installation

1. **Repository klonen**:
   ```bash
   git clone <repository-url>
   ```

2. **MySQL-Datenbank einrichten**:
   - Erstelle eine MySQL-Datenbank namens `kostenrechner`.
   - Führe die SQL-Skripte im Ordner `database` aus, um die Tabellen zu erstellen.

3. **Projekt kompilieren und ausführen**:
   - Stelle sicher, dass der MySQL JDBC-Treiber (`mysql-connector-java.jar`) vorhanden ist.
   - Kompiliere das Projekt mit einem Java-Compiler:
     ```bash
     javac -cp .:mysql-connector-java.jar Table.java
     ```
   - Führe die Anwendung aus:
     ```bash
     java -cp .:mysql-connector-java.jar Table
     ```

## Nutzung

- **Neue Buchung hinzufügen**: Klicke auf den "neu"-Button, wähle eine Kategorie, gib den Betrag und die Zusatzinformationen ein. Die Buchung wird sowohl in der Tabelle als auch in der MySQL-Datenbank gespeichert.
- **Buchung löschen**: Markiere eine Zeile und klicke auf den "löschen"-Button, um die Buchung aus der Datenbank und der Tabelle zu entfernen.
- **Filtern**: Klicke auf "Filter", um die Buchungen nach Einnahmen, Ausgaben oder Datum zu filtern. Dies hilft, nur die relevanten Einträge anzuzeigen, z. B. nur alle Ausgaben im letzten Monat.
- **Filter löschen**: Klicke auf "Filter löschen", um alle angewendeten Filter zu entfernen und die gesamte Buchungsliste anzuzeigen.

## Datenbank-Konfiguration

Die Datenbank-Verbindungsparameter befinden sich im Quellcode (`Table.java`) und müssen ggf. an deine lokale Umgebung angepasst werden:

```java
private static final String URL = "jdbc:mysql://localhost:3306/kostenrechner?useSSL=false&serverTimezone=UTC";
private static final String USER = "root";
private static final String PASSWORD = "<dein-passwort>";
```
Passe den Benutzernamen (`USER`) und das Passwort (`PASSWORD`) entsprechend deinen Datenbankeinstellungen an.

## Lizenz

Dieses Projekt steht unter der MIT-Lizenz. Weitere Informationen findest du in der Datei `LICENSE`.

