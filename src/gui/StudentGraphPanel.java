package gui;

import program.StudentEdge;
import program.StudentGraph;
import program.UniversityStudent;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * The panel that displays the graph of all students
 */
public class StudentGraphPanel extends JPanel implements Subscriber {
    public StudentGraphPanel() {
        update();

        /* Subscribe to ControlPanel */
        ControlPanel.subscribe(this);
    }

    @Override
    public void update() {
        /* Remove all components */
        removeAll();
        setLayout(null);

        /* Panel to hold student details */
        JPanel studentGraphPanel = new JPanel();
        studentGraphPanel.setLayout(new BoxLayout(studentGraphPanel, BoxLayout.Y_AXIS));

        /* Set Headline */
        JLabel headline = new JLabel("Edges between students");
        headline.setAlignmentX(Component.CENTER_ALIGNMENT);
        headline.setFont(new Font("Arial", Font.BOLD, 16));
        headline.setBounds(300, 10, 200, 20);
        add(headline);

        /* Get all the students */
        java.util.List<program.UniversityStudent> students = program.DataParser.getStudents();

        if (students == null || students.isEmpty()) {
            return;
        }

        /* Get studentgraph adjacencylist */
        StudentGraph studentGraph = new StudentGraph(students);
        Map<UniversityStudent, List<StudentEdge>> adjacencyList = studentGraph.getAdjacencyList();

        /* Display adjacency list */
        for (int i=0; i < students.size(); i++) {
            UniversityStudent student = students.get(i);
            List<StudentEdge> edges = adjacencyList.get(student);

            studentGraphPanel.add(new JLabel("Student " + (i+1) +": " + student.getName()));

            /* Add Separator */
            studentGraphPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

            if (edges == null || edges.isEmpty()) {
                continue;
            }

            for (int j=0; j<edges.size(); j++) {
                StudentEdge edge = edges.get(j);
                JLabel jlabEdge = new JLabel(student.getName() + " --- " + edge.getWeight() + " --- " + edge.getTargetStudent().getName());
                jlabEdge.setBounds(10, 10 + 20 * (i * edges.size() + j), 200, 20);
                studentGraphPanel.add(jlabEdge);

                /* Add Separator */
                studentGraphPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
            }
            /* Add Separator */
            studentGraphPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        }

        JScrollPane scrollPane = new JScrollPane(studentGraphPanel);
        scrollPane.setBounds(10, 40, 763, 400);

        add(scrollPane);

        repaint();
        revalidate();
    }

}
