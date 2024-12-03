import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.ExecutorService;

public class TabbedPaneGUI extends JPanel {
    public TabbedPaneGUI(StudentGraph graph, ExecutorService executor) {
        super(new GridLayout(1, 1));

        JTabbedPane tabbedPane = new JTabbedPane();
        ImageIcon icon = null;

        JComponent panel1 = new StudentGraphGUI(graph);
        tabbedPane.addTab("StudentGraph", icon, panel1,
                "Does nothing");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        JComponent panel2 = new PodFormationGUI(graph);
        tabbedPane.addTab("PodFormation", icon, panel2,
                "Does twice as much nothing");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        JComponent panel3 = new ReferralPathFinderGUI(graph);
        tabbedPane.addTab("ReferralPathFinder", icon, panel3,
                "Still does nothing");
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

        JComponent panel4 = new FriendRequestThreadGUI(graph, executor);
        panel4.setPreferredSize(new Dimension(410, 50));
        tabbedPane.addTab("Friend Request", icon, panel4,
                "Does nothing at all");
        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

        JComponent panel5 = new ChatThreadGUI(graph, executor);
        tabbedPane.addTab("Chat Thread", icon, panel5,
                "Does nothing at all");
        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

        //Add the tabbed pane to this panel.
        add(tabbedPane);

        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }
}
