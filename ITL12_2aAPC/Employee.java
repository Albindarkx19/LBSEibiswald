package ITL12_2aAPC;

import java.util.Random;

public class Employee extends Person
{
    private String employeeId;
    private String supervisorId;
    private float salary;

    // Konstruktor
    public Employee(String ln, String fn, String empId, int a, String supId, float salary)
    {
        super(ln, fn, a);
        this.employeeId = empId;
        this.supervisorId = supId;
        this.salary = salary;
    }

    // Getter und Setter für EmployeeID
    public String getEmployeeId()
    {
        return employeeId;
    }

    public void setEmployeeId(String employeeId)
    {
        this.employeeId = employeeId;
    }

    // Getter und Setter für SupervisorID
    public String getSupervisorId()
    {
        return supervisorId;
    }

    public void setSupervisorId(String supervisorId)
    {
        this.supervisorId = supervisorId;
    }

    // Getter und Setter für Salary
    public float getSalary()
    {
        return salary;
    }

    public void setSalary(float salary)
    {
        this.salary = salary;
    }

    // Methode zur Erhöhung des Gehalts mit zufälligem Betrag zwischen 100 und 1500 €
    public void salaryIncrease()
    {
        Random rand = new Random();
        float increase = 100 + rand.nextFloat() * (1500 - 100);  // Zufälliger Betrag zwischen 100 und 1500
        this.salary += increase;
        System.out.println("Das Gehalt wurde um " + increase + "€ erhöht. Neues Gehalt: " + salary + "€");
    }

    // Methode zur Ausgabe aller Informationen
    public void printAll()
    {
        System.out.println("Mitarbeiterinformationen:");
        System.out.println("Vollständiger Name: " + fullName());
        System.out.println("Alter: " + getAge());
        System.out.println("Mitarbeiter-ID: " + employeeId);
        System.out.println("Vorgesetzten-ID: " + supervisorId);
        System.out.println("Gehalt: " + salary + "€");
    }

    // Überschreiben der toString Methode für eine schöne Ausgabe
    @Override
    public String toString()
    {
        return "Employee [ID: " + employeeId + ", " + super.toString() + ", Vorgesetzter: " + supervisorId + ", Gehalt: " + salary + "€]";
    }
}
