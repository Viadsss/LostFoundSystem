package com.appdev.presentation.pages;

import com.appdev.logic.managers.PageManager;
import com.appdev.logic.managers.StyleManager;
import com.appdev.logic.models.FoundItem;
import com.appdev.logic.models.LostItem;
import com.appdev.logic.models.MatchItem;
import com.appdev.logic.services.ImageService;
import com.appdev.logic.services.ItemService;
import com.appdev.presentation.components.forms.ItemFormUpdate;
import com.appdev.presentation.components.forms.ItemFormView;
import com.appdev.presentation.components.forms.MatchItemFormView;
import com.appdev.presentation.components.table.MatchSearchFilterDocumentListener;
import com.appdev.presentation.components.table.SearchFilterDocumentListener;
import com.appdev.presentation.components.table.TableDateTimeCellRenderer;
import com.appdev.presentation.components.table.TableIdCellRenderer;
import com.appdev.presentation.components.table.TableImageCellRenderer;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Component;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import net.miginfocom.swing.MigLayout;
import raven.modal.ModalDialog;
import raven.modal.Toast;
import raven.modal.component.SimpleModalBorder;
import raven.modal.option.Location;
import raven.modal.option.Option;
import raven.modal.option.Option.BackgroundClickType;
import raven.modal.toast.option.ToastLocation;
import raven.modal.toast.option.ToastOption;
import raven.modal.toast.option.ToastStyle;
import raven.modal.toast.option.ToastStyle.BackgroundType;
import raven.modal.toast.option.ToastStyle.BorderType;

public class AdminPage extends JPanel {
  private ItemService itemService = new ItemService();
  private int selectedLostItemId = -1;
  private int selectedFoundItemId = -1;
  private int selectedMatchItemId = -1;
  private DefaultTableModel lostItemModel, foundItemModel, matchItemModel;
  private JTable lostItemTable, foundItemTable, matchItemTable;

  public AdminPage(boolean showMatch) {
    init(showMatch);
  }

  private void init(boolean showMatch) {
    setLayout(new MigLayout("fillx,wrap", "[fill]", "[][fill,grow]"));
    add(createInfo("Admin Page", 1));
    add(createTab(showMatch));
    add(createFooterAction());
  }

  private JPanel createInfo(String title, int level) {
    JPanel panel = new JPanel(new MigLayout("fillx,wrap", "[fill]"));
    JLabel lbTitle = new JLabel(title);
    lbTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +" + (4 - level));
    panel.add(lbTitle);
    return panel;
  }

  private Component createTab(boolean showMatch) {
    JTabbedPane tabb = new JTabbedPane();
    tabb.putClientProperty(FlatClientProperties.STYLE, "" + "tabType:card");
    tabb.addTab("Lost Items", lostItemTable());
    tabb.addTab("Found Items", foundItemTable());
    tabb.addTab("Match Items", matchItemTable());

    if (showMatch) {
      tabb.setSelectedIndex(2);
    }

    return tabb;
  }

