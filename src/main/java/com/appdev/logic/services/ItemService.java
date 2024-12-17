package com.appdev.logic.services;

import com.appdev.data.dao.FoundItemDAO;
import com.appdev.data.dao.LostItemDAO;
import com.appdev.logic.models.FoundItem;
import com.appdev.logic.models.LostItem;
import com.appdev.logic.validations.ItemValidator;

public class ItemService {
    private final LostItemDAO lostItemDAO;
    private final FoundItemDAO foundItemDAO;
    private final ItemValidator validator;

    public ItemService() {
        this.lostItemDAO = new LostItemDAO();
        this.foundItemDAO = new FoundItemDAO();
        this.validator = new ItemValidator();
    }

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
}
