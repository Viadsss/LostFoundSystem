package com.appdev.data.dao;

import com.appdev.data.database.DbConnection;
import com.appdev.logic.models.FoundItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.tinylog.Logger;

public class FoundItemDAO {
  private final Connection connection;

  public FoundItemDAO() {
    this.connection = DbConnection.getConnection();
  }

  public List<FoundItem> getAllFoundItems() {
    String query = "SELECT * FROM found_items";
    List<FoundItem> foundItems = new ArrayList<>();

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        FoundItem item =
            new FoundItem(
                rs.getInt("found_item_id"),
                rs.getString("item_type"),
                rs.getString("item_subtype"),
                rs.getString("item_description"),
                rs.getString("location_details"),
                rs.getTimestamp("date_time_found").toLocalDateTime(),
                rs.getString("item_photo_path"),
                rs.getString("reporter_name"),
                rs.getString("reporter_email"),
                rs.getString("reporter_phone"),
                FoundItem.Status.valueOf(rs.getString("status")),
                rs.getTimestamp("createdAt").toLocalDateTime());
        foundItems.add(item);
      }

    } catch (SQLException e) {
      Logger.error(e, "Error retrieving all found items from the database.");
    }

    return foundItems;
  }

  public FoundItem getFoundItemById(int id) {
    String query = "SELECT * FROM found_items WHERE found_item_id = ?";
    FoundItem item = null;

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setInt(1, id);

      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        item =
            new FoundItem(
                rs.getInt("found_item_id"),
                rs.getString("item_type"),
                rs.getString("item_subtype"),
                rs.getString("item_description"),
                rs.getString("location_details"),
                rs.getTimestamp("date_time_found").toLocalDateTime(),
                rs.getString("item_photo_path"),
                rs.getString("reporter_name"),
                rs.getString("reporter_email"),
                rs.getString("reporter_phone"),
                FoundItem.Status.valueOf(rs.getString("status")),
                rs.getTimestamp("createdAt").toLocalDateTime());
      }

    } catch (SQLException e) {
      Logger.error(e, "Error retrieving found item with ID {} from the database.", id);
    }

    return item;
  }

  public void updateFoundItem(FoundItem item) {
    String query =
        "UPDATE found_items SET item_type = ?, item_subtype = ?, item_description = ?, location_details = ?, "
            + "date_time_found = ?, item_photo_path = ?, reporter_name = ?, reporter_email = ?, reporter_phone = ?, "
            + "status = ? WHERE found_item_id = ?";

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setString(1, item.getItemType());
      pstmt.setString(2, item.getItemSubtype());
      pstmt.setString(3, item.getItemDescription());
      pstmt.setString(4, item.getLocationDetails());
      pstmt.setTimestamp(
          5, Timestamp.valueOf(item.getDateTimeFound())); // Convert LocalDateTime to Timestamp
      pstmt.setString(6, item.getItemPhotoPath());
      pstmt.setString(7, item.getReporterName());
      pstmt.setString(8, item.getReporterEmail());
      pstmt.setString(9, item.getReporterPhone());
      pstmt.setString(10, item.getStatus().name()); // Assuming Status is an enum
      pstmt.setInt(11, item.getFoundItemId());

      pstmt.executeUpdate();

    } catch (SQLException e) {
      Logger.error(e, "Error updating foundlost item with ID {} in the database.", item.getFoundItemId());
    }
  }
}
