package com.appdev.presentation.components.labels;

import javax.swing.JLabel;

public class DescriptionLabel extends JLabel {
  public DescriptionLabel(String boldText, String regularText) {
    super();
    setLabelText(boldText, regularText);
  }

  private void setLabelText(String boldText, String regularText) {
    setText(String.format("<html><b>%s</b>%s</html>", boldText, regularText));
  }
}
