package gui;

import program.DataParser;
import program.ReferralPathFinder;
import program.StudentGraph;
import program.UniversityStudent;

import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReferralPanel extends JPanel implements Subscriber {

    private JTextField studentNameField;
    private JTextField companyNameField;
    private JTextArea outputArea;
    private StudentGraph graph;

    public ReferralPanel() {
        /* Subscribe to ControlPanel */
        ControlPanel.subscribe(this);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        /* Headline */
        JLabel headline = new JLabel("Find referral paths");
        headline.setAlignmentX(Component.CENTER_ALIGNMENT);
        headline.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(headline, gbc);

        /* Label and text field for student's name */
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Student's Name:"), gbc);

        gbc.gridx = 1;
        studentNameField = new JTextField(20);
        add(studentNameField, gbc);

        /* Label and text field for company's name */
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Company's Name:"), gbc);

        gbc.gridx = 1;
        companyNameField = new JTextField(20);
        add(companyNameField, gbc);

        /* Button to start something */
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findReferralPath();
            }
        });
        add(startButton, gbc);

        /* Output field for displaying text */
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        outputArea = new JTextArea(5, 20);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, gbc);
    }

    /**
     * Uses referralpathfinder to find the referral path and display it
     */
    private void findReferralPath() {
        String studentName = studentNameField.getText();
        String companyName = companyNameField.getText();

        if (graph == null) {
            outputArea.setText("Please load a graph first");
            return;
        }

        if (studentName.isEmpty() || companyName.isEmpty()) {
            outputArea.setText("Please enter both student's name and company's name");
            return;
        }

        /* Validate Students */
        UniversityStudent student = UniversityStudent.getStudentFromString(studentName, DataParser.getStudents());

        if (student == null) {
            outputArea.setText("Student not found");
            return;
        }

        /* Find referral path */
        ReferralPathFinder finder = new ReferralPathFinder(graph);
        List<UniversityStudent> referralPath = finder.findReferralPath(student, companyName);

        /* Validate referral path */
        if (referralPath == null) {
            outputArea.setText("No referral path found");
            return;
        }

        String output = "Referral Path: ";
        for (UniversityStudent u : referralPath) {
            output += u.getName() + " -> ";
        }
        output += companyName;
        outputArea.setText(output);
    }

    @Override
    public void update(StudentGraph podGraph, StudentGraph referralGraph) {
        graph = referralGraph;
    }
}
