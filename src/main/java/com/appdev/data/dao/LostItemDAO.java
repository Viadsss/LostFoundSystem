package com.appdev.data.dao;

import com.appdev.data.database.DbConnection;
import com.appdev.logic.models.LostItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.tinylog.Logger;

public class LostItemDAO {
  private final Connection connection;

  public LostItemDAO() {
    this.connection = DbConnection.getConnection();
  }

  public List<LostItem> getAllLostItems() {
    String query = "SELECT * FROM lost_items";
    List<LostItem> lostItems = new ArrayList<>();

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        LostItem item =
            new LostItem(
                rs.getInt("lost_item_id"),
                rs.getString("item_type"),
                rs.getString("item_subtype"),
                rs.getString("item_description"),
                rs.getString("location_details"),
                rs.getTimestamp("date_time_lost").toLocalDateTime(),
                rs.getString("item_photo_path"),
                rs.getString("reporter_name"),
                rs.getString("reporter_email"),
                rs.getString("reporter_phone"),
                LostItem.Status.valueOf(rs.getString("status")),
                rs.getTimestamp("createdAt").toLocalDateTime());
        lostItems.add(item);
      }

    } catch (SQLException e) {
      Logger.error(e, "Error retrieving all lost items from the database.");
    }

    return lostItems;
  }

  public LostItem getLostItemById(int id) {
    String query = "SELECT * FROM lost_items WHERE lost_item_id = ?";
    LostItem item = null;

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setInt(1, id);

      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        item =
            new LostItem(
                rs.getInt("lost_item_id"),
                rs.getString("item_type"),
                rs.getString("item_subtype"),
                rs.getString("item_description"),
                rs.getString("location_details"),
                rs.getTimestamp("date_time_lost").toLocalDateTime(),
                rs.getString("item_photo_path"),
                rs.getString("reporter_name"),
                rs.getString("reporter_email"),
                rs.getString("reporter_phone"),
                LostItem.Status.valueOf(rs.getString("status")),
                rs.getTimestamp("createdAt").toLocalDateTime());
      }

    } catch (SQLException e) {
      Logger.error(e, "Error retrieving lost item with ID {} from the database.", id);
    }

    return item;
  }

  public void updateLostItem(LostItem item) {
    String query =
        "UPDATE lost_items SET item_type = ?, item_subtype = ?, item_description = ?, location_details = ?, "
            + "date_time_lost = ?, item_photo_path = ?, reporter_name = ?, reporter_email = ?, reporter_phone = ?, "
            + "status = ? WHERE lost_item_id = ?";

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setString(1, item.getItemType());
      pstmt.setString(2, item.getItemSubtype());
      pstmt.setString(3, item.getItemDescription());
      pstmt.setString(4, item.getLocationDetails());
      pstmt.setTimestamp(
          5, Timestamp.valueOf(item.getDateTimeLost())); // Convert LocalDateTime to Timestamp
      pstmt.setString(6, item.getItemPhotoPath());
      pstmt.setString(7, item.getReporterName());
      pstmt.setString(8, item.getReporterEmail());
      pstmt.setString(9, item.getReporterPhone());
      pstmt.setString(10, item.getStatus().name()); // Assuming Status is an enum
      pstmt.setInt(11, item.getLostItemId());

      pstmt.executeUpdate();

    } catch (SQLException e) {
      Logger.error(e, "Error updating lost item with ID {} in the database.", item.getLostItemId());
    }
  }

  public void updateLostItemStatus(int lostItemId, LostItem.Status status) {
    String query = "UPDATE lost_items SET status = ? WHERE lost_item_id = ?";

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setString(1, status.name());
      pstmt.setInt(2, lostItemId);

      pstmt.executeUpdate();
    } catch (SQLException e) {
      Logger.error(
          e, "Error updating status of found item with ID {} in the database.", lostItemId);
    }
  }
}
