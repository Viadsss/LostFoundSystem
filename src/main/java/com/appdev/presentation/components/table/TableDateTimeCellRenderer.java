package com.appdev.presentation.components.table;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.table.DefaultTableCellRenderer;

public class TableDateTimeCellRenderer extends DefaultTableCellRenderer {

  private final DateTimeFormatter formatter;

  // Constructor that accepts a custom format pattern
  public TableDateTimeCellRenderer(String pattern) {
    this.formatter = DateTimeFormatter.ofPattern(pattern);
  }

  // Default constructor with a common pattern
  public TableDateTimeCellRenderer() {
    this("MMMM d, yyyy hh:mm a");
  }

  @Override
  protected void setValue(Object value) {
    if (value instanceof LocalDateTime) {
      LocalDateTime dateTime = (LocalDateTime) value;
      setText(dateTime.format(formatter));
    } else {
      super.setValue(value);
    }
  }
}
