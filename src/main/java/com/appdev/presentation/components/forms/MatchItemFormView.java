package com.appdev.presentation.components.forms;

import javax.swing.JSplitPane;

import com.appdev.logic.models.FoundItem;
import com.appdev.logic.models.LostItem;

public class MatchItemFormView extends JSplitPane {
    public MatchItemFormView(LostItem lostItem, FoundItem foundItem) {
        super(JSplitPane.HORIZONTAL_SPLIT);

        ItemFormView lostItemView = new ItemFormView(lostItem);
        ItemFormView foundItemView = new ItemFormView(foundItem);

        setLeftComponent(lostItemView);
        setRightComponent(foundItemView);
        setDividerLocation(450);
        setOneTouchExpandable(true);
    }
}
