package com.appdev.presentation.components.labels;

import javax.swing.JLabel;

public class RequiredLabel extends JLabel {

  public RequiredLabel(String text) {
    super();
    setLabelText(text);
  }

  private void setLabelText(String text) {
    // Add the asterisk with HTML styling
    setText(String.format("<html>%s<span style='color:red;'>*</span></html>", text));
  }
}
