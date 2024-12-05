import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class SwingUI extends JFrame {
    private JTextArea outputArea; // To display results
    private List<UniversityStudent> students;
    private StudentGraph graph;

    public SwingUI() {
        // Set up the main frame
        setTitle("Longhorn Network");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4));

        // Add buttons
        JButton loadFileButton = new JButton("Load File");
        JButton roommateMatchingButton = new JButton("Roommate Matching");
        JButton podFormationButton = new JButton("Pod Formation");
        JButton referralPathButton = new JButton("Referral Path");

        buttonPanel.add(loadFileButton);
        buttonPanel.add(roommateMatchingButton);
        buttonPanel.add(podFormationButton);
        buttonPanel.add(referralPathButton);

        // Add the button panel to the frame
        add(buttonPanel, BorderLayout.NORTH);

        // Create an output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        // Set up action listeners
        loadFileButton.addActionListener(new LoadFileListener());
        roommateMatchingButton.addActionListener(new RoommateMatchingListener());
        podFormationButton.addActionListener(new PodFormationListener());
        referralPathButton.addActionListener(new ReferralPathListener());
    }

    // Action listener for loading a file
    private class LoadFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
            int returnValue = fileChooser.showOpenDialog(SwingUI.this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                try {
                    students = DataParser.parseStudents(fileChooser.getSelectedFile().getAbsolutePath());
                    outputArea.setText("Successfully loaded " + students.size() + " students.\n");
                } catch (IOException ex) {
                    outputArea.setText("Error loading file: " + ex.getMessage());
                }
            }
        }
    }

    // Action listener for roommate matching
    private class RoommateMatchingListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (students == null) {
                outputArea.setText("Please load a file first.\n");
                return;
            }
            GaleShapley.assignRoommates(students);
            outputArea.append("Roommate Assignments:\n");
            for (UniversityStudent student : students) {
                outputArea.append(student.name + " is roommates with " + student.roommatePreferences.get(0) + "\n");
            }
        }
    }

    // Action listener for pod formation
    private class PodFormationListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (students == null) {
                outputArea.setText("Please load a file first.\n");
                return;
            }
            graph = new StudentGraph(students);
            PodFormation podFormation = new PodFormation(graph);
            podFormation.formPods(4);
            outputArea.append("Pod Assignments:\n");
            // Example pod output
            podFormation.formPods(4); // Call to generate pods
        }
    }

    // Helper method to find a student by name
    private UniversityStudent findStudentByName(String name) {
        if (students == null) return null;
        for (UniversityStudent student : students) {
            if (student.name.equalsIgnoreCase(name)) {
                return student;
            }
        }
        return null;
    }

    // Action listener for referral pathfinding
    private class ReferralPathListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (graph == null) {
                outputArea.setText("Please perform pod formation first.\n");
                return;
            }
            String studentName = JOptionPane.showInputDialog(SwingUI.this, "Enter Student Name:");
            String targetCompany = JOptionPane.showInputDialog(SwingUI.this, "Enter Target Company:");
            UniversityStudent student = findStudentByName(studentName);
            if (student == null) {
                outputArea.setText("Student not found.\n");
                return;
            }
            ReferralPathFinder pathFinder = new ReferralPathFinder(graph);
//            List<UniversityStudent> path = pathFinder.findReferralPath(student, targetCompany);
//            if (path.isEmpty()) {
//                outputArea.append("No referral path found to " + targetCompany + ".\n");
//            } else {
//                outputArea.append("Referral Path to " + targetCompany + ":\n");
//                for (UniversityStudent s : path) {
//                    outputArea.append(s.name + " -> ");
//                }
//                outputArea.append("END\n");
//            }
            Map<UniversityStudent, List<UniversityStudent>> paths = pathFinder.findReferralPath(student, targetCompany);

            if (paths.isEmpty()) {
                outputArea.setText("No referral paths found to " + targetCompany + ".");
            } else {
                StringBuilder result = new StringBuilder();
                for (Map.Entry<UniversityStudent, List<UniversityStudent>> entry : paths.entrySet()) {
                    UniversityStudent targetStudent = entry.getKey();
                    List<UniversityStudent> path = entry.getValue();

                    result.append("Path to ").append(targetCompany).append(" via ").append(targetStudent.name).append(": ");
                    for (int i = 0; i < path.size(); i++) {
                        result.append(path.get(i).name);
                        if (i < path.size() - 1) result.append(" -> ");
                    }
                    result.append("\n");
                }
                outputArea.setText(result.toString());
            }
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SwingUI ui = new SwingUI();
            ui.setVisible(true);
        });
    }
}
