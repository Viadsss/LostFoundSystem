package com.appdev.logic.models;

import java.time.LocalDateTime;

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
      String itemPhotoPath,
      String reporterName,
      String reporterEmail,
      String reporterPhone,
      LocalDateTime dateTimeFound,
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
}
