package com.appdev.presentation.components.labels;

import com.appdev.logic.managers.StyleManager;
import javax.swing.JLabel;

public class ErrorLabel extends JLabel {
  public ErrorLabel(String text) {
    super();
    setLabelText(text);
  }

  private void setLabelText(String text) {
    StyleManager.styleErrorMessagelabel(this);
    setText(text);
  }
}
