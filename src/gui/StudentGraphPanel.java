package gui;

import javax.swing.*;

/**
 * The panel that displays the graph of all students
 */
public class StudentGraphPanel extends JPanel {
    public StudentGraphPanel() {
        /* Set Headline */
        JLabel headline = new JLabel("Graph of all students");
        headline.setBounds(10, 10, 200, 20);
        headline.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
        add(headline);

        /* Add separator */
        JLabel jlabSeparator = new JLabel();
        jlabSeparator.setPreferredSize(new java.awt.Dimension(800, 30));
        add(jlabSeparator);

        /* No students in the beginning */
        JLabel noStudents = new JLabel("No students loaded yet.");
        noStudents.setBounds(10, 40, 200, 20);
        add(noStudents);
    }
}
