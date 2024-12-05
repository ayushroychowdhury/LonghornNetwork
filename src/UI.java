
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class UI extends JFrame{
    private JPanel peoplePanel;
    private JPanel mainPanel;
    private StudentGraph graph;
    private CardLayout cardLayout;
    private HashMap<String, PersonPage> pageMap;
    
    public UI(StudentGraph graph){
        this.graph = graph;
        pageMap = new HashMap<String, PersonPage>();
        setTitle("Friend App");
        setSize(1200,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        peoplePanel = new JPanel();
        peoplePanel.setLayout(new BoxLayout(peoplePanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(peoplePanel);
        scrollPane.setPreferredSize(new Dimension(200, 600));
        add(scrollPane, BorderLayout.WEST);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        add(mainPanel, BorderLayout.CENTER);

        for (Node node : graph.adjList){
            JButton personButton = new JButton(node.stu.name);
            personButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Change to the selected person's page
                    cardLayout.show(mainPanel, node.stu.name);
                }
            });
            peoplePanel.add(personButton);
            personButton.setPreferredSize(new Dimension(peoplePanel.getWidth(),50));
            PersonPage page = new PersonPage(node.stu, graph, pageMap);
            pageMap.put(node.stu.name,page);
            mainPanel.add(page.getMainPage(), node.stu.name);
        }

        setVisible(true);
    }

}
