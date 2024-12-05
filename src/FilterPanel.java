import javax.swing.*;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FilterPanel extends JPanel {

    // Existing filters
    private JComboBox<String> internshipComboBox;
    private JTextField ageField;

    // New filters
    private JComboBox<String> genderComboBox;
    private JComboBox<String> yearComboBox;
    private JComboBox<String> majorComboBox;

    private JButton applyFilterButton;
    private JButton resetFilterButton;

    // Store unique filter options
    private List<String> internshipOptions;
    private List<String> genderOptions;
    private List<String> yearOptions;
    private List<String> majorOptions;

    public FilterPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Filters"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Initialize combo boxes
        internshipComboBox = new JComboBox<>();
        internshipComboBox.addItem("All Internships");

        genderComboBox = new JComboBox<>();
        genderComboBox.addItem("All Genders");

        yearComboBox = new JComboBox<>();
        yearComboBox.addItem("All Years");

        majorComboBox = new JComboBox<>();
        majorComboBox.addItem("All Majors");

        // Age filter
        ageField = new JTextField(5);
        ageField.setToolTipText("Enter maximum age");

        // Buttons
        applyFilterButton = new JButton("Apply Filter");
        resetFilterButton = new JButton("Reset Filter");

        applyFilterButton.addActionListener(new ApplyFilterListener());
        resetFilterButton.addActionListener(new ResetFilterListener());

        // Add components to panel with labels
        int y = 0;

        // Internship Filter
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("Internship:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(internshipComboBox, gbc);
        y++;

        // Gender Filter
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("Gender:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(genderComboBox, gbc);
        y++;

        // Year Filter
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("Year:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(yearComboBox, gbc);
        y++;

        // Major Filter
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("Major:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(majorComboBox, gbc);
        y++;

        // Age Filter
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("Max Age:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(ageField, gbc);
        y++;

        // Buttons
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.anchor = GridBagConstraints.CENTER;
        add(applyFilterButton, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(resetFilterButton, gbc);
    }

    /**
     * Updates the filter options based on the uploaded data.
     *
     * @param students List of all students.
     */
    public void updateFilters(List<UniversityStudent> students) {
        // Initialize sets to collect unique values
        Set<String> internships = new HashSet<>();
        Set<String> genders = new HashSet<>();
        Set<Integer> years = new HashSet<>();
        Set<String> majors = new HashSet<>();

        for (UniversityStudent student : students) {
            // Internships
            if (student.getInternships() != null) {
                internships.addAll(student.getInternships());
            }

            // Gender
            if (student.getGender() != null && !student.getGender().isEmpty()) {
                genders.add(student.getGender());
            }

            // Year
            years.add(student.getYear());

            // Major
            if (student.getMajor() != null && !student.getMajor().isEmpty()) {
                majors.add(student.getMajor());
            }
        }

        // Convert sets to sorted lists
        internshipOptions = new ArrayList<>(internships);
        Collections.sort(internshipOptions);

        genderOptions = new ArrayList<>(genders);
        Collections.sort(genderOptions);

        // Convert Integer to String for yearOptions and sort numerically
        yearOptions = years.stream()
                .map(String::valueOf)
                .sorted(Comparator.comparingInt(Integer::parseInt))
                .collect(Collectors.toList());

        majorOptions = new ArrayList<>(majors);
        Collections.sort(majorOptions);

        // Populate Internship ComboBox
        internshipComboBox.removeAllItems();
        internshipComboBox.addItem("All Internships");
        for (String internship : internshipOptions) {
            internshipComboBox.addItem(internship);
        }

        // Populate Gender ComboBox
        genderComboBox.removeAllItems();
        genderComboBox.addItem("All Genders");
        for (String gender : genderOptions) {
            genderComboBox.addItem(gender);
        }

        // Populate Year ComboBox
        yearComboBox.removeAllItems();
        yearComboBox.addItem("All Years");
        for (String year : yearOptions) {
            yearComboBox.addItem(year);
        }

        // Populate Major ComboBox
        majorComboBox.removeAllItems();
        majorComboBox.addItem("All Majors");
        for (String major : majorOptions) {
            majorComboBox.addItem(major);
        }
    }

    private class ApplyFilterListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedInternship = (String) internshipComboBox.getSelectedItem();
            String selectedGender = (String) genderComboBox.getSelectedItem();
            String selectedYear = (String) yearComboBox.getSelectedItem();
            String selectedMajor = (String) majorComboBox.getSelectedItem();
            String ageText = ageField.getText().trim();

            Integer maxAge = null;
            if (!ageText.isEmpty()) {
                try {
                    maxAge = Integer.parseInt(ageText);
                    if (maxAge < 0) {
                        throw new NumberFormatException("Age cannot be negative.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(FilterPanel.this,
                            "Please enter a valid positive integer for age.",
                            "Invalid Age",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Create RowFilter based on selected filters
            List<RowFilter<DefaultTableModel, Object>> filters = new ArrayList<>();

            // Internship Filter
            if (!"All Internships".equals(selectedInternship)) {
                filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(selectedInternship), 2)); // Column 2: Internships
            }

            // Gender Filter
            if (!"All Genders".equals(selectedGender)) {
                filters.add(RowFilter.regexFilter("^" + Pattern.quote(selectedGender) + "$", 3)); // Column 3: Gender
            }

            // Year Filter
            if (!"All Years".equals(selectedYear)) {
                filters.add(RowFilter.regexFilter("^" + Pattern.quote(selectedYear) + "$", 4)); // Column 4: Year
            }

            // Major Filter
            if (!"All Majors".equals(selectedMajor)) {
                filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(selectedMajor), 5)); // Column 5: Major
            }

            // Age Filter
            if (maxAge != null) {
                filters.add(RowFilter.numberFilter(RowFilter.ComparisonType.BEFORE, maxAge + 1, 1)); // Column 1: Age
            }

            RowFilter<DefaultTableModel, Object> compoundRowFilter = null;
            if (!filters.isEmpty()) {
                compoundRowFilter = RowFilter.andFilter(filters);
            }

            // Apply filter to StudentListPanel
            MainFrame mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(FilterPanel.this);
            StudentListPanel studentListPanel = mainFrame.getStudentListPanel();

            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) studentListPanel.table.getModel());
            sorter.setRowFilter(compoundRowFilter);
            studentListPanel.table.setRowSorter(sorter);
        }
    }

    private class ResetFilterListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            internshipComboBox.setSelectedIndex(0);
            genderComboBox.setSelectedIndex(0);
            yearComboBox.setSelectedIndex(0);
            majorComboBox.setSelectedIndex(0);
            ageField.setText("");

            // Reset filter in StudentListPanel
            MainFrame mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(FilterPanel.this);
            StudentListPanel studentListPanel = mainFrame.getStudentListPanel();
            studentListPanel.resetFilter();
        }
    }
}
