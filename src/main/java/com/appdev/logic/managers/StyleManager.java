package com.appdev.logic.managers;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
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
    label.putClientProperty("FlatLaf.style", "font: bold $h00.font");
  }

  public static void h0(JLabel label) {
    label.putClientProperty("FlatLaf.style", "font: bold $h0.font");
  }

  public static void h1(JLabel label) {
    label.putClientProperty("FlatLaf.style", "font: $h1.font");
  }

  public static void h2(JLabel label) {
    label.putClientProperty("FlatLaf.style", "font: $h2.font");
  }

  public static void h3(JLabel label) {
    label.putClientProperty("FlatLaf.style", "font: $h3.font");
  }

  public static void styleTablePanel(JPanel panel) {
    panel.putClientProperty(FlatClientProperties.STYLE, "arc:20; background:$Table.background;");
  }

  public static void styleTable(JTable table) {
    table.putClientProperty(
        FlatClientProperties.STYLE,
        "rowHeight:90; showHorizontalLines:true; showVerticalLines:true;");
    table.getTableHeader().putClientProperty(FlatClientProperties.STYLE, "height:30; font:bold;");
  }

  public static void styleScrollPane(JScrollPane scrollPane) {
    scrollPane
        .getVerticalScrollBar()
        .putClientProperty(
            FlatClientProperties.STYLE,
            "trackArc:$ScrollBar.thumbArc; trackInsets:3,3,3,3; thumbInsets:3,3,3,3; background:$Table.background;");
  }

  public static void styleTitle(JLabel title) {
    title.putClientProperty(FlatClientProperties.STYLE, "font:bold +2;");
  }

  public static void styleActionPanel(JPanel actionPanel) {
    actionPanel.putClientProperty(FlatClientProperties.STYLE, "background:null;");
  }

  public static void styleSearchField(JTextField searchField) {
    searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search...");
    searchField.putClientProperty(
        FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("icons/search.svg", 0.4f));
  }

  public static void styleSeparator(JSeparator separator) {
    separator.putClientProperty(FlatClientProperties.STYLE, "foreground:$Table.gridColor;");
  }

  public static void styleInvalidField(JComponent component) {
    component.putClientProperty(FlatClientProperties.OUTLINE, FlatClientProperties.OUTLINE_ERROR);
  }

  public static void styleResetField(JComponent component) {
    component.putClientProperty(FlatClientProperties.OUTLINE, null);
  }

  public static void styleErrorMessagelabel(JLabel label) {
    label.putClientProperty(
        FlatClientProperties.STYLE, "foreground: #E53E4D; font: semibold $small.font;");
  }
}
