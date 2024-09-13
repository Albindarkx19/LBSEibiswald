package Polymorphie;

public class Kuh extends Tier {

    public Kuh(String name, int pfoten) {
        super(name, pfoten);
    }

    @Override
    public void gibLaut() {
        System.out.println("Mooooooooh");
    }
}
