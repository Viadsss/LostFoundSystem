package com.appdev.presentation.components.forms;

import com.appdev.logic.managers.ItemTypeManager;
import com.appdev.logic.models.FoundItem;
import com.appdev.logic.models.LostItem;
import com.appdev.logic.services.ImageService;
import com.appdev.logic.services.ItemService;
import com.appdev.presentation.components.labels.RequiredLabel;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;
import raven.datetime.component.date.DatePicker;
import raven.datetime.component.time.TimePicker;
import raven.extras.AvatarIcon;

public class ItemFormUpdate extends JScrollPane {
  private JPanel panel;

  public ItemFormUpdate(LostItem item) {
    panel = new JPanel(new MigLayout("fillx,wrap,insets 5 30 5 30, width 500", "[fill]", ""));
    setViewportView(panel);
    setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
    setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
    init(item);
  }

  public ItemFormUpdate(FoundItem item) {
    panel = new JPanel(new MigLayout("fillx,wrap,insets 5 30 5 30, width 500", "[fill]", ""));
    setViewportView(panel);
    setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
    setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
    init(item);
  }

  private void init(LostItem item) {
    itemTypeBox = new JComboBox<>(ItemTypeManager.ITEM_TYPES);
    itemSubtypeBox = new JComboBox<>(new String[] {""});

    itemDescriptionArea = new JTextArea();
    itemLocationArea = new JTextArea();
    JScrollPane scrollDescription = new JScrollPane(itemDescriptionArea);
    JScrollPane scrollLocation = new JScrollPane(itemLocationArea);

    dateField = new JFormattedTextField();
    datePicker = new DatePicker();
    datePicker.setEditor(dateField);
    datePicker.setUsePanelOption(true);
    datePicker.setDateSelectionAble(localDate -> !localDate.isAfter(LocalDate.now()));
    datePicker.isCloseAfterSelected();

    timeField = new JFormattedTextField();
    timePicker = new TimePicker();
    timePicker.setEditor(timeField);
    timePicker.setOrientation(SwingConstants.HORIZONTAL);

    photoButton = new JButton("Choose Photo");
    photoLabel = new JLabel(new AvatarIcon("", 350, 350, 0));

    nameField = new JTextField();
    emailField = new JTextField();
    phoneField = new JTextField();

    // Initialize the item types and subtypes map
    itemSubtypesMap = new HashMap<>();
    itemSubtypesMap.put(
        "Electronics", new String[] {"Select Electronics", "Smartphone", "Laptop", "Tablet"});
    itemSubtypesMap.put(
        "Accessories", new String[] {"Select Accessories", "Wallet", "Watch", "Glasses"});
    itemSubtypesMap.put("Clothing", new String[] {"Select Clothing", "Jacket", "Shirt", "Scarf"});

    createTitle("Lost Item Information");
    panel.add(new RequiredLabel("Item Type"), "gapy 5 0");
    panel.add(itemTypeBox);

    panel.add(new RequiredLabel("Item Subtype"), "gapy 5 0");
    panel.add(itemSubtypeBox);

    panel.add(new RequiredLabel("Item Description"), "gapy 5 0");
    panel.add(scrollDescription, "height 100");

    panel.add(new RequiredLabel("Where do you think you might have lost the item?"), "gapy 5 0");
    panel.add(scrollLocation, "height 100");

    panel.add(new RequiredLabel("When did you notice that the item was lost?"), "gapy 5 0");
    panel.add(dateField, "split 2");
    panel.add(timeField);

    panel.add(new JLabel("Item Photo"), "gapy 5 0");
    panel.add(photoButton);
    panel.add(photoLabel, "gapy 0 20");

    createTitle("Reporter Information");
    panel.add(new RequiredLabel("Full Name"), "gapy 5 0");
    panel.add(nameField);

    panel.add(new RequiredLabel("Email Address"), "gapy 5 0");
    panel.add(emailField);

    panel.add(new JLabel("Phone Number"), "gapy 5 0");
    panel.add(phoneField);

    // Actions
    itemTypeBox.addActionListener(new ItemTypeActionListener());
    photoButton.addActionListener(
        e -> {
          ImageService imageService = new ImageService();
          selectedFile = imageService.selectImage(this);

          if (selectedFile != null) {
            System.out.println("Selected File: " + selectedFile.getAbsolutePath());
            Icon newPhoto = new AvatarIcon(selectedFile.getAbsolutePath(), 350, 350, 0);
            photoLabel.setIcon(newPhoto);
          } else {
            System.out.println("No file selected.");
          }
        });

    // Changes
    itemTypeBox.setSelectedItem(item.getItemType());
    if (item.getItemType() != null) {
      itemSubtypeBox.setModel(
          new DefaultComboBoxModel<>(ItemTypeManager.getSubtypesForType(item.getItemType())));
      itemSubtypeBox.setSelectedItem(item.getItemSubtype());
    }

    itemDescriptionArea.setText(item.getItemDescription());
    itemLocationArea.setText(item.getLocationDetails());
    datePicker.setSelectedDate(item.getDateTimeLost().toLocalDate());
    timePicker.setSelectedTime(item.getDateTimeLost().toLocalTime());
    photoLabel.setIcon(item.getImageIcon(350, 350, 0));

    nameField.setText(item.getReporterName());
    emailField.setText(item.getReporterEmail());
    phoneField.setText(item.getReporterPhone());
  }

