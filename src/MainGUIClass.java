import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The GUI class.
 */

public class MainGUIClass {

    private File inputFile;
    private JFrame frame;
    private JTextArea outputArea;
    private JComboBox<String> startStudentDropdown;
    private JComboBox<String> targetCompanyDropdown;
    private JComboBox<String> senderDropdown;
    private JComboBox<String> receiverDropdown;
    private JTextArea messageArea;
    private List<UniversityStudent> students;
    private StudentGraph graph;
    private ReferralPathFinder pathFinder;
    private ExecutorService executorService;

    /**
     * The constructor
     */
    public MainGUIClass() {
        executorService = Executors.newCachedThreadPool();
        createGUI();
    }

    /**
     * GUI create
     */
    private void createGUI() {
        frame = new JFrame("Longhorn Network");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 900);
        frame.setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton selectFileButton = new JButton("Select Input File");
        inputPanel.add(selectFileButton);
        selectFileButton.addActionListener(e -> selectInputFile());

        JButton viewRawFileButton = new JButton("View Raw Input File");
        inputPanel.add(viewRawFileButton);
        viewRawFileButton.addActionListener(e -> viewRawInputFile());

        inputPanel.add(new JLabel("Starting Student Name:"));
        startStudentDropdown = new JComboBox<>();
        startStudentDropdown.setEditable(true);
        inputPanel.add(startStudentDropdown);

        inputPanel.add(new JLabel("Target Company:"));
        targetCompanyDropdown = new JComboBox<>();
        targetCompanyDropdown.setEditable(true);
        inputPanel.add(targetCompanyDropdown);

        JButton findPathButton = new JButton("Find Referral Path");
        inputPanel.add(findPathButton);
        findPathButton.addActionListener(e -> findReferralPath());

        JButton viewPodsButton = new JButton("View Pods");
        inputPanel.add(viewPodsButton);
        viewPodsButton.addActionListener(e -> viewPods());

        JButton viewRoommatesButton = new JButton("View Roommate Assignments");
        inputPanel.add(viewRoommatesButton);
        viewRoommatesButton.addActionListener(e -> viewRoommateAssignments());

        JButton viewCompaniesButton = new JButton("View Companies List");
        inputPanel.add(viewCompaniesButton);
        viewCompaniesButton.addActionListener(e -> viewCompaniesList());

        JButton clearOutputButton = new JButton("Clear Output Box");
        inputPanel.add(clearOutputButton);
        clearOutputButton.addActionListener(e -> clearOutputBox());

        frame.add(inputPanel, BorderLayout.NORTH);

