package com.appdev.presentation.components.forms;

import com.appdev.logic.models.FoundItem;
import com.appdev.logic.models.LostItem;
import com.appdev.logic.models.MatchItem;
import com.appdev.logic.services.ItemService;
import javax.swing.JSplitPane;

public class MatchItemFormView extends JSplitPane {
  public MatchItemFormView(LostItem lostItem, FoundItem foundItem) {
    super(JSplitPane.HORIZONTAL_SPLIT);

    ItemFormView lostItemView = new ItemFormView(lostItem);
    ItemFormView foundItemView = new ItemFormView(foundItem);

    setLeftComponent(lostItemView);
    setRightComponent(foundItemView);
    setOneTouchExpandable(true);
    setContinuousLayout(true);
    setResizeWeight(0.5);
    setDividerSize(10);
  }

  public MatchItemFormView(MatchItem item) {
    super(JSplitPane.VERTICAL_SPLIT);

    ItemService itemService = new ItemService();
    LostItem lostItem = itemService.getLostItem(item.getLostItemId());
    FoundItem foundItem = itemService.getFoundItem(item.getFoundItemId());

    ItemFormView lostItemView = new ItemFormView(lostItem);
    ItemFormView foundItemView = new ItemFormView(foundItem);
    MatchItemDetailsView matchItemView = new MatchItemDetailsView(item);

    JSplitPane splitPane2 =
        new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, lostItemView, foundItemView);
    splitPane2.setOneTouchExpandable(true);
    splitPane2.setOneTouchExpandable(true);
    splitPane2.setResizeWeight(0.5);
    splitPane2.setDividerSize(10);

    setLeftComponent(splitPane2);
    setRightComponent(matchItemView);
    setOneTouchExpandable(true);
    setContinuousLayout(true);
    setResizeWeight(0.8);
    setDividerSize(10);
  }
}
