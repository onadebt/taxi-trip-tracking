package cz.muni.fi.pv168.project.ui.tabs;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.ui.model.CategoryTableModel;
import cz.muni.fi.pv168.project.ui.renderers.ImageRenderer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RidesCategoriesPanel extends JPanel {

    private final CategoryTableModel categoryTableModel;
    private final JTable categoryTable;
    private final ImageRenderer imageRenderer = new ImageRenderer(48, 48);

    public RidesCategoriesPanel(CategoryTableModel categoryTableModel) {
        super(new BorderLayout());
        this.categoryTableModel = categoryTableModel;
        this.categoryTable = new JTable(categoryTableModel);
        initComponents();
    }

    private void initComponents() {
        JLabel titleLabel = new JLabel("Manage Ride Categories");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        this.add(titleLabel, BorderLayout.NORTH);

        JTextField searchField = new JTextField(20);
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterCategories(searchField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterCategories(searchField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterCategories(searchField.getText());
            }
        });

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(new JLabel("Search: "), BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        this.add(searchPanel, BorderLayout.NORTH);

        categoryTable.getColumnModel().getColumn(2).setCellRenderer(imageRenderer);
        // change the row height here:
        categoryTable.setRowHeight(32);

        categoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScrollPane = new JScrollPane(categoryTable);
        this.add(tableScrollPane, BorderLayout.CENTER);

        JToolBar buttonsPanel = new JToolBar();
        buttonsPanel.setFloatable(false);
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        JButton addButton = new JButton("Add Category");
        addButton.addActionListener(e -> addCategory());
        buttonsPanel.add(addButton);

        JButton editButton = new JButton("Edit Category");
        editButton.setEnabled(false);
        editButton.addActionListener(e -> editCategory(getSelectedCategory()));
        buttonsPanel.add(editButton);

        JButton deleteButton = new JButton("Delete Category");
        deleteButton.setEnabled(false);
        deleteButton.addActionListener(e -> deleteCategory(getSelectedCategory()));
        buttonsPanel.add(deleteButton);

        categoryTable.getSelectionModel().addListSelectionListener(e -> {
            boolean selected = categoryTable.getSelectedRow() >= 0;
            editButton.setEnabled(selected);
            deleteButton.setEnabled(selected);
        });


        categoryTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopupMenu(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopupMenu(e);
                }
            }
        });

        this.add(buttonsPanel, BorderLayout.NORTH);
    }

    private void addCategory() {
        String name = JOptionPane.showInputDialog("Enter category name:");
        if (name != null && !name.trim().isEmpty()) {
            String iconPath = chooseIcon();
            if (iconPath != null) {
                Category newCategory = new Category(name, iconPath);
                categoryTableModel.addRow(newCategory);
            }
        }
    }

    private void editCategory(Category selectedCategory) {
        if (selectedCategory != null) {
            Long newId = selectedCategory.getId();
            String newName = JOptionPane.showInputDialog("Edit category name:", selectedCategory.getName());
            String newIconPath = chooseIcon();

            Category tempCategory = new Category(newId, newName, newIconPath);
            categoryTableModel.updateRow(tempCategory);
            categoryTable.getColumnModel().getColumn(2).setCellRenderer(imageRenderer);
        }
    }

    private void deleteCategory(Category selectedCategory) {
        if (selectedCategory != null) {
            int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this category?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                categoryTableModel.removeRow(categoryTable.getSelectedRow()); // Remove the category from the table model
            }
        }
    }

    private Category getSelectedCategory() {
        int selectedRow = categoryTable.getSelectedRow();
        if (selectedRow >= 0) {
            return categoryTableModel.getRow(selectedRow);
        }
        return null;
    }

    private void filterCategories(String searchText) {
        // TODO: filtering categories
    }

    private String chooseIcon() {
        String[] iconNames = {"NormalRide.png", "Express.png", "Luxury.png", "sport-car.png",
                "convertible-car.png", "limousine-car.png", "normal-car.png",
                "small-car.png", "truck-car.png"};
        ImageIcon[] icons = new ImageIcon[iconNames.length];

        for (int i = 0; i < iconNames.length; i++) {
            java.net.URL iconURL = getClass().getClassLoader().getResource("icons/" + iconNames[i]);
            if (iconURL != null) {
                icons[i] = new ImageIcon(iconURL);
            }
        }

        JComboBox<ImageIcon> iconComboBox = new JComboBox<>(icons);
        int result = JOptionPane.showConfirmDialog(null, iconComboBox, "Select an icon", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            int selectedIndex = iconComboBox.getSelectedIndex();
            if (selectedIndex >= 0 && selectedIndex < iconNames.length) {
                return "/icons/" + iconNames[selectedIndex];
            }
        }

        return null;
    }

    private void showPopupMenu(MouseEvent e) {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem addCategory = new JMenuItem("Add Category");
        addCategory.addActionListener(event -> addCategory());
        popupMenu.add(addCategory);

        JMenuItem editCategory = new JMenuItem("Edit Category");
        editCategory.addActionListener(event -> editCategory(getSelectedCategory()));
        popupMenu.add(editCategory);

        popupMenu.addSeparator();

        JMenuItem deleteCategory = new JMenuItem("Delete Category");
        deleteCategory.addActionListener(event -> deleteCategory(getSelectedCategory()));
        popupMenu.add(deleteCategory);

        popupMenu.show(e.getComponent(), e.getX(), e.getY());
    }
}