  private void init(FoundItem item) {
    itemTypeBox = new JComboBox<>(ItemTypeManager.ITEM_TYPES);
    itemSubtypeBox = new JComboBox<>(new String[] {""});

    itemDescriptionArea = new JTextArea();
    itemLocationArea = new JTextArea();
    JScrollPane scrollDescription = new JScrollPane(itemDescriptionArea);
    JScrollPane scrollLocation = new JScrollPane(itemLocationArea);

    dateField = new JFormattedTextField();
    datePicker = new DatePicker();
    datePicker.setEditor(dateField);
    datePicker.setUsePanelOption(true);
    datePicker.setDateSelectionAble(localDate -> !localDate.isAfter(LocalDate.now()));
    datePicker.isCloseAfterSelected();

    timeField = new JFormattedTextField();
    timePicker = new TimePicker();
    timePicker.setEditor(timeField);
    timePicker.setOrientation(SwingConstants.HORIZONTAL);

    photoButton = new JButton("Choose Photo");
    photoLabel = new JLabel(new AvatarIcon("", 350, 350, 0));

    nameField = new JTextField();
    emailField = new JTextField();
    phoneField = new JTextField();

    // Initialize the item types and subtypes map
    itemSubtypesMap = new HashMap<>();
    itemSubtypesMap.put(
        "Electronics", new String[] {"Select Electronics", "Smartphone", "Laptop", "Tablet"});
    itemSubtypesMap.put(
        "Accessories", new String[] {"Select Accessories", "Wallet", "Watch", "Glasses"});
    itemSubtypesMap.put("Clothing", new String[] {"Select Clothing", "Jacket", "Shirt", "Scarf"});

    createTitle("Lost Item Information");
    panel.add(new RequiredLabel("Item Type"), "gapy 5 0");
    panel.add(itemTypeBox);

    panel.add(new RequiredLabel("Item Subtype"), "gapy 5 0");
    panel.add(itemSubtypeBox);

    panel.add(new RequiredLabel("Item Description"), "gapy 5 0");
    panel.add(scrollDescription, "height 100");

    panel.add(new RequiredLabel("Where do did you find the item?"), "gapy 5 0");
    panel.add(scrollLocation, "height 100");

    panel.add(new RequiredLabel("When did you find the item?"), "gapy 5 0");
    panel.add(dateField, "split 2");
    panel.add(timeField);

    panel.add(new JLabel("Item Photo"), "gapy 5 0");
    panel.add(photoButton);
    panel.add(photoLabel, "gapy 0 20");

    createTitle("Reporter Information");
    panel.add(new RequiredLabel("Full Name"), "gapy 5 0");
    panel.add(nameField);

    panel.add(new RequiredLabel("Email Address"), "gapy 5 0");
    panel.add(emailField);

    panel.add(new JLabel("Phone Number"), "gapy 5 0");
    panel.add(phoneField);

    // Actions
    itemTypeBox.addActionListener(new ItemTypeActionListener());
    photoButton.addActionListener(
        e -> {
          ImageService imageService = new ImageService();
          selectedFile = imageService.selectImage(this);

          if (selectedFile != null) {
            System.out.println("Selected File: " + selectedFile.getAbsolutePath());
            Icon newPhoto = new AvatarIcon(selectedFile.getAbsolutePath(), 350, 350, 0);
            photoLabel.setIcon(newPhoto);
          } else {
            System.out.println("No file selected.");
          }
        });

    // Changes
    itemTypeBox.setSelectedItem(item.getItemType());
    if (item.getItemType() != null) {
      itemSubtypeBox.setModel(
          new DefaultComboBoxModel<>(ItemTypeManager.getSubtypesForType(item.getItemType())));
      itemSubtypeBox.setSelectedItem(item.getItemSubtype());
    }

    itemDescriptionArea.setText(item.getItemDescription());
    itemLocationArea.setText(item.getLocationDetails());
    datePicker.setSelectedDate(item.getDateTimeFound().toLocalDate());
    timePicker.setSelectedTime(item.getDateTimeFound().toLocalTime());
    photoLabel.setIcon(item.getImageIcon(350, 350, 0));

    nameField.setText(item.getReporterName());
    emailField.setText(item.getReporterEmail());
    phoneField.setText(item.getReporterPhone());
  }

