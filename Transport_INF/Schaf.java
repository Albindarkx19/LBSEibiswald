package Transport_INF;

public class Schaf implements Transportierbar {
    private String name;
    private int laengeCm, breiteCm, hoeheCm;
    private boolean zerbrechlich, stapelbar;

    public Schaf(String name, int laengeCm, int breiteCm, int hoeheCm, boolean zerbrechlich, boolean stapelbar) {
        this.name = name;
        this.laengeCm = laengeCm;
        this.breiteCm = breiteCm;
        this.hoeheCm = hoeheCm;
        this.zerbrechlich = zerbrechlich;
        this.stapelbar = stapelbar;
    }

    @Override
    public int laengeCm() {
        return laengeCm;
    }

    @Override
    public int breiteCm() {
        return breiteCm;
    }

    @Override
    public int hoeheCm() {
        return hoeheCm;
    }

    @Override
    public boolean zerbrechlich() {
        return zerbrechlich;
    }

    @Override
    public boolean stapelbar() {
        return stapelbar;
    }

    @Override
    public String beschriftung() {
        return "Schaf: " + name;
    }
}
