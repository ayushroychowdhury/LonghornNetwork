package gui;

import program.DataParser;
import program.UniversityStudent;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * The panel that displays all students
 */
public class StudentPanel extends JPanel implements Subscriber {
    public StudentPanel() {
        update();

        /* Subscribe to ControlPanel */
        ControlPanel.subscribe(this);
    }

    @Override
    public void update() {
        removeAll();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /* Set Headline */
        JLabel headline = new JLabel("List of all students");
        headline.setAlignmentX(Component.CENTER_ALIGNMENT);
        headline.setFont(new Font("Arial", Font.BOLD, 16));
        add(headline);

        /* Panel to hold student details */
        JPanel studentPanel = new JPanel();
        studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));

        /* Get all the students */
        List<UniversityStudent> students = DataParser.getStudents();

        if (students == null || students.isEmpty()) {
            return;
        }

        /* Add students */
        for (int i = 0; i < students.size(); i++) {
            /* Add Student Counter */
            JLabel studentCounter = new JLabel("Student " + (i + 1));
            studentCounter.setAlignmentX(Component.LEFT_ALIGNMENT);
            studentPanel.add(studentCounter);

            /* Add Separator */
            studentPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

            /* Add Name */
            JLabel nameLabel = new JLabel("Name: " + DataParser.getStudents().get(i).getName());
            nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            studentPanel.add(nameLabel);

            /* Add Separator */
            studentPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

            /* Add Age */
            JLabel ageLabel = new JLabel("Age: " + DataParser.getStudents().get(i).getAge());
            ageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            studentPanel.add(ageLabel);

            /* Add Separator */
            studentPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

            /* Add Gender */
            JLabel genderLabel = new JLabel("Gender: " + DataParser.getStudents().get(i).getGender());
            genderLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            studentPanel.add(genderLabel);

            /* Add Separator */
            studentPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

            /* Add Year */
            JLabel yearLabel = new JLabel("Year: " + DataParser.getStudents().get(i).getYear());
            yearLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            studentPanel.add(yearLabel);

            /* Add Separator */
            studentPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

            /* Add Major */
            JLabel majorLabel = new JLabel("Major: " + DataParser.getStudents().get(i).getMajor());
            majorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            studentPanel.add(majorLabel);

            /* Add Separator */
            studentPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

            /* Add GPA */
            JLabel gpaLabel = new JLabel("GPA: " + DataParser.getStudents().get(i).getGpa());
            gpaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            studentPanel.add(gpaLabel);

            /* Add Separator */
            studentPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

            /* Add Roommate Preferences */
            JLabel roommateLabel = new JLabel("Roommate Preferences: " + DataParser.getStudents().get(i).getRoommatePreferences());
            roommateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            studentPanel.add(roommateLabel);

            /* Add Separator */
            studentPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

            /* Add Previous Internships */
            JLabel internshipsLabel = new JLabel("Previous Internships: " + DataParser.getStudents().get(i).getPreviousInternships());
            internshipsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            studentPanel.add(internshipsLabel);

            /* Add separator */
            studentPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
            studentPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        }

        /* Add elements to scroll pane */
        JScrollPane scrollPane = new JScrollPane(studentPanel);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        add(scrollPane);

        revalidate();
        repaint();
    }
}
