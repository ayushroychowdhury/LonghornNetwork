import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ChatThreadGUI extends JPanel implements ActionListener {
    JTextArea inputSender;
    JTextArea inputReceiver;
    JTextArea inputMessage;
    JTextArea inputAmount;
    JTextArea output;
    StudentGraph graph;
    ExecutorService executor;

    public ChatThreadGUI(StudentGraph graph, ExecutorService executor) {
        super(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        this.graph = graph;
        this.executor = executor;

        JButton button = new JButton("Send");
        button.setActionCommand("Send");
        button.addActionListener(this);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        add(button, c);

        JLabel label = new JLabel("Sender");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.01;
        c.gridx = 1;
        c.gridy = 0;
        add(label, c);

        JTextArea textArea = new JTextArea("Student");
        textArea.setBorder(BorderFactory.createLineBorder(Color.black));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.25;
        c.gridx = 2;
        c.gridy = 0;
        add(textArea, c);
        inputSender = textArea;

        JLabel label2 = new JLabel("Receiver");
        c.weightx = 0.01;
        c.gridx = 3;
        c.gridy = 0;
        add(label2, c);

        JTextArea textArea3 = new JTextArea("Student");
        textArea.setBorder(BorderFactory.createLineBorder(Color.black));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.25;
        c.gridx = 4;
        c.gridy = 0;
        add(textArea3, c);
        inputReceiver = textArea3;

        JButton button2 = new JButton("Get Chat History");
        button2.setActionCommand("Get");
        button2.addActionListener(this);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        add(button2, c);

        JLabel label3 = new JLabel("Message");
        c.weightx = 0.01;
        c.gridx = 1;
        c.gridy = 1;
        add(label3, c);

        JTextArea textArea4 = new JTextArea("Student");
        textArea4.setBorder(BorderFactory.createLineBorder(Color.black));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.25;
        c.gridx = 2;
        c.gridy = 1;
        add(textArea4, c);
        inputMessage = textArea4;

        JLabel label4 = new JLabel("Amount");
        c.weightx = 0.01;
        c.gridx = 3;
        c.gridy = 1;
        add(label4, c);

        JTextArea textArea5 = new JTextArea("Amount");
        textArea4.setBorder(BorderFactory.createLineBorder(Color.black));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.25;
        c.gridx = 4;
        c.gridy = 1;
        add(textArea5, c);
        inputAmount = textArea5;

        JTextArea textArea2 = new JTextArea("Results");
        textArea2.setBorder(BorderFactory.createLineBorder(Color.red));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 40;
        c.weightx = 0.0;
        c.gridwidth = 5;
        c.gridx = 0;
        c.gridy = 2;
        //add(textArea2, c);
        output = textArea2;

        JScrollPane scrollPane = new JScrollPane(output);
        add(scrollPane, c);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String studentSender = inputSender.getText().trim();
        UniversityStudent studentSenderUS = graph.getStudent(studentSender);
        String studentReceiver = inputReceiver.getText().trim();
        UniversityStudent studentReceiverUS = graph.getStudent(studentReceiver);
        if (studentSenderUS == null) {
            JOptionPane.showMessageDialog(this, "No student found with name " + studentSender, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (studentReceiverUS == null) {
            JOptionPane.showMessageDialog(this, "No student found with name " + studentReceiver, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (!FriendRequestThread.areFriends(studentSenderUS, studentReceiverUS)) {
            JOptionPane.showMessageDialog(this, "Students are not friends!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if ("Send".equals(e.getActionCommand())) {
            String message = inputMessage.getText().trim();
            if (message == null) {
                JOptionPane.showMessageDialog(this, "No message", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                int amount = 0;
                try {
                    amount = Integer.parseInt(inputAmount.getText().trim());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Invalid Amount Entered", "Error", JOptionPane.ERROR_MESSAGE);
                }

                while (amount > 0) {
                    executor.execute(new ChatThread(studentSenderUS, studentReceiverUS, message));
                    --amount;
                }
            }
        } else if ("Get".equals(e.getActionCommand())) {
            List<String> messages = ChatThread.getChatHistory(studentSenderUS, studentReceiverUS);
            String result = "";
            for (String message : messages) {
                result += message + "\n";
            }
            output.setText(result);
        }
    }
}
