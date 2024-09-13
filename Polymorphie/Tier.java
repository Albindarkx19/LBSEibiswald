package Polymorphie;

public abstract class Tier
{
    protected String name;
    protected int pfoten;

    public Tier(String name, int pfoten)
    {
        this.name = name;
        this.pfoten = pfoten;
    }

    public abstract void gibLaut();
}
