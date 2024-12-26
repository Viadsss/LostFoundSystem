/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.appdev.logic.models;

import java.time.LocalDateTime;


public class ReportedItem {
      String itemType;
      String itemSubtype;
      String itemDescription;
      String locationDetails;
      LocalDateTime dateTimeReported;
      String itemPhotoPath;
      String reporterName;
      String reporterEmail;
      String reporterPhone;
    
    
    public ReportedItem(
      String itemType,
      String itemSubtype,
      String itemDescription,
      String locationDetails,
      LocalDateTime dateTimeReported,
      String itemPhotoPath,
      String reporterName,
      String reporterEmail,
      String reporterPhone) {
        this.itemType = itemType;
        this.itemSubtype = itemSubtype;
        this.itemDescription = itemDescription;
        this.locationDetails = locationDetails;
        this.dateTimeReported = dateTimeReported;
        this.itemPhotoPath = itemPhotoPath;
        this.reporterName = reporterName;
        this.reporterEmail = reporterEmail;
        this.reporterPhone = reporterPhone;
        
  }
    
    public String getItemType() {
    return itemType;
  }


  public String getItemSubtype() {
    return itemSubtype;
  }


  public String getItemDescription() {
    return itemDescription;
  }


  public String getLocationDetails() {
    return locationDetails;
  }
  
  
  public LocalDateTime getDateTimeReported() {
    return dateTimeReported;
  }

  
  public String getItemPhotoPath() {
    return itemPhotoPath;
  }


  public String getReporterName() {
    return reporterName;
  }


  public String getReporterEmail() {
    return reporterEmail;
  }


  public String getReporterPhone() {
    return reporterPhone;
  }
  

}




