package com.appdev.presentation.components.table;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.TableCellRenderer;

public class TableImageCellRenderer extends JPanel implements TableCellRenderer {

  private final TableCellRenderer delegate;
  private JLabel imageLabel;

  public TableImageCellRenderer(JTable table) {
    delegate = table.getDefaultRenderer(Object.class);
    init();
  }

  private void init() {
    setLayout(new BorderLayout());
    imageLabel = new JLabel();
    imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
    add(imageLabel, BorderLayout.CENTER);
  }

  @Override
  public Component getTableCellRendererComponent(
      JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    JLabel com =
        (JLabel)
            delegate.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

    if (value instanceof Icon) {
      imageLabel.setIcon((Icon) value);
    } else {
      imageLabel.setIcon(null); // If it's not an icon, ensure no icon is set
    }

    if (isSelected) {
      setBackground(table.getSelectionBackground());
      imageLabel.setBackground(table.getSelectionBackground());
      setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, table.getSelectionBackground()));
    } else {
      setBackground(com.getBackground());
      imageLabel.setBackground(com.getBackground());
      setBorder(new MatteBorder(0, 0, 1, 1, new Color(235, 235, 235)));
    }

    return this;
  }
}
