import javax.swing.*;
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
        // Manually initializing the components
        Zähler1 = new JTextField();
        Nenner1 = new JTextField();
        Zähler2 = new JTextField();
        Nenner2 = new JTextField();
        operatorComboBox = new JComboBox<>();
        Ergebnis = new JTextField();
        calculateButton = new JButton("Berechnen");
        simplifyButton = new JButton("Kürzen");
        mainPanel = new JPanel();

        // Set up the layout and add components
        mainPanel.setLayout(new java.awt.GridLayout(4, 3));
        mainPanel.add(Zähler1);
        mainPanel.add(operatorComboBox);
        mainPanel.add(Zähler2);
        mainPanel.add(new JLabel("-----------------------"));
        mainPanel.add(new JLabel("-----------------------"));
        mainPanel.add(new JLabel("-----------------------"));
        mainPanel.add(Nenner1);
        mainPanel.add(new JLabel("-----------------------"));
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
    }
}
