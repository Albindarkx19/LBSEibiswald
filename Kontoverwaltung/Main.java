/*
 * Project: Kontoverwaltung
 * Klasse: 2aAPC / 2024
 * Author:  Albin Prushi
 * Last Change:
 *    by:   APR
 *    date: 11.09.2024
 * Copyright (c): LBS Eibiswald, 2024
 */


package Kontoverwaltung;

import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    static ArrayList<Konto> konten = new ArrayList<>();

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        boolean programRunning = true;

        while (programRunning)
        {
            System.out.println("Welche Aktion möchten Sie durchführen?");
            System.out.println("1 - Konto anlegen");
            System.out.println("2 - Einzahlen");
            System.out.println("3 - Abheben");
            System.out.println("4 - Kontoauszug");
            System.out.println("5 - Programm beenden");

            String choice = scanner.nextLine();

            switch (choice)
            {
                case "1":
                    kontoAnlegen(scanner);
                    break;
                case "2":
                    einzahlen(scanner);
                    break;
                case "3":
                    abheben(scanner);
                    break;
                case "4":
                    kontoauszug(scanner);
                    break;
                case "5":
                    programRunning = false;
                    System.out.println("Programm beendet.");
                    break;
                default:
                    System.out.println("Ungültige Eingabe.");
                    break;
            }
        }

        scanner.close();
    }

    public static void kontoAnlegen(Scanner scanner)
    {
        System.out.println("Kontoinhaber: ");
        String kontoinhaber = scanner.nextLine();
        System.out.println("Bankleitzahl: ");
        String blz = scanner.nextLine();
        System.out.println("Kontonummer: ");
        String kontonummer = scanner.nextLine();
        System.out.println("Wählen Sie Kontoart: 1 - Girokonto, 2 - Sparkonto, 3 - Kreditkonto");
        int kontoart = Integer.parseInt(scanner.nextLine());

        switch (kontoart)
        {
            case 1:
                System.out.println("Überziehungsrahmen: ");
                double ueberziehungsrahmen = Double.parseDouble(scanner.nextLine());
                konten.add(new Girokonto(kontoinhaber, blz, kontonummer, ueberziehungsrahmen));
                break;
            case 2:
                System.out.println("Zinssatz: ");
                double zinssatz = Double.parseDouble(scanner.nextLine());
                konten.add(new Sparkonto(kontoinhaber, blz, kontonummer, zinssatz));
                break;
            case 3:
                System.out.println("Kreditlimit: ");
                double kreditlimit = Double.parseDouble(scanner.nextLine());
                konten.add(new Kreditkonto(kontoinhaber, blz, kontonummer, kreditlimit));
                break;
            default:
                System.out.println("Ungültige Kontoart.");
        }

        System.out.println("Konto erfolgreich angelegt.");
    }

    public static void einzahlen(Scanner scanner)
    {
        Konto konto = kontoAuswahl(scanner);
        if (konto != null)
        {
            System.out.println("Betrag einzahlen: ");
            double betrag = Double.parseDouble(scanner.nextLine());
            konto.einzahlen(betrag);
        }
    }

    public static void abheben(Scanner scanner)
    {
        Konto konto = kontoAuswahl(scanner);
        if (konto != null)
        {
            System.out.println("Betrag abheben: ");
            double betrag = Double.parseDouble(scanner.nextLine());
            konto.abheben(betrag);
        }
    }

    public static void kontoauszug(Scanner scanner)
    {
        Konto konto = kontoAuswahl(scanner);
        if (konto != null)
        {
            konto.kontoauszug();
        }
    }

    public static Konto kontoAuswahl(Scanner scanner)
    {
        if (konten.isEmpty())
        {
            System.out.println("Es sind keine Konten vorhanden.");
            return null;
        }

        System.out.println("Wählen Sie ein Konto aus:");
        for (int i = 0; i < konten.size(); i++)
        {
            System.out.println((i + 1) + " - " + konten.get(i).getKontoinhaber() + " (" + konten.get(i).kontoart + ")");
        }

        int kontoIndex = Integer.parseInt(scanner.nextLine()) - 1;
        if (kontoIndex < 0 || kontoIndex >= konten.size())
        {
            System.out.println("Ungültige Auswahl.");
            return null;
        }

        return konten.get(kontoIndex);
    }
}
