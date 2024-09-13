package Polymorphie;

public class Hund extends Tier
{

    public Hund(String name, int pfoten)
    {
        super(name, pfoten);
    }

    @Override
    public void gibLaut()
    {
        System.out.println("Wuff wuff");
    }
}
