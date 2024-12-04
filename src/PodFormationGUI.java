import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PodFormationGUI extends JPanel implements ActionListener {
    private static final int PodCol = 0, StudentsCol = 1;
    private static int podNumber = 4;
    private JTextArea inputPodNumber;
    private StudentGraph graph;
    private JScrollPane podScroller;

    public PodFormationGUI(StudentGraph graph) {
        this.graph = graph;

        JTable table = new JTable(new PodFormationGUI.TableModel());
        formatTable(table);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        this.podScroller = scrollPane;

        JPanel buttonPanel = new JPanel();

        JButton button = new JButton("Form");
        button.setActionCommand("Form");
        button.addActionListener(this);
        buttonPanel.add(button);

        JTextArea textArea = new JTextArea("Number");
        textArea.setBorder(BorderFactory.createLineBorder(Color.black));
        textArea.setColumns(3);
        buttonPanel.add(textArea);
        this.inputPodNumber = textArea;

        add(buttonPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Form".equals(e.getActionCommand())) {
        int amount = 0;
            try {
                amount = Integer.parseInt(inputPodNumber.getText().trim());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Amount Entered", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            podNumber = amount;

            JTable table = new JTable(new PodFormationGUI.TableModel());
            formatTable(table);
            this.podScroller.setViewportView(table);
        }
    }

    private void formatTable(JTable table) {
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
                column.setMaxWidth(50);
                column.sizeWidthToFit();
            } else if (i == StudentsCol) {
                column.sizeWidthToFit();
            }
        }
    }

    class TableModel extends AbstractTableModel {
        private String[] columnNames = {
                "Pod",
                "Students",
        };
        private Object[][] data;

        public TableModel() {
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
    }
}
