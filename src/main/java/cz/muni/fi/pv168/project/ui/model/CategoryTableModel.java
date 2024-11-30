package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.service.crud.CrudService;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class CategoryTableModel extends AbstractTableModel implements EntityTableModel<Category> {
    private List<Category> categories;
    private final CrudService<Category> categoryCrudService;

    private final List<Column<Category, ?>> columns = List.of(
            Column.editable("ID", Long.class, Category::getId, Category::setId),
            Column.editable("Name", String.class, Category::getName, Category::setName),
            Column.editable("Icon", Icon.class, Category::getIcon, Category::setIcon)
    );

    public CategoryTableModel(CrudService<Category> categoryCrudService) {
        this.categoryCrudService = categoryCrudService;
        this.categories = new ArrayList<>(categoryCrudService.findAll());
    }

    public void refresh() {
        this.categories = new ArrayList<>(categoryCrudService.findAll());
        fireTableDataChanged();
    }

    public void addRow(Category category) {
        try {
            int newRowIndex = getRowCount();
            categoryCrudService.create(category).intoException();
            categories.add(category);
            fireTableRowsInserted(newRowIndex, newRowIndex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void removeRow(int rowIndex) {
        var categoryToBeDeleted = getEntity(rowIndex);
        categoryCrudService.deleteById(categoryToBeDeleted.getId());
        categories.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void updateRow(Category category) {
        try {
            int rowIndex = categories.indexOf(category);
            categoryCrudService.update(category).intoException();
            fireTableRowsUpdated(rowIndex, rowIndex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Category getRow(int rowIndex) {
        return categories.get(rowIndex);
    }

    public int indexOf(Category category) {
        return categories.indexOf(category);
    }

    @Override
    public int getRowCount() {
        return categories.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns.get(columnIndex).getName();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Category category = categories.get(rowIndex);
        return columns.get(columnIndex).getValue(category);
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (value != null) {
            var category = getEntity(rowIndex);
            updateRow(category);
            //columns.get(columnIndex).setValue(value, category);
        }
    }

    public Category getEntity(int rowIndex) {
        return categories.get(rowIndex);
    }

    public void addCategory(Category newCategory) {

    }

    public void removeCategory(Category category) {

    }
}
