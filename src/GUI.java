import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GUI {
    public GUI(List<UniversityStudent> students, List<List<String>> pods, List<String> paths, List<String> chatLog) {
        JFrame frame = new JFrame("Student Information System");
        frame.setSize(1000, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton viewStudentsButton = new JButton("All Students");
        JButton showStableMatchingButton = new JButton("Stable Matching");
        JButton showPodFormationButton = new JButton("Pod Formation");
        JButton showReferralPathButton = new JButton("Referral Path");
        JButton showFriendsAndChatLogsButton = new JButton("Chat and Friend Threads");

        buttonPanel.add(viewStudentsButton);
        buttonPanel.add(showStableMatchingButton);
        buttonPanel.add(showPodFormationButton);
        buttonPanel.add(showReferralPathButton);
        buttonPanel.add(showFriendsAndChatLogsButton);

        frame.add(buttonPanel, BorderLayout.NORTH);




        viewStudentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String output = "";
                for (Student st : students) {
                    output += st.toString();
                    output += "\n\n";
                }
                textArea.setText(output);
            }
        });

        showStableMatchingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String output = "";
                for (UniversityStudent student : students) {
                    output += student.name + " is roommates with " + student.roommate + "\n";
                }
                textArea.setText(output);
            }
        });

        showPodFormationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String output = "";  
                for (int i = 0; i < pods.size(); ++i) {
                    output += "Pod " + i + ":";
                    for (String stud : pods.get(i)) {
                        output += " " + stud;
                    }
                    output += "\n";
                }
                textArea.setText(output);
            }
        });

        showReferralPathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String output = "";
                for (String path : paths) {
                    output += path;
                    output += "\n";
                }
                textArea.setText(output);
            }
        });

        showFriendsAndChatLogsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String output = "";

                for (UniversityStudent student : students) {
                    output += student.name + " is friends with ";
                    if (student.friends.size() == 0)
                        output += "nobody.\n";
                    else {
                        for (String stu : student.friends) {
                            output += stu + ", ";
                        }
                        output += "\n";
                    }
                }
        
                output += "\n\nCHAT LOG";
                for (String log : chatLog) {
                    output += log + "\n";
                }
                textArea.setText(output);
            }
        });


        frame.setVisible(true);
    }
}