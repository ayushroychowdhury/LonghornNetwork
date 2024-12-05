import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentListPanel extends JPanel {

    JTable table;
    DefaultTableModel tableModel;

    public StudentListPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Student List"));

        // Define table columns without Roommates
        String[] columnNames = {"Name", "Age", "Internships", "Gender", "Year", "Major"};

        // Initialize table model and JTable
        tableModel = new DefaultTableModel(columnNames, 0) {
            // Make cells non-editable
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true); // Enable sorting

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    /**
     * Sets the list of students to display in the table.
     *
     * @param students List of UniversityStudent objects.
     */
    public void setStudents(List<UniversityStudent> students) {
        // Clear existing data
        tableModel.setRowCount(0);

        // Populate table with student data
        for (UniversityStudent student : students) {
            // Use correct getter methods
            List<String> internshipsList = student.getInternships();

            // Handle potential nulls and empty lists
            String internships = (internshipsList != null && !internshipsList.isEmpty())
                    ? String.join(", ", internshipsList)
                    : "None";

            // Retrieve other attributes
            String gender = (student.getGender() != null && !student.getGender().isEmpty())
                    ? student.getGender()
                    : "Not Specified";
            String year = (student.getYear() > 0)
                    ? String.valueOf(student.getYear())
                    : "Not Specified";
            String major = (student.getMajor() != null && !student.getMajor().isEmpty())
                    ? student.getMajor()
                    : "Not Specified";

            tableModel.addRow(new Object[]{
                    student.getName(),
                    student.getAge(),
                    internships,
                    gender,
                    year,
                    major
            });
        }
    }

    /**
     * Resets any applied filters and displays all data.
     */
    public void resetFilter() {
        table.setRowSorter(null);
    }
}
