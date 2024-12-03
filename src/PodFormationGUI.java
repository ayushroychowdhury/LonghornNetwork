import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PodFormationGUI extends JPanel {
    private boolean DEBUG = true;
    private static final int PodCol = 0, StudentsCol = 1;
    private static int podNumber = 4;

    public PodFormationGUI(StudentGraph graph) {
        JTable table = new JTable(new PodFormationGUI.TableModel(graph));

        // Center All Data
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );

        // Setting Column Width
        TableColumn column = null;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            column.setCellRenderer(centerRenderer);
            if (i == PodCol) {
                column.setMinWidth(25);
//                column.setPreferredWidth(42);
                column.setMaxWidth(50);
                column.sizeWidthToFit();
            } else if (i == StudentsCol) {
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
                "Pod",
                "Students",
        };
        private Object[][] data;

        public TableModel(StudentGraph graph) {
            PodFormation pods = new PodFormation(graph);
            pods.formPods(podNumber);
            List<List<UniversityStudent>> podsList = pods.getPods();

            data = new Object[podsList.size()][columnNames.length];
            int i = 0;

            for (List<UniversityStudent> pod : podsList) {
                data[i][PodCol] = i;
                data[i][StudentsCol] = "";

                for (UniversityStudent student : pod) {
                    data[i][StudentsCol] += student.getName() +", ";
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
}
