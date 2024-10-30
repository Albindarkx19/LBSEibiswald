
package DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    private static final String URL = "jdbc:mysql://localhost:3306/uebungen?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Verbindung zur Datenbank herstellen
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Verbindung zur Datenbank erfolgreich hergestellt!");

            // SQL-Abfrage erstellen
            String query = "SELECT * FROM t_lager"; // Ersetze 'deine_tabelle' durch den Namen deiner Tabelle
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            // Ergebnisse durchlaufen und anzeigen
            System.out.println("ArtikelID | Artikelname | Preis | Stückzahl");
            System.out.println("---------------------------------------------");
            while (resultSet.next()) {
                int artikelID = resultSet.getInt("ArtikelID");
                String artikelname = resultSet.getString("Artikelname");
                double preis = resultSet.getDouble("Preis");
                int stückzahl = resultSet.getInt("Stückzahl");

                System.out.printf("%d | %s | %.2f | %d%n", artikelID, artikelname, preis, stückzahl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Ressourcen schließen
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
                System.out.println("Datenbankverbindung geschlossen.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
