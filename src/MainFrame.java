
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private UploadPanel uploadPanel;
    private StudentListPanel studentListPanel;
    private FilterPanel filterPanel;

    public MainFrame() {
        setTitle("Student List Visualization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1600, 800);
        setLayout(new BorderLayout());

        uploadPanel = new UploadPanel();
        studentListPanel = new StudentListPanel();
        filterPanel = new FilterPanel();

        add(uploadPanel, BorderLayout.WEST);
        add(new JScrollPane(studentListPanel), BorderLayout.CENTER);
        add(filterPanel, BorderLayout.EAST);

        setVisible(true);
    }

    public StudentListPanel getStudentListPanel() {
        return studentListPanel;
    }

    public FilterPanel getFilterPanel() {
        return filterPanel;
    }

    public UploadPanel getUploadPanel() {
        return uploadPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}
