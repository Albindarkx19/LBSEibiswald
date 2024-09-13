package Polymorphie;

public class Katze extends Tier
{

    public Katze(String name, int pfoten)
    {
        super(name, pfoten);
    }

    @Override
    public void gibLaut()
    {
        System.out.println("ijsdaldjas");
    }
}
