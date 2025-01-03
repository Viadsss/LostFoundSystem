package com.appdev.logic.models;

import java.time.LocalDateTime;
import javax.swing.Icon;
import raven.extras.AvatarIcon;

public class MatchItem {
  private int matchId;
  private int lostItemId;
  private int foundItemId;
  private String idPhotoPath;
  private String profilePath;
  private Status status;
  private LocalDateTime createdAt;

  public enum Status {
    MATCHED,
    RESOLVED
  }

  public MatchItem(
      int matchId,
      int lostItemId,
      int foundItemId,
      String idPhotoPath,
      String profilePath,
      Status status,
      LocalDateTime createdAt) {
    this.matchId = matchId;
    this.lostItemId = lostItemId;
    this.foundItemId = foundItemId;
    this.idPhotoPath = idPhotoPath;
    this.profilePath = profilePath;
    this.status = status;
    this.createdAt = createdAt;
  }

  public int getMatchId() {
    return matchId;
  }

  public void setMatchId(int matchId) {
    this.matchId = matchId;
  }

  public int getLostItemId() {
    return lostItemId;
  }

  public void setLostItemId(int lostItemId) {
    this.lostItemId = lostItemId;
  }

  public int getFoundItemId() {
    return foundItemId;
  }

  public void setFoundItemId(int foundItemId) {
    this.foundItemId = foundItemId;
  }

  public String getIdPhotoPath() {
    return idPhotoPath;
  }

  public void setIdPhotoPath(String idPhotoPath) {
    this.idPhotoPath = idPhotoPath;
  }

  public String getProfilePath() {
    return profilePath;
  }

  public void setProfilePath(String profilePath) {
    this.profilePath = profilePath;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public Icon getProfileIcon(int width, int height, float round) {
    final String PHOTO_FOLDER = "imgs/profiles/";
    AvatarIcon icon = new AvatarIcon(PHOTO_FOLDER + this.getProfilePath(), width, height, round);
    icon.setType(AvatarIcon.Type.MASK_SQUIRCLE);
    return icon;
  }

  public Icon getIdPhotoIcon(int width, int height, float round) {
    final String PHOTO_FOLDER = "imgs/ids/";
    AvatarIcon icon = new AvatarIcon(PHOTO_FOLDER + this.getIdPhotoPath(), width, height, round);
    icon.setType(AvatarIcon.Type.MASK_SQUIRCLE);
    return icon;
  }
}
