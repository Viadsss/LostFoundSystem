package com.appdev.logic.services;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.tinylog.Logger;

public class ImageService {
  public static final String LOST_ITEMS_PATH = "lostItems/";
  public static final String FOUND_ITEMS_PATH = "foundItems/";
  public static final String IDS_PATH = "ids/";
  public static final String PROFILES_PATH = "profiles/";
  private static JFileChooser fileChooser;

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
  public String saveImage(Component parent, File file, String directory) {
    try {
      Path destinationFile = null;
      Path destinationDir = Path.of("imgs/" + directory);
      if (!Files.exists(destinationDir)) {
        Files.createDirectories(destinationDir);
      }

      String originalFileName = file.getName();
      String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
      String uuid = UUID.randomUUID().toString();
      String finalFileName = uuid + fileExtension;

      // Copy the file to the destination directory
      destinationFile = destinationDir.resolve(finalFileName);
      Files.copy(file.toPath(), destinationFile, StandardCopyOption.REPLACE_EXISTING);

      return finalFileName;
    } catch (IOException e) {
      Logger.error(e, "Error saving image to {} directory ", directory);
      return null;
    }
  }

  /**
   * Deletes an image file based on its file name.
   *
   * @param directory - the directory where the file is located (e.g., "lostItems", "foundItems").
   * @param fileName - the name of the file to be deleted.
   * @return {@code true} if the file is successfully deleted, {@code false} otherwise.
   */
  public boolean deleteImage(String directory, String fileName) {
    try {
      Path filePath = Path.of("imgs/" + directory, fileName);

      if (Files.exists(filePath)) {
        Files.delete(filePath);
        Logger.info("Image file {} in directory {} successfully deleted.", fileName, directory);
        return true;
      } else {
        Logger.warn("Image file {} not found in directory {}.", fileName, directory);
        return false;
      }
    } catch (IOException e) {
      Logger.error(e, "Error deleting image file {} in directory {}.", fileName, directory);
      return false;
    }
  }

  /**
   * Opens a file chooser dialog to allow the user to select an image.
   *
   * @param parent - the parent component for the file chooser dialog.
   * @return the selected file, or {@code null} if no file is selected.
   */
  public File selectImage(Component parent) {
    if (fileChooser == null) {
      fileChooser = new JFileChooser();
      String userHome = System.getProperty("user.home");
      String downloadsFolder = userHome + File.separator + "Downloads";
      File downloadsDirectory = new File(downloadsFolder);
      fileChooser.setDialogTitle("Choose an Image");
      fileChooser.setCurrentDirectory(downloadsDirectory);
      fileChooser.setFileFilter(new FileNameExtensionFilter("Image File", "jpg", "jpeg", "png"));
      fileChooser.setAcceptAllFileFilterUsed(false);
    }

    int returnValue = fileChooser.showOpenDialog(parent);
    return returnValue == JFileChooser.APPROVE_OPTION ? fileChooser.getSelectedFile() : null;
  }
}
