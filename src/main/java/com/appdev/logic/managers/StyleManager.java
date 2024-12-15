package com.appdev.logic.managers;

import java.awt.*;
import javax.swing.*;

public class StyleManager {
  public static void card(JPanel panel) {
    panel.putClientProperty(
        "FlatLaf.style",
        ""
            + "arc:20;"
            + "[light]background:darken(@background,3%);"
            + "[dark]background:lighten(@background,3%)");
  }

  public static void buttonLink(JButton button) {
    button.setContentAreaFilled(false);
    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
  }

  public static void h00(JLabel label) {
    label.putClientProperty( "FlatLaf.style", "font: bold $h00.font" );
  }

   public static void h0(JLabel label) {
    label.putClientProperty( "FlatLaf.style", "font: bold $h0.font" );
  } 

  public static void h1(JLabel label) {
    label.putClientProperty( "FlatLaf.style", "font: $h1.font" );
  }

  public static void h2(JLabel label) {
    label.putClientProperty( "FlatLaf.style", "font: $h2.font" );
  }

  public static void h3(JLabel label) {
    label.putClientProperty( "FlatLaf.style", "font: $h3.font" );
  }
}