  private void createTitle(String title) {
    JLabel lb = new JLabel(title);
    lb.putClientProperty(FlatClientProperties.STYLE, "font:+2");
    panel.add(lb, "gapy 5 0");
    panel.add(new JSeparator(), "height 2!,gapy 0 0");
  }

  private class ItemTypeActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      // Get the selected item type
      String selectedType = (String) itemTypeBox.getSelectedItem();

      // Update the item subtypes based on the selected type
      itemSubtypeBox.setModel(
          new DefaultComboBoxModel<>(ItemTypeManager.getSubtypesForType(selectedType)));
    }
  }

  public boolean updateLostItem(LostItem currentItem) {
    ImageService imageService = new ImageService();
    ItemService itemService = new ItemService();

    try {
      String itemType = itemTypeBox.getSelectedItem().toString();
      String itemSubtype = itemSubtypeBox.getSelectedItem().toString();
      String itemDescription = itemDescriptionArea.getText().trim();
      String locationDetails = itemLocationArea.getText().trim();

      LocalDate date = datePicker.getSelectedDate();
      LocalTime time = timePicker.getSelectedTime();
      LocalDateTime dateTimeLost = date.atTime(time);
      String itemPhotoPath = null;

      String reporterName = nameField.getText().trim();
      String reporterEmail = emailField.getText().trim();
      String reporterPhone = phoneField.getText().trim();

      if (selectedFile == null) {
        itemPhotoPath = currentItem.getItemPhotoPath();
      } else {
        itemPhotoPath =
            imageService.saveImage(
                this, selectedFile, ImageService.LOST_ITEMS_PATH, currentItem.getLostItemId());
      }

      currentItem.setItemType(itemType);
      currentItem.setItemSubtype(itemSubtype);
      currentItem.setItemDescription(itemDescription);
      currentItem.setLocationDetails(locationDetails);
      currentItem.setDateTimeLost(dateTimeLost);
      currentItem.setItemPhotoPath(itemPhotoPath);
      currentItem.setReporterName(reporterName);
      currentItem.setReporterEmail(reporterEmail);

      if (reporterPhone.equals("")) {
        currentItem.setReporterPhone(null);
      } else {
        currentItem.setReporterPhone(reporterPhone);
      }

      itemService.updateLostItem(currentItem);
    } catch (IllegalArgumentException ex) {
      JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      return false;
    }

    return true;
  }

  public boolean updateFoundItem(FoundItem currentItem) {
    ImageService imageService = new ImageService();
    ItemService itemService = new ItemService();

    try {
      String itemType = itemTypeBox.getSelectedItem().toString();
      String itemSubtype = itemSubtypeBox.getSelectedItem().toString();
      String itemDescription = itemDescriptionArea.getText().trim();
      String locationDetails = itemLocationArea.getText().trim();

      LocalDate date = datePicker.getSelectedDate();
      LocalTime time = timePicker.getSelectedTime();
      LocalDateTime dateTimeFound = date.atTime(time);
      String itemPhotoPath = null;

      String reporterName = nameField.getText().trim();
      String reporterEmail = emailField.getText().trim();
      String reporterPhone = phoneField.getText().trim();

      if (selectedFile == null) {
        itemPhotoPath = currentItem.getItemPhotoPath();
      } else {
        itemPhotoPath =
            imageService.saveImage(
                this, selectedFile, ImageService.FOUND_ITEMS_PATH, currentItem.getFoundItemId());
      }

      currentItem.setItemType(itemType);
      currentItem.setItemSubtype(itemSubtype);
      currentItem.setItemDescription(itemDescription);
      currentItem.setLocationDetails(locationDetails);
      currentItem.setDateTimeFound(dateTimeFound);
      currentItem.setItemPhotoPath(itemPhotoPath);
      currentItem.setReporterName(reporterName);
      currentItem.setReporterEmail(reporterEmail);

      if (reporterPhone.equals("")) {
        currentItem.setReporterPhone(null);
      } else {
        currentItem.setReporterPhone(reporterPhone);
      }

      itemService.updateFoundItem(currentItem);
    } catch (IllegalArgumentException ex) {
      JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      return false;
    }

    return true;
  }

  private Map<String, String[]> itemSubtypesMap;
  private JComboBox<String> itemTypeBox, itemSubtypeBox;
  private JTextArea itemDescriptionArea, itemLocationArea;
  private JFormattedTextField dateField, timeField;
  private DatePicker datePicker;
  private TimePicker timePicker;
  private JLabel photoLabel;
  private JTextField nameField, emailField, phoneField;
  private File selectedFile;
  private JButton photoButton;
}
