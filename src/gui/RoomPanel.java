package gui;

import program.GaleShapley;
import program.StudentGraph;
import program.UniversityStudent;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * The panel that displays the graph of all students
 */
public class RoomPanel extends JPanel implements Subscriber {
    /**
     * Constructor for the pod panel
     */
    public RoomPanel() {
        update(null,null);

        /* Subscribe to ControlPanel */
        ControlPanel.subscribe(this);
    }

    /**
     * Update the rooms with the given graph and display them
     * @param podGraph not used
     * @param referralGraph not used
     */
    @Override
    public void update(StudentGraph podGraph, StudentGraph referralGraph) {
        /* Remove all components */
        removeAll();
        setLayout(null);

        /* Panel to hold student details */
        JPanel roomPanel = new JPanel();
        roomPanel.setLayout(new BoxLayout(roomPanel, BoxLayout.Y_AXIS));

        /* Set Headline */
        JLabel headline = new JLabel("Room Assignments");
        headline.setAlignmentX(Component.CENTER_ALIGNMENT);
        headline.setFont(new Font("Arial", Font.BOLD, 16));
        headline.setBounds(300, 10, 200, 20);
        add(headline);

        /* Get all the students */
        java.util.List<program.UniversityStudent> students = program.DataParser.getStudents();

        if (students == null || students.isEmpty()) {
            return;
        }

        /* Get room assignments */
        Map<UniversityStudent, UniversityStudent> roommates = GaleShapley.getRoommates();

        if (roommates == null || roommates.isEmpty()) {
            return;
        }

        /* Display room assignments */
        for (Map.Entry<UniversityStudent, UniversityStudent> entry : roommates.entrySet()) {
            UniversityStudent student = entry.getKey();
            UniversityStudent roommate = entry.getValue();

            roomPanel.add(new JLabel(student.getName() + " is roommates with " + roommate.getName()));

            /* Add Separator */
            roomPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        }

        JScrollPane scrollPane = new JScrollPane(roomPanel);
        scrollPane.setBounds(10, 40, 763, 400);

        add(scrollPane);

        repaint();
        revalidate();
    }

}
