import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;

/**
 * Reference for Student Graph GUI
 * @author Yahir Lopez
 */
public class StudentGraphGUI extends JPanel {
    /**
     * Related Data for Java Swing Components
     */
    private static final int NameCol = 0, AgeCol = 1, GenderCol = 2, YearCol = 3, MajorCol = 4, GPACol = 5, RoommateCol = 6, InternshipsCol = 7, ConnectedStudentsCol = 8;

    /**
     * Constructor for Student Graph GUI JPanel
     * @param graph StudentGraph
     */
    public StudentGraphGUI(StudentGraph graph) {
        JTable table = new JTable(new TableModel(graph));

        // Center All Data
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );

        // Setting Column Width
        TableColumn column = null;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            column.setCellRenderer(centerRenderer);
            if (i == AgeCol || i == YearCol || i == GPACol) {
                column.setMinWidth(25);
                column.setMaxWidth(50);
                column.sizeWidthToFit();
            } else if (i == NameCol || i == RoommateCol) {
                column.setMinWidth(50);
                column.setPreferredWidth(75);
                column.setMaxWidth(100);
            } else if (i == GenderCol || i == MajorCol) {
                column.setMinWidth(25);
                column.setPreferredWidth(75);
                column.setMaxWidth(200);            }
            else if (i == InternshipsCol || i == ConnectedStudentsCol) {
                column.sizeWidthToFit();
            }
        }

        table.setPreferredScrollableViewportSize(new Dimension(1000, 200));
        table.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }

    /**
     * Reference for the TableModel used in StudentGraphGUI
     */
    class TableModel extends AbstractTableModel {
        /**
         * Data about Table
         */
        private String[] columnNames = {
                "Name",
                "Age",
                "Gender",
                "Year",
                "Major",
                "GPA",
                "Roommate",
                "Internships",
                "Connected Students and Weight"
        };
        private Object[][] data;

        /**
         * Constructor for TableModel used in StudentGraphGUI
         */
        public TableModel(StudentGraph graph) {
            data = new Object[graph.getStudents().size()][columnNames.length];
            int i = 0;

            for (UniversityStudent student : graph.getStudents()) {
                data[i][NameCol] = student.getName();
                data[i][AgeCol] = student.getAge();
                data[i][GenderCol] = student.getGender();
                data[i][YearCol] = student.getYear();
                data[i][MajorCol] = student.getMajor();
                data[i][GPACol] = student.getGPA();

                UniversityStudent roommate = GaleShapley.getRoommate(student);
                if (roommate != null) {
                    data[i][RoommateCol] = roommate.getName();
                } else
                    data[i][RoommateCol] = "None";

                data[i][InternshipsCol] = student.getPreviousInternships();
                data[i][ConnectedStudentsCol] = "";
                List<StudentGraph.StudentGraphEdge> edges = graph.getEdges(student);
                for (StudentGraph.StudentGraphEdge edge : edges) {
                    if (edge.getWeight() < 0) continue;
                    data[i][ConnectedStudentsCol] += edge.getDestStudent().getName() + " (" + edge.getWeight() + "), ";
                }
                ++i;
            }
        }

        /**
         * Abstract function of AbstractTableModel
         * @return int
         */
        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        /**
         * Abstract function of AbstractTableModel
         * @return int
         */
        @Override
        public int getRowCount() {
            return data.length;
        }

        /**
         * Abstract function of AbstractTableModel
         * @return int
         */
        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        /**
         * Abstract function of AbstractTableModel
         * @return String or int
         */
        @Override
        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

    }
}
