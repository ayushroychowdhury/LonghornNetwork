package gui;

import program.FriendManager;
import program.Student;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

/**
 * A panel managing the friends
 */
public class FriendPanel extends JPanel implements Subscriber {

    JPanel rightPanel;
    JScrollPane friendsScrollPane;

    /**
     * Constructor for the friend panel
     */
    public FriendPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        /* Left side adds new friends */
        JPanel leftPanel = new JPanel();
        leftPanel.setSize(400, 600);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        /* Set a layout manager for the left panel */
        leftPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        /* Add components to the left panel */

        /* Headline */
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel headline = new JLabel("Add Friends");
        headline.setFont(new Font("Arial", Font.BOLD, 16));
        leftPanel.add(headline, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        leftPanel.add(new JLabel("First Student"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JTextField firstStudentField = new JTextField(15);
        leftPanel.add(firstStudentField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        leftPanel.add(new JLabel("Second Student"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        JTextField secondStudentField = new JTextField(15);
        leftPanel.add(secondStudentField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        JButton addFriendsButton = getFriendsButton(firstStudentField, secondStudentField);
        leftPanel.add(addFriendsButton, gbc);

        /* Right side displays all the friends */
        rightPanel = new JPanel();
        rightPanel.setSize(400, 600);

        /* Set a layout manager for the right panel */
        rightPanel.setLayout(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel friendsHeadline = new JLabel("Friends");
        friendsHeadline.setFont(new Font("Arial", Font.BOLD, 16));
        rightPanel.add(friendsHeadline, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        friendsScrollPane = getFriendsScrollPane();
        rightPanel.add(friendsScrollPane, gbc);



        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(400); // Set the initial position of the divider
        splitPane.setEnabled(false); // Lock the divider
        add(splitPane);
    }

    /**
     * Get the scroll pane displaying the friends
     * @return The scroll pane for the friends
     */
    private JScrollPane getFriendsScrollPane() {
        JPanel friendsPanel = new JPanel();
        friendsPanel.setLayout(new BoxLayout(friendsPanel, BoxLayout.Y_AXIS));

        /* Add friends to the friends panel */
        for (Set<Student> friendship : FriendManager.getFriendSets()) {
            String friendRepresentation = "";
            friendRepresentation += friendship.toArray()[0].toString() + " and " + friendship.toArray()[1].toString();
            JLabel friendLabel = new JLabel(friendRepresentation);
            friendsPanel.add(friendLabel);
        }

        JScrollPane friendsScrollPane = new JScrollPane(friendsPanel);
        friendsScrollPane.setPreferredSize(new Dimension(300, 400));
        return friendsScrollPane;
    }

    /**
     * Get the button to add friends
     * @param firstStudentField The text field for the first student
     * @param secondStudentField The text field for the second student
     * @return The button to add friends
     */
    private JButton getFriendsButton(JTextField firstStudentField, JTextField secondStudentField) {
        JButton addFriendsButton = new JButton("Add Friends");
        addFriendsButton.addActionListener(e -> {
                    String firstStudent = firstStudentField.getText();
                    String secondStudent = secondStudentField.getText();
                    /* Add friends */
                    try{
                        FriendManager.addFriends(firstStudent, secondStudent);
                        update();
                    }catch (IllegalArgumentException ex){
                        JOptionPane.showMessageDialog(null, "Invalid students");
                    }
                    update();
                });
        return addFriendsButton;
    }

    @Override
    public void update() {
        rightPanel.remove(friendsScrollPane);
        friendsScrollPane = getFriendsScrollPane();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        rightPanel.add(friendsScrollPane, gbc);

        rightPanel.revalidate();
        rightPanel.repaint();
    }
}
