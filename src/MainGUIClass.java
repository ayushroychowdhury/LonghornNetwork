import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;

public class MainGUIClass {

    private JFrame frame;
    private JTextArea outputArea;
    private JTextField startStudentField;
    private JTextField targetCompanyField;
    private File inputFile;
    private List<UniversityStudent> students;
    private StudentGraph graph;
    private ReferralPathFinder pathFinder;

    /**
     * Constructs the MainGUIClass and initializes the GUI.
     */
    public MainGUIClass() {
        createGUI();
    }

    /**
     * Creates and initializes the GUI components.
     */
    private void createGUI() {
        frame = new JFrame("Longhorn Network");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(7, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton selectFileButton = new JButton("Select Input File");
        inputPanel.add(selectFileButton);
        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectInputFile();
            }
        });

        JButton viewRawFileButton = new JButton("View Raw Input File");
        inputPanel.add(viewRawFileButton);
        viewRawFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewRawInputFile();
            }
        });

        inputPanel.add(new JLabel("Starting Student Name:"));
        startStudentField = new JTextField();
        inputPanel.add(startStudentField);

        inputPanel.add(new JLabel("Target Company:"));
        targetCompanyField = new JTextField();
        inputPanel.add(targetCompanyField);

        JButton findPathButton = new JButton("Find Referral Path");
        inputPanel.add(findPathButton);
        findPathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findReferralPath();
            }
        });

        JButton viewPodsButton = new JButton("View Pods");
        inputPanel.add(viewPodsButton);
        viewPodsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewPods();
            }
        });

        JButton viewRoommatesButton = new JButton("View Roommate Assignments");
        inputPanel.add(viewRoommatesButton);
        viewRoommatesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewRoommateAssignments();
            }
        });

        JButton viewStudentsButton = new JButton("View Student Information");
        inputPanel.add(viewStudentsButton);
        viewStudentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewStudentInformation();
            }
        });

        JButton viewCompaniesButton = new JButton("View Companies List");
        inputPanel.add(viewCompaniesButton);
        viewCompaniesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewCompaniesList();
            }
        });

        JButton instructionsButton = new JButton("Instructions");
        inputPanel.add(instructionsButton);
        instructionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInstructions();
            }
        });

        JButton introButton = new JButton("Intro");
        inputPanel.add(introButton);
        introButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showIntro();
            }
        });

        frame.add(inputPanel, BorderLayout.NORTH);

        // Output Area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Output"));
        frame.add(scrollPane, BorderLayout.CENTER);

        // Display the frame
        frame.setVisible(true);
    }

    private void selectInputFile() {
        JFileChooser fileChooser = new JFileChooser("../testing/");
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            inputFile = fileChooser.getSelectedFile();
            outputArea.setText("Input file selected: " + inputFile.getName());
            parseInputFile();
        }
    }

    private void parseInputFile() {
        if (inputFile == null) {
            outputArea.setText("No input file selected.");
            return;
        }
        try {
            students = DataParser.parseStudents(inputFile.getAbsolutePath());
            graph = new StudentGraph(students);
            pathFinder = new ReferralPathFinder(graph);
            outputArea.append("\nFile parsed successfully.");
        } catch (Exception e) {
            outputArea.setText("Error parsing input file: " + e.getMessage());
        }
    }

    private void viewRawInputFile() {
        if (inputFile == null) {
            outputArea.setText("No input file selected.");
            return;
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
            outputArea.setText(content.toString());
        } catch (IOException e) {
            outputArea.setText("Error reading input file: " + e.getMessage());
        }
    }

    private void findReferralPath() {
        if (students == null || graph == null || pathFinder == null) {
            outputArea.setText("Please select and parse an input file first.");
            return;
        }
        String startName = startStudentField.getText().trim();
        String targetCompany = targetCompanyField.getText().trim();

        if (startName.isEmpty() || targetCompany.isEmpty()) {
            outputArea.setText("Please provide both the starting student name and the target company.");
            return;
        }

        UniversityStudent startStudent = findStudentByName(startName);

        if (startStudent == null) {
            outputArea.setText("Student with name '" + startName + "' not found.");
            return;
        }

        List<UniversityStudent> referralPath = pathFinder.findReferralPath(startStudent, targetCompany);

        if (referralPath.isEmpty()) {
            outputArea.setText("No referral path found from " + startName + " to a student with an internship at " + targetCompany + ".");
        } else {
            StringBuilder output = new StringBuilder();
            output.append("Referral path from ").append(startName).append(" to a student with an internship at ").append(targetCompany).append(":\n");
            for (UniversityStudent student : referralPath) {
                output.append(" - ").append(student.name).append("\n");
            }
            outputArea.setText(output.toString());
        }
    }

    private void viewPods() {
        if (students == null || graph == null) {
            outputArea.setText("Please select and parse an input file first.");
            return;
        }

        PodFormation podFormation = new PodFormation(graph);
        podFormation.formPods(4); // Pod size set to 4

        StringBuilder output = new StringBuilder();
        output.append("Pods Formed:\n");

        Map<UniversityStudent, List<UniversityStudent>> pods = podFormation.getPods();
        Set<List<UniversityStudent>> uniquePods = new HashSet<>(pods.values());

        int podNumber = 1;
        for (List<UniversityStudent> pod : uniquePods) {
            output.append("Pod ").append(podNumber).append(": ");
            for (UniversityStudent member : pod) {
                output.append(member.name).append(", ");
            }
            output.setLength(output.length() - 2); // Remove trailing comma
            output.append("\n");
            podNumber++;
        }

        outputArea.setText(output.toString());
    }




    private void viewRoommateAssignments() {
        if (students == null) {
            outputArea.setText("Please select and parse an input file first.");
            return;
        }
        GaleShapley.assignRoommates(students);
        StringBuilder output = new StringBuilder();
        output.append("Roommate Assignments:\n");
        for (UniversityStudent student : students) {
            UniversityStudent roommate = GaleShapley.getRoommateMatches().get(student);
            if (roommate != null) {
                output.append(student.name).append(" ↔ ").append(roommate.name).append("\n");
            } else {
                output.append(student.name).append(" has no roommate assigned.\n");
            }
        }
        outputArea.setText(output.toString());
    }

    private void viewStudentInformation() {
        if (students == null) {
            outputArea.setText("Please select and parse an input file first.");
            return;
        }
        StringBuilder output = new StringBuilder();
        output.append("Student Information:\n\n");
        for (UniversityStudent student : students) {
//            System.out.println("Name: " + student.name);
//            System.out.println("Age: " + student.age);
//            System.out.println("Major: " + student.major);
//            System.out.println("GPA: " + student.gpa);
//            System.out.println("Roommate Preferences : " + student.roommatePreferences);
//            System.out.println("Previous Internships: " + student.previousInternships);
            output.append("Name: ").append(student.name).append("\n")
                    .append("Age: ").append(student.age).append("\n")
                    .append("Gender: ").append(student.gender).append("\n")
                    .append("Year: ").append(student.year).append("\n")
                    .append("Major: ").append(student.major).append("\n")
                    .append("GPA: ").append(student.gpa).append("\n")
                    .append("Roommate Preferences: ").append(student.roommatePreferences).append("\n")
                    .append("Previous Internships: ").append(student.previousInternships).append("\n\n");
        }
        outputArea.setText(output.toString());
    }

    private void viewCompaniesList() {
        if (students == null) {
            outputArea.setText("Please select and parse an input file first.");
            return;
        }
        Set<String> companies = new HashSet<>();
        for (UniversityStudent student : students) {
            companies.addAll(student.previousInternships);
        }
        StringBuilder output = new StringBuilder();
        output.append("Companies List:\n");
        for (String company : companies) {
            output.append(" - ").append(company).append("\n");
        }
        outputArea.setText(output.toString());
    }

    private void showInstructions() {
        outputArea.setText("Instructions:\n1. Select an input file to load student data.\n2. Use the provided buttons to explore features:\n   - View raw file\n   - Find referral paths\n   - View pods\n   - View roommate assignments\n   - View student information\n   - View companies list\n3. Follow on-screen messages for additional actions.");
    }

    private void showIntro() {
        outputArea.setText("Welcome to the Longhorn Network GUI!\nThis application helps you explore student networks, roommate assignments, pods, and internship referral paths interactively. Start by selecting an input file!");
    }

    private UniversityStudent findStudentByName(String name) {
        for (UniversityStudent student : students) {
            if (student.name.equalsIgnoreCase(name)) {
                return student;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUIClass::new);
    }
}
