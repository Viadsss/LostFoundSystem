package com.appdev.presentation.components.table;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class MatchSearchFilterDocumentListener implements DocumentListener {
  private final JTable table;
  private final TableRowSorter<DefaultTableModel> sorter;
  private final JLabel title;
  private final String titleText;
  private final javax.swing.JTextField textField;

  public MatchSearchFilterDocumentListener(
      JTable table,
      TableRowSorter<DefaultTableModel> sorter,
      JLabel title,
      String titleText,
      javax.swing.JTextField textField) {
    this.table = table;
    this.sorter = sorter;
    this.title = title;
    this.titleText = titleText;
    this.textField = textField;
  }

  private void updateFilter() {
    String textSearch = textField.getText();
    String text = textSearch.trim().toLowerCase();

    if (text.isEmpty()) {
      sorter.setRowFilter(null);
    } else {
      sorter.setRowFilter(
          new RowFilter<DefaultTableModel, Integer>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
              int rowIndex = entry.getIdentifier();
              DefaultTableModel model = (DefaultTableModel) table.getModel();

              // Check if the text includes a keyword (e.g., id:, type:, subtype:, date:)
              if (text.startsWith("id:")) {
                String idSearchText = text.substring(3).trim();
                Object idValue = model.getValueAt(rowIndex, 0);
                return idValue != null && idValue.toString().toLowerCase().contains(idSearchText);
              }

              if (text.startsWith("lostid:")) {
                String typeSearchText = text.substring(7).trim();
                String type = model.getValueAt(rowIndex, 1).toString().toLowerCase();
                return type.contains(typeSearchText);
              }

              if (text.startsWith("foundid:")) {
                String typeSearchText = text.substring(8).trim();
                String type = model.getValueAt(rowIndex, 2).toString().toLowerCase();
                return type.contains(typeSearchText);
              }

              if (text.startsWith("date:")) {
                String dateSearchText = text.substring(5).trim();
                Object dateValue = model.getValueAt(rowIndex, 3);

                if (dateValue instanceof LocalDateTime) {
                  String formattedDate =
                      ((LocalDateTime) dateValue)
                          .format(DateTimeFormatter.ofPattern("MMMM d, yyyy hh:mm a"));
                  return formattedDate
                      .toLowerCase()
                      .contains(dateSearchText); // Compare with formatted date
                }
              }

              if (text.startsWith("status:")) {
                String typeSearchText = text.substring(7).trim();
                String type = model.getValueAt(rowIndex, 4).toString().toLowerCase();
                return type.contains(typeSearchText);
              }

              // Default behavior: search across all columns if no keyword is specified
              // Filter 'ID' column
              Object idValue = model.getValueAt(rowIndex, 0);
              if (idValue != null && idValue.toString().toLowerCase().contains(text)) {
                return true;
              }

              // Filter 'Lost Item ID' column
              Object lostIdValue = model.getValueAt(rowIndex, 1);
              if (lostIdValue != null && idValue.toString().toLowerCase().contains(text)) {
                return true;
              }

              // Filter 'Found Item ID' column
              Object foundIdValue = model.getValueAt(rowIndex, 2);
              if (foundIdValue != null && idValue.toString().toLowerCase().contains(text)) {
                return true;
              }

              // Filter 'Match Date & Time' column with formatted date
              Object dateValue = model.getValueAt(rowIndex, 3);
              if (dateValue instanceof LocalDateTime) {
                String formattedDate =
                    ((LocalDateTime) dateValue)
                        .format(DateTimeFormatter.ofPattern("MMMM d, yyyy hh:mm a"));
                if (formattedDate.toLowerCase().contains(text)) {
                  return true;
                }
              }
              // Filter 'Status' column
              String status = model.getValueAt(rowIndex, 4).toString().toLowerCase();
              if (status.contains(text)) {
                return true;
              }

              return false; // If no match in any of the columns, return false
            }
          });
    }

    // Update the title with the count of filtered rows
    title.setText(titleText + " (" + table.getRowCount() + ")");
  }

  @Override
  public void insertUpdate(DocumentEvent e) {
    updateFilter();
  }

  @Override
  public void removeUpdate(DocumentEvent e) {
    updateFilter();
  }

  @Override
  public void changedUpdate(DocumentEvent e) {
    updateFilter();
  }
}
