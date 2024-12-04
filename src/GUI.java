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
        frame.setSize(500, 300);

        // header panel - text and buttons
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setPreferredSize(new Dimension(frame.getWidth(), 60));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Align content to the center
        JLabel headerText = new JLabel(" --- Longhorn Network --- ");
        headerText.setHorizontalAlignment(SwingConstants.CENTER);
        JButton graphButton = new JButton("Show Graph");
        JButton roommatesButton = new JButton("Show Roommates");
        JButton podButton = new JButton("Show Pods");
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(graphButton); buttonPanel.add(roommatesButton); buttonPanel.add(podButton);
        headerPanel.add(headerText, BorderLayout.CENTER);
        headerPanel.add(buttonPanel, BorderLayout.SOUTH);
        frame.getContentPane().add(headerPanel, BorderLayout.NORTH);

        // main text panel
        JPanel mainPanel = new JPanel();
        JTextArea outputText = new JTextArea("Welcome to Longhorn Network! \nUse the buttons above to select what to display.");
        outputText.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));
        outputText.setEditable(false);
        mainPanel.add(outputText);
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);

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

        frame.setVisible(true);
    }
}

