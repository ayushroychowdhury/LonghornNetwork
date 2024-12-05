import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Reference for ReferralPathFinder GUI
 * @author Yahir Lopez
 */
public class ReferralPathFinderGUI extends JPanel implements ActionListener {
    /**
     * Java Swing Components and related data
     */
    private ReferralPathFinder referralPathFinder;
    private JTextArea inputStudent;
    private JTextArea inputCompany;
    private JTextArea output;
    private StudentGraph graph;

    /**
     * Constructor for ReferralPathFinder GUI JPanel
     * @param graph StudentGraph
     */
    public ReferralPathFinderGUI(StudentGraph graph) {
        super(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        referralPathFinder = new ReferralPathFinder(graph);
        this.graph = graph;

        JButton button = new JButton("Find");
        button.setActionCommand("Find");
        button.addActionListener(this);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        add(button, c);

        JLabel label = new JLabel("Enter Student");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.01;
        c.gridx = 1;
        c.gridy = 0;
        add(label, c);

        JTextArea textArea = new JTextArea("Student");
        textArea.setBorder(BorderFactory.createLineBorder(Color.black));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.25;
        c.gridx = 2;
        c.gridy = 0;
        add(textArea, c);
        inputStudent = textArea;

        JLabel label2 = new JLabel("Enter Company");
        c.weightx = 0.01;
        c.gridx = 3;
        c.gridy = 0;
        add(label2, c);

        JTextArea textArea3 = new JTextArea("Company");
        textArea.setBorder(BorderFactory.createLineBorder(Color.black));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.25;
        c.gridx = 4;
        c.gridy = 0;
        add(textArea3, c);
        inputCompany = textArea3;

        JTextArea textArea2 = new JTextArea("Results");
        textArea2.setBorder(BorderFactory.createLineBorder(Color.red));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 40;
        c.weightx = 0.0;
        c.gridwidth = 5;
        c.gridx = 0;
        c.gridy = 1;
        add(textArea2, c);
        output = textArea2;

    }

    /**
     * Function to call when a button is pressed
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Find".equals(e.getActionCommand())) {
            String studentName = inputStudent.getText().trim();
            UniversityStudent student = graph.getStudent(studentName);
            if (student == null) {
                JOptionPane.showMessageDialog(this, "No student found with name " + studentName, "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                String companyName = inputCompany.getText().trim();
                List<UniversityStudent> studentsList = referralPathFinder.findReferralPath(student, companyName);

                if (studentsList == null || studentsList.size() == 0) {
                    output.setText("No referral path found");
                }
                else {
                    String studentNames = "";
                    for (UniversityStudent s : studentsList) {
                        studentNames += s.getName() + ", ";
                    }
                    output.setText(studentNames);
                }
            }
        }
    }
}
