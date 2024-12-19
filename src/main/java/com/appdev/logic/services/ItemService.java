package com.appdev.logic.services;

import com.appdev.data.dao.FoundItemDAO;
import com.appdev.data.dao.LostItemDAO;
import com.appdev.data.dao.MatchItemDAO;
import com.appdev.logic.models.FoundItem;
import com.appdev.logic.models.LostItem;
import com.appdev.logic.models.MatchItem;
import com.appdev.logic.validations.ItemValidator;
import java.util.List;

public class ItemService {
  private final LostItemDAO lostItemDAO;
  private final FoundItemDAO foundItemDAO;
  private final MatchItemDAO matchItemDAO;
  private final ItemValidator validator;

  public ItemService() {
    this.lostItemDAO = new LostItemDAO();
    this.foundItemDAO = new FoundItemDAO();
    this.matchItemDAO = new MatchItemDAO();
    this.validator = new ItemValidator();
  }

  // CREATE
  public void addMatchItem(LostItem lostItem, FoundItem foundItem) {
    int lostId = lostItem.getLostItemId();
    int foundId = foundItem.getFoundItemId();

    matchItemDAO.addMatchItem(lostId, foundId);

    lostItemDAO.updateLostItemStatus(lostId, LostItem.Status.MATCHED);
    foundItemDAO.updateFoundItemStatus(foundId, FoundItem.Status.MATCHED);
  }

  // READ
  public List<LostItem> getAllLostItems() {
    return lostItemDAO.getAllLostItems();
  }

  public List<FoundItem> getAllFoundItems() {
    return foundItemDAO.getAllFoundItems();
  }

  public List<MatchItem> getAllMatchItems() {
    return matchItemDAO.getAllMatchItems();
  }

  public LostItem getLostItem(int id) {
    return lostItemDAO.getLostItemById(id);
  }

  public FoundItem getFoundItem(int id) {
    return foundItemDAO.getFoundItemById(id);
  }

  public MatchItem getMatchItem(int id) {
    return matchItemDAO.getMatchItemById(id);
  }

  // UPDATE
  public void updateLostItem(LostItem item) throws IllegalArgumentException {
    if (!validator.isValidLostItem(item)) {
      throw new IllegalArgumentException("Invalid Lost Item: The provided item data is invalid.");
    }

    lostItemDAO.updateLostItem(item);
  }

  public void updateFoundItem(FoundItem item) throws IllegalArgumentException {
    if (!validator.isValidFoundItem(item)) {
      throw new IllegalArgumentException("Invalid Found Item: The provided item data is invalid.");
    }

    foundItemDAO.updateFoundItem(item);
  }

  public void updateFoundItemStatus(int id, FoundItem.Status status) {
    foundItemDAO.updateFoundItemStatus(id, status);
  }

  public void updateMatchItem(MatchItem item) {
    matchItemDAO.updateMatchItem(item);
    lostItemDAO.updateLostItemStatus(item.getLostItemId(), LostItem.Status.RETURNED);
    foundItemDAO.updateFoundItemStatus(item.getFoundItemId(), FoundItem.Status.RETURNED);
  }

  public void deleteMatchItem(MatchItem item) {
    lostItemDAO.updateLostItemStatus(item.getLostItemId(), LostItem.Status.PENDING);
    foundItemDAO.updateFoundItemStatus(item.getFoundItemId(), FoundItem.Status.PENDING);
    matchItemDAO.deleteMatchItem(item.getMatchId());
  }
}
