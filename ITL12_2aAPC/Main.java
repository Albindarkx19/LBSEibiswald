package ITL12_2aAPC;

import java.util.ArrayList;
import java.util.Scanner;

public class Main
{

    // Methode zum Suchen eines Mitarbeiters anhand der ID
    public static Employee findEmployeeById(ArrayList<Employee> employees, String empId)
    {
        for (Employee employee : employees)
        {
            if (employee.getEmployeeId().equals(empId))
            {
                return employee;
            }
        }
        return null;  // Falls kein Mitarbeiter mit der entsprechenden ID gefunden wird
    }

    public static void main(String[] args)
    {
        // Liste von Employees erstellen
        ArrayList<Employee> employees = new ArrayList<>();

        // Mitarbeiter hinzufügen
        employees.add(new Employee("Meier", "Anna", "E12345", 28, "S67890", 50000));
        employees.add(new Employee("Schmidt", "John", "E23456", 35, "S67891", 55000));
        employees.add(new Employee("Klein", "Emma", "E34567", 30, "S67892", 60000));

        // Mitarbeiterliste anzeigen
        System.out.println("Liste der Mitarbeiter:");
        for (Employee emp : employees)
        {
            System.out.println(emp.getEmployeeId() + ": " + emp.fullName());
        }

        // Benutzer kann einen Mitarbeiter anhand der ID auswählen
        Scanner scanner = new Scanner(System.in);
        System.out.print("Geben Sie die Mitarbeiter-ID ein, um den Mitarbeiter zu suchen: ");
        String empId = scanner.nextLine();

        // Mitarbeiter suchen
        Employee selectedEmployee = findEmployeeById(employees, empId);

        if (selectedEmployee != null)
        {
            System.out.println("Mitarbeiter gefunden:");
            selectedEmployee.printAll();

            // Zufällige Gehaltserhöhung für den ausgewählten Mitarbeiter
            selectedEmployee.salaryIncrease();
        }
        else
        {
            System.out.println("Kein Mitarbeiter mit der ID " + empId + " gefunden.");
        }

        scanner.close();
    }
}
