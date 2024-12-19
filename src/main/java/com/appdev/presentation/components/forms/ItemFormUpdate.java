package com.appdev.presentation.components.forms;

import com.appdev.logic.managers.ItemTypeManager;
import com.appdev.logic.managers.StyleManager;
import com.appdev.logic.models.FoundItem;
import com.appdev.logic.models.LostItem;
import com.appdev.logic.services.ImageService;
import com.appdev.logic.services.ItemService;
import com.appdev.logic.validations.ItemValidator;
import com.appdev.presentation.components.labels.ErrorLabel;
import com.appdev.presentation.components.labels.RequiredLabel;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.miginfocom.swing.MigLayout;
import raven.datetime.component.date.DatePicker;
import raven.datetime.component.time.TimePicker;
import raven.extras.AvatarIcon;

public class ItemFormUpdate extends JScrollPane {
  private JPanel panel;
  private ItemValidator validator = new ItemValidator();

  public ItemFormUpdate(LostItem item) {
    panel =
        new JPanel(
            new MigLayout("fillx,wrap,insets 5 30 10 30, width 500, hidemode 2", "[fill]", ""));
    setViewportView(panel);
    setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
    setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
    init(item);
  }

  public ItemFormUpdate(FoundItem item) {
    panel =
        new JPanel(
            new MigLayout("fillx,wrap,insets 5 30 10 30, width 500, hidemode 2", "[fill]", ""));
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
    scrollDescription = new JScrollPane(itemDescriptionArea);
    scrollLocation = new JScrollPane(itemLocationArea);

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
    clearButton = new JButton("Clear");
    photoLabel = new JLabel(new AvatarIcon("", 350, 350, 0));

    nameField = new JTextField();
    emailField = new JTextField();
    phoneField = new JTextField();

    nameField.putClientProperty(
        FlatClientProperties.PLACEHOLDER_TEXT, "Enter your name (e.g., John D. Smith)");
    emailField.putClientProperty(
        FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("icons/email.svg", 0.6f));
    phoneField.putClientProperty(
        FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("icons/phone.svg", 0.6f));

    // Error labels for various fields
    itemTypeErrorLabel = new ErrorLabel("Item type is required and cannot be 'Choose Type'.");
    itemSubtypeErrorLabel =
        new ErrorLabel("Item subtype must be selected and cannot start with 'Select'.");
    itemDescriptionErrorLabel =
        new ErrorLabel("Description cannot be empty and must be less than 255 characters.");
    itemLocationErrorLabel =
        new ErrorLabel("Location details cannot be empty and must be less than 255 characters.");
    dateTimeErrorLabel =
        new ErrorLabel("Date and time must represent a valid date and time in the present.");
    nameErrorLabel =
        new ErrorLabel(
            "Name must be 2-50 characters, and can only contain letters, spaces, '.', '-', and '''.");
    emailErrorLabel = new ErrorLabel("Email address must follow example@domain.com");
    phoneErrorLabel = new ErrorLabel("Phone number must be 11 digits.");

    itemTypeErrorLabel.setVisible(false);
    itemSubtypeErrorLabel.setVisible(false);
    itemDescriptionErrorLabel.setVisible(false);
    itemLocationErrorLabel.setVisible(false);
    dateTimeErrorLabel.setVisible(false);
    nameErrorLabel.setVisible(false);
    emailErrorLabel.setVisible(false);
    phoneErrorLabel.setVisible(false);

    createTitle("Lost Item Information");
    panel.add(new RequiredLabel("Item Type"), "gapy 5 0");
    panel.add(itemTypeBox);
    panel.add(itemTypeErrorLabel);

    panel.add(new RequiredLabel("Item Subtype"), "gapy 5 0");
    panel.add(itemSubtypeBox);
    panel.add(itemSubtypeErrorLabel);

    panel.add(new RequiredLabel("Item Description"), "gapy 5 0");
    panel.add(scrollDescription, "height 100");
    panel.add(itemDescriptionErrorLabel);

    panel.add(new RequiredLabel("Where do you think you might have lost the item?"), "gapy 5 0");
    panel.add(scrollLocation, "height 100");
    panel.add(itemLocationErrorLabel);

    panel.add(new RequiredLabel("When did you notice that the item was lost?"), "gapy 5 0");
    panel.add(dateField, "split 2");
    panel.add(timeField);
    panel.add(dateTimeErrorLabel);

    panel.add(new JLabel("Item Photo"), "gapy 5 0");
    panel.add(photoButton, "split 2");
    panel.add(clearButton);
    panel.add(photoLabel, "gapy 0 20");

    createTitle("Reporter Information");
    panel.add(new RequiredLabel("Full Name"), "gapy 5 0");
    panel.add(nameField);
    panel.add(nameErrorLabel);

    panel.add(new RequiredLabel("Email Address"), "gapy 5 0");
    panel.add(emailField);
    panel.add(emailErrorLabel);

    panel.add(new JLabel("Phone Number"), "gapy 5 0");
    panel.add(phoneField);
    panel.add(phoneErrorLabel);

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
            photoLabel.setVisible(true);
          } else {
            System.out.println("No file selected.");
          }
        });
    clearButton.addActionListener(
        e -> {
          selectedFile = null;
          photoLabel.setIcon(new AvatarIcon("", 350, 350, 0));
          photoLabel.setVisible(false);
        });

    itemTypeBox.addItemListener(new ItemTypeListener());
    itemSubtypeBox.addItemListener(new ItemTypeListener());
    itemDescriptionArea.getDocument().addDocumentListener(new DebouncedDocumentListener());
    itemLocationArea.getDocument().addDocumentListener(new DebouncedDocumentListener());
    dateField.getDocument().addDocumentListener(new DebouncedDocumentListener());
    timeField.getDocument().addDocumentListener(new DebouncedDocumentListener());
    nameField.getDocument().addDocumentListener(new DebouncedDocumentListener());
    emailField.getDocument().addDocumentListener(new DebouncedDocumentListener());
    phoneField.getDocument().addDocumentListener(new DebouncedDocumentListener());

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
    if (item.getItemPhotoPath() == null) {
      photoLabel.setVisible(false);
    } else {
      photoLabel.setIcon(item.getImageIcon(350, 350, 0));
    }

    nameField.setText(item.getReporterName());
    emailField.setText(item.getReporterEmail());
    phoneField.setText(item.getReporterPhone());
  }

  private void init(FoundItem item) {
    itemTypeBox = new JComboBox<>(ItemTypeManager.ITEM_TYPES);
    itemSubtypeBox = new JComboBox<>(new String[] {""});

    itemDescriptionArea = new JTextArea();
    itemLocationArea = new JTextArea();
    scrollDescription = new JScrollPane(itemDescriptionArea);
    scrollLocation = new JScrollPane(itemLocationArea);

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
    clearButton = new JButton("Clear");
    photoLabel = new JLabel(new AvatarIcon("", 350, 350, 0));

    nameField = new JTextField();
    emailField = new JTextField();
    phoneField = new JTextField();

    nameField.putClientProperty(
        FlatClientProperties.PLACEHOLDER_TEXT, "Enter your name (e.g., John D. Smith)");
    emailField.putClientProperty(
        FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("icons/email.svg", 0.6f));
    phoneField.putClientProperty(
        FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("icons/phone.svg", 0.6f));

    // Error labels for various fields
    itemTypeErrorLabel = new ErrorLabel("Item type is required and cannot be 'Choose Type'.");
    itemSubtypeErrorLabel =
        new ErrorLabel("Item subtype must be selected and cannot start with 'Select'.");
    itemDescriptionErrorLabel =
        new ErrorLabel("Description cannot be empty and must be less than 255 characters.");
    itemLocationErrorLabel =
        new ErrorLabel("Location details cannot be empty and must be less than 255 characters.");
    dateTimeErrorLabel =
        new ErrorLabel("Date and time must represent a valid date and time in the present.");
    photoErrorLabel = new ErrorLabel("Photo is required.");
    nameErrorLabel =
        new ErrorLabel(
            "Name must be 2-50 characters, and can only contain letters, spaces, '.', '-', and '''.");
    emailErrorLabel = new ErrorLabel("Email address must follow example@domain.com");
    phoneErrorLabel = new ErrorLabel("Phone number must be 11 digits.");

    itemTypeErrorLabel.setVisible(false);
    itemSubtypeErrorLabel.setVisible(false);
    itemDescriptionErrorLabel.setVisible(false);
    itemLocationErrorLabel.setVisible(false);
    dateTimeErrorLabel.setVisible(false);
    photoErrorLabel.setVisible(false);
    phoneErrorLabel.setVisible(true);
    nameErrorLabel.setVisible(false);
    emailErrorLabel.setVisible(false);
    phoneErrorLabel.setVisible(false);

    createTitle("Lost Item Information");
    panel.add(new RequiredLabel("Item Type"), "gapy 5 0");
    panel.add(itemTypeBox);
    panel.add(itemTypeErrorLabel);

    panel.add(new RequiredLabel("Item Subtype"), "gapy 5 0");
    panel.add(itemSubtypeBox);
    panel.add(itemSubtypeErrorLabel);

    panel.add(new RequiredLabel("Item Description"), "gapy 5 0");
    panel.add(scrollDescription, "height 100");
    panel.add(itemDescriptionErrorLabel);

    panel.add(new RequiredLabel("Where do did you find the item?"), "gapy 5 0");
    panel.add(scrollLocation, "height 100");
    panel.add(itemLocationErrorLabel);

    panel.add(new RequiredLabel("When did you find the item?"), "gapy 5 0");
    panel.add(dateField, "split 2");
    panel.add(timeField);
    panel.add(dateTimeErrorLabel);

    panel.add(new RequiredLabel("Item Photo"), "gapy 5 0");
    panel.add(photoErrorLabel);
    panel.add(photoButton, "split 2");
    panel.add(clearButton);
    panel.add(photoLabel, "gapy 0 20");

    createTitle("Reporter Information");
    panel.add(new RequiredLabel("Full Name"), "gapy 5 0");
    panel.add(nameField);
    panel.add(nameErrorLabel);

    panel.add(new RequiredLabel("Email Address"), "gapy 5 0");
    panel.add(emailField);
    panel.add(emailErrorLabel);

    panel.add(new JLabel("Phone Number"), "gapy 5 0");
    panel.add(phoneField);
    panel.add(phoneErrorLabel);

    // Actions
    itemTypeBox.addActionListener(new ItemTypeActionListener());
    photoButton.addActionListener(
        e -> {
          ImageService imageService = new ImageService();
          selectedFile = imageService.selectImage(this);

          if (selectedFile != null) {
            setFieldValid(photoLabel, photoErrorLabel);
            System.out.println("Selected File: " + selectedFile.getAbsolutePath());
            Icon newPhoto = new AvatarIcon(selectedFile.getAbsolutePath(), 350, 350, 0);
            photoLabel.setIcon(newPhoto);
            photoLabel.setVisible(true);
          } else {
            System.out.println("No file selected.");
          }
        });
    clearButton.addActionListener(
        e -> {
          selectedFile = null;
          photoLabel.setIcon(new AvatarIcon("", 350, 350, 0));
          photoLabel.setVisible(false);
        });

    itemTypeBox.addItemListener(new ItemTypeListener());
    itemSubtypeBox.addItemListener(new ItemTypeListener());
    itemDescriptionArea.getDocument().addDocumentListener(new DebouncedDocumentListener());
    itemLocationArea.getDocument().addDocumentListener(new DebouncedDocumentListener());
    dateField.getDocument().addDocumentListener(new DebouncedDocumentListener());
    timeField.getDocument().addDocumentListener(new DebouncedDocumentListener());
    nameField.getDocument().addDocumentListener(new DebouncedDocumentListener());
    emailField.getDocument().addDocumentListener(new DebouncedDocumentListener());
    phoneField.getDocument().addDocumentListener(new DebouncedDocumentListener());

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
    if (item.getItemPhotoPath() == null) {
      photoLabel.setVisible(false);
    } else {
      photoLabel.setIcon(item.getImageIcon(350, 350, 0));
    }

    nameField.setText(item.getReporterName());
    emailField.setText(item.getReporterEmail());
    phoneField.setText(item.getReporterPhone());
  }

  public boolean updateLostItem(LostItem currentItem) {
    if (!validateUpdateLostItemForm()) {
      JOptionPane.showMessageDialog(
          this,
          "Invalid Lost Item: The provided item data is invalid.",
          "Error",
          JOptionPane.ERROR_MESSAGE);
      return false;
    }

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
        if (photoLabel.isVisible()) {
          itemPhotoPath = currentItem.getItemPhotoPath();
        } else {
          itemPhotoPath = null;
        }
      } else {
          itemPhotoPath = imageService.saveImage(
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
    if (!validateUpdateFoundItemForm()) {
      JOptionPane.showMessageDialog(
          this,
          "Invalid Found Item: The provided item data is invalid.",
          "Error",
          JOptionPane.ERROR_MESSAGE);
      return false;
    }

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

  private boolean validateItemTypeAndSubtype() {
    String selectedItemType = itemTypeBox.getSelectedItem().toString();
    String selectedItemSubtype = itemSubtypeBox.getSelectedItem().toString();

    boolean isValid = true;

    // Validate Item Type
    if (validator.isValidItemType(selectedItemType)) {
      setFieldValid(itemTypeBox, itemTypeErrorLabel);
    } else {
      setFieldInvalid(itemTypeBox, itemTypeErrorLabel);
      isValid = false;
    }

    // Validate Item Subtype
    if (validator.isValidItemSubtype(selectedItemType, selectedItemSubtype)) {
      setFieldValid(itemSubtypeBox, itemSubtypeErrorLabel);
    } else {
      setFieldInvalid(itemSubtypeBox, itemSubtypeErrorLabel);
      isValid = false;
    }

    return isValid;
  }

  private boolean validateDescriptionArea() {
    String description = itemDescriptionArea.getText();
    if (validator.isValidDescription(description)) {
      setFieldValid(scrollDescription, itemDescriptionErrorLabel);
      return true;
    } else {
      setFieldInvalid(scrollDescription, itemDescriptionErrorLabel);
      return false;
    }
  }

  private boolean validateLocationArea() {
    String location = itemLocationArea.getText();
    if (validator.isValidLocationDetails(location)) {
      setFieldValid(scrollLocation, itemLocationErrorLabel);
      return true;
    } else {
      setFieldInvalid(scrollLocation, itemLocationErrorLabel);
      return false;
    }
  }

  private boolean validateDateTimeField() {
    LocalDate date = datePicker.getSelectedDate();
    LocalTime time = timePicker.getSelectedTime();
    String dateStr = dateField.getText();
    String timeStr = timeField.getText();

    boolean isValid = true;
    if (date == null) {
      setFieldInvalid(dateField, dateTimeErrorLabel);
    }

    if (date != null && validator.isValidDateField(dateStr)) {
      setFieldValid(dateField, dateTimeErrorLabel);
    } else {
      setFieldInvalid(dateField, dateTimeErrorLabel);
      isValid = false;
    }

    if (time != null && validator.isValidTimeField(timeStr)) {
      setFieldValid(timeField, dateTimeErrorLabel);
    } else {
      setFieldInvalid(timeField, dateTimeErrorLabel);
      isValid = false;
    }

    return isValid;
  }

  private boolean validatePhoto() {
    boolean isVisible = photoLabel.isVisible();

    if (isVisible) {
      setFieldValid(photoLabel, photoErrorLabel);
    } else {
      setFieldInvalid(photoLabel, photoErrorLabel);
    }

    return isVisible;
  }

  private boolean validateNameField() {
    String name = nameField.getText();
    if (validator.isValidReporterName(name)) {
      setFieldValid(nameField, nameErrorLabel);
      return true;
    } else {
      setFieldInvalid(nameField, nameErrorLabel);
      return false;
    }
  }

  private boolean validateEmailField() {
    String email = emailField.getText();
    if (validator.isValidEmail(email)) {
      setFieldValid(emailField, emailErrorLabel);
      return true;
    } else {
      setFieldInvalid(emailField, emailErrorLabel);
      return false;
    }
  }

  private boolean validatePhoneField() {
    String phone = phoneField.getText();
    if (validator.isValidPhone(phone)) {
      setFieldValid(phoneField, phoneErrorLabel);
      return true;
    } else {
      setFieldInvalid(phoneField, phoneErrorLabel);
      return false;
    }
  }

  private boolean validateUpdateLostItemForm() {
    return validateItemTypeAndSubtype()
        & validateDescriptionArea()
        & validateLocationArea()
        & validateDateTimeField()
        & validateNameField()
        & validateEmailField()
        & validatePhoneField();
  }

  private boolean validateUpdateFoundItemForm() {
    return validateItemTypeAndSubtype()
        & validateDescriptionArea()
        & validateLocationArea()
        & validateDateTimeField()
        & validatePhoto()
        & validateNameField()
        & validateEmailField()
        & validatePhoneField();
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

  private class ItemTypeListener implements ItemListener {
    @Override
    public void itemStateChanged(ItemEvent e) {
      if (e.getStateChange() == ItemEvent.SELECTED) {
        validateItemTypeAndSubtype();
      }
    }
  }

  private class DebouncedDocumentListener implements DocumentListener {
    @Override
    public void insertUpdate(DocumentEvent e) {
      restartTimer();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
      restartTimer();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
      restartTimer();
    }

    private void restartTimer() {
      if (debounceTimer != null && debounceTimer.isRunning()) {
        debounceTimer.stop();
      }

      debounceTimer =
          new Timer(
              300,
              new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                  validateTextFields();
                }
              });

      debounceTimer.setRepeats(false);
      debounceTimer.start();
    }

    private void validateTextFields() {
      validateDescriptionArea();
      validateLocationArea();
      validateDateTimeField();
      validateNameField();
      validateEmailField();
      validatePhoneField();
    }
  }

  private JComboBox<String> itemTypeBox, itemSubtypeBox;
  private JTextArea itemDescriptionArea, itemLocationArea;
  private JScrollPane scrollDescription, scrollLocation;
  private JFormattedTextField dateField, timeField;
  private DatePicker datePicker;
  private TimePicker timePicker;
  private JLabel photoLabel;
  private JTextField nameField, emailField, phoneField;
  private File selectedFile;
  private JButton photoButton, clearButton;
  private Timer debounceTimer;

  private JLabel itemTypeErrorLabel,
      itemSubtypeErrorLabel,
      itemDescriptionErrorLabel,
      itemLocationErrorLabel,
      dateTimeErrorLabel,
      photoErrorLabel,
      nameErrorLabel,
      emailErrorLabel,
      phoneErrorLabel;
}
