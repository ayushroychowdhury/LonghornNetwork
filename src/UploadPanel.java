
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UploadPanel extends JPanel {

    private JButton uploadButton;
    private JFileChooser fileChooser;
    private JLabel statusLabel;

    public UploadPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(250, 800));
        setBorder(BorderFactory.createTitledBorder("Upload Data"));

        uploadButton = new JButton("Upload Text File");
        statusLabel = new JLabel("<html><center>No file selected.</center></html>");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        uploadButton.addActionListener(new UploadButtonListener());

        add(uploadButton, BorderLayout.NORTH);
        add(statusLabel, BorderLayout.CENTER);
    }

    private class UploadButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            fileChooser = new JFileChooser();
            // Set file filter to accept only .txt files
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
            fileChooser.setFileFilter(filter);

            int option = fileChooser.showOpenDialog(UploadPanel.this);
            if (option == JFileChooser.APPROVE_OPTION) {
                java.io.File file = fileChooser.getSelectedFile();
                statusLabel.setText("<html><center>Selected: " + file.getName() + "</center></html>");

                // Parse Text File and build StudentGraph
                try {
                    List<UniversityStudent> students = DataParser.parseStudents(file.getAbsolutePath());
                    StudentGraph graph = new StudentGraph(students);

                    // Pass the students list to StudentListPanel
                    MainFrame mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(UploadPanel.this);
                    mainFrame.getStudentListPanel().setStudents(students);

                    // Update Filters based on data
                    mainFrame.getFilterPanel().updateFilters(students);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(UploadPanel.this,
                            "Error parsing file: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
