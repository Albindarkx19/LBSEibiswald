package Transport_INF;

public interface Transportierbar {
    float MAX_FLACHE = 6.0f; // Maximale Fläche in Quadratmetern
    int MAX_LAENGE_CM = 300; // Maximale Länge in Zentimetern
    int MAX_BREITE_CM = 200; // Maximale Breite in Zentimetern

    int laengeCm();
    int breiteCm();
    int hoeheCm();

    boolean zerbrechlich();
    boolean stapelbar();
    String beschriftung();
}



