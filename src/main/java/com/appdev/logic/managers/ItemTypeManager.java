package com.appdev.logic.managers;

import java.util.HashMap;
import java.util.Map;

public class ItemTypeManager {

  private static final Map<String, String[]> itemSubtypesMap = new HashMap<>();

  // Static block to initialize the item subtypes for each item type
  static {
    itemSubtypesMap.put(
        "Electronics", new String[] {"Select Electronics", "Smartphone", "Laptop", "Tablet"});
    itemSubtypesMap.put(
        "Accessories", new String[] {"Select Accessories", "Wallet", "Watch", "Glasses"});
    itemSubtypesMap.put("Clothing", new String[] {"Select Clothing", "Jacket", "Shirt", "Scarf"});
  }

  // An array of item types for the combo box
  public static final String[] ITEM_TYPES = {
    "Choose Type", "Electronics", "Accessories", "Clothing"
  };

  // Method to get the subtypes for a given item type
  public static String[] getSubtypesForType(String itemType) {
    return itemSubtypesMap.getOrDefault(itemType, new String[] {""});
  }
}
