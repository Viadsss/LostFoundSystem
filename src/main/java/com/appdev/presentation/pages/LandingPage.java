package com.appdev.presentation.pages;

import com.appdev.logic.managers.PageManager;
import com.appdev.logic.managers.StyleManager;
import com.formdev.flatlaf.FlatClientProperties;
import io.github.cdimascio.dotenv.Dotenv;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import org.tinylog.Logger;

public class LandingPage extends JScrollPane {
  private static final Dotenv dotenv = Dotenv.load();
  private static final String PASSWORD = dotenv.get("ADMIN_PASSWORD");
  private JPanel panel;
  private JLabel titleLabel, descriptionLabel;
  private JButton itemFoundButton, itemLostButton, adminAccessButton;
  private JFrame OptionFrame;

  public LandingPage() {
    if (PASSWORD == null || PASSWORD.isEmpty()) {
      Logger.error("ADMIN_PASSWORD is missing from the .env file");
      System.exit(1);
    }
    initializeComponents();
    configureLayout();
    applyStyles();
    attachEventHandlers();
  }

  private void initializeComponents() {
    titleLabel = new JLabel("ULaF", SwingConstants.CENTER); // Center text horizontally
    descriptionLabel = new JLabel("University Lost and Found System", SwingConstants.CENTER);

    itemFoundButton = new JButton("Report Found Item");
    itemLostButton = new JButton("Report Lost Item");
    adminAccessButton = new JButton("Admin Access");

    OptionFrame = new JFrame();

    // Panel for containing all components
    panel = new JPanel(new MigLayout("fillx,wrap 1", "[center]", "")); // Centered content
  }

  private void configureLayout() {
    // Panel to hold the main content and center it vertically and horizontally
    JPanel topPanel =
        new JPanel(new MigLayout("fill,align 50% 50%", "[center]", "[center]")); // Centered content
    setViewportView(topPanel);
    setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
    setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
    getVerticalScrollBar().setUnitIncrement(16);

    topPanel.add(panel); // Add main content to the center

    // Add components to the main panel
    panel.add(titleLabel, "growx, wrap"); // Centered label
    panel.add(descriptionLabel, "growx, wrap"); // Centered label

    // Buttons in a column
    JPanel buttonPanel =
        new JPanel(new MigLayout("wrap 1", "[grow,fill]", "")); // One button per row
    buttonPanel.add(itemLostButton);
    buttonPanel.add(itemFoundButton);
    buttonPanel.add(adminAccessButton);

    panel.add(buttonPanel, "growx, wrap");
  }

  private void applyStyles() {
    titleLabel.setFont(titleLabel.getFont().deriveFont(32f)); // Larger font size for the title
    descriptionLabel.setFont(
        descriptionLabel.getFont().deriveFont(16f)); // Larger font size for the description
    StyleManager.h0(titleLabel); // Custom styles for title
    StyleManager.h3(descriptionLabel); // Custom styles for description
    itemFoundButton.putClientProperty(
        FlatClientProperties.STYLE,
        ""
            + "font: +2 bold;"
            + "margin: 8, 12, 8, 12;"
            + "background: #741B13;"
            + "borderColor: #741B13;"
            + "borderWidth: 1;"
            + "focusColor: #74211380;"
            + "focusedBorderColor: #681E118D;"
            + "foreground: #F6F6F6;"
            + "hoverBackground: #811E15;"
            + "hoverBorderColor: #681E118D;"
            + "pressedBackground: #A0251A");
    itemLostButton.putClientProperty(
        FlatClientProperties.STYLE,
        ""
            + "font: +2 bold;"
            + "margin: 8, 12, 8, 12;"
            + "background: #741B13;"
            + "borderColor: #741B13;"
            + "borderWidth: 1;"
            + "focusColor: #74211380;"
            + "focusedBorderColor: #681E118D;"
            + "foreground: #F6F6F6;"
            + "hoverBackground: #811E15;"
            + "hoverBorderColor: #681E118D;"
            + "pressedBackground: #A0251A");
    adminAccessButton.putClientProperty(
        FlatClientProperties.STYLE,
        ""
            + "font: +2 bold;"
            + "margin: 8, 12, 8, 12;"
            + "background: #741B13;"
            + "borderColor: #741B13;"
            + "borderWidth: 1;"
            + "focusColor: #74211380;"
            + "focusedBorderColor: #681E118D;"
            + "foreground: #F6F6F6;"
            + "hoverBackground: #811E15;"
            + "hoverBorderColor: #681E118D;"
            + "pressedBackground: #A0251A");
  }

  private void attachEventHandlers() {
    itemFoundButton.addActionListener(e -> PageManager.getInstance().showPage(new ItemFoundPage()));
    itemLostButton.addActionListener(e -> PageManager.getInstance().showPage(new ItemLostPage()));
    adminAccessButton.addActionListener(
        e -> {
          JPasswordField passwordField = new JPasswordField(20);
          passwordField.putClientProperty(FlatClientProperties.STYLE, "showRevealButton: true");
          Object[] options = {"OK", "Cancel"};

          int option =
              JOptionPane.showOptionDialog(
                  OptionFrame,
                  new Object[] {"Enter Admin Password:", passwordField},
                  "Admin Access",
                  JOptionPane.OK_CANCEL_OPTION,
                  JOptionPane.QUESTION_MESSAGE,
                  null,
                  options,
                  options[0]);
          if (option == JOptionPane.OK_OPTION) {
            String inputPassword = new String(passwordField.getPassword());

            if (inputPassword.equals(PASSWORD)) {
              PageManager.getInstance().showPage(new AdminPage(false));
            } else {
              JOptionPane.showMessageDialog(
                  OptionFrame, "Incorrect Password.", "Admin Access", JOptionPane.ERROR_MESSAGE);
            }
          }
        });
  }
}
