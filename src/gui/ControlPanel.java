package gui;

import program.DataParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The control panel manages loading of student datasets
 */
public class ControlPanel extends JPanel {

    private static java.util.List<JPanel> subscribers = new ArrayList<>();

    /**
     * Constructor for the control panel
     */
    public ControlPanel() {
        /* Set layout */
        setLayout(new FlowLayout());

        /* Set Headline */
        JLabel headline = new JLabel("Here you can load a student dataset");
        headline.setBounds(10, 10, 200, 20);
        headline.setFont(new Font("Arial", Font.BOLD, 16));
        add(headline);

        /* Add separator */
        JLabel jlabSeparator = new JLabel();
        jlabSeparator.setPreferredSize(new Dimension(800, 30));
        add(jlabSeparator);

        /* Add filename textfield */
        JLabel filenameLabel = new JLabel("Filename: ");
        JTextField filenameField = new JTextField(40);
        filenameField.setSize(200, 20);
        filenameField.setBounds(10, 40, 200, 20);
        add(filenameLabel);
        add(filenameField);

        /* Add load button */
        JButton loadButton = new JButton("Load File");
        loadButton.setBounds(10, 70, 200, 20);
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filename = filenameField.getText();
                try {
                    DataParser.parseStudents(filename);
                    for (JPanel subscriber : subscribers) {
                        ((Subscriber) subscriber).update();
                    }
                }catch (IOException ex){
                    JOptionPane.showMessageDialog(null, "Error loading file");
                }
            }
        });
        add(loadButton);
    }

    /**
     * Add a subscriber to the list of subscribers
     * @param subscriber The subscriber to add
     */
    public static void subscribe(JPanel subscriber) {
        subscribers.add(subscriber);
    }
}
