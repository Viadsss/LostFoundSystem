package com.appdev.presentation.components.table;

import java.text.DecimalFormat;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class TablePercentageCellRenderer extends DefaultTableCellRenderer {
  private static final DecimalFormat formatter = new DecimalFormat("#0.00%");

  public TablePercentageCellRenderer() {
    setHorizontalAlignment(SwingConstants.CENTER); // Set text alignment to center
  }

  @Override
  protected void setValue(Object value) {
    if (value instanceof Double) {
      value = formatter.format((Double) value); // Format the double value as a percentage
    }
    super.setValue(value); // Use the default rendering after formatting
  }
}
