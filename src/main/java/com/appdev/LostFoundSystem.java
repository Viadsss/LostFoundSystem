package com.appdev;

import com.appdev.presentation.MainFrame;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.inter.FlatInterFont;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Font;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class LostFoundSystem {
  public static void main(String[] args) {
    FlatLaf.registerCustomDefaultsSource("themes");
    FlatMacLightLaf.setup();

    FlatInterFont.install();
    UIManager.put("defaultFont", new Font(FlatInterFont.FAMILY, Font.PLAIN, 13));

    SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
  }
}
