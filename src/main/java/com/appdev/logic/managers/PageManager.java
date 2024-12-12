package com.appdev.logic.managers;

import com.appdev.presentation.MainFrame;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import java.awt.EventQueue;
import javax.swing.JComponent;

public class PageManager {
  private MainFrame application;
  private static PageManager instance;

  private PageManager() {}

  public static PageManager getInstance() {
    if (instance == null) instance = new PageManager();
    return instance;
  }

  public void initApplication(MainFrame application) {
    this.application = application;
  }

  public void showPage(JComponent page) {
    EventQueue.invokeLater(
        () -> {
          FlatAnimatedLafChange.showSnapshot();
          application.setContentPane(page);
          application.revalidate();
          application.repaint();
          FlatAnimatedLafChange.hideSnapshotWithAnimation();
        });
  }
}
