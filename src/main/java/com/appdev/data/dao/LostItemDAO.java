package com.appdev.data.dao;

import com.appdev.data.database.DbConnection;
import com.appdev.logic.models.LostItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
