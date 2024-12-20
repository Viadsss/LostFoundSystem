package com.appdev.presentation.components.forms;

import com.appdev.logic.managers.StyleManager;
import com.appdev.logic.models.MatchItem;
import com.appdev.logic.services.ImageService;
import com.appdev.logic.services.ItemService;
import com.appdev.presentation.components.labels.ErrorLabel;
import com.appdev.presentation.components.labels.RequiredLabel;
import com.formdev.flatlaf.FlatClientProperties;
import java.io.File;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import net.miginfocom.swing.MigLayout;
import raven.extras.AvatarIcon;

public class MatchItemFormVerify extends JScrollPane {
  ImageService imageService = new ImageService();
  ItemService itemService = new ItemService();
  private JPanel panel;

  public MatchItemFormVerify(MatchItem item) {
    panel =
        new JPanel(
            new MigLayout("fillx,wrap,insets 5 30 10 30, width 500, hidemode 2", "[fill]", ""));
    setViewportView(panel);
    setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
    setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
    getVerticalScrollBar().setUnitIncrement(16);
    init(item);
  }

  private void init(MatchItem item) {
    createTitle("Upload Photo for Verification");

    idPhotoButton = new JButton("Choose Photo");
    clearIdButton = new JButton("Clear");
    idPhotoLabel = new JLabel(new AvatarIcon("", 350, 350, 0));
    idPhotoErrorLabel = new ErrorLabel("Photo is required.");

    profilePhotoButton = new JButton("Choose Photo");
    clearProfileButton = new JButton("Clear");
    profilePhotoLabel = new JLabel(new AvatarIcon("", 350, 350, 0));
    profilePhotoErrorLabel = new ErrorLabel("Photo is required.");

    idPhotoErrorLabel.setVisible(false);
    profilePhotoErrorLabel.setVisible(false);

    panel.add(new RequiredLabel("Upload Verification ID"), "gapy 5 0");
    panel.add(idPhotoErrorLabel);
    panel.add(idPhotoButton, "split 2");
    panel.add(clearIdButton);
    panel.add(idPhotoLabel, "gapy 0 40");

    panel.add(new RequiredLabel("Upload On-site Picture"), "gapy 5 0");
    panel.add(profilePhotoErrorLabel);
    panel.add(profilePhotoButton, "split 2");
    panel.add(clearProfileButton);
    panel.add(profilePhotoLabel, "gapy 0 40");

    // Actions
    idPhotoButton.addActionListener(
        e -> {
          idPhotoFile = imageService.selectImage(this);
          if (idPhotoFile != null) {
            setFieldValid(idPhotoLabel, idPhotoErrorLabel);
            System.out.println("Selected File: " + idPhotoFile.getAbsolutePath());
            Icon newPhoto = new AvatarIcon(idPhotoFile.getAbsolutePath(), 350, 350, 0);
            idPhotoLabel.setIcon(newPhoto);
            idPhotoLabel.setVisible(true);
          } else {
            System.out.println("No Verification Id Photo File Selected");
          }
        });

    profilePhotoButton.addActionListener(
        e -> {
          profilePhotoFile = imageService.selectImage(this);
          if (profilePhotoFile != null) {
            setFieldValid(profilePhotoLabel, profilePhotoErrorLabel);
            System.out.println("Selected File: " + profilePhotoFile.getAbsolutePath());
            Icon newPhoto = new AvatarIcon(profilePhotoFile.getAbsolutePath(), 350, 350, 0);
            profilePhotoLabel.setIcon(newPhoto);
            profilePhotoLabel.setVisible(true);
          } else {
            System.out.println("No On-site Photo File Selected");
          }
        });

    clearIdButton.addActionListener(
        e -> {
          idPhotoFile = null;
          idPhotoLabel.setIcon(new AvatarIcon("", 350, 350, 0));
          idPhotoLabel.setVisible(false);
        });

    clearProfileButton.addActionListener(
        e -> {
          profilePhotoFile = null;
          profilePhotoLabel.setIcon(new AvatarIcon("", 350, 350, 0));
          profilePhotoLabel.setVisible(false);
        });

    // Changes
    if (item.getIdPhotoPath() == null) {
      idPhotoLabel.setVisible(false);
    } else {
      idPhotoLabel.setIcon(item.getIdPhotoIcon(350, 350, 0));
    }

    if (item.getProfilePath() == null) {
      profilePhotoLabel.setVisible(false);
    } else {
      profilePhotoLabel.setIcon(item.getProfileIcon(350, 350, 0));
    }
  }

  public boolean verifyMatchItem(MatchItem item) {
    if (!validateMatchItemForm()) {
      JOptionPane.showMessageDialog(
          this,
          "Approve Error: The provided item photo are invalid.",
          "Approve Error",
          JOptionPane.ERROR_MESSAGE);
      return false;
    }

    String idPhotoPath = null;
    String profilePhotoPath = null;

    if (idPhotoFile == null) {
      idPhotoPath = item.getIdPhotoPath();
    } else {
      idPhotoPath = imageService.saveImage(this, idPhotoFile, ImageService.IDS_PATH);
    }

    if (profilePhotoFile == null) {
      profilePhotoPath = item.getProfilePath();
    } else {
      profilePhotoPath = imageService.saveImage(this, profilePhotoFile, ImageService.PROFILES_PATH);
    }

    item.setIdPhotoPath(idPhotoPath);
    item.setProfilePath(profilePhotoPath);
    item.setStatus(MatchItem.Status.RESOLVED);

    itemService.updateMatchItem(item);
    return true;
  }

  private boolean validateMatchItemForm() {
    return validatePhoto(idPhotoLabel, idPhotoErrorLabel)
        & validatePhoto(profilePhotoLabel, profilePhotoErrorLabel);
  }

  private boolean validatePhoto(JLabel photoLabel, JLabel photoErrorLabel) {
    boolean isVisible = photoLabel.isVisible();

    if (isVisible) {
      setFieldValid(photoLabel, photoErrorLabel);
    } else {
      setFieldInvalid(photoLabel, photoErrorLabel);
    }

    return isVisible;
  }

  private void setFieldValid(JComponent field, JLabel label) {
    StyleManager.styleResetField(field);
    label.setVisible(false);
  }

  private void setFieldInvalid(JComponent field, JLabel label) {
    StyleManager.styleInvalidField(field);
    label.setVisible(true);
  }

  private void createTitle(String title) {
    JLabel lb = new JLabel(title);
    lb.putClientProperty(FlatClientProperties.STYLE, "font:+2");
    panel.add(lb, "gapy 5 0");
    panel.add(new JSeparator(), "height 2!,gapy 0 0");
  }

  private JButton idPhotoButton, clearIdButton, profilePhotoButton, clearProfileButton;
  private JLabel idPhotoLabel, profilePhotoLabel, idPhotoErrorLabel, profilePhotoErrorLabel;
  private File idPhotoFile, profilePhotoFile;
}
