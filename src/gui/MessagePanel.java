package gui;

import program.*;

import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A panel for sending messages between students
 */
public class MessagePanel extends JPanel implements Subscriber{

    private JTextField firstPersonField;
    private JTextField secondPersonField;
    private JTextField messageField;
    private JTextArea outputArea;

    /**
     * Constructor for the message panel
     */
    public MessagePanel() {
        /* Subscribe to control panel */
        ControlPanel.subscribe(this);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        /* First person's name */
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("First Person:"), gbc);

        gbc.gridx = 1;
        firstPersonField = new JTextField(20);
        add(firstPersonField, gbc);

        /* Second person's name */
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Second Person:"), gbc);

        gbc.gridx = 1;
        secondPersonField = new JTextField(20);
        add(secondPersonField, gbc);

        /* Chat message */
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Message:"), gbc);

        gbc.gridx = 1;
        messageField = new JTextField(20);
        messageField.setPreferredSize(new Dimension(200, 100));
        add(messageField, gbc);

        /* Send button */
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton sendButton = new JButton("Send Message");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        add(sendButton, gbc);

        /* Chat history button */
        gbc.gridy = 4;
        JButton historyButton = new JButton("Show Chat History");
        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHistory();
            }
        });
        add(historyButton, gbc);

        /* Output field */
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        outputArea = new JTextArea(5, 20);
        outputArea.setEditable(false);
        //outputArea.setPreferredSize(new Dimension(400, 200));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        add(scrollPane, gbc);
    }


    /**
     * Show the chat history between the two students in the textfields
     */
    private void showHistory() {
        /* Validate that both students are valid */
        String firstPerson = firstPersonField.getText();
        String secondPerson = secondPersonField.getText();

        List<UniversityStudent> students = DataParser.getStudents();

        if (students == null) {
            outputArea.setText("No students found.");
            return;
        }else {
            outputArea.setText("");
        }

        UniversityStudent firstStudent = UniversityStudent.getStudentFromString(firstPerson, students);
        UniversityStudent secondStudent = UniversityStudent.getStudentFromString(secondPerson, students);

        if (firstStudent == null || secondStudent == null) {
            outputArea.setText("Invalid students.");
            return;
        }

        /* Get chat history */
        List<ChatHistoryEntry> chatHistory = ChatManager.getChatHistory();

        if (chatHistory == null) {
            outputArea.setText("No chat history found.");
            return;
        }
        for (ChatHistoryEntry entry : chatHistory) {
            if ((entry.getSender().equals(firstStudent) && entry.getReceiver().equals(secondStudent)) ||
                    (entry.getSender().equals(secondStudent) && entry.getReceiver().equals(firstStudent))) {
                outputArea.append(entry.getSender().getName() + " sent a message to " + entry.getReceiver().getName() + ": " + entry.getMessage() + "\n");
            }
        }

        if (outputArea.getText().isEmpty()) {
            outputArea.setText("No chat history found.");
        }
    }

    /**
     * Send a message from the student in the first textfield to the student in the second textfield
     */
    private void sendMessage() {
        /* Validate that both students are valid */
        String firstPerson = firstPersonField.getText();
        String secondPerson = secondPersonField.getText();

        List<UniversityStudent> students = DataParser.getStudents();

        if (students == null) {
            outputArea.setText("No students found.");
            return;
        }else {
            outputArea.setText("");
        }

        UniversityStudent firstStudent = UniversityStudent.getStudentFromString(firstPerson, students);
        UniversityStudent secondStudent = UniversityStudent.getStudentFromString(secondPerson, students);

        if (firstStudent == null || secondStudent == null) {
            outputArea.setText("Invalid students.");
            return;
        }

        /* Check if students are friends */
        if (!FriendManager.areFriends(firstStudent, secondStudent)) {
            outputArea.setText(firstStudent.getName() + " is not friends with " + secondStudent.getName() + ".");
            return;
        }

        /* Get message */
        String message = messageField.getText();

        /* Send message */
        ChatManager.handleChatMessage(firstStudent, secondStudent, message);

        outputArea.setText("Message sent.");
    }

    /**
     * Update the message panel (i.e. clear chat history)
     * @param podGraph unused
     * @param referralGraph unused
     */
    @Override
    public void update(StudentGraph podGraph, StudentGraph referralGraph) {
        /* Clear chat history */
        ChatManager.clear();
    }
}