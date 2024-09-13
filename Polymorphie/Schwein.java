package Polymorphie;

public class Schwein extends Tier
{

    public Schwein(String name, int pfoten)
    {
        super(name, pfoten);
    }

    @Override
    public void gibLaut()
    {
        System.out.println("grunz grunz");
    }
}

