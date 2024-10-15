package cz.muni.fi.pv168.project.ui.tabs;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class RidesCategories {

    // List to hold categories
    private static DefaultListModel<Category> categoryListModel = new DefaultListModel<>();

    // Array of icon file names (located in resources/icons folder)
    private static String[] iconNames = {"NormalRide.png", "Express.png", "Luxuary.png"};

    public static JPanel createRidesCategoriesPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("Manage Ride Categories");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Category List
        JList<Category> categoryList = new JList<>(categoryListModel);
        categoryList.setCellRenderer(new CategoryRenderer()); // Custom renderer for color and icon

        JScrollPane listScrollPane = new JScrollPane(categoryList);
        listScrollPane.setBorder(new TitledBorder("Categories"));
        panel.add(listScrollPane, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout());

        // Add Category button
        JButton addButton = new JButton("Add Category");
        addButton.addActionListener(e -> addCategory());
        buttonsPanel.add(addButton);

        // Edit Category button
        JButton editButton = new JButton("Edit Category");
        editButton.addActionListener(e -> editCategory(categoryList.getSelectedValue()));
        buttonsPanel.add(editButton);

        JButton deleteButton = new JButton("Delete Category");
        deleteButton.setEnabled(false); // Tlačítko je výchozí zakázané
        deleteButton.addActionListener(e -> deleteCategory(categoryList.getSelectedValue()));
        buttonsPanel.add(deleteButton);

        categoryList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedCount = categoryList.getSelectedIndices().length;
                // Povolení/zakázání tlačítka edit podle počtu vybraných záznamů
                editButton.setEnabled(selectedCount == 1); // Povolit pouze pro jeden vybraný záznam
                deleteButton.setEnabled(selectedCount >= 1); // Povolit mazání, když je vybrán alespoň jeden záznam
            }
        });

        panel.add(buttonsPanel, BorderLayout.SOUTH);

        return panel;
    }

    private static void deleteCategory(Category selectedCategory) {
        if (selectedCategory != null) {
            int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this category?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                categoryListModel.removeElement(selectedCategory);
            }
        }
    }

    // Method to add a new category
    private static void addCategory() {
        String name = JOptionPane.showInputDialog("Enter category name:");
        if (name != null && !name.trim().isEmpty()) {
            Color color = JColorChooser.showDialog(null, "Choose Category Color", Color.BLACK);
            Icon icon = chooseIcon(); // Call method to choose an icon
            if (icon != null) {
                categoryListModel.addElement(new Category(name, color, icon));
            }
        }
    }

    // Method to edit the selected category
    private static void editCategory(Category selectedCategory) {
        if (selectedCategory != null) {
            String newName = JOptionPane.showInputDialog("Edit category name:", selectedCategory.getName());
            if (newName != null && !newName.trim().isEmpty()) {
                selectedCategory.setName(newName);
            }
            Color newColor = JColorChooser.showDialog(null, "Choose new color", selectedCategory.getColor());
            if (newColor != null) {
                selectedCategory.setColor(newColor);
            }
            Icon newIcon = chooseIcon(); // Allow user to choose a new icon
            if (newIcon != null) {
                selectedCategory.setIcon(newIcon);
            }
            // Notify the list model that data changed to refresh the UI
            categoryListModel.set(categoryListModel.indexOf(selectedCategory), selectedCategory);
        }
    }

    // Method to choose an icon from the predefined icons
    private static Icon chooseIcon() {
        // Create an array of ImageIcons
        ImageIcon[] icons = new ImageIcon[iconNames.length];
        for (int i = 0; i < iconNames.length; i++) {
            java.net.URL iconURL = RidesCategories.class.getResource("/icons/" + iconNames[i]);
            if (iconURL != null) {
                icons[i] = new ImageIcon(iconURL);
            }
        }

        // Create JComboBox with icons
        JComboBox<ImageIcon> iconComboBox = new JComboBox<>(icons);
        iconComboBox.setRenderer(new ComboBoxRenderer());

        // Show dialog to select icon
        int result = JOptionPane.showConfirmDialog(null, iconComboBox,
                "Select an icon", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            return (ImageIcon) iconComboBox.getSelectedItem();
        }
        return null;
    }

    // Category class to hold name, color, and icon
    static class Category {
        private String name;
        private Color color;
        private Icon icon;

        public Category(String name, Color color, Icon icon) {
            this.name = name;
            this.color = color;
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public Icon getIcon() {
            return icon;
        }

        public void setIcon(Icon icon) {
            this.icon = icon;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // Custom renderer to display category name with color and icon
    static class CategoryRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Category category = (Category) value;
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setText(category.getName());
            label.setIcon(category.getIcon());
            label.setForeground(category.getColor());
            return label;
        }
    }

    // Renderer for JComboBox to display icons
    private static class ComboBoxRenderer extends JLabel implements ListCellRenderer<ImageIcon> {

        @Override
        public Component getListCellRendererComponent(JList<? extends ImageIcon> list, ImageIcon value, int index, boolean isSelected, boolean cellHasFocus) {
            setIcon(value);
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setOpaque(true); // Ensures background is painted
            return this;
        }
    }
}
