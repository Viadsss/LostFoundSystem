package com.appdev.logic.models;

import java.time.LocalDateTime;
import javax.swing.Icon;
import raven.extras.AvatarIcon;

public class FoundItem extends Item {
  private int foundItemId;
  private LocalDateTime dateTimeFound;
  private Status status;

  public enum Status {
    REPORTED,
    PENDING,
    MATCHED,
    RETURNED
  }

  public FoundItem(
      int foundItemId,
      String itemType,
      String itemSubtype,
      String itemDescription,
      String locationDetails,
      LocalDateTime dateTimeFound,
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
    this.foundItemId = foundItemId;
    this.dateTimeFound = dateTimeFound;
    this.status = status;
  }

  public int getFoundItemId() {
    return foundItemId;
  }

  public void setFoundItemId(int foundItemId) {
    this.foundItemId = foundItemId;
  }

  public LocalDateTime getDateTimeFound() {
    return dateTimeFound;
  }

  public void setDateTimeFound(LocalDateTime dateTimeFound) {
    this.dateTimeFound = dateTimeFound;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  @Override
  public Icon getImageIcon(int width, int height, float round) {
    final String PHOTO_FOLDER = "imgs/foundItems/";
    AvatarIcon icon = new AvatarIcon(PHOTO_FOLDER + super.getItemPhotoPath(), width, height, round);
    icon.setType(AvatarIcon.Type.MASK_SQUIRCLE);
    return icon;
  }
}
