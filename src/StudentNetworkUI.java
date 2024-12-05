import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class StudentNetworkUI extends JFrame {
    private List<UniversityStudent> students;
    private StudentGraph graph;
    private ReferralPathFinder pathFinder;

    public StudentNetworkUI(List<UniversityStudent> students, StudentGraph graph) {
        this.students = students;
        this.graph = graph;
        this.pathFinder = new ReferralPathFinder(graph);

        setTitle("Student Network Visualization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        initializeUI();
    }

    private void initializeUI() {
        JTabbedPane tabbedPane = new JTabbedPane();

        // Add tabs
        tabbedPane.addTab("Students", createStudentsTab());
        tabbedPane.addTab("Roommates", createRoommatesTab());
        tabbedPane.addTab("Pods", createPodsTab());
        tabbedPane.addTab("Graph", createGraphTab());
        tabbedPane.addTab("Referral Path", createReferralPathTab());
        tabbedPane.addTab("Friends", createFriendsTab()); // New tab
        tabbedPane.addTab("Chat", createChatTab());       // New tab

        add(tabbedPane);
    }

    private JPanel createStudentsTab() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"Name", "Age", "Gender", "Year", "Major", "GPA"};
        Object[][] data = new Object[students.size()][columnNames.length];

        for (int i = 0; i < students.size(); i++) {
            UniversityStudent s = students.get(i);
            data[i][0] = s.getName();
            data[i][1] = s.getAge();
            data[i][2] = s.getGender();
            data[i][3] = s.getYear();
            data[i][4] = s.getMajor();
            data[i][5] = s.getGpa();
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createRoommatesTab() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        StringBuilder sb = new StringBuilder();
        Set<String> assigned = new HashSet<>();
        for (UniversityStudent student : students) {
            Student roommate = student.getRoommate();
            if (roommate != null && !assigned.contains(student.getName())) {
                sb.append(student.getName()).append(" is roommates with ").append(roommate.getName()).append("\n");
                assigned.add(student.getName());
                assigned.add(roommate.getName());
            }
        }
        textArea.setText(sb.toString());

        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createPodsTab() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        Map<Integer, List<UniversityStudent>> podMap = new HashMap<>();
        for (UniversityStudent student : students) {
            int podID = student.getPodID();
            podMap.computeIfAbsent(podID, k -> new ArrayList<>()).add(student);
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, List<UniversityStudent>> entry : podMap.entrySet()) {
            sb.append("Pod ").append(entry.getKey()).append(": ");
            List<UniversityStudent> podMembers = entry.getValue();
            for (int i = 0; i < podMembers.size(); i++) {
                sb.append(podMembers.get(i).getName());
                if (i < podMembers.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append("\n");
        }

        textArea.setText(sb.toString());

        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createGraphTab() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        StringBuilder sb = new StringBuilder();
        for (UniversityStudent student : graph.getAllNodes()) {
            sb.append(student.getName()).append(": ");
            List<StudentGraph.Edge> edges = graph.getEdgesFrom(student);
            for (int i = 0; i < edges.size(); i++) {
                StudentGraph.Edge edge = edges.get(i);
                sb.append(edge.getTarget().getName()).append(" (").append(edge.getWeight()).append(")");
                if (i < edges.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append("\n");
        }

        textArea.setText(sb.toString());

        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createReferralPathTab() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));

        inputPanel.add(new JLabel("Starting Student:"));
        JComboBox<String> studentComboBox = new JComboBox<>(students.stream().map(Student::getName).toArray(String[]::new));
        inputPanel.add(studentComboBox);

        inputPanel.add(new JLabel("Target Company:"));
        JTextField companyField = new JTextField();
        inputPanel.add(companyField);

        JButton findPathButton = new JButton("Find Referral Path");
        inputPanel.add(findPathButton);

        panel.add(inputPanel, BorderLayout.NORTH);

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        panel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        findPathButton.addActionListener(e -> {
            String studentName = (String) studentComboBox.getSelectedItem();
            String targetCompany = companyField.getText().trim();

            UniversityStudent startStudent = students.stream()
                    .filter(s -> s.getName().equals(studentName))
                    .findFirst()
                    .orElse(null);

            if (startStudent == null || targetCompany.isEmpty()) {
                resultArea.setText("Please select a valid student and enter a target company.");
                return;
            }

            List<UniversityStudent> path = pathFinder.findReferralPath(startStudent, targetCompany);
            if (path != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Referral Path: ");
                for (int i = 0; i < path.size(); i++) {
                    sb.append(path.get(i).getName());
                    if (i < path.size() - 1) {
                        sb.append(" -> ");
                    }
                }
                resultArea.setText(sb.toString());
            } else {
                resultArea.setText("No referral path found to " + targetCompany);
            }
        });

        return panel;
    }

    private JPanel createFriendsTab() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));

        inputPanel.add(new JLabel("Sender:"));
        JComboBox<String> senderComboBox = new JComboBox<>(students.stream().map(Student::getName).toArray(String[]::new));
        inputPanel.add(senderComboBox);

        inputPanel.add(new JLabel("Receiver:"));
        JComboBox<String> receiverComboBox = new JComboBox<>(students.stream().map(Student::getName).toArray(String[]::new));
        inputPanel.add(receiverComboBox);

        JButton sendRequestButton = new JButton("Send Friend Request");
        inputPanel.add(sendRequestButton);

        panel.add(inputPanel, BorderLayout.NORTH);

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        panel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        sendRequestButton.addActionListener(e -> {
            String senderName = (String) senderComboBox.getSelectedItem();
            String receiverName = (String) receiverComboBox.getSelectedItem();

            if (senderName.equals(receiverName)) {
                resultArea.setText("Sender and receiver cannot be the same.");
                return;
            }

            UniversityStudent sender = findStudentByName(senderName);
            UniversityStudent receiver = findStudentByName(receiverName);

            if (sender == null || receiver == null) {
                resultArea.setText("Invalid sender or receiver.");
                return;
            }

            // Create and start the friend request thread
            FriendRequestThread friendRequestThread = new FriendRequestThread(sender, receiver);
            new Thread(friendRequestThread).start();

            resultArea.setText(senderName + " sent a friend request to " + receiverName);
        });

        return panel;
    }

    private JPanel createChatTab() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));

        inputPanel.add(new JLabel("Sender:"));
        JComboBox<String> senderComboBox = new JComboBox<>(students.stream().map(Student::getName).toArray(String[]::new));
        inputPanel.add(senderComboBox);

        inputPanel.add(new JLabel("Receiver:"));
        JComboBox<String> receiverComboBox = new JComboBox<>();
        inputPanel.add(receiverComboBox);

        inputPanel.add(new JLabel("Message:"));
        JTextField messageField = new JTextField();
        inputPanel.add(messageField);

        JButton sendMessageButton = new JButton("Send Message");
        inputPanel.add(sendMessageButton);

        panel.add(inputPanel, BorderLayout.NORTH);

        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        panel.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        // Update receiver list based on sender's friends
        senderComboBox.addActionListener(e -> {
            String senderName = (String) senderComboBox.getSelectedItem();
            UniversityStudent sender = findStudentByName(senderName);

            receiverComboBox.removeAllItems();
            if (sender != null) {
                for (UniversityStudent friend : sender.getFriendList()) {
                    receiverComboBox.addItem(friend.getName());
                }
            }
        });

        sendMessageButton.addActionListener(e -> {
            String senderName = (String) senderComboBox.getSelectedItem();
            String receiverName = (String) receiverComboBox.getSelectedItem();
            String message = messageField.getText().trim();

            if (senderName == null || receiverName == null || message.isEmpty()) {
                chatArea.setText("Please select sender, receiver, and enter a message.");
                return;
            }

            UniversityStudent sender = findStudentByName(senderName);
            UniversityStudent receiver = findStudentByName(receiverName);

            if (sender == null || receiver == null) {
                chatArea.setText("Invalid sender or receiver.");
                return;
            }

            // Create and start the chat thread
            ChatThread chatThread = new ChatThread(sender, receiver, message);
            new Thread(chatThread).start();

            chatArea.append(senderName + " to " + receiverName + ": " + message + "\n");
            messageField.setText("");
        });

        return panel;
    }

    private UniversityStudent findStudentByName(String name) {
        return students.stream()
                .filter(s -> s.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Load data
                List<UniversityStudent> students = DataParser.parseStudents("testing/referral_path_sample.txt");
                GaleShapley.assignRoommates(students);
                StudentGraph graph = new StudentGraph(students);
                PodFormation podFormation = new PodFormation(graph);
                podFormation.formPods(4);

                StudentNetworkUI ui = new StudentNetworkUI(students, graph);
                ui.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}