package com.appdev;

import com.appdev.presentation.MainFrame;
import javax.swing.SwingUtilities;

public class Main {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
  }
}
