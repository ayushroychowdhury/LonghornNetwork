import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class FriendRequestThreadGUI extends JPanel implements ActionListener {
    JTextArea inputSender;
    JTextArea inputReceiver;
    JTextArea output;
    StudentGraph graph;
    ExecutorService executor;

    public FriendRequestThreadGUI(StudentGraph graph, ExecutorService executor) {
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

        JButton button2 = new JButton("Get Friends");
        button2.setActionCommand("Get");
        button2.addActionListener(this);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        add(button2, c);

        JTextArea textArea2 = new JTextArea("Results");
        textArea2.setBorder(BorderFactory.createLineBorder(Color.red));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 40;
        c.weightx = 0.0;
        c.gridwidth = 4;
        c.gridx = 1;
        c.gridy = 1;
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
        }

        if ("Send".equals(e.getActionCommand())) {
            if (studentReceiverUS == null) {
                JOptionPane.showMessageDialog(this, "No student found with name " + studentReceiver, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (FriendRequestThread.areFriends(studentSenderUS, studentReceiverUS)) {
                JOptionPane.showMessageDialog(this, "Student already sent a request!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            executor.execute(new FriendRequestThread(studentSenderUS, studentReceiverUS));
        }
        else if ("Get".equals(e.getActionCommand())){
            List<UniversityStudent> friends = FriendRequestThread.getFriends(studentSenderUS);
            if ( friends == null || friends.size() == 0) {
                JOptionPane.showMessageDialog(this, "This student has no friends " + studentSender, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String result = "";
            for (UniversityStudent friend : friends) {
                result += friend.getName() + "\n";
            }
            output.setText(result);
        }
    }
}
