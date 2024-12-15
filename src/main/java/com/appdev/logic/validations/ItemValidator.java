package com.appdev.logic.validations;

public class ItemValidator {

  // Validate ID (e.g., must be a positive integer)
  public boolean isId(int id) {
    return id > 0;
  }

  // Validate Item Type (e.g., not null or empty)
  public static boolean isItemType(String itemType) {
    return itemType != null && !itemType.trim().isEmpty();
  }

  // Validate Item Subtype (e.g., not null or empty)
  public static boolean isItemSubtype(String itemSubtype) {
    return itemSubtype != null && !itemSubtype.trim().isEmpty();
  }

  // Validate Item Description (e.g., within a reasonable length)
  public static boolean isItemDescription(String description) {
    return description != null && description.length() >= 10 && description.length() <= 500;
  }

  // Validate Location Details (e.g., not null or empty)
  public static boolean isLocationDetails(String location) {
    return location != null && !location.trim().isEmpty();
  }

  // Validate Date (e.g., not null and follows a specific format)
  // public boolean isDate(String date) {
  //     return date != null && Pattern.matches("\\d{4}-\\d{2}-\\d{2}", date); // Matches YYYY-MM-DD
  // format
  // }

  // Validate File Path (e.g., not null and ends with an image extension)
  public static boolean isPath(String path) {
    return path != null && path.matches(".*\\.(jpg|jpeg|png|webp|gif)$");
  }
}
