package com.appdev.logic.managers;

import java.util.HashMap;
import java.util.Map;

public class ItemTypeManager {

  private static final Map<String, String[]> itemSubtypesMap = new HashMap<>();

  // Static block to initialize the item subtypes for each item type
  static {
    itemSubtypesMap.put(
        "Electronics", new String[] {
            "Select Electronics", "Smartphone", "Laptop", "Tablet", 
            "Charger", "Earphones", "Calculator", "USB Drive", "Handfan", "Others"
        });

    itemSubtypesMap.put(
        "Accessories", new String[] {
            "Select Accessories", "Wallet", "Watch", "Glasses", 
            "Jewelry", "Keychain", "Others"
        });

    itemSubtypesMap.put(
        "Clothing", new String[] {
            "Select Clothing", "Jacket", "Shirt", "Scarf", "Shoes", 
            "Hat", "Gloves", "Others"
        });

    itemSubtypesMap.put(
        "Documents", new String[] {
            "Select Document", "School ID", "Passport", "Birth Certificate", 
            "Driver's License", "Government ID", "Others"
        });

    itemSubtypesMap.put(
        "Personal Items", new String[] {
            "Select Personal Item", "Water Bottle", "Umbrella", "Perfume", 
            "Makeup Kit", "Hand Sanitizer", "Hairbrush", "Others"
        });

    itemSubtypesMap.put(
        "School Supplies", new String[] {
            "Select School Supply", "Notebook", "Textbook", "Pen", 
            "Pencil Case", "Ruler", "Folder", "ID Card", "Others"
        });

    itemSubtypesMap.put("Others", new String[] {"Others"});
  }

  // An array of item types for the combo box
  public static final String[] ITEM_TYPES = {
    "Choose Type", "Electronics", "Accessories", "Clothing", 
    "Documents", "Personal Items", "School Supplies", "Others"
  };

  // Method to get the subtypes for a given item type
  public static String[] getSubtypesForType(String itemType) {
    return itemSubtypesMap.getOrDefault(itemType, new String[] {""});
  }
}
