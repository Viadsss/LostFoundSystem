package com.appdev.presentation.pages;

import javax.swing.*;
import com.appdev.logic.managers.PageManager;
import com.appdev.logic.managers.StyleManager;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;


public class LandingPage extends JScrollPane {
    private JPanel panel;
    private JLabel titleLabel, descriptionLabel;
    private JButton itemFoundButton, itemLostButton, adminAccessButton;
    private JOptionPane adminAccessOptionPane;
    private JFrame OptionFrame;
    private String password = "AdminAccessPassword";
    public LandingPage() {
        initializeComponents();
        configureLayout();
        applyStyles();
        attachEventHandlers();
    }


    private void initializeComponents() {
        titleLabel = new JLabel("ULaF", SwingConstants.CENTER); // Center text horizontally
        descriptionLabel = new JLabel("University Lost and Found System", SwingConstants.CENTER);

        itemFoundButton = new JButton("Report an item I found.");
        itemLostButton = new JButton("Report an item lost.");
        adminAccessButton = new JButton("Admin Access");
        
        adminAccessOptionPane = new JOptionPane();
        OptionFrame = new JFrame();
        
        // Panel for containing all components
        panel = new JPanel(new MigLayout("fillx,wrap 1", "[center]", "")); // Centered content
    }


    private void configureLayout() {
        // Panel to hold the main content and center it vertically and horizontally
        JPanel topPanel = new JPanel(new MigLayout("fill,align 50% 50%", "[center]", "[center]")); // Centered content
        setViewportView(topPanel);
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
        getVerticalScrollBar().setUnitIncrement(16);

        topPanel.add(panel); // Add main content to the center

        // Add components to the main panel
        panel.add(titleLabel, "growx, wrap"); // Centered label
        panel.add(descriptionLabel, "growx, wrap"); // Centered label

        // Buttons in a column
        JPanel buttonPanel = new JPanel(new MigLayout("wrap 1", "[grow,fill]", "")); // One button per row
        buttonPanel.add(itemFoundButton);
        buttonPanel.add(itemLostButton);
        buttonPanel.add(adminAccessButton);

        panel.add(buttonPanel, "growx, wrap");
    }


    private void applyStyles() {
        titleLabel.setFont(titleLabel.getFont().deriveFont(32f)); // Larger font size for the title
        descriptionLabel.setFont(descriptionLabel.getFont().deriveFont(16f)); // Larger font size for the description
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
        adminAccessButton.addActionListener(e -> {
                        String inputPassword = JOptionPane.showInputDialog(OptionFrame,"Enter Admin Password:","Admin Access",JOptionPane.PLAIN_MESSAGE);
                        
                        if(inputPassword != null && inputPassword.equals(password)){
                            PageManager.getInstance().showPage(new AdminPage(true));
                        }else{
                            JOptionPane.showMessageDialog(OptionFrame, "Incorrect Password.", "Admin Access", JOptionPane.ERROR_MESSAGE);
                            
                        }
                });
    }

}
