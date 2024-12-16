package com.appdev.logic.models;

import java.time.LocalDateTime;
import javax.swing.Icon;
import raven.extras.AvatarIcon;

public class LostItem extends Item {
  private int lostItemId;
  private LocalDateTime dateTimeLost;
  private Status status;

  public enum Status {
    PENDING,
    MATCHED,
    RETURNED
  }

  public LostItem(
      int lostItemId,
      String itemType,
      String itemSubtype,
      String itemDescription,
      String locationDetails,
      LocalDateTime dateTimeLost,
      String itemPhotoPath,
      String reporterName,
      String reporterEmail,
      String reporterPhone,
      Status status,
      LocalDateTime createdAt) {
    super(
        itemType,
        itemSubtype,
        itemDescription,
        locationDetails,
        itemPhotoPath,
        reporterName,
        reporterEmail,
        reporterPhone,
        createdAt);
    this.lostItemId = lostItemId;
    this.dateTimeLost = dateTimeLost;
    this.status = status;
  }

  public int getLostItemId() {
    return lostItemId;
  }

  public void setLostItemId(int lostItemId) {
    this.lostItemId = lostItemId;
  }

  public LocalDateTime getDateTimeLost() {
    return dateTimeLost;
  }

  public void setDateTimeLost(LocalDateTime dateTimeLost) {
    this.dateTimeLost = dateTimeLost;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  @Override
  public Icon getImageIcon(int width, int height, float round) {
    final String PHOTO_FOLDER = "imgs/lostItems/";
    AvatarIcon icon = new AvatarIcon(PHOTO_FOLDER + super.getItemPhotoPath(), width, height, round);
    icon.setType(AvatarIcon.Type.MASK_SQUIRCLE);
    return icon;
  }
}
