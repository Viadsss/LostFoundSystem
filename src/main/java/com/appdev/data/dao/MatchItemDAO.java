package com.appdev.data.dao;

import com.appdev.data.database.DbConnection;
import com.appdev.logic.models.MatchItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.tinylog.Logger;

public class MatchItemDAO {
  private final Connection connection;

  public MatchItemDAO() {
    this.connection = DbConnection.getConnection();
  }

  public List<MatchItem> getAllMatchItems() {
    String query = "SELECT * FROM match_items";
    List<MatchItem> matchItems = new ArrayList<>();

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        MatchItem item =
            new MatchItem(
                rs.getInt("match_id"),
                rs.getInt("lost_item_id"),
                rs.getInt("found_item_id"),
                rs.getString("id_photo_path"),
                rs.getString("profile_path"),
                MatchItem.Status.valueOf(rs.getString("status")),
                rs.getTimestamp("createdAt").toLocalDateTime());
        matchItems.add(item);
      }

    } catch (SQLException e) {
      Logger.error(e, "Error retrieving all match items from the database.");
    }

    return matchItems;
  }

  public MatchItem getMatchItemById(int id) {
    String query = "SELECT * FROM match_items WHERE match_id = ?";
    MatchItem item = null;

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setInt(1, id);

      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        item =
            new MatchItem(
                rs.getInt("match_id"),
                rs.getInt("lost_item_id"),
                rs.getInt("found_item_id"),
                rs.getString("id_photo_path"),
                rs.getString("profile_path"),
                MatchItem.Status.valueOf(rs.getString("status")),
                rs.getTimestamp("createdAt").toLocalDateTime());
      }

    } catch (SQLException e) {
      Logger.error(e, "Error retrieving match item with ID {} from the database.", id);
    }

    return item;
  }
}
