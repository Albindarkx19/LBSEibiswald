/*
 * Project: Polymorphie
 * Klasse: 2aAPC / 2024
 * Author:  Albin Prushi
 * Last Change:
 *    by:   APR
 *    date: 1.10.2024
 * Copyright (c): LBS Eibiswald, 2024
 */

package Polymorphie;

import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Katze, Hund, Kuh, Fisch oder Schwein");
        String wahl = scanner.nextLine();

        Tier tier;

        switch (wahl.toLowerCase())
        {
            case "katze":
                tier = new Katze("Katze", 4);
                break;
            case "hund":
                tier = new Hund("Hund", 4);
                break;
            case "kuh":
                tier = new Kuh("Kuh", 4);
                break;
            case "fisch":
                tier = new Fisch("Fisch", 0);
                break;
            case "schwein":
                tier = new Schwein("Schwein", 4);
                break;
            default:
                System.out.println("Gib eines der tiere ein die angegeben sind");
                return;
        }

        tier.gibLaut();
    }
}
