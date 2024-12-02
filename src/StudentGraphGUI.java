import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;

public class StudentGraphGUI extends JPanel {
    private boolean DEBUG = true;
    private static final int NameCol = 0, AgeCol = 1, GenderCol = 2, YearCol = 3, MajorCol = 4, GPACol = 5, RoommateCol = 6, InternshipsCol = 7, ConnectedStudentsCol = 8;

    public StudentGraphGUI(StudentGraph graph) {
        super(new GridLayout(1,0));

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
//                column.setPreferredWidth(42);
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

    class TableModel extends AbstractTableModel {
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

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 2) {
                return false;
            } else {
                return true;
            }
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            if (DEBUG) {
                System.out.println("Setting value at " + row + "," + col
                        + " to " + value
                        + " (an instance of "
                        + value.getClass() + ")");
            }

            data[row][col] = value;
            fireTableCellUpdated(row, col);

            if (DEBUG) {
                System.out.println("New value of data:");
                printDebugData();
            }
        }

        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();

            for (int i=0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j=0; j < numCols; j++) {
                    System.out.print("  " + data[i][j]);
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }
    }


    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI(StudentGraph graph) {
        //Create and set up the window.
        JFrame frame = new JFrame("StudentGraphGUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        StudentGraphGUI newContentPane = new StudentGraphGUI(graph);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void draw(StudentGraph graph) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(graph);
            }
        });
    }

}
