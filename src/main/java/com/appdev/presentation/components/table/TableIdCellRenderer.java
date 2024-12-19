package com.appdev.presentation.components.table;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class TableIdCellRenderer extends DefaultTableCellRenderer {
  public TableIdCellRenderer() {
    setHorizontalAlignment(SwingConstants.CENTER);
  }

  @Override
  protected void setValue(Object value) {
    super.setValue(value); // Use the default rendering
  }
}
