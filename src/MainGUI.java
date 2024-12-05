import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Reference for Main GUI
 * @author Yahir Lopez
 */
public class MainGUI {
    private StudentGraph graph;
    private ExecutorService executor;

    /**
     * Constructor for the MainGUI
     * @param graph StudentGraph
     */
    public MainGUI(StudentGraph graph) {
        this.graph = graph;
        this.executor = Executors.newSingleThreadExecutor();
    }

    /**
     * Show the MainGUI which calls invokeLater
     */
    public void show() {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    /**
     * Creates the MainGui
     */
    private void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("StudentGraphGUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new TabbedPaneGUI(this.graph, this.executor), BorderLayout.CENTER);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
