package Kontoverwaltung;

public class Sparkonto extends Konto
{
    private double zinssatz;

    public Sparkonto(String kontoinhaber, String bankleitzahl, String kontonummer, double zinssatz)
    {
        super(kontoinhaber, bankleitzahl, kontonummer, "Sparkonto");
        this.zinssatz = zinssatz;
    }

    @Override
    public void abheben(double betrag)
    {
        if (kontostand >= betrag)
        {
            kontostand -= betrag;
            System.out.println(betrag + "€ abgehoben. Neuer Kontostand: " + kontostand + "€");
        }
        else
        {
            System.out.println("Nicht genügend Guthaben.");
        }
    }

    public void berechneZinsen()
    {
        double zinsen = kontostand * zinssatz / 100;
        kontostand += zinsen;
        System.out.println("Zinsen von " + zinsen + "€ gutgeschrieben. Neuer Kontostand: " + kontostand + "€");
    }
}
