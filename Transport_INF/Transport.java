/*
 * Project: Transport_INF
 * Klasse: 2aAPC / 2024
 * Author:  Albin Prushi
 * Last Change:
 *    by:   APR
 *    date: 1.10.2024
 * Copyright (c): LBS Eibiswald, 2024
 */

package Transport_INF;

public class Transport
{

    public static String transportMachbar(Transportierbar t) {
        float flaeche = (t.laengeCm() * t.breiteCm()) / 10000.0f; // Fläche in Quadratmetern

        if (t.laengeCm() > Transportierbar.MAX_LAENGE_CM) {
            return "Transport nicht machbar: Länge von " + t.laengeCm() + " cm überschreitet die maximale Länge von " + Transportierbar.MAX_LAENGE_CM + " cm.";
        }

        if (t.breiteCm() > Transportierbar.MAX_BREITE_CM) {
            return "Transport nicht machbar: Breite von " + t.breiteCm() + " cm überschreitet die maximale Breite von " + Transportierbar.MAX_BREITE_CM + " cm.";
        }

        if (flaeche > Transportierbar.MAX_FLACHE) {
            return "Transport nicht machbar: Fläche von " + flaeche + " m² überschreitet die maximale Fläche von " + Transportierbar.MAX_FLACHE + " m².";
        }

        return "Transport machbar.";
    }

    public static float berechneFlaeche(Transportierbar t) {
        return (t.laengeCm() * t.breiteCm()) / 10000.0f; // Fläche in Quadratmetern
    }

    public static void main(String[] args) {
        Tisch tisch = new Tisch("Esstisch", 350, 150, 75, false, true);
        Schaf schaf = new Schaf("Shaun", 150, 50, 80, true, false);

        System.out.println("Transport für Tisch:");
        System.out.println("Fläche: " + berechneFlaeche(tisch) + " m²");
        System.out.println(transportMachbar(tisch));

        System.out.println("\nTransport für Schaf:");
        System.out.println("Fläche: " + berechneFlaeche(schaf) + " m²");
        System.out.println(transportMachbar(schaf));
    }
}

