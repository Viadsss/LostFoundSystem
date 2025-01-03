package com.appdev.logic.validations;

import com.appdev.logic.managers.ItemTypeManager;
import com.appdev.logic.models.FoundItem;
import com.appdev.logic.models.Item;
import com.appdev.logic.models.LostItem;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/** Validates fields of Item, LostItem, and FoundItem models. */
public class ItemValidator {

  private static final int MAX_DESCRIPTION_LENGTH = 255;
  private static final int MAX_LOCATION_LENGTH = 255;
  private static final int MAX_NAME_LENGTH = 50;

  /**
   * Validates an item.
   *
   * @param item the Item object to validate.
   * @return true if valid, false otherwise.
   */
  public boolean isValidItem(Item item) {
    return isValidItemType(item.getItemType())
        && isValidItemSubtype(item.getItemType(), item.getItemSubtype())
        && isValidDescription(item.getItemDescription())
        && isValidLocationDetails(item.getLocationDetails())
        && isValidReporterName(item.getReporterName())
        && isValidEmail(item.getReporterEmail())
        && isValidPhone(item.getReporterPhone())
        && item.getCreatedAt() != null;
  }

  /**
   * Validates a LostItem.
   *
   * @param lostItem the LostItem object to validate.
   * @return true if valid, false otherwise.
   */
  public boolean isValidLostItem(LostItem lostItem) {
    return isValidItem(lostItem)
        && isValidItemId(lostItem.getLostItemId())
        && lostItem.getDateTimeLost() != null
        && lostItem.getDateTimeLost().isBefore(LocalDateTime.now());
  }

  /**
   * Validates a FoundItem.
   *
   * @param foundItem the FoundItem object to validate.
   * @return true if valid, false otherwise.
   */
  public boolean isValidFoundItem(FoundItem foundItem) {
    return isValidItem(foundItem)
        && isValidItemId(foundItem.getFoundItemId())
        && foundItem.getDateTimeFound() != null
        && foundItem.getDateTimeFound().isBefore(LocalDateTime.now());
  }

  /**
   * Validates the id of the item.
   *
   * @param id the id of the item.
   * @return true if valid, false otherwise.
   */
  private boolean isValidItemId(int id) {
    return id > 0;
  }

  /**
   * Validates the item type.
   *
   * @param itemType the type of the item.
   * @return true if valid, false otherwise.
   */
  public boolean isValidItemType(String itemType) {
    // Ensure the itemType is not the placeholder "Choose Type"
    if ("Choose Type".equals(itemType)) {
      return false; // Invalid item type
    }

    // Check if the itemType is one of the predefined item types in ItemTypeManager
    for (String type : ItemTypeManager.ITEM_TYPES) {
      if (type.equals(itemType)) {
        return true; // Valid item type
      }
    }
    return false; // Invalid item type
  }

  /**
   * Validates the item subtype based on item type.
   *
   * @param itemType the type of the item.
   * @param itemSubtype the subtype of the item.
   * @return true if valid, false otherwise.
   */
  public boolean isValidItemSubtype(String itemType, String itemSubtype) {
    if (!isValidItemType(itemType)) {
      return false;
    }
    // Ensure the itemSubtype is not the placeholder values like "Select Electronics", "Select
    // Accessories", etc.
    if (itemSubtype != null && itemSubtype.startsWith("Select")) {
      return false; // Invalid item subtype (placeholder value)
    }

    // Get valid subtypes for the given item type from ItemTypeManager
    String[] validSubtypes = ItemTypeManager.getSubtypesForType(itemType);

    // Check if the itemSubtype is valid (must be in the valid subtypes list)
    for (String subtype : validSubtypes) {
      if (subtype.equals(itemSubtype)) {
        return true; // Valid item subtype
      }
    }
    return false; // Invalid item subtype
  }

  /**
   * Validates the item description.
   *
   * @param description the description of the item.
   * @return true if valid, false otherwise.
   */
  public boolean isValidDescription(String description) {
    return description != null
        && !description.isBlank()
        && description.length() <= MAX_DESCRIPTION_LENGTH;
  }

  /**
   * Validates the location details.
   *
   * @param locationDetails the location details.
   * @return true if valid, false otherwise.
   */
  public boolean isValidLocationDetails(String locationDetails) {
    return locationDetails != null
        && !locationDetails.isBlank()
        && locationDetails.length() <= MAX_LOCATION_LENGTH;
  }

  /**
   * Validates the reporter's name.
   *
   * @param name the reporter's name.
   * @return true if valid, false otherwise.
   */
  public boolean isValidReporterName(String name) {
    if (name == null || name.isBlank() || name.length() < 2 || name.length() > MAX_NAME_LENGTH) {
      return false;
    }
    String regex = "^[A-Za-z\\s'.-]+$"; // Allows letters, spaces, apostrophes, hyphens, and dots
    return name.matches(regex);
  }

  /**
   * Validates the reporter's email.
   *
   * @param email the reporter's email.
   * @return true if valid, false otherwise.
   */
  public boolean isValidEmail(String email) {
    if (email == null || email.isBlank()) {
      return false;
    }
    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    return email.matches(emailRegex);
  }

  /**
   * Validates the reporter's phone number.
   *
   * @param phone the reporter's phone number.
   * @return true if valid, false otherwise.
   */
  public boolean isValidPhone(String phone) {
    // Allow for null phone number
    if (phone == null || phone.isEmpty()) {
      return true; // If phone is null, it's valid
    }

    String phoneRegex = "^[0-9]{11}$"; // Ensures exactly 11 digits, and only digits
    return phone.matches(phoneRegex);
  }

  public boolean isValidDateField(String date) {
    if (date == null || date.isEmpty()) return false;

    try {
      DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      LocalDate.parse(date, format);
      return true;
    } catch (DateTimeParseException e) {
      return false;
    }
  }

  public boolean isValidTimeField(String time) {
    if (time == null || time.isEmpty()) return false;

    try {
      DateTimeFormatter format = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);
      LocalTime.parse(time, format);
      return true;
    } catch (DateTimeParseException e) {
      return false;
    }
  }
}
