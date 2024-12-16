package com.appdev.logic.services;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.tinylog.Logger;

public class ImageService {
  public static final String LOST_ITEMS_PATH = "lostItems/";
  public static final String FOUND_ITEMS_PATH = "foundItems/";
  public static final String IDS_PATH = "ids/";
  public static final String PROFILES_PATH = "profiles/";

  /**
   * Saves the image file to a specified directory with a new name based on the provided ID. Creates
   * the directory if it doesn't exist and replaces the file if it already exists.
   *
   * @param parent - the parent component (unused in this method).
   * @param file - the file to be saved.
   * @param directory - the target directory (e.g., "lostItems", "foundItems").
   * @param id - the ID used to generate the file name.
   * @return the final file name, or {@code null} if an error occurs.
   */
  public String saveImage(Component parent, File file, String directory, int id) {
    try {
      Path destinationFile = null;
      Path destinationDir = Path.of("imgs/" + directory);
      if (!Files.exists(destinationDir)) {
        Files.createDirectories(destinationDir);
        System.out.println("Created directory: " + destinationDir);
      }

      String originalFileName = file.getName();
      String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
      // String uuid = UUID.randomUUID().toString();
      // String finalFileName = Integer.toString(id) + "_" + uuid + fileExtension; // Example:
      // "1.jpg", "2.png"
      String finalFileName = Integer.toString(id) + fileExtension;

      // Copy the file to the destination directory
      destinationFile = destinationDir.resolve(finalFileName);
      Files.copy(file.toPath(), destinationFile, StandardCopyOption.REPLACE_EXISTING);

      return finalFileName;
    } catch (IOException e) {
      Logger.error(e, "Error saving image to {} directory ", directory);
      return null;
    }
  }
}
