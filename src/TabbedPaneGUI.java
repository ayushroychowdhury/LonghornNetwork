import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutorService;

/**
 * Reference for TabbedPane GUI
 * @author Yahir Lopez
 */
public class TabbedPaneGUI extends JPanel {

    /**
     * Constructor for Tabbed Pane GUI JPanel
     * @param graph StudentGraph
     * @param executor ExecutorService
     */
    public TabbedPaneGUI(StudentGraph graph, ExecutorService executor) {
        super(new GridLayout(1, 1));

        JTabbedPane tabbedPane = new JTabbedPane();
        ImageIcon icon = createImageIcon("/imgs/graphimg2.png");

        JComponent panel1 = new StudentGraphGUI(graph);
        tabbedPane.addTab("StudentGraph", icon, panel1,
                "Display Student Graph");

        icon = createImageIcon("/imgs/pods.jpg");
        JComponent panel2 = new PodFormationGUI(graph);
        tabbedPane.addTab("PodFormation", icon, panel2,
                "Display Pod Formation");

        icon = createImageIcon("/imgs/company.jpg");
        JComponent panel3 = new ReferralPathFinderGUI(graph);
        tabbedPane.addTab("ReferralPathFinder", icon, panel3,
                "Find Referral Paths");

        icon = createImageIcon("/imgs/friends.jpg");
        JComponent panel4 = new FriendRequestThreadGUI(graph, executor);
        panel4.setPreferredSize(new Dimension(410, 50));
        tabbedPane.addTab("Friend Request", icon, panel4,
                "Send and Show Friend Request");

        icon = createImageIcon("/imgs/chat.png");
        JComponent panel5 = new ChatThreadGUI(graph, executor);
        tabbedPane.addTab("Chat Thread", icon, panel5,
                "Send and show Chat Messages");

        //Add the tabbed pane to this panel.
        add(tabbedPane);

        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     * @param path String
     * @return ImageIcon
     * */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = TabbedPaneGUI.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
