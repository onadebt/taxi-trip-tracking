package cz.muni.fi.pv168.project.ui.tabs;

import cz.muni.fi.pv168.project.ui.model.Category;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.renderers.CategoryRenderer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RidesCategories {

    private static List<Category> allCategories = new ArrayList<>();
    private static CategoryListModel categoryListModel = new CategoryListModel(allCategories);

    private static final String[] iconNames = {"NormalRide.png", "Express.png", "Luxuary.png", "sport-car.png", "convertible-car.png","limousine-car.png",
            "normal-car.png", "small-car.png", "truck-car.png"};
    
    public static JPanel createRidesCategoriesPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Manage Ride Categories");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

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
        panel.add(searchPanel, BorderLayout.NORTH);

        JList<Category> categoryList = new JList<>(categoryListModel);
        categoryList.setCellRenderer(new CategoryRenderer());

        JScrollPane listScrollPane = new JScrollPane(categoryList);
        listScrollPane.setBorder(BorderFactory.createTitledBorder("Categories"));
        panel.add(listScrollPane, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout());

        JButton addButton = new JButton("Add Category");
        addButton.addActionListener(e -> addCategory());
        buttonsPanel.add(addButton);

        JButton editButton = new JButton("Edit Category");
        editButton.setEnabled(false);
        editButton.addActionListener(e -> editCategory(categoryList.getSelectedValue()));
        buttonsPanel.add(editButton);

        JButton deleteButton = new JButton("Delete Category");
        deleteButton.setEnabled(false);
        deleteButton.addActionListener(e -> deleteCategory(categoryList.getSelectedValuesList()));
        buttonsPanel.add(deleteButton);

        categoryList.addListSelectionListener(e -> {
            int selectedCount = categoryList.getSelectedIndices().length;
            editButton.setEnabled(selectedCount == 1);
            deleteButton.setEnabled(selectedCount > 0);
        });

        categoryList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int[] selectedIndices = categoryList.getSelectedIndices();
                    Category selectedCategory = categoryList.getSelectedValue();
                    List<Category> xd = categoryList.getSelectedValuesList();
                    showContextMenu(e.getComponent(), e.getX(), e.getY(), selectedCategory, selectedIndices.length, xd);
                }
            }
        });

        panel.add(buttonsPanel, BorderLayout.SOUTH);

        Category testCategory = new Category("standard", new ImageIcon (RidesCategories.class.getResource("/icons/" + "NormalRide.png")));
        allCategories.add(testCategory);

        return panel;
    }

    public static CategoryListModel getCategoryListModel() {
        return categoryListModel;
    }

    private static void showContextMenu(Component component, int x, int y, Category selectedCategory,  int selectedCount, List<Category> xd) {
        JPopupMenu contextMenu = new JPopupMenu();

        JMenuItem addItem = new JMenuItem("Add Category");
        addItem.addActionListener(e -> addCategory());
        contextMenu.add(addItem);

        JMenuItem editItem = new JMenuItem("Edit Category");
        editItem.setEnabled(selectedCount == 1);
        editItem.addActionListener(e -> editCategory(selectedCategory));
        contextMenu.add(editItem);

        JMenuItem deleteItem = new JMenuItem("Delete Category");
        deleteItem.setEnabled(selectedCount > 0);
        deleteItem.addActionListener(e -> deleteCategory(xd));
        contextMenu.add(deleteItem);

        contextMenu.show(component, x, y);

    }

    private static void addCategory() {
        String name = JOptionPane.showInputDialog("Enter category name:");
        if (name != null && !name.trim().isEmpty()) {
            Icon icon = chooseIcon();
            if (icon != null) {
                Category newCategory = new Category(name, icon);
                categoryListModel.addCategory(newCategory);
                allCategories.add(newCategory);
            }
        }
    }

    private static void editCategory(Category selectedCategory) {
        if (selectedCategory != null) {
            String newName = JOptionPane.showInputDialog("Edit category name:", selectedCategory.getName());
            if (newName != null && !newName.trim().isEmpty()) {
                selectedCategory.setName(newName);
            }

            Icon newIcon = chooseIcon();
            if (newIcon != null) {
                selectedCategory.setIcon(newIcon);
            }

            int index = categoryListModel.indexOf(selectedCategory);
            if (index >= 0) {
                categoryListModel.updateCategory(index, selectedCategory);
            }
        }
    }

    private static void deleteCategory(List<Category> categoryList) {
        if (!categoryList.isEmpty()) {
            int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected categories?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                for (Category category : categoryList) {
                    categoryListModel.removeCategory(category);
                }
            }
        }
    }

    private static Icon chooseIcon() {
        ImageIcon[] icons = new ImageIcon[iconNames.length];
        for (int i = 0; i < iconNames.length; i++) {
            java.net.URL iconURL = RidesCategories.class.getResource("/icons/" + iconNames[i]);
            if (iconURL != null) {
                icons[i] = new ImageIcon(iconURL);
            }
        }

        JComboBox<ImageIcon> iconComboBox = new JComboBox<>(icons);
        int result = JOptionPane.showConfirmDialog(null, iconComboBox, "Select an icon", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            return (ImageIcon) iconComboBox.getSelectedItem();
        }
        return null;
    }

    private static void filterCategories(String searchText) {
        List<Category> filteredCategories = allCategories.stream()
                .filter(category -> category.getName().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
        categoryListModel.setCategories(filteredCategories);
    }
}
