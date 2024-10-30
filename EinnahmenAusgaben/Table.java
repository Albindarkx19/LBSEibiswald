package EinnahmenAusgaben;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Table {

    // Datenbank-Verbindungsparameter
    private static final String URL = "jdbc:mysql://localhost:3306/kostenrechner?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static JLabel totalEinnahmenLabel = new JLabel("Gesamteinnahmen: 0.00");
    private static JLabel totalAusgabenLabel = new JLabel("Gesamtausgaben: 0.00");
    private static JLabel totalEntriesLabel = new JLabel("Anzahl der Einträge: 0");
    private static JLabel currentDateLabel = new JLabel("Heutiges Datum: " + LocalDate.now());

    public static void main(String[] argsStrings) {
        // Frame erstellen
        JFrame frame = new JFrame("Einnahmen und Ausgaben");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Tabellenmodell und Tabelle erstellen
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);

        // Spaltenüberschriften
        tableModel.addColumn("ID");
        tableModel.addColumn("KategorieID");
        tableModel.addColumn("Datum");
        tableModel.addColumn("Zusatzinfo");
        tableModel.addColumn("Einnahmen");
        tableModel.addColumn("Ausgaben");

        // RowSorter für die Tabelle zum Filtern verwenden
        TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(rowSorter);

        // ScrollPane für die Tabelle
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Panel für die unteren Buttons (Löschen, Neu, Filter, Filter löschen)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Löschen Button
        JButton loeschenButton = new JButton("löschen");
        buttonPanel.add(loeschenButton);

        // Neu Button
        JButton neuButton = new JButton("neu");
        buttonPanel.add(neuButton);

        // Filter Button
        JButton filterButton = new JButton("Filter");
        buttonPanel.add(filterButton);

        // Filter löschen Button
        JButton clearFilterButton = new JButton("Filter löschen");
        buttonPanel.add(clearFilterButton);

        // Gesamtbetrag, aktuelles Datum und Anzahl der Einträge Label
        JPanel statusPanel = new JPanel(new GridLayout(4, 1));
        statusPanel.add(currentDateLabel); // Aktuelles Datum anzeigen
        statusPanel.add(totalEinnahmenLabel); // Gesamteinnahmen anzeigen
        statusPanel.add(totalAusgabenLabel); // Gesamtausgaben anzeigen
        statusPanel.add(totalEntriesLabel); // Anzahl der Einträge anzeigen
        frame.add(statusPanel, BorderLayout.SOUTH);

        // Buttons unten hinzufügen
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Suchfeld hinzufügen und Filterfunktion aktivieren
        JTextField searchField = new JTextField("");
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                filterTable(searchField.getText(), rowSorter, tableModel, table);
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                filterTable(searchField.getText(), rowSorter, tableModel, table);
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                filterTable(searchField.getText(), rowSorter, tableModel, table);
            }
        });
        frame.add(searchField, BorderLayout.NORTH);

        // Daten aus der Datenbank abrufen und in die Tabelle einfügen
        loadDataFromDatabase(tableModel);

        // Gesamtbetrag und Anzahl der Einträge berechnen und anzeigen
        updateSummary(tableModel, table);

        // Funktionalität für den "neu" Button (neue Buchung)
        neuButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // Ein neues Dialogfenster zum Hinzufügen einer neuen Buchung öffnen
                JComboBox<String> kategorieComboBox = new JComboBox<>();
                JTextField zusatzinfoField = new JTextField();
                JTextField betragField = new JTextField();
                String[] einnahmeAusgabeOptions = {"Einnahme", "Ausgabe"};
                JComboBox<String> einnahmeAusgabeBox = new JComboBox<>(einnahmeAusgabeOptions);

                // Lade die Kategorien in die ComboBox
                loadCategoriesIntoComboBox(kategorieComboBox);

                Object[] inputFields =
                        {
                        "Kategorie:", kategorieComboBox,
                        "Zusatzinfo:", zusatzinfoField,
                        "Betrag:", betragField,
                        "Einnahme/Ausgabe:", einnahmeAusgabeBox
                };

                int option = JOptionPane.showConfirmDialog(null, inputFields, "Neue Buchung", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION)
                {
                    try
                    {
                        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                        String insertQuery = "INSERT INTO Buchung (KategorieID, Datum, Zusatzinfo, Betrag) VALUES (?, ?, ?, ?)";
                        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

                        // Verwende die ausgewählte Kategorie aus der ComboBox
                        String selectedCategory = (String) kategorieComboBox.getSelectedItem();
                        preparedStatement.setInt(1, getCategoryId(selectedCategory)); // Hole die ID aus dem Namen

                        // Heutiges Datum verwenden
                        LocalDate currentDate = LocalDate.now();
                        preparedStatement.setDate(2, Date.valueOf(currentDate));

                        preparedStatement.setString(3, zusatzinfoField.getText());
                        double betrag = Double.parseDouble(betragField.getText());
                        preparedStatement.setDouble(4, betrag);
                        preparedStatement.executeUpdate();

                        // In Tabelle einfügen
                        if (betrag > 0) {
                            tableModel.addRow(new Object[]{
                                    null, // ID wird automatisch von der Datenbank gesetzt
                                    getCategoryId(selectedCategory),
                                    currentDate,  // Heutiges Datum
                                    zusatzinfoField.getText(),
                                    betrag, // Einnahmen
                                    null    // Ausgaben bleibt leer
                            });
                        } else {
                            tableModel.addRow(new Object[]{
                                    null, // ID wird automatisch von der Datenbank gesetzt
                                    getCategoryId(selectedCategory),
                                    currentDate,  // Heutiges Datum
                                    zusatzinfoField.getText(),
                                    null,   // Einnahmen bleibt leer
                                    betrag  // Ausgaben
                            });
                        }

                        connection.close();
                        // Gesamtbetrag und Anzahl der Einträge nach dem Einfügen aktualisieren
                        updateSummary(tableModel, table);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // Funktionalität für den "löschen" Button (markierte Zeile löschen)
        loeschenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Zeile aus der Datenbank und der Tabelle entfernen
                    try {
                        int id = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                        String deleteQuery = "DELETE FROM Buchung WHERE ID = ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
                        preparedStatement.setInt(1, id);
                        preparedStatement.executeUpdate();

                        tableModel.removeRow(selectedRow);
                        connection.close();
                        // Gesamtbetrag und Anzahl der Einträge nach dem Löschen aktualisieren
                        updateSummary(tableModel, table);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Bitte wählen Sie eine Zeile zum Löschen aus.");
                }
            }
        });

        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFilterDialog(rowSorter, tableModel, table);
            }
        });

        clearFilterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rowSorter.setRowFilter(null); // Entferne alle Filter
                updateSummary(tableModel, table); // Aktualisiere Zusammenfassung
            }
        });


        frame.setVisible(true);
    }

    private static void loadDataFromDatabase(DefaultTableModel tableModel)
    {
        try
        {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String query = "SELECT * FROM Buchung";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
            {
                int id = resultSet.getInt("ID");
                int kategorieID = resultSet.getInt("KategorieID");
                Date datum = resultSet.getDate("Datum");
                String zusatzinfo = resultSet.getString("Zusatzinfo");
                double betrag = resultSet.getDouble("Betrag");

                if (betrag > 0)
                {
                    tableModel.addRow(new Object[]{
                            id,
                            kategorieID,
                            datum,
                            zusatzinfo,
                            betrag, // Einnahmen
                            null    // Ausgaben bleibt leer
                    });
                }
                else
                {
                    tableModel.addRow(new Object[]{
                            id,
                            kategorieID,
                            datum,
                            zusatzinfo,
                            null,   // Einnahmen bleibt leer
                            betrag  // Ausgaben
                    });
                }
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Methode zum Berechnen und Anzeigen der Zusammenfassung basierend auf den angezeigten Zeilen
    private static void updateSummary(DefaultTableModel tableModel, JTable table)
    {
        double totalEinnahmen = 0.0;
        double totalAusgaben = 0.0;
        int entryCount = 0;

        for (int i = 0; i < table.getRowCount(); i++) { // Verwende `table.getRowCount()` anstatt `tableModel.getRowCount()`
            int modelRow = table.convertRowIndexToModel(i); // Konvertiere die Tabellenzeile in die Modellzeile
            Double einnahmen = (Double) tableModel.getValueAt(modelRow, 4); // Einnahmen
            Double ausgaben = (Double) tableModel.getValueAt(modelRow, 5);  // Ausgaben

            if (einnahmen != null)
            {
                totalEinnahmen += einnahmen;
            }
            if (ausgaben != null)
            {
                totalAusgaben += ausgaben;
            }
            entryCount++;
        }

        totalEinnahmenLabel.setText(String.format("Gesamteinnahmen: %.2f", totalEinnahmen));
        totalAusgabenLabel.setText(String.format("Gesamtausgaben: %.2f", totalAusgaben));
        totalEntriesLabel.setText("Anzahl der Einträge: " + entryCount);
    }

    // Methode zum Filtern der Tabelle basierend auf dem Suchfeld
    private static void filterTable(String query, TableRowSorter<DefaultTableModel> rowSorter, DefaultTableModel tableModel, JTable table) {
        if (query.trim().length() == 0) {
            rowSorter.setRowFilter(null); // Keine Filterung
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + query)); // Filterung nach Suchbegriff
        }
        updateSummary(tableModel, table); // Aktualisiere Zusammenfassung nach Filterung
    }

    // Methode zum Anzeigen des Filterdialogfensters
    private static void showFilterDialog(TableRowSorter<DefaultTableModel> rowSorter, DefaultTableModel tableModel, JTable table) {
        JDialog filterDialog = new JDialog();
        filterDialog.setTitle("Filter Optionen");
        filterDialog.setSize(400, 200);
        filterDialog.setLayout(new GridLayout(4, 2));

        // Checkboxen für Einnahmen und Ausgaben
        JCheckBox einnahmenCheckBox = new JCheckBox("Nur Einnahmen anzeigen");
        JCheckBox ausgabenCheckBox = new JCheckBox("Nur Ausgaben anzeigen");

        // Textfeld Datum
        JTextField datumField = new JTextField("");

        // Anwenden-Button
        JButton applyButton = new JButton("Anwenden");

        // Hinzufügen der Checkboxen und des Datumsfelds
        filterDialog.add(new JLabel("Datum filtern:"));
        filterDialog.add(datumField);
        filterDialog.add(einnahmenCheckBox);
        filterDialog.add(ausgabenCheckBox);
        filterDialog.add(applyButton);

        // Funktionalität für den Anwenden-Button
        applyButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                List<RowFilter<Object, Object>> filters = new ArrayList<>();

                try
                {
                    if (einnahmenCheckBox.isSelected() && !ausgabenCheckBox.isSelected())
                    {
                        filters.add(RowFilter.notFilter(RowFilter.regexFilter(".*", 5))); // Zeigt nur Einnahmen
                    }
                    else if (ausgabenCheckBox.isSelected() && !einnahmenCheckBox.isSelected())
                    {
                        filters.add(RowFilter.notFilter(RowFilter.regexFilter(".*", 4))); // Zeigt nur Ausgaben
                    }

                    if (!datumField.getText().trim().isEmpty())
                    {
                        filters.add(RowFilter.regexFilter(datumField.getText(), 2)); // Filter für das Datum
                    }

                    rowSorter.setRowFilter(filters.isEmpty() ? null : RowFilter.andFilter(filters));
                    updateSummary(tableModel, table); // Zusammenfassung aktualisieren
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }

                filterDialog.dispose();
            }
        });

        filterDialog.setVisible(true);
    }

    private static void loadCategoriesIntoComboBox(JComboBox<String> comboBox) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String query = "SELECT Bezeichnung FROM Kostenkategorie";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                comboBox.addItem(resultSet.getString("Bezeichnung"));
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int getCategoryId(String categoryName) {
        int categoryId = -1;
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String query = "SELECT ID FROM Kostenkategorie WHERE Bezeichnung = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, categoryName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                categoryId = resultSet.getInt("ID");
            }

            connection.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return categoryId;
    }
}
