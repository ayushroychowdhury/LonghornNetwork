package gui;

import program.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * The panel that displays the graph of all students
 */
public class PodPanel extends JPanel implements Subscriber {

    int podSize = 4;

    public PodPanel() {
        update(null, null);

        /* Subscribe to ControlPanel */
        ControlPanel.subscribe(this);
    }

    @Override
    public void update(StudentGraph podGraph, StudentGraph referralGraph) {
        /* Remove all components */
        removeAll();
        setLayout(null);

        /* Panel to hold student details */
        JPanel podPanel = new JPanel();
        podPanel.setLayout(new BoxLayout(podPanel, BoxLayout.Y_AXIS));

        /* Set Headline */
        JLabel headline = new JLabel("Pod Formation");
        headline.setAlignmentX(Component.CENTER_ALIGNMENT);
        headline.setFont(new Font("Arial", Font.BOLD, 16));
        headline.setBounds(300, 10, 200, 20);
        add(headline);

        if (podGraph == null) {
            return;
        }

        /* Get pod formation */
        PodFormation podFormation = new PodFormation(podGraph);
        podFormation.formPods(podSize);
        List<List<Student>> pods = podFormation.getPods();

        /* Display pods */
        for (int i = 0; i < pods.size(); i++) {
            String podString = "Pod " + (i+1) +": ";
            for (Student student : pods.get(i)) {
                podString += student.getName() + ", ";
            }
            if (!pods.get(i).isEmpty()) {
                podString = podString.substring(0, podString.length() - 2);
            }
            podPanel.add(new JLabel(podString));
            podPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        }

        JScrollPane scrollPane = new JScrollPane(podPanel);
        scrollPane.setBounds(10, 40, 763, 400);

        add(scrollPane);


        /* Change podsize */
        JLabel podSizeLabel = new JLabel("Pod Size: ");
        podSizeLabel.setBounds(10, 450, 100, 20);
        add(podSizeLabel);

        JTextField podSizeField = new JTextField(5);
        podSizeField.setBounds(100, 450, 50, 20);
        podSizeField.setText(Integer.toString(podSize));
        podSizeField.addActionListener(e -> {
            try {
                podSize = Integer.parseInt(podSizeField.getText());
                update(podGraph, referralGraph);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number");
            }
        });
        add(podSizeField);

        repaint();
        revalidate();
    }

}
