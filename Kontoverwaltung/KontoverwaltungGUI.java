package Kontoverwaltung;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class KontoverwaltungGUI extends JFrame {
    private JPanel mainPanel;
    private JTextField kontonummerField;
    private JTextField kontoinhaberField;
    private JTextField gebuehrenZinsenField;
    private JComboBox<String> kontoArtComboBox;
    private JButton closeButton;
    private JButton kontoAnlegenButton;
    private JButton kontenAnzeigenButton;
    private JButton einzahlenButton;
    private JButton abhebenButton;
    private JTextField betragField;
    private JTextField kontostandField;
    private JComboBox<String> accountComboBox;  // ComboBox zum Auswählen der Konten
    private JLabel bankLabel;
    private JLabel nameLabel;
    private JLabel gebuehrenLabel;
    private JLabel kontoartLabel;
    private JLabel betragLabel;
    private JLabel kontonummerLabel;

    // Liste, um alle angelegten Konten zu speichern
    private ArrayList<Konto> kontenList;

    public KontoverwaltungGUI() {
        setTitle("Kontoverwaltung");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Initialisierung der Kontoliste
        kontenList = new ArrayList<>();

        // Populiere die "Kontoart" ComboBox
        kontoArtComboBox.addItem("Girokonto");
        kontoArtComboBox.addItem("Sparkonto");
        kontoArtComboBox.addItem("Kreditkonto");

        // ActionListener für den "Verlassen"-Button
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCloseButtonClicked();
            }
        });

        // ActionListener für den "Konto Anlegen"-Button
        kontoAnlegenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onKontoAnlegen();
            }
        });

        // ActionListener für den "Konten Anzeigen"-Button
        kontenAnzeigenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onKontenAnzeigen();
            }
        });

        // ActionListener für den "Einzahlen"-Button
        einzahlenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openTransactionWindow("Einzahlen");
            }
        });

        // ActionListener für den "Abheben"-Button
        abhebenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openTransactionWindow("Abheben");
            }
        });

        setupListeners();
    }

    // Konto Anlegen Funktion
    private void onKontoAnlegen() {
        String kontoinhaber = kontoinhaberField.getText();
        String kontonummer = kontonummerField.getText();
        String gebuehrenZinsen = gebuehrenZinsenField.getText();
        String kontostand = kontostandField.getText();
        String kontoart = (String) kontoArtComboBox.getSelectedItem();

        if (kontoinhaber.isEmpty() || kontonummer.isEmpty() || gebuehrenZinsen.isEmpty() || kontostand.isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "Bitte füllen Sie alle Felder aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double startKontostand = Double.parseDouble(kontostand);
        double gebuehrenZinsenWert = Double.parseDouble(gebuehrenZinsen);

        Konto newKonto = null;

        switch (kontoart) {
            case "Girokonto":
                newKonto = new Girokonto(kontoinhaber, "123456", kontonummer, gebuehrenZinsenWert);
                break;
            case "Sparkonto":
                newKonto = new Sparkonto(kontoinhaber, "123456", kontonummer, gebuehrenZinsenWert);
                break;
            case "Kreditkonto":
                newKonto = new Kreditkonto(kontoinhaber, "123456", kontonummer, -gebuehrenZinsenWert);
                break;
        }

        newKonto.einzahlen(startKontostand);
        kontenList.add(newKonto);

        // Füge das Konto der JComboBox hinzu
        accountComboBox.addItem(kontoinhaber + " - " + kontonummer);

        JOptionPane.showMessageDialog(mainPanel, "Neues Konto angelegt:\nKontoinhaber: " + kontoinhaber + ", Kontoart: " + kontoart);
    }

    // Öffne ein Transaktionsfenster (Einzahlen oder Abheben)
    private void openTransactionWindow(String actionType) {
        JFrame transactionFrame = new JFrame(actionType + " - Kontotransaktion");
        transactionFrame.setSize(400, 200);
        transactionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        JLabel betragLabel = new JLabel("Betrag:");
        JTextField betragField = new JTextField(10);
        JButton confirmButton = new JButton("Bestätigen");
        JButton cancelButton = new JButton("Abbrechen");

        // Konto-Nummer von der ComboBox verwenden
        JComboBox<String> transactionAccountComboBox = new JComboBox<>();
        for (Konto konto : kontenList) {
            transactionAccountComboBox.addItem(konto.kontoinhaber + " - " + konto.kontonummer);
        }

        panel.add(new JLabel("Wähle Konto:"));
        panel.add(transactionAccountComboBox);
        panel.add(betragLabel);
        panel.add(betragField);
        panel.add(confirmButton);
        panel.add(cancelButton);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedAccount = (String) transactionAccountComboBox.getSelectedItem();
                String kontonummer = selectedAccount.split(" - ")[1]; // Kontonummer extrahieren
                String betragText = betragField.getText();

                if (betragText.isEmpty()) {
                    JOptionPane.showMessageDialog(transactionFrame, "Bitte geben Sie einen Betrag ein.", "Fehler", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double betrag = Double.parseDouble(betragText);
                Konto konto = findKonto(kontonummer);

                if (konto != null) {
                    if (actionType.equals("Einzahlen")) {
                        konto.einzahlen(betrag);
                    } else if (actionType.equals("Abheben")) {
                        konto.abheben(betrag);
                    }
                    JOptionPane.showMessageDialog(transactionFrame, actionType + " erfolgreich. Neuer Kontostand: " + konto.kontostand + "€");
                    transactionFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(transactionFrame, "Konto nicht gefunden.", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(e -> transactionFrame.dispose());

        transactionFrame.add(panel);
        transactionFrame.setVisible(true);
    }

    // Methode zum Finden eines Kontos über die Kontonummer
    private Konto findKonto(String kontonummer) {
        for (Konto konto : kontenList) {
            if (konto.kontonummer.equals(kontonummer)) {
                return konto;
            }
        }
        return null;
    }

    // Funktion zum Anzeigen aller erstellten Konten
    private void onKontenAnzeigen() {
        JFrame accountsFrame = new JFrame("Gespeicherte Konten");
        accountsFrame.setSize(400, 300);
        accountsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea accountsTextArea = new JTextArea();
        accountsTextArea.setEditable(false);

        for (Konto konto : kontenList) {
            accountsTextArea.append("Kontoinhaber: " + konto.kontoinhaber + ", Kontonummer: " + konto.kontonummer + ", Kontostand: " + konto.kontostand + "€\n");
        }

        JScrollPane scrollPane = new JScrollPane(accountsTextArea);
        accountsFrame.add(scrollPane);
        accountsFrame.setVisible(true);
    }

    // Funktion zum Schließen der Anwendung
    private void onCloseButtonClicked() {
        int response = JOptionPane.showConfirmDialog(
                mainPanel,
                "Möchten Sie das Programm wirklich verlassen?",
                "Verlassen bestätigen",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void setupListeners() {
        kontoArtComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedKontoArt = (String) kontoArtComboBox.getSelectedItem();
                JOptionPane.showMessageDialog(mainPanel, "Ausgewählte Kontoart: " + selectedKontoArt);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                KontoverwaltungGUI gui = new KontoverwaltungGUI();
                gui.setVisible(true);
            }
        });
    }
}
