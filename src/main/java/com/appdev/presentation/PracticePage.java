package com.appdev.presentation;

import com.appdev.logic.managers.StyleManager;
import com.appdev.presentation.components.labels.RequiredLabel;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class PracticePage extends JPanel {
  public PracticePage() {
    setLayout(new MigLayout("fill, insets 20", "[center]", "[center]"));

    // Create Login Panel
    JPanel loginPanel = new JPanel(new MigLayout("wrap, fillx, insets 30 45 30 45", "[200:700]"));
    StyleManager.card(loginPanel);

    JLabel loginLbl = new JLabel("Welcome bro");
    JLabel loginLbl2 = new JLabel("Welcome bro");
    JLabel loginLbl3 = new JLabel("Welcome bro");

    StyleManager.h1(loginLbl);
    StyleManager.h0(loginLbl2);
    StyleManager.h00(loginLbl3);

    loginPanel.add(loginLbl);
    loginPanel.add(loginLbl2);
    loginPanel.add(loginLbl3);
    loginPanel.add(new RequiredLabel("Login"));
    loginPanel.add(new JLabel("Username:"));
    loginPanel.add(new JTextField(20));
    loginPanel.add(new JLabel("Password:"));
    loginPanel.add(new JTextField(), "growx");
    loginPanel.add(new JButton("Log In"));
    loginPanel.add(new JTextField(), "growx");
    loginPanel.add(new JButton("Log In"));
    loginPanel.add(new JTextField(), "growx");
    loginPanel.add(new JButton("Log In"));
    loginPanel.add(new JTextField(), "growx");
    loginPanel.add(new JButton("Log In"));
    loginPanel.add(new JTextField(), "growx");
    loginPanel.add(new JButton("Log In"));
    loginPanel.add(new JTextField(), "growx");
    loginPanel.add(new JButton("Log In"));
    loginPanel.add(new JTextField(), "growx");
    loginPanel.add(new JButton("Log In"));
    loginPanel.add(new JTextField(), "growx");
    loginPanel.add(new JButton("Log In"));
    loginPanel.add(new JTextField(), "growx");
    loginPanel.add(new JButton("Log In"));
    loginPanel.add(new JTextField(), "growx");
    loginPanel.add(new JButton("Log In"));
    loginPanel.add(new JTextField(), "growx");
    loginPanel.add(new JButton("Log In"));
    loginPanel.add(new JTextField(), "growx");
    loginPanel.add(new JButton("Log In"));
    loginPanel.add(new JTextField(), "growx");
    loginPanel.add(new JButton("Log In"));
    loginPanel.add(new JTextField(), "growx");
    loginPanel.add(new JButton("Log In"));
    loginPanel.add(new JTextField(), "growx");
    loginPanel.add(new JButton("Log In"));

    JScrollPane scrollPane = new JScrollPane(loginPanel);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    add(scrollPane);
  }
}
