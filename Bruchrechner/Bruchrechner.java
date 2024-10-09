/*
 * Project: Bruchrechner
 * Klasse: 2aAPC / 2024
 * Author:  Albin Prushi
 * Last Change:
 *    by:   APR
 *    date: 1.10.2024
 * Copyright (c): LBS Eibiswald, 2024
 */

package Bruchrechner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bruchrechner {
    private JTextField Zähler1;
    private JTextField Nenner1;
    private JTextField Zähler2;
    private JTextField Nenner2;
    private JComboBox<String> operatorComboBox;
    private JTextField Ergebnis;
    private JButton calculateButton;
    private JButton simplifyButton;
    private JPanel mainPanel;

    private int resultZaehler;
    private int resultNenner;

    public Bruchrechner() {
        // Manually initializing the components with larger sizes
        Zähler1 = new JTextField(10);
        Nenner1 = new JTextField(10);
        Zähler2 = new JTextField(10);
        Nenner2 = new JTextField(10);
        operatorComboBox = new JComboBox<>();
        Ergebnis = new JTextField(20);
        calculateButton = new JButton("Berechnen");
        simplifyButton = new JButton("Kürzen");
        mainPanel = new JPanel();

        // Set up the layout and add components with increased layout dimensions
        mainPanel.setLayout(new GridLayout(5, 3, 10, 10));  // Adjusted layout with spacing
        mainPanel.setPreferredSize(new Dimension(400, 200)); // Set a larger preferred size
        mainPanel.add(Zähler1);
        mainPanel.add(operatorComboBox);
        mainPanel.add(Zähler2);
        mainPanel.add(new JLabel("________________________"));
        mainPanel.add(new JLabel(" "));
        mainPanel.add(new JLabel("________________________"));
        mainPanel.add(Nenner1);
        mainPanel.add(new JLabel(" "));
        mainPanel.add(Nenner2);
        mainPanel.add(calculateButton);
        mainPanel.add(Ergebnis);
        mainPanel.add(simplifyButton);

        // Adding operations to the ComboBox
        operatorComboBox.addItem("+");
        operatorComboBox.addItem("-");
        operatorComboBox.addItem("*");
        operatorComboBox.addItem("/");

        // ActionListener for Berechnen button
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int zaehler1 = Integer.parseInt(Zähler1.getText());
                    int nenner1 = Integer.parseInt(Nenner1.getText());

                    int zaehler2 = Integer.parseInt(Zähler2.getText());
                    int nenner2 = Integer.parseInt(Nenner2.getText());

                    if (nenner1 == 0 || nenner2 == 0) {
                        Ergebnis.setText("Nenner darf nicht 0 sein!");
                        return;
                    }

                    String operation = (String) operatorComboBox.getSelectedItem();

                    switch (operation) {
                        case "+":
                            resultZaehler = zaehler1 * nenner2 + zaehler2 * nenner1;
                            resultNenner = nenner1 * nenner2;
                            break;
                        case "-":
                            resultZaehler = zaehler1 * nenner2 - zaehler2 * nenner1;
                            resultNenner = nenner1 * nenner2;
                            break;
                        case "*":
                            resultZaehler = zaehler1 * zaehler2;
                            resultNenner = nenner1 * nenner2;
                            break;
                        case "/":
                            resultZaehler = zaehler1 * nenner2;
                            resultNenner = nenner1 * zaehler2;
                            break;
                    }

                    Ergebnis.setText(resultZaehler + "/" + resultNenner);
                } catch (Exception ex) {
                    Ergebnis.setText("Ungültige Eingabe!");
                }
            }
        });

        // ActionListener for Kürzen button
        simplifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int gcd = GCD(resultZaehler, resultNenner);
                    resultZaehler /= gcd;
                    resultNenner /= gcd;

                    Ergebnis.setText(resultZaehler + "/" + resultNenner);
                } catch (Exception ex) {
                    Ergebnis.setText("Fehler beim Kürzen!");
                }
            }
        });
    }

    // Method to calculate the Greatest Common Divisor (GCD)
    private int GCD(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return Math.abs(a);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bruchrechner");
        frame.setContentPane(new Bruchrechner().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(500, 200);  // Set a larger frame size
    }
}
