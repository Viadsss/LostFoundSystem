package com.appdev.logic.services;

import com.appdev.logic.models.FoundItem;
import com.appdev.logic.models.LostItem;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.text.similarity.JaccardSimilarity;

public class MatchService {
  private static final double MATCH_THRESHOLD = 0.8;

  /**
   * Matches lost items with found items using Jaccard similarity.
   *
   * @param lostItems List of lost items.
   * @param foundItems List of found items.
   * @return A LinkedHashMap where the key is a LostItem and the value is a list of matching
   *     FoundItems.
   */
  public Map<LostItem, List<FoundItem>> matchLostAndFoundItems(
      List<LostItem> lostItems, List<FoundItem> foundItems) {
    Map<LostItem, List<FoundItem>> matches = new LinkedHashMap<>();
    JaccardSimilarity jaccard = new JaccardSimilarity();

    for (LostItem lostItem : lostItems) {
      String lostDescription = preprocessString(lostItem.getItemDescription());
      List<FoundItem> matchedFoundItems = new ArrayList<>();

      for (FoundItem foundItem : foundItems) {
        String foundDescription = preprocessString(foundItem.getItemDescription());
        double jaccardSimilarity = jaccard.apply(lostDescription, foundDescription);
        if (jaccardSimilarity >= MATCH_THRESHOLD) {
          matchedFoundItems.add(foundItem);
        }
      }

      matches.put(lostItem, matchedFoundItems);
    }

    return matches;
  }

  public void printMatches(Map<LostItem, List<FoundItem>> matches) {
    // Iterate through the matches map and print LostItem IDs with their matching FoundItem IDs
    for (Map.Entry<LostItem, List<FoundItem>> entry : matches.entrySet()) {
      LostItem lostItem = entry.getKey();
      List<FoundItem> foundItems = entry.getValue();

      System.out.print("Lost Item ID " + lostItem.getLostItemId() + " - ");
      if (foundItems.isEmpty()) {
        System.out.println("No matches found");
      } else {
        List<Integer> foundItemIds = new ArrayList<>();
        for (FoundItem foundItem : foundItems) {
          foundItemIds.add(foundItem.getFoundItemId()); // Collecting the IDs of the found items
        }
        System.out.println("Found Item IDs: " + foundItemIds);
      }
    }
  }

  /**
   * Preprocesses a given string by normalizing it for comparison. The string is converted to
   * lowercase, special characters (except digits and alphabets) are removed, leading and trailing
   * spaces are trimmed, and multiple spaces are replaced with a single space.
   *
   * @param str The string to be processed.
   * @return The processed string.
   */
  private String preprocessString(String str) {
    str = str.toLowerCase();
    str = str.replaceAll("[^a-zA-Z0-9\\s]", "");
    str = str.trim();
    str = str.replaceAll("\\s+", " ");
    return str;
  }
}
