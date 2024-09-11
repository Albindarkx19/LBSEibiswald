package Kontoverwaltung;

public class Kreditkonto extends Konto
{
    private double kreditlimit;

    public Kreditkonto(String kontoinhaber, String bankleitzahl, String kontonummer, double kreditlimit)
    {
        super(kontoinhaber, bankleitzahl, kontonummer, "Kreditkonto");
        this.kreditlimit = kreditlimit;
    }

    @Override
    public void abheben(double betrag)
    {
        // Überprüfen, ob die Abhebung das Kreditlimit nicht überschreitet
        if ((kontostand - betrag) >= kreditlimit) // Korrekte Überprüfung, ob der neue Kontostand >= Kreditlimit bleibt
        {
            kontostand -= betrag;
            System.out.println(betrag + "€ abgehoben. Neuer Kontostand: " + kontostand + "€");
        }
        else
        {
            System.out.println("Abhebung verweigert: Kreditlimit überschritten.");
        }
    }

    public void einzahlen(double betrag)
    {
        // Sicherstellen, dass das Konto durch Einzahlungen nicht ins Positive geht
        if (kontostand + betrag > 0)
        {
            System.out.println("Einzahlung verweigert: Der Kontostand darf nicht höher als 0 sein.");
        }
        else
        {
            kontostand += betrag;
            System.out.println(betrag + "€ eingezahlt. Neuer Kontostand: " + kontostand + "€");
        }
    }
}
