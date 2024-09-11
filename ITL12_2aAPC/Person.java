package ITL12_2aAPC;

public class Person
{
    private String name;
    private String firstName;
    private int age;


    // Konstruktor
    public Person(String ln, String fn, int a)
    {
        this.name = ln;
        this.firstName = fn;
        this.age = a;
    }

    // Getter und Setter
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    // Methode zum Zurückgeben des vollen Namens
    public String fullName()
    {
        return firstName + " " + name;
    }

    // Überschreiben der toString Methode
    @Override
    public String toString()
    {
        return "Person [Name: " + name + ", Vorname: " + firstName + ", Alter: " + age + "]";
    }
}
