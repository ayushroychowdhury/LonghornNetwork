import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GUI {
    public static void displayGUI() {
        // set up the main frame
        JFrame frame = new JFrame("LONGHORN NETWORK");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 550);

        // header panel - text and buttons
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setPreferredSize(new Dimension(frame.getWidth(), 80));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Align content to the center
        JLabel headerText = new JLabel(" --- Longhorn Network --- ");
        headerText.setHorizontalAlignment(SwingConstants.CENTER);
        headerText.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        JButton graphButton = new JButton("Show Graph");
        JButton roommatesButton = new JButton("Show Roommates");
        JButton podButton = new JButton("Show Pods");
        JButton studentButton = new JButton("Show Student:");
        JTextField inputStudent = new JTextField();
        inputStudent.setPreferredSize(new Dimension(150, 30));
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(graphButton); buttonPanel.add(roommatesButton); buttonPanel.add(podButton);
        buttonPanel.add(studentButton); buttonPanel.add(inputStudent);
        headerPanel.add(headerText, BorderLayout.CENTER);
        headerPanel.add(buttonPanel, BorderLayout.SOUTH);
        frame.getContentPane().add(headerPanel, BorderLayout.NORTH);

        // main text panel

        JPanel mainPanel = new JPanel();
        JTextArea outputText = new JTextArea("Welcome to Longhorn Network! \nUse the buttons above to select what to display.");
        outputText.setPreferredSize(new Dimension(400, 280));
        outputText.setEditable(false);
        mainPanel.add(outputText);
        JScrollPane scrollPane = new JScrollPane(outputText);
        scrollPane.setPreferredSize(new Dimension(400, 300)); // Set preferred size for scrollable area
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Add action listeners to buttons to change the text of JTextArea
        graphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputText.setText(Main.graphText);
            }
        });

        roommatesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputText.setText(Main.roommateText);
            }
        });

        podButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputText.setText(Main.podText);
            }
        });

        studentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentName = inputStudent.getText();
                if (Main.nameMap.containsKey(studentName)) {
                    outputText.setText(Main.nameMap.get(studentName).toString());
                } else {
                    outputText.setText("Student not found!");
                }
            }
        });

        frame.setVisible(true);
    }
}