        // Messaging Section
        JPanel messagingPanel = new JPanel(new GridBagLayout());
        messagingPanel.setBorder(BorderFactory.createTitledBorder("Messaging Section"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        messagingPanel.add(new JLabel("Sender Name:"), gbc);

        gbc.gridx = 1;
        senderDropdown = new JComboBox<>();
        senderDropdown.setEditable(true);
        messagingPanel.add(senderDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        messagingPanel.add(new JLabel("Receiver Name:"), gbc);

        gbc.gridx = 1;
        receiverDropdown = new JComboBox<>();
        receiverDropdown.setEditable(true);
        messagingPanel.add(receiverDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        messagingPanel.add(new JLabel("Message:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        messageArea = new JTextArea(4, 20);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        JScrollPane messageScrollPane = new JScrollPane(messageArea);
        messagingPanel.add(messageScrollPane, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        JButton sendMessageButton = new JButton("Send Message");
        messagingPanel.add(sendMessageButton, gbc);
        sendMessageButton.addActionListener(e -> sendMessage());

        gbc.gridx = 1;
        gbc.gridy = 4;
        JButton loadMessagesButton = new JButton("Load Messages");
        messagingPanel.add(loadMessagesButton, gbc);
        loadMessagesButton.addActionListener(e -> loadMessages());

        frame.add(messagingPanel, BorderLayout.CENTER);

        // Output Area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Output"));
        scrollPane.setPreferredSize(new Dimension(900, 300));
        frame.add(scrollPane, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    /**
     * select the input file
     */
    private void selectInputFile() {
        JFileChooser fileChooser = new JFileChooser("../testing/");
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            inputFile = fileChooser.getSelectedFile();
            outputArea.setText("Input file selected: " + inputFile.getName());
            parseInputFile(inputFile);
        }
    }

    /**
     * parse the input file
     * @param file
     */
    private void parseInputFile(File file) {
        try {
            students = DataParser.parseStudents(file.getAbsolutePath());
            graph = new StudentGraph(students);
            pathFinder = new ReferralPathFinder(graph);

            // Populate dropdowns
            startStudentDropdown.removeAllItems();
            senderDropdown.removeAllItems();
            receiverDropdown.removeAllItems();
            targetCompanyDropdown.removeAllItems();

            Set<String> companies = new HashSet<>();
            for (UniversityStudent student : students) {
                startStudentDropdown.addItem(student.name);
                senderDropdown.addItem(student.name);
                receiverDropdown.addItem(student.name);
                companies.addAll(student.previousInternships);
            }

            for (String company : companies) {
                targetCompanyDropdown.addItem(company);
            }

            outputArea.append("\nFile parsed successfully.");
        } catch (Exception e) {
            outputArea.setText("Error parsing input file: " + e.getMessage());
        }
    }

    /**
     * send a message
     */
    private void sendMessage() {
        String senderName = (String) senderDropdown.getSelectedItem();
        String receiverName = (String) receiverDropdown.getSelectedItem();
        String messageText = messageArea.getText().trim();

        if (senderName == null || receiverName == null || messageText.isEmpty()) {
            outputArea.setText("Please provide valid sender, receiver, and message.");
            return;
        }

        UniversityStudent sender = findStudentByName(senderName);
        UniversityStudent receiver = findStudentByName(receiverName);

        if (sender == null || receiver == null) {
            outputArea.setText("Sender or Receiver not found in the student list.");
            return;
        }

        ChatThread chatThread = new ChatThread(sender, receiver, messageText);
        executorService.execute(chatThread);

        outputArea.append("\nMessage sent:\n");
        messageArea.setText("");
        outputArea.append("From: " + senderName + "\n");
        outputArea.append("To: " + receiverName + "\n");
        outputArea.append("Message: " + messageText + "\n");
    }

    /**
     * loads messages
     */

    private void loadMessages() {
        String senderName = (String) senderDropdown.getSelectedItem();
        String receiverName = (String) receiverDropdown.getSelectedItem();

        if (senderName == null || receiverName == null) {
            outputArea.setText("Please select both sender and receiver.");
            return;
        }

        UniversityStudent sender = findStudentByName(senderName);
        UniversityStudent receiver = findStudentByName(receiverName);

        if (sender == null || receiver == null) {
            outputArea.setText("Sender or Receiver not found in the student list.");
            return;
        }

        List<String> messages = receiver.getChatHistoryWith(sender);
        outputArea.setText("Messages between " + senderName + " and " + receiverName + ":\n");
        for (String message : messages) {
            outputArea.append(senderName + ": " + message + "\n");
        }
    }

    /**
     * find a referral path with a student name and target
     */
    private void findReferralPath() {
        if (students == null || graph == null || pathFinder == null) {
            outputArea.setText("Please select and parse an input file first.");
            return;
        }
        String startName = (String) startStudentDropdown.getSelectedItem();
        String targetCompany = (String) targetCompanyDropdown.getSelectedItem();

        if (startName == null || targetCompany == null) {
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

    /**
     * form and show the pods
     */
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

    /**
     * roommate match
     */

    private void viewRoommateAssignments() {
        if (students == null || students.isEmpty()) {
            outputArea.setText("No student data available. Please select and parse an input file first.");
            return;
        }

        outputArea.setText("Processing roommate assignments...");

        SwingWorker<Void, String> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    GaleShapley.assignRoommates(students);
                    publish("Roommate assignments completed successfully.");
                } catch (Exception e) {
                    publish("Error during roommate assignment: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void process(List<String> chunks) {
                for (String message : chunks) {
                    outputArea.append("\n" + message);
                }
            }

            @Override
            protected void done() {
                displayRoommateAssignments();
            }
        };

        worker.execute();
    }

    /**
     * display roommate match results
     */
    private void displayRoommateAssignments() {
        StringBuilder output = new StringBuilder();
        output.append("Roommate Assignments:\n");
        Set<UniversityStudent> processed = new HashSet<>();

        for (UniversityStudent student : students) {
            if (processed.contains(student)) {
                continue;
            }
            UniversityStudent roommate = GaleShapley.getRoommateMatches().get(student);
            if (roommate != null) {
                output.append(student.name).append(" ↔ ").append(roommate.name).append("\n");
                processed.add(roommate);
            } else {
                output.append(student.name).append(" has no roommate assigned.\n");
            }
            processed.add(student);
        }
        outputArea.setText(output.toString());
    }

    /**
     * view all companies names
     */
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

    /**
     * clears the output box
     */
    private void clearOutputBox() {
        outputArea.setText("");
    }

    /**
     * view the raw input file
     */
    private void viewRawInputFile() {
        if (inputFile == null) {
            outputArea.setText("No input file selected.");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            outputArea.setText(content.toString());
        } catch (IOException e) {
            outputArea.setText("Error reading input file: " + e.getMessage());
        }
    }


    /**
     * finds a student and their information.
     * @param name
     * @return
     */
    private UniversityStudent findStudentByName(String name) {
        if (students == null) return null;
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