  private Component lostItemTable() {
    JPanel panel =
        new JPanel(new MigLayout("fillx,wrap,insets 10 0 10 0", "[fill]", "[][][]0[fill,grow]"));

    // Table Model
    Object columns[] =
        new Object[] {
          "ID",
          "Item Type",
          "Item Subtype",
          "Item Description",
          "Location Details",
          "Date & Time Lost",
          "Reporter Name",
          "Status",
          "Photo"
        };
    lostItemModel =
        new DefaultTableModel(columns, 0) {
          @Override
          public boolean isCellEditable(int row, int column) {
            return false;
          }

          @Override
          public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 0) return Integer.class;
            if (columnIndex == 5) return LocalDateTime.class;
            return super.getColumnClass(columnIndex);
          }
        };

    // Table
    lostItemTable = new JTable(lostItemModel);

    // Table Sorter
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(lostItemModel);
    lostItemTable.setRowSorter(sorter);

    // Table Scroll
    JScrollPane scrollPane = new JScrollPane(lostItemTable);
    scrollPane.setBorder(BorderFactory.createEmptyBorder());

    // Table Options
    lostItemTable.getColumnModel().getColumn(0).setMinWidth(40);
    lostItemTable.getColumnModel().getColumn(0).setMaxWidth(40);
    lostItemTable.getColumnModel().getColumn(5).setPreferredWidth(130);
    lostItemTable.getColumnModel().getColumn(7).setMaxWidth(100);
    lostItemTable.getColumnModel().getColumn(7).setMinWidth(100);
    lostItemTable.getColumnModel().getColumn(8).setMinWidth(100);
    lostItemTable.getColumnModel().getColumn(8).setMaxWidth(100);

    lostItemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    lostItemTable.getTableHeader().setReorderingAllowed(false);

    // Custom Cell Renderers
    lostItemTable.getColumnModel().getColumn(0).setCellRenderer(new TableIdCellRenderer());
    lostItemTable.getColumnModel().getColumn(5).setCellRenderer(new TableDateTimeCellRenderer());
    lostItemTable
        .getColumnModel()
        .getColumn(8)
        .setCellRenderer(new TableImageCellRenderer(lostItemTable));

    JLabel title = new JLabel("Lost Items");

    // create header
    JPanel actionPanel = new JPanel(new MigLayout("insets 5 20 5 20", "[fill,230]push[][]"));
    JTextField searchField = new JTextField();
    JButton viewButton = new JButton("View");
    JButton updateButton = new JButton("Update");

    JSeparator separator = new JSeparator();

    // STYLES
    StyleManager.styleTablePanel(panel);
    StyleManager.styleTable(lostItemTable);
    StyleManager.styleTitle(title);
    StyleManager.styleActionPanel(actionPanel);
    StyleManager.styleScrollPane(scrollPane);
    StyleManager.styleSearchField(searchField);
    StyleManager.styleSeparator(separator);

    viewButton.setIcon(new FlatSVGIcon("icons/view.svg", 0.8f));
    updateButton.setIcon(new FlatSVGIcon("icons/update.svg", 0.8f));

    // Actions
    lostItemModel.addTableModelListener(
        e -> {
          title.setText("Lost Items (" + lostItemTable.getRowCount() + ")");
        });

    lostItemTable
        .getSelectionModel()
        .addListSelectionListener(
            event -> {
              if (!event.getValueIsAdjusting()) {
                int selectedRow = lostItemTable.getSelectedRow();

                if (selectedRow != -1) {
                  int id = (int) lostItemTable.getValueAt(selectedRow, 0);
                  selectedLostItemId = id;
                }
              }
            });

    searchField
        .getDocument()
        .addDocumentListener(
            new SearchFilterDocumentListener(
                lostItemTable, sorter, title, "Lost Items", searchField));

    viewButton.addActionListener(
        e -> {
          if (selectedLostItemId != -1) {
            LostItem item = itemService.getLostItem(selectedLostItemId);
            showItemFormViewModal(item);
          } else {
            JOptionPane.showMessageDialog(
                this,
                "Please select a lost item first on the table",
                "Viewing Error",
                JOptionPane.ERROR_MESSAGE);
          }
        });
    updateButton.addActionListener(
        e -> {
          if (selectedLostItemId != -1) {
            LostItem item = itemService.getLostItem(selectedLostItemId);
            showItemFormUpdateModal(item);
          } else {
            JOptionPane.showMessageDialog(
                this,
                "Please select a lost item first on the table",
                "Updating Error",
                JOptionPane.ERROR_MESSAGE);
          }
        });

    // Adding
    actionPanel.add(searchField);
    actionPanel.add(viewButton);
    actionPanel.add(updateButton);

    panel.add(title, "gapx 20");
    panel.add(actionPanel);
    panel.add(separator, "height 2");
    panel.add(scrollPane);

    for (LostItem item : itemService.getAllLostItems()) {
      lostItemModel.addRow(
          new Object[] {
            item.getLostItemId(),
            item.getItemType(),
            item.getItemSubtype(),
            item.getItemDescription(),
            item.getLocationDetails(),
            item.getDateTimeLost(),
            item.getReporterName(),
            item.getStatus().toString(),
            item.getImageIcon(80, 80, 3f)
          });
    }

    return panel;
  }

  private Component foundItemTable() {
    JPanel panel =
        new JPanel(new MigLayout("fillx,wrap,insets 10 0 10 0", "[fill]", "[][][]0[fill,grow]"));

    // Table Model
    Object columns[] =
        new Object[] {
          "ID",
          "Item Type",
          "Item Subtype",
          "Item Description",
          "Location Details",
          "Date & Time Found",
          "Reporter Name",
          "Status",
          "Photo"
        };
    foundItemModel =
        new DefaultTableModel(columns, 0) {
          @Override
          public boolean isCellEditable(int row, int column) {
            return false;
          }

          @Override
          public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 0) return Integer.class;
            if (columnIndex == 5) return LocalDateTime.class;
            return super.getColumnClass(columnIndex);
          }
        };

    // Table
    foundItemTable = new JTable(foundItemModel);

    // Table Sorter
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(foundItemModel);
    foundItemTable.setRowSorter(sorter);

    // Table Scroll
    JScrollPane scrollPane = new JScrollPane(foundItemTable);
    scrollPane.setBorder(BorderFactory.createEmptyBorder());

    // Table Options
    foundItemTable.getColumnModel().getColumn(0).setMinWidth(40);
    foundItemTable.getColumnModel().getColumn(0).setMaxWidth(40);
    foundItemTable.getColumnModel().getColumn(5).setPreferredWidth(130);
    foundItemTable.getColumnModel().getColumn(7).setMaxWidth(100);
    foundItemTable.getColumnModel().getColumn(7).setMinWidth(100);
    foundItemTable.getColumnModel().getColumn(8).setMinWidth(100);
    foundItemTable.getColumnModel().getColumn(8).setMaxWidth(100);

    foundItemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    foundItemTable.getTableHeader().setReorderingAllowed(false);

    // Custom Cell Renderers
    foundItemTable.getColumnModel().getColumn(0).setCellRenderer(new TableIdCellRenderer());
    foundItemTable.getColumnModel().getColumn(5).setCellRenderer(new TableDateTimeCellRenderer());
    foundItemTable
        .getColumnModel()
        .getColumn(8)
        .setCellRenderer(new TableImageCellRenderer(foundItemTable));

    JLabel title = new JLabel("Found Items");

    // create header
    JPanel actionPanel = new JPanel(new MigLayout("insets 5 20 5 20", "[fill,230]push[][]"));
    JTextField searchField = new JTextField();
    JButton viewButton = new JButton("View");
    JButton updateButton = new JButton("Update");

    JSeparator separator = new JSeparator();

    // STYLES
    StyleManager.styleTablePanel(panel);
    StyleManager.styleTable(foundItemTable);
    StyleManager.styleTitle(title);
    StyleManager.styleActionPanel(actionPanel);
    StyleManager.styleScrollPane(scrollPane);
    StyleManager.styleSearchField(searchField);
    StyleManager.styleSeparator(separator);

    viewButton.setIcon(new FlatSVGIcon("icons/view.svg", 0.8f));
    updateButton.setIcon(new FlatSVGIcon("icons/update.svg", 0.8f));

    // Actions
    foundItemModel.addTableModelListener(
        e -> {
          title.setText("Found Items (" + foundItemTable.getRowCount() + ")");
        });

    foundItemTable
        .getSelectionModel()
        .addListSelectionListener(
            event -> {
              if (!event.getValueIsAdjusting()) {
                int selectedRow = foundItemTable.getSelectedRow();

                if (selectedRow != -1) {
                  int id = (int) foundItemTable.getValueAt(selectedRow, 0);
                  selectedFoundItemId = id;
                }
              }
            });

    searchField
        .getDocument()
        .addDocumentListener(
            new SearchFilterDocumentListener(
                foundItemTable, sorter, title, "Found Items", searchField));

    viewButton.addActionListener(
        e -> {
          if (selectedFoundItemId != -1) {
            FoundItem item = itemService.getFoundItem(selectedFoundItemId);
            showItemFormViewModal(item);
          } else {
            JOptionPane.showMessageDialog(
                this,
                "Please select a found item first on the table",
                "Viewing Error",
                JOptionPane.ERROR_MESSAGE);
          }
        });

    updateButton.addActionListener(
        e -> {
          if (selectedFoundItemId != -1) {
            FoundItem item = itemService.getFoundItem(selectedFoundItemId);
            showItemFormUpdateModal(item);
          } else {
            JOptionPane.showMessageDialog(
                this,
                "Please select a found item first on the table",
                "Updating Error",
                JOptionPane.ERROR_MESSAGE);
          }
        });

    // Adding
    actionPanel.add(searchField);
    actionPanel.add(viewButton);
    actionPanel.add(updateButton);

    panel.add(title, "gapx 20");
    panel.add(actionPanel);
    panel.add(separator, "height 2");
    panel.add(scrollPane);

    for (FoundItem item : itemService.getAllFoundItems()) {
      foundItemModel.addRow(
          new Object[] {
            item.getFoundItemId(),
            item.getItemType(),
            item.getItemSubtype(),
            item.getItemDescription(),
            item.getLocationDetails(),
            item.getDateTimeFound(),
            item.getReporterName(),
            item.getStatus().toString(),
            item.getImageIcon(80, 80, 3f)
          });
    }

    return panel;
  }

  private Component matchItemTable() {
    JPanel panel =
        new JPanel(new MigLayout("fillx,wrap,insets 10 0 10 0", "[fill]", "[][][]0[fill,grow]"));

    // Table Model
    Object columns[] =
        new Object[] {
          "ID",
          "Lost Item ID",
          "Found Item ID",
          "Match Date & Time",
          "Status",
          "Verification ID Photo",
          "Profile Photo"
        };
    matchItemModel =
        new DefaultTableModel(columns, 0) {
          @Override
          public boolean isCellEditable(int row, int column) {
            return false;
          }

          @Override
          public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 0) return Integer.class;
            if (columnIndex == 1) return Integer.class;
            if (columnIndex == 2) return Integer.class;
            if (columnIndex == 3) return LocalDateTime.class;
            return super.getColumnClass(columnIndex);
          }
        };

    // Table
    matchItemTable = new JTable(matchItemModel);

    // Table Sorter
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(matchItemModel);
    matchItemTable.setRowSorter(sorter);

    // Table Scroll
    JScrollPane scrollPane = new JScrollPane(matchItemTable);
    scrollPane.setBorder(BorderFactory.createEmptyBorder());

    // Table Options
    matchItemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    matchItemTable.getTableHeader().setReorderingAllowed(false);

    // Custom Cell Renderers
    matchItemTable.getColumnModel().getColumn(0).setCellRenderer(new TableIdCellRenderer());
    matchItemTable.getColumnModel().getColumn(1).setCellRenderer(new TableIdCellRenderer());
    matchItemTable.getColumnModel().getColumn(2).setCellRenderer(new TableIdCellRenderer());
    matchItemTable.getColumnModel().getColumn(3).setCellRenderer(new TableDateTimeCellRenderer());
    matchItemTable
        .getColumnModel()
        .getColumn(5)
        .setCellRenderer(new TableImageCellRenderer(matchItemTable));
    matchItemTable
        .getColumnModel()
        .getColumn(6)
        .setCellRenderer(new TableImageCellRenderer(matchItemTable));

    JLabel title = new JLabel("Match Items");

    // create header
    JPanel actionPanel = new JPanel(new MigLayout("insets 5 20 5 20", "[fill,230]push[][]"));
    JTextField searchField = new JTextField();
    JButton viewButton = new JButton("View");
    JButton verifyButton = new JButton("Verify");
    JButton deleteButton = new JButton("Delete");

    JSeparator separator = new JSeparator();

    // STYLES
    StyleManager.styleTablePanel(panel);
    StyleManager.styleTable(matchItemTable);
    StyleManager.styleTitle(title);
    StyleManager.styleActionPanel(actionPanel);
    StyleManager.styleScrollPane(scrollPane);
    StyleManager.styleSearchField(searchField);
    StyleManager.styleSeparator(separator);

    viewButton.setIcon(new FlatSVGIcon("icons/view.svg", 0.8f));
    verifyButton.setIcon(new FlatSVGIcon("icons/verify.svg", 0.8f));
    deleteButton.setIcon(new FlatSVGIcon("icons/delete.svg", 0.8f));

    // Actions
    matchItemModel.addTableModelListener(
        e -> {
          title.setText("Match Items (" + matchItemTable.getRowCount() + ")");
        });

    searchField
        .getDocument()
        .addDocumentListener(
            new MatchSearchFilterDocumentListener(
                matchItemTable, sorter, title, "Match Items", searchField));

    matchItemTable
        .getSelectionModel()
        .addListSelectionListener(
            event -> {
              if (!event.getValueIsAdjusting()) {
                int selectedRow = matchItemTable.getSelectedRow();

                if (selectedRow != -1) {
                  int id = (int) matchItemTable.getValueAt(selectedRow, 0);
                  selectedMatchItemId = id;
                }
              }
            });

    viewButton.addActionListener(
        e -> {
          if (selectedMatchItemId != -1) {
            MatchItem item = itemService.getMatchItem(selectedMatchItemId);
            showMatchItemFormViewModal(item);
          } else {
            JOptionPane.showMessageDialog(
                this,
                "Please select a match item first on the table",
                "Viewing Error",
                JOptionPane.ERROR_MESSAGE);
          }
        });

    verifyButton.addActionListener(
        e -> {
          if (selectedMatchItemId != -1) {
            MatchItem item = itemService.getMatchItem(selectedMatchItemId);
            if (item.getStatus() == MatchItem.Status.RESOLVED) {
              JOptionPane.showMessageDialog(
                  this,
                  "The selected Match Item is already verified and resolved.",
                  "Verifying Info",
                  JOptionPane.INFORMATION_MESSAGE);
              return;
            }
            System.out.println("VERIFYING THIS ID: " + selectedMatchItemId);
            PageManager.getInstance().showPage(new VerificationPage(item));
          } else {
            JOptionPane.showMessageDialog(
                this,
                "Please select a match item first on the table",
                "Verifying Error",
                JOptionPane.ERROR_MESSAGE);
          }
        });

    deleteButton.addActionListener(
        e -> {
          if (selectedMatchItemId != -1) {
            ImageService imageService = new ImageService();
            MatchItem item = itemService.getMatchItem(selectedMatchItemId);

            int result =
                JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this match?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
              itemService.deleteMatchItem(item);
              imageService.deleteImage(ImageService.IDS_PATH, item.getIdPhotoPath());
              imageService.deleteImage(ImageService.PROFILES_PATH, item.getProfilePath());
              refreshAllTables();
            }
          } else {
            JOptionPane.showMessageDialog(
                this,
                "Please select a match item first on the table",
                "Deleting Error",
                JOptionPane.ERROR_MESSAGE);
          }
        });

    // Adding
    actionPanel.add(searchField);
    actionPanel.add(viewButton);
    actionPanel.add(verifyButton);
    actionPanel.add(deleteButton);

    panel.add(title, "gapx 20");
    panel.add(actionPanel);
    panel.add(separator, "height 2");
    panel.add(scrollPane);

    for (MatchItem item : itemService.getAllMatchItems()) {
      matchItemModel.addRow(
          new Object[] {
            item.getMatchId(),
            item.getLostItemId(),
            item.getFoundItemId(),
            item.getCreatedAt(),
            item.getStatus().toString(),
            item.getIdPhotoIcon(80, 80, 3f),
            item.getProfileIcon(80, 80, 3f)
          });
    }

    return panel;
  }

  private Component createFooterAction() {
    JPanel panel = new JPanel(new MigLayout("", "[grow, center]", "[grow]"));
    JButton infoButton = new JButton("Info");
    JButton matchButton = new JButton("Match");

    matchButton.addActionListener(
        e -> {
          if (selectedLostItemId == -1 || selectedFoundItemId == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Please select a row on both lost items table and found items table first before matching",
                "Matching Error",
                JOptionPane.ERROR_MESSAGE);
          } else {
            LostItem lostItem = itemService.getLostItem(selectedLostItemId);
            FoundItem foundItem = itemService.getFoundItem(selectedFoundItemId);

            if (lostItem.getStatus() != LostItem.Status.PENDING) {
              JOptionPane.showMessageDialog(
                  this,
                  "The selected Lost Item is not in a pending state. Please ensure that the item is pending before proceeding.",
                  "Matching Error",
                  JOptionPane.ERROR_MESSAGE);
              return;
            }

            if (foundItem.getStatus() != FoundItem.Status.PENDING) {
              JOptionPane.showMessageDialog(
                  this,
                  "The selected Found Item is not in a pending state. Please ensure that the item is pending before proceeding.",
                  "Matching Error",
                  JOptionPane.ERROR_MESSAGE);
              return;
            }

            showMatchItemFormViewModal(lostItem, foundItem);
          }
        });

    panel.add(infoButton);
    panel.add(matchButton);

    infoButton.setIcon(new FlatSVGIcon("icons/info.svg", 0.8f));
    matchButton.setIcon(new FlatSVGIcon("icons/match.svg", 0.8f));

    return panel;
  }

  private void refreshLostItemTable() {
    // Clear existing rows
    lostItemModel.setRowCount(0);

    // Re-populate the table with updated data
    for (LostItem item : itemService.getAllLostItems()) {
      lostItemModel.addRow(
          new Object[] {
            item.getLostItemId(),
            item.getItemType(),
            item.getItemSubtype(),
            item.getItemDescription(),
            item.getLocationDetails(),
            item.getDateTimeLost(),
            item.getReporterName(),
            item.getStatus().toString(),
            item.getImageIcon(80, 80, 3f)
          });
    }

    lostItemTable.getTableHeader().repaint();
    lostItemTable.repaint();
  }

  private void refreshFoundItemTable() {
    // Clear existing rows
    foundItemModel.setRowCount(0);

    // Re-populate the table with updated data
    for (FoundItem item : itemService.getAllFoundItems()) {
      foundItemModel.addRow(
          new Object[] {
            item.getFoundItemId(),
            item.getItemType(),
            item.getItemSubtype(),
            item.getItemDescription(),
            item.getLocationDetails(),
            item.getDateTimeFound(),
            item.getReporterName(),
            item.getStatus().toString(),
            item.getImageIcon(80, 80, 3f)
          });
    }

    foundItemTable.getTableHeader().repaint();
    foundItemTable.repaint();
  }

  private void refreshMatchItemTable() {
    matchItemModel.setRowCount(0);

    for (MatchItem item : itemService.getAllMatchItems()) {
      matchItemModel.addRow(
          new Object[] {
            item.getMatchId(),
            item.getLostItemId(),
            item.getFoundItemId(),
            item.getCreatedAt(),
            item.getStatus().toString(),
            item.getIdPhotoIcon(80, 80, 3f),
            item.getProfileIcon(80, 80, 3f)
          });
    }

    matchItemTable.getTableHeader().repaint();
    matchItemTable.repaint();
  }

  private void refreshAllTables() {
    selectedLostItemId = -1;
    selectedFoundItemId = -1;
    selectedMatchItemId = -1;

    

    refreshLostItemTable();
    refreshFoundItemTable();
    refreshMatchItemTable();
  }

  private void showItemFormViewModal(LostItem item) {
    Option option = ModalDialog.createOption();
    option
        .setBackgroundClickType(BackgroundClickType.BLOCK)
        .getLayoutOption()
        .setSize(-1, 1f)
        .setLocation(Location.CENTER, Location.TOP)
        .setAnimateDistance(0.7f, 0);

    SimpleModalBorder modal = new SimpleModalBorder(new ItemFormView(item), "View");
    ModalDialog.showModal(this, modal, option);
  }

  private void showItemFormViewModal(FoundItem item) {
    Option option = ModalDialog.createOption();
    option
        .setBackgroundClickType(BackgroundClickType.BLOCK)
        .getLayoutOption()
        .setSize(-1, 1f)
        .setLocation(Location.CENTER, Location.TOP)
        .setAnimateDistance(0.7f, 0);

    SimpleModalBorder modal = new SimpleModalBorder(new ItemFormView(item), "View");
    ModalDialog.showModal(this, modal, option);
  }

  private void showMatchItemFormViewModal(MatchItem item) {
    Option option = ModalDialog.createOption();
    option
        .setBackgroundClickType(BackgroundClickType.BLOCK)
        .getLayoutOption()
        .setSize(-1, 1f)
        .setLocation(Location.CENTER, Location.TOP)
        .setAnimateDistance(0.7f, 0);

    SimpleModalBorder modal = new SimpleModalBorder(new MatchItemFormView(item), "View");
    ModalDialog.showModal(this, modal, option);
  }

  private void showItemFormUpdateModal(LostItem item) {
    ItemFormUpdate form = new ItemFormUpdate(item);

    SimpleModalBorder.Option[] customOptions = {
      new SimpleModalBorder.Option("Update", 0), new SimpleModalBorder.Option("Cancel", 1)
    };

    Option option = ModalDialog.createOption();
    option
        .setBackgroundClickType(BackgroundClickType.BLOCK)
        .getLayoutOption()
        .setSize(-1, 1f)
        .setLocation(Location.CENTER, Location.TOP)
        .setAnimateDistance(0.7f, 0);

    SimpleModalBorder modal =
        new SimpleModalBorder(
            form,
            "Update",
            customOptions,
            (controller, action) -> {
              if (action == 0) {
                if (form.updateLostItem(item)) {
                  refreshAllTables();
                  showToast(Toast.Type.SUCCESS, "Lost Item Updated successfully");
                } else {
                  controller.consume();
                }
              } else if (action == 1) {
                controller.close();
              }
            });

    ModalDialog.showModal(this, modal, option);
  }

  private void showItemFormUpdateModal(FoundItem item) {
    ItemFormUpdate form = new ItemFormUpdate(item);

    List<SimpleModalBorder.Option> optionList = new ArrayList<>();
    optionList.add(new SimpleModalBorder.Option("Update", 0));
    if (item.getStatus() == FoundItem.Status.REPORTED) {
      optionList.add(new SimpleModalBorder.Option("Mark as Pending", 1));
    }
    optionList.add(new SimpleModalBorder.Option("Cancel", 2));

    SimpleModalBorder.Option[] customOptions = optionList.toArray(new SimpleModalBorder.Option[0]);

    Option option = ModalDialog.createOption();
    option
        .setBackgroundClickType(BackgroundClickType.BLOCK)
        .getLayoutOption()
        .setSize(-1, 1f)
        .setLocation(Location.CENTER, Location.TOP)
        .setAnimateDistance(0.7f, 0);

    SimpleModalBorder modal =
        new SimpleModalBorder(
            form,
            "Update",
            customOptions,
            (controller, action) -> {
              if (action == 0) {
                if (form.updateFoundItem(item)) {
                  refreshAllTables();
                  showToast(Toast.Type.SUCCESS, "Found Item Updated successfully");
                } else {
                  controller.consume();
                }
              } else if (action == 1) {
                controller.consume();
                int result =
                    JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to mark this item as Pending?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                  itemService.updateFoundItemStatus(
                      item.getFoundItemId(), FoundItem.Status.PENDING);
                  refreshAllTables();
                  showToast(Toast.Type.SUCCESS, "Found Item status changed to Pending.");
                  controller.close();
                }

              } else if (action == 2) {
                controller.close();
              }
            });

    ModalDialog.showModal(this, modal, option);
  }

  private void showMatchItemFormViewModal(LostItem lostItem, FoundItem foundItem) {
    MatchItemFormView form = new MatchItemFormView(lostItem, foundItem);

    SimpleModalBorder.Option[] customOptions = {
      new SimpleModalBorder.Option("Match", 0), new SimpleModalBorder.Option("Cancel", 1)
    };

    Option option = ModalDialog.createOption();
    option
        .setBackgroundClickType(BackgroundClickType.BLOCK)
        .getLayoutOption()
        .setSize(-1, 1f)
        .setLocation(Location.CENTER, Location.TOP)
        .setAnimateDistance(0.7f, 0);

    SimpleModalBorder modal =
        new SimpleModalBorder(
            form,
            "Match",
            customOptions,
            (controller, action) -> {
              if (action == 0) {
                itemService.addMatchItem(lostItem, foundItem);
                refreshAllTables();
                showToast(Toast.Type.SUCCESS, "Matched the Items Successfully");
              } else if (action == 1) {
                System.out.println("Clicked CANCEL");
              }
            });

    ModalDialog.showModal(this, modal, option);
  }

  private void showToast(Toast.Type type, String message) {
    ToastStyle style = new ToastStyle();
    style.setBackgroundType(BackgroundType.DEFAULT);
    style.setBorderType(BorderType.LEADING_LINE);
    style.setShowLabel(true);
    style.setIconSeparateLine(true);
    ToastOption option = Toast.createOption().setStyle(style);
    Toast.show(this, type, message, ToastLocation.TOP_CENTER, option);
  }
}
