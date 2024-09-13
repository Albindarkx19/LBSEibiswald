package Polymorphie;

public class Fisch extends Tier
{

    public Fisch(String name, int pfoten)
    {
        super(name, pfoten);
    }

    @Override
    public void gibLaut()
    {
        System.out.println("blupp");
    }
}
