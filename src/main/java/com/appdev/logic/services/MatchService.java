package com.appdev.logic.services;

import com.appdev.logic.models.FoundItem;
import com.appdev.logic.models.LostItem;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.text.similarity.JaccardSimilarity;

public class MatchService {
  private static final double MATCH_THRESHOLD = 0.85;
  private MatchingMode currentMode = MatchingMode.SAME_TYPE; // Default mode

  public enum MatchingMode {
    SAME_TYPE,
    SAME_TYPE_AND_SUBTYPE,
    SAME_DAY
  }

  /**
   * Matches lost items with found items using Jaccard similarity.
   *
   * @param lostItems List of lost items.
   * @param foundItems List of found items.
   * @return A LinkedHashMap where the key is a LostItem and the value is a list of matching
   *     MatchResults.
   */
  public Map<LostItem, List<MatchResult>> matchLostAndFoundItems(
      List<LostItem> lostItems, List<FoundItem> foundItems) {
    Map<LostItem, List<MatchResult>> matches = new LinkedHashMap<>();
    JaccardSimilarity jaccard = new JaccardSimilarity();

    for (LostItem lostItem : lostItems) {
      String lostDescrption = preprocessString(lostItem.getItemDescription());
      List<MatchResult> matchedFoundItems = new ArrayList<>();

      for (FoundItem foundItem : foundItems) {

        if (lostItem.getStatus() != LostItem.Status.PENDING) {
          continue;
        }

        if (foundItem.getStatus() != FoundItem.Status.PENDING) {
          continue;
        }

        if (!matchesFilter(lostItem, foundItem)) {
          continue;
        }

        String foundDescription = preprocessString(foundItem.getItemDescription());
        double jaccardSimilarity = jaccard.apply(lostDescrption, foundDescription);

        // Print the IDs for debugging
        // System.out.println("Lost Item ID: " + lostItem.getLostItemId() + ", Found Item ID: " + foundItem.getFoundItemId() + ", Similarity: " + jaccardSimilarity);

        if (jaccardSimilarity >= MATCH_THRESHOLD) {
          matchedFoundItems.add(new MatchResult(foundItem, jaccardSimilarity));
        }
      }

      matches.put(lostItem, matchedFoundItems);
    }

    return matches;
  }

  public void printMatches(Map<LostItem, List<MatchResult>> matches) {
    for (Map.Entry<LostItem, List<MatchResult>> entry : matches.entrySet()) {
      LostItem lostItem = entry.getKey();
      List<MatchResult> foundItems = entry.getValue();

      System.out.print("Lost Item ID " + lostItem.getLostItemId() + " - ");
      if (foundItems.isEmpty()) {
        System.out.println("No matches found");
      } else {
        System.out.println("Matches:");
        for (MatchResult match : foundItems) {
          System.out.println("\t" + match);
        }
      }
    }
  }

  private boolean matchesFilter(LostItem lostItem, FoundItem foundItem) {
    switch (currentMode) {
      case SAME_TYPE:
        return lostItem.getItemType().equals(foundItem.getItemType());

      case SAME_TYPE_AND_SUBTYPE:
        return lostItem.getItemType().equalsIgnoreCase(foundItem.getItemType())
            && lostItem.getItemSubtype().equalsIgnoreCase(foundItem.getItemSubtype());

      case SAME_DAY:
        return lostItem
            .getDateTimeLost()
            .toLocalDate()
            .equals(foundItem.getDateTimeFound().toLocalDate());

      default:
        return true; // No filter if mode is undefined
    }
  }

  public void setMatchingMode(MatchingMode mode) {
    this.currentMode = mode;
  }

  public MatchingMode getMatchingMode() {
    return currentMode;
  }

  /** Inner class to hold a found item and its similarity score. */
  public class MatchResult {
    private FoundItem foundItem;
    private double similarity;

    public MatchResult(FoundItem foundItem, double similarity) {
      this.foundItem = foundItem;
      this.similarity = similarity;
    }

    public FoundItem getFoundItem() {
      return foundItem;
    }

    public double getSimilarity() {
      return similarity;
    }

    @Override
    public String toString() {
      return "FoundItem ID: " + foundItem.getFoundItemId() + ", Similarity: " + similarity;
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
