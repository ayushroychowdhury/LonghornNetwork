import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class PersonPage {
    private JPanel page;
    private JPanel roommatePanel;
    private JPanel friendsPanel;
    private JPanel friendRequestPanel;
    private JPanel podPanel;
    private JPanel accept;
    private JPanel reject;
    private JPanel addFriend;
    private JPanel refPanel;
    private JPanel sMessages;
    private JPanel rMessages;
    private CardLayout rLayout;
    private CardLayout sLayout;
    private StudentGraph graph;
    private UniversityStudent student;
    private JLabel pathLabel;
    HashMap<String, PersonPage> pageMap;

    private ArrayList<JPanel> rPanels;
    private ArrayList<JPanel> sPanels;

    private HashMap<String, JPanel> rMap;
    private HashMap<String, JPanel> sMap;

    private String currChat;

    PersonPage(UniversityStudent student, StudentGraph graph, HashMap<String, PersonPage> pageMap){
        this.graph = graph;
        this.student = student;
        this.pageMap = pageMap;

        pathLabel = new JLabel("");
        pathLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center align labels horizontally
        page = new JPanel();
        page.setLayout(new GridLayout(2,1));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2,3));
        page.add(topPanel);

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new GridLayout(1,3));
        page.add(messagePanel);

        roommatePanel = new JPanel();
        roommatePanel.setPreferredSize(new Dimension(150, 50)); // Set fixed size for consistency
        roommatePanel.setBorder(BorderFactory.createTitledBorder("Roommate Information"));
        JLabel roommateLabel = new JLabel("Roommate: " + student.getRoommateName());
        roommatePanel.add(roommateLabel);
        topPanel.add(roommatePanel);


        addFriend = new JPanel();
        addFriend.setLayout(new BoxLayout(addFriend, BoxLayout.Y_AXIS));
        addFriend.setBorder(BorderFactory.createTitledBorder(student.name + "'s Main Page"));

        JTextField friendAdder = new JTextField(10);
        friendAdder.setMaximumSize(new Dimension(300,50));
        friendAdder.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                addFriend(graph.getStudent(friendAdder.getText()));
            }
        });
        addFriend.add(friendAdder);

        topPanel.add(addFriend);

        podPanel = new JPanel();
        podPanel.setLayout(new BoxLayout(podPanel, BoxLayout.Y_AXIS));
        podPanel.setBorder(BorderFactory.createTitledBorder("Pod Members"));

        ArrayList<UniversityStudent> podMembers = student.getPodMembers();
        for (int i = 0; i < podMembers.size(); ++i){
            JLabel label = new JLabel(podMembers.get(i).name);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center align labels horizontally
            podPanel.add(label);
        }

        topPanel.add(podPanel);

        friendsPanel = new JPanel();
        friendsPanel.setLayout(new BoxLayout(friendsPanel, BoxLayout.Y_AXIS));
        friendsPanel.setBorder(BorderFactory.createTitledBorder("Current Friends"));

        ArrayList<UniversityStudent> friends = student.getFriends();
        for (int i = 0; i < friends.size(); ++i){
            JLabel label = new JLabel(friends.get(i).name);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center align labels horizontally
            friendsPanel.add(label);
        }
        topPanel.add(friendsPanel);

        friendRequestPanel = new JPanel();
        friendRequestPanel.setLayout(new GridLayout(1,2));
        friendRequestPanel.setBorder(BorderFactory.createTitledBorder("Friend Requests"));

        accept = new JPanel();
        accept.setLayout(new BoxLayout(accept, BoxLayout.Y_AXIS));
        accept.setBorder(BorderFactory.createTitledBorder("Accept"));

        reject = new JPanel();
        reject.setLayout(new BoxLayout(reject, BoxLayout.Y_AXIS));
        reject.setBorder(BorderFactory.createTitledBorder("Reject"));

        friendRequestPanel.add(accept);
        friendRequestPanel.add(reject);

        ArrayList<UniversityStudent> requests = student.getRequests();
        for (int i = 0; i < requests.size(); ++i){
            UniversityStudent send = requests.get(i);
            addRequest(send);
        }
        topPanel.add(friendRequestPanel);

        refPanel = new JPanel();
        refPanel.setLayout(new BoxLayout(refPanel, BoxLayout.Y_AXIS));
        refPanel.setBorder(BorderFactory.createTitledBorder("Referral Generator"));

        JTextField refStr = new JTextField(10);
        refStr.setMaximumSize(new Dimension(300,50));
        refStr.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                findRefPath(refStr.getText());
            }
        });

        refPanel.add(refStr);
        refPanel.add(pathLabel);
        topPanel.add(refPanel);

        
        rLayout = new CardLayout();
        rPanels = new ArrayList<JPanel>();
        rMap = new HashMap<String, JPanel>();

        rMessages = new JPanel();
        rMessages.setLayout(rLayout);
        rMessages.setBorder(BorderFactory.createTitledBorder("Received Messages"));


        JPanel sDaddy = new JPanel();
        sDaddy.setLayout(new BoxLayout(sDaddy, BoxLayout.Y_AXIS));

        sLayout = new CardLayout();
        sPanels = new ArrayList<JPanel>();
        sMap = new HashMap<String, JPanel>();

        sMessages = new JPanel();
        sMessages.setLayout(sLayout);
        sMessages.setBorder(BorderFactory.createTitledBorder("Sent Messages"));

        JTextField sendText = new JTextField(10);
        sendText.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                sendMessage(sendText.getText(),currChat);
            }
        });

        sDaddy.add(sMessages);
        sDaddy.add(sendText);

        JPanel messageOption = new JPanel();
        messageOption.setLayout(new BoxLayout(messageOption, BoxLayout.Y_AXIS));
        messageOption.setBorder(BorderFactory.createTitledBorder("Chat Histories"));

        for (int i = 0; i < graph.adjList.size(); ++i){
            UniversityStudent s = graph.adjList.get(i).stu;
            if (s.name != student.name){
            
            JButton stuButton = new JButton(s.name);
            stuButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    displayMessages(s.name);
                }
            });
            messageOption.add(stuButton);
            JPanel tempPanelR = new JPanel();
            tempPanelR.setLayout(new BoxLayout(tempPanelR, BoxLayout.Y_AXIS));
            rMessages.add(tempPanelR,s.name);
            rPanels.add(tempPanelR);
            rMap.put(s.name,tempPanelR);

            JPanel tempPanelS = new JPanel();
            tempPanelS.setLayout(new BoxLayout(tempPanelS, BoxLayout.Y_AXIS));
            sMessages.add(tempPanelS,s.name);
            rPanels.add(tempPanelS);
            sMap.put(s.name,tempPanelS);
        }
        }


        messagePanel.add(messageOption);
        messagePanel.add(rMessages);
        messagePanel.add(sDaddy);

    }

    public JPanel getRoommatePanel(){
        return roommatePanel;
    }

    public JPanel getFriendsPanel(){
        return friendsPanel;
    }

    public JPanel getRequestJPanel(){
        return friendRequestPanel;
    }
    
    public JPanel getPodJPanel(){
        return podPanel;
    }

    public JPanel getMainPage(){
        return page;
    }

    public void addFriend(UniversityStudent studentG){
        if (studentG != null){
        FriendRequestThread addThread = new FriendRequestThread(student, studentG, 0);
        Thread addFriendThread = new Thread(addThread);
        addFriendThread.start();

        try {
            addFriendThread.join();
        } catch (Exception e){
            e.printStackTrace();
        }
        PersonPage other = pageMap.get(studentG.name);
        other.addRequest(student);
    }
    }

    public void addFriendList(String name){
        JLabel label = new JLabel(name);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center align labels horizontally
        friendsPanel.add(label);

        PersonPage other = pageMap.get(name);
        other.addFriendLimit(student.name);
    }

    public void addFriendLimit(String name){
        JLabel label = new JLabel(name);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center align labels horizontally
        friendsPanel.add(label);
    }

    public void addRequest(UniversityStudent studentG){
        JButton acceptButton = new JButton("A:" + studentG.name);
            JButton rejectButton = new JButton("R:" + studentG.name);

            accept.add(acceptButton);
            reject.add(rejectButton);
            acceptButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Change to the selected person's page
                    FriendRequestThread aThread = new FriendRequestThread(studentG,student, 1);
                    Thread aThreadTrue = new Thread(aThread);
                    aThreadTrue.start();
                    try {
                    aThreadTrue.join();
                    } catch (InterruptedException t){
                        t.printStackTrace();
                    }
                    addFriendList(studentG.name);
                    accept.remove(acceptButton);
                    reject.remove(rejectButton);
                    accept.revalidate();
                    reject.revalidate();
                    accept.repaint();
                    reject.repaint();
                }
            });

            rejectButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Change to the selected person's page
                    FriendRequestThread rThread = new FriendRequestThread(studentG,student, 2);
                    Thread rThreadTrue = new Thread(rThread);
                    rThreadTrue.start();
                    try {
                    rThreadTrue.join();
                    } catch (InterruptedException t){
                        t.printStackTrace();
                    }
                    accept.remove(acceptButton);
                    reject.remove(rejectButton);
                    accept.revalidate();
                    reject.revalidate();
                    accept.repaint();
                    reject.repaint();
                }
            });
    }


    private void findRefPath(String targetCompany){
        ReferralPathFinder pathFinder = new ReferralPathFinder(graph);
        ArrayList<UniversityStudent> path = pathFinder.findReferralPath(student, targetCompany);
        String pathStr = "";
        if (path.size() > 0){
            pathStr += path.get(0).name;
            for (int i = 1; i < path.size(); ++i){
                pathStr += "->" + path.get(i).name;
            }
        }
        pathLabel.setText(pathStr);
    }

    private void displayMessages(String name){
        sLayout.show(sMessages,name);
        rLayout.show(rMessages,name);
        currChat = name;
    }

    private void sendMessage(String message, String name){
        UniversityStudent rStu = graph.stuToNode.get(currChat).stu;
        ChatThread newMess = new ChatThread(student,rStu,message);
        Thread thread = new Thread(newMess);

        thread.start();

        try {
            thread.join();
        } catch (Exception e){
            e.printStackTrace();
        }
        addSentMessage(rStu.name,message);
        pageMap.get(rStu.name).addReceiveMessage(student.name,message);
    }

    private void addSentMessage(String rName, String message){
        JPanel corrPanel = sMap.get(rName);
        if (corrPanel != null){
        JLabel mess = new JLabel(message);
        mess.setAlignmentX(Component.CENTER_ALIGNMENT);
        corrPanel.add(mess);
        corrPanel.revalidate();
        corrPanel.repaint();
        }
    }

    private void addReceiveMessage(String sName, String message){
        JPanel corrPanel = rMap.get(sName);
        if (corrPanel != null){
        JLabel mess = new JLabel(message);
        mess.setAlignmentX(Component.CENTER_ALIGNMENT);
        corrPanel.add(mess);
        }
    }

}
