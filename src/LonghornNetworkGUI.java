import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LonghornNetworkGUI {
    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("LONGHORN NETWORK");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());
        
        // Create the title label
        JLabel titleLabel = new JLabel("--- Longhorn Network ---", SwingConstants.CENTER);
        frame.add(titleLabel, BorderLayout.NORTH);

        // Create the text area
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Create the button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        
        // Create buttons
        JButton showGraphButton = new JButton("Show Graph");
        JButton showRoommatesButton = new JButton("Show Roommates");
        JButton showPodsButton = new JButton("Show Pods");
        
        // Add buttons to the panel
        buttonPanel.add(showGraphButton);
        buttonPanel.add(showRoommatesButton);
        buttonPanel.add(showPodsButton);
        
        // Add the panel to the frame
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners to the buttons
        showGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText("Displaying the graph...");
                // Add code to display the graph here
                JOptionPane.showMessageDialog(frame, "Graph functionality not yet implemented!");
            }
        });

        showRoommatesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(
                        "M1 is roommates with W5.\n" +
                        "M2 is roommates with W2.\n" +
                        "M3 is roommates with W4.\n" +
                        "M4 is roommates with W1.\n" +
                        "M5 is roommates with W3.\n" +
                        "W1 is roommates with M4.\n" +
                        "W2 is roommates with M2.\n" +
                        "W3 is roommates with M5.\n" +
                        "W4 is roommates with M3.\n" +
                        "W5 is roommates with M1."
                );
            }
        });

        showPodsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText("Displaying pods...");
                // Add code to display pods here
                JOptionPane.showMessageDialog(frame, "Pods functionality not yet implemented!");
            }
        });
        // Set the frame visibility
        frame.setVisible(true);
    }
}
