package gui;

import javax.swing.*;

/**
 * Main frame for the LHN gui
 */
public class MainFrame extends JFrame {
    public MainFrame() {
        /* Call constructor of JFrame */
        super("LHN | Longhorn Network");

        /* Set size to 4:3 format */
        setSize(800, 600);

        /* Center frame */
        setLocationRelativeTo(null);

        /* Frame should not be resizable */
        setResizable(false);

        /* Set longhorn icon */
        setIconImage(new ImageIcon("src/gui/longhorn.png").getImage());

        /* Set default close operation */
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* Add content to frame */
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Control", new ControlPanel());
        tabbedPane.addTab("Students", new StudentPanel());
        tabbedPane.addTab("Student Graph", new StudentGraphPanel());
        tabbedPane.addTab("Pods", new PodPanel());
        tabbedPane.addTab("Rooms", new RoomPanel());
        tabbedPane.addTab("Friends", new FriendPanel());
        tabbedPane.addTab("Messages", new MessagePanel());

        add(tabbedPane);
    }
}
