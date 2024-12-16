package com.appdev.logic.models;

import java.time.LocalDateTime;
import javax.swing.Icon;

public abstract class Item {
  private String itemType;
  private String itemSubtype;
  private String itemDescription;
  private String locationDetails;
  private String itemPhotoPath;
  private String reporterName;
  private String reporterEmail;
  private String reporterPhone;
  private LocalDateTime createdAt;

  public Item(
      String itemType,
      String itemSubtype,
      String itemDescription,
      String locationDetails,
      String itemPhotoPath,
      String reporterName,
      String reporterEmail,
      String reporterPhone,
      LocalDateTime createdAt) {
    this.itemType = itemType;
    this.itemSubtype = itemSubtype;
    this.itemDescription = itemDescription;
    this.locationDetails = locationDetails;
    this.itemPhotoPath = itemPhotoPath;
    this.reporterName = reporterName;
    this.reporterEmail = reporterEmail;
    this.reporterPhone = reporterPhone;
    this.createdAt = createdAt;
  }

  public String getItemType() {
    return itemType;
  }

  public void setItemType(String itemType) {
    this.itemType = itemType;
  }

  public String getItemSubtype() {
    return itemSubtype;
  }

  public void setItemSubtype(String itemSubtype) {
    this.itemSubtype = itemSubtype;
  }

  public String getItemDescription() {
    return itemDescription;
  }

  public void setItemDescription(String itemDescription) {
    this.itemDescription = itemDescription;
  }

  public String getLocationDetails() {
    return locationDetails;
  }

  public void setLocationDetails(String locationDetails) {
    this.locationDetails = locationDetails;
  }

  public String getItemPhotoPath() {
    return itemPhotoPath;
  }

  public void setItemPhotoPath(String itemPhotoPath) {
    this.itemPhotoPath = itemPhotoPath;
  }

  public String getReporterName() {
    return reporterName;
  }

  public void setReporterName(String reporterName) {
    this.reporterName = reporterName;
  }

  public String getReporterEmail() {
    return reporterEmail;
  }

  public void setReporterEmail(String reporterEmail) {
    this.reporterEmail = reporterEmail;
  }

  public String getReporterPhone() {
    return reporterPhone;
  }

  public void setReporterPhone(String reporterPhone) {
    this.reporterPhone = reporterPhone;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public abstract Icon getImageIcon(int width, int height, float round);
}
