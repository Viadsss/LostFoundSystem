package com.appdev.logic.validations;

import com.appdev.logic.models.Item;
import com.appdev.logic.models.LostItem;
import com.appdev.logic.managers.ItemTypeManager;
import com.appdev.logic.models.FoundItem;

import java.time.LocalDateTime;

/**
 * Validates fields of Item, LostItem, and FoundItem models.
 */
public class ItemValidator {

    private static final int MAX_DESCRIPTION_LENGTH = 500;
    private static final int MAX_LOCATION_LENGTH = 200;
    private static final int MAX_NAME_LENGTH = 50;

    /**
     * Validates an item.
     *
     * @param item the Item object to validate.
     * @return true if valid, false otherwise.
     */
    public boolean isValidItem(Item item) {
    System.out.println("Valid item type? " + isValidItemType(item.getItemType()));
    System.out.println("Valid item subtype? " + isValidItemSubtype(item.getItemType(), item.getItemSubtype()));
    System.out.println("Valid description? " + isValidDescription(item.getItemDescription()));
    System.out.println("Valid location details? " + isValidLocationDetails(item.getLocationDetails()));
    System.out.println("Valid reporter name? " + isValidReporterName(item.getReporterName()));
    System.out.println("Valid email? " + isValidEmail(item.getReporterEmail()));
    System.out.println("Valid phone number? " + isValidPhone(item.getReporterPhone()));
    System.out.println("CreatedAt not null? " + item.getCreatedAt() != null);

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
    System.out.println("Valid item? " + isValidItem(lostItem));
    System.out.println("Valid LostItem ID? " + isValidItemId(lostItem.getLostItemId()));
    System.out.println("DateTimeLost not null? " + lostItem.getDateTimeLost() != null);
    System.out.println("DateTimeLost is before now? " + 
                lostItem.getDateTimeLost() != null && lostItem.getDateTimeLost().isBefore(LocalDateTime.now()));

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
    System.out.println("Valid item? " + isValidItem(foundItem));
    System.out.println("Valid FoundItem ID? " + isValidItemId(foundItem.getFoundItemId()));
    System.out.println("DateTimeFound not null " + foundItem.getDateTimeFound() != null);
    System.out.println("DateTimeFound is before now? " + 
                foundItem.getDateTimeFound() != null && foundItem.getDateTimeFound().isBefore(LocalDateTime.now()));

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
    private boolean isValidItemType(String itemType) {
        // Ensure the itemType is not the placeholder "Choose Type"
        if ("Choose Type".equals(itemType)) {
            return false;  // Invalid item type
        }

        // Check if the itemType is one of the predefined item types in ItemTypeManager
        for (String type : ItemTypeManager.ITEM_TYPES) {
            if (type.equals(itemType)) {
                return true;  // Valid item type
            }
        }
        return false;  // Invalid item type
    }

    /**
     * Validates the item subtype based on item type.
     *
     * @param itemType the type of the item.
     * @param itemSubtype the subtype of the item.
     * @return true if valid, false otherwise.
     */
    private boolean isValidItemSubtype(String itemType, String itemSubtype) {
        if (!isValidItemType(itemType)) {
          return false; 
        }
        // Ensure the itemSubtype is not the placeholder values like "Select Electronics", "Select Accessories", etc.
        if (itemSubtype != null && itemSubtype.startsWith("Select")) {
            return false;  // Invalid item subtype (placeholder value)
        }

        // Get valid subtypes for the given item type from ItemTypeManager
        String[] validSubtypes = ItemTypeManager.getSubtypesForType(itemType);

        // Check if the itemSubtype is valid (must be in the valid subtypes list)
        for (String subtype : validSubtypes) {
            if (subtype.equals(itemSubtype)) {
                return true;  // Valid item subtype
            }
        }
        return false;  // Invalid item subtype
    }   

    /**
     * Validates the item description.
     *
     * @param description the description of the item.
     * @return true if valid, false otherwise.
     */
    private boolean isValidDescription(String description) {
        return description != null && !description.isBlank() && description.length() <= MAX_DESCRIPTION_LENGTH;
    }

    /**
     * Validates the location details.
     *
     * @param locationDetails the location details.
     * @return true if valid, false otherwise.
     */
    private boolean isValidLocationDetails(String locationDetails) {
        return locationDetails != null && !locationDetails.isBlank() && locationDetails.length() <= MAX_LOCATION_LENGTH;
    }

    /**
     * Validates the reporter's name.
     *
     * @param name the reporter's name.
     * @return true if valid, false otherwise.
     */
  private boolean isValidReporterName(String name) {
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
    private boolean isValidEmail(String email) {
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
  private boolean isValidPhone(String phone) {
      // Allow for null phone number
      if (phone == null) {
          return true;  // If phone is null, it's valid
      }

      String phoneRegex = "^[0-9]{11}$"; // Ensures exactly 11 digits, and only digits
      return phone.matches(phoneRegex);
  }
}