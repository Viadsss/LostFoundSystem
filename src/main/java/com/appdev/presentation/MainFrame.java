package com.appdev.presentation;

import com.appdev.logic.managers.PageManager;
import javax.swing.JFrame;

public class MainFrame extends JFrame {
  public MainFrame() {
    setTitle("ULaF");
    setSize(1366, 768); // 1280 x 720
    setLocationRelativeTo(null);
    // setContentPane(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    PageManager.getInstance().initApplication(this);
  }
}
