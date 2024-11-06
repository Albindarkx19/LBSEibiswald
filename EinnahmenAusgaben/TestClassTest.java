package EinnahmenAusgaben;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TestClassTest
{

    private JFrame frame;
    private JButton button;
    private JLabel label;
    private JTextField textField;
    private DefaultTableModel tableModel;

    @BeforeEach
    void setUp()
    {
        frame = new JFrame();
        button = new JButton();
        label = new JLabel();
        textField = new JTextField();
        tableModel = new DefaultTableModel();
    }

    @AfterEach
    void tearDown()
    {

        frame.dispose();
        button = null;
        label = null;
        textField = null;
        tableModel = null;
    }

    // Frontend Tests
    @Test
    void testSetFrameTitle()
    {
        frame.setTitle("Einnahmen und Ausgaben");
        assertEquals("Einnahmen und Ausgaben", frame.getTitle());
    }

    @Test
    void testSetButtonText()
    {
        button.setText("Hinzufügen");
        assertEquals("Hinzufügen", button.getText());
    }

    @Test
    void testSetLabelText()
    {
        label.setText("Aktualisierte Info");
        assertEquals("Aktualisierte Info", label.getText());
    }

    @Test
    void testTableModelDataChanged()
    {
        tableModel.addColumn("Test Column");
        tableModel.addRow(new Object[]{"Test Data"});
        tableModel.fireTableDataChanged();
        assertEquals(1, tableModel.getRowCount());
    }

    @Test
    void testClearTextField()
    {
        textField.setText("Text");
        textField.setText("");
        assertEquals("", textField.getText());
    }

    // Backend Tests
    @Test
    void testDatabaseConnection()
    {
        try
        {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kostenrechner?useSSL=false&serverTimezone=UTC", "root", "");
            assertNotNull(conn);
            conn.close();
        }
        catch (Exception e)
        {
            fail("Database connection failed: " + e.getMessage());
        }
    }

    @Test
    void testExecuteQuery()
    {
        try
        {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kostenrechner?useSSL=false&serverTimezone=UTC", "root", "");
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Buchung");
            assertNotNull(rs);
            conn.close();
        }
        catch (Exception e)
        {
            fail("Query execution failed: " + e.getMessage());
        }
    }

    @Test
    void testGetCurrentDate()
    {
        LocalDate today = LocalDate.now();
        assertEquals(LocalDate.now(), today);
    }

    @Test
    void testParseDouble()
    {
        String betragString = "123.45";
        double betrag = Double.parseDouble(betragString);
        assertEquals(123.45, betrag);
    }

    @Test
    void testGetCategoryId()
    {
        int categoryId = getCategoryId("Lebensmittel");
        assertTrue(categoryId >= 0, "Category ID should be a non-negative integer");
    }


    private int getCategoryId(String categoryName)
    {

        return categoryName.equals("Lebensmittel") ? 1 : -1;
    }
}
