package com.appdev.presentation.pages;

import com.appdev.data.dao.FoundItemDAO;
import com.appdev.data.dao.LostItemDAO;
import com.appdev.logic.managers.StyleManager;
import com.appdev.logic.models.FoundItem;
import com.appdev.logic.models.LostItem;
import com.appdev.presentation.components.forms.ItemFormUpdate;
import com.appdev.presentation.components.forms.ItemFormView;
import com.appdev.presentation.components.table.SearchFilterDocumentListener;
import com.appdev.presentation.components.table.TableDateTimeCellRenderer;
import com.appdev.presentation.components.table.TableImageCellRenderer;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
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
  LostItemDAO lostItemDAO = new LostItemDAO();
  FoundItemDAO foundItemDAO = new FoundItemDAO();
  private int selectedLostItemId = -1;
  private int selectedFoundItemId = -1;
  private DefaultTableModel lostItemModel, foundItemModel;
  private JTable lostItemTable, foundItemTable;

  public AdminPage() {
    init();
  }

  private void init() {
    setLayout(new MigLayout("fillx,wrap", "[fill]", "[][fill,grow]"));
    add(createInfo("Admin Page", 1));
    add(createTab());
    add(createFooterAction());
  }

  private JPanel createInfo(String title, int level) {
    JPanel panel = new JPanel(new MigLayout("fillx,wrap", "[fill]"));
    JLabel lbTitle = new JLabel(title);
    lbTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +" + (4 - level));
    panel.add(lbTitle);
    return panel;
  }

  private Component createTab() {
    JTabbedPane tabb = new JTabbedPane();
    tabb.putClientProperty(FlatClientProperties.STYLE, "" + "tabType:card");
    tabb.addTab("Lost Items", lostItemTable());
    tabb.addTab("Found Items", foundItemTable());
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
    lostItemTable.getColumnModel().getColumn(7).setMinWidth(100);
    lostItemTable.getColumnModel().getColumn(7).setMaxWidth(100);

    lostItemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    lostItemTable.getTableHeader().setReorderingAllowed(false);

    // Custom Cell Renderers
    lostItemTable
        .getColumnModel()
        .getColumn(0)
        .setCellRenderer(
            new DefaultTableCellRenderer() {
              @Override
              protected void setValue(Object value) {
                super.setValue(value);
              }

              @Override
              public void setHorizontalAlignment(int alignment) {
                super.setHorizontalAlignment(SwingConstants.CENTER);
              }
            });
    lostItemTable.getColumnModel().getColumn(5).setCellRenderer(new TableDateTimeCellRenderer());
    lostItemTable
        .getColumnModel()
        .getColumn(7)
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

    // Actions
    lostItemModel.addTableModelListener(
        e -> {
          title.setText("Lost Items (" + lostItemTable.getRowCount() + ")");
        });

    lostItemTable.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            int selectedRow = lostItemTable.getSelectedRow();

            if (selectedRow != -1) {
              int id = (int) lostItemTable.getValueAt(selectedRow, 0);
              selectedLostItemId = id;
              System.out.println("Lost ID: " + selectedLostItemId);
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
            LostItem item = lostItemDAO.getLostItemById(selectedLostItemId);
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
            LostItem item = lostItemDAO.getLostItemById(selectedLostItemId);
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

    for (LostItem item : lostItemDAO.getAllLostItems()) {
      lostItemModel.addRow(
          new Object[] {
            item.getLostItemId(),
            item.getItemType(),
            item.getItemSubtype(),
            item.getItemDescription(),
            item.getLocationDetails(),
            item.getDateTimeLost(),
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
    foundItemTable.getColumnModel().getColumn(7).setMinWidth(100);
    foundItemTable.getColumnModel().getColumn(7).setMaxWidth(100);

    foundItemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    foundItemTable.getTableHeader().setReorderingAllowed(false);

    // Custom Cell Renderers
    foundItemTable
        .getColumnModel()
        .getColumn(0)
        .setCellRenderer(
            new DefaultTableCellRenderer() {
              @Override
              protected void setValue(Object value) {
                super.setValue(value);
              }

              @Override
              public void setHorizontalAlignment(int alignment) {
                super.setHorizontalAlignment(SwingConstants.CENTER);
              }
            });
    foundItemTable.getColumnModel().getColumn(5).setCellRenderer(new TableDateTimeCellRenderer());
    foundItemTable
        .getColumnModel()
        .getColumn(7)
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

    // Actions
    foundItemModel.addTableModelListener(
        e -> {
          title.setText("Found Items (" + foundItemTable.getRowCount() + ")");
        });

    foundItemTable.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            int selectedRow = foundItemTable.getSelectedRow();

            if (selectedRow != -1) {
              int id = (int) foundItemTable.getValueAt(selectedRow, 0);
              selectedFoundItemId = id;
              System.out.println("Found ID: " + selectedFoundItemId);
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
            FoundItem item = foundItemDAO.getFoundItemById(selectedFoundItemId);
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
            FoundItem item = foundItemDAO.getFoundItemById(selectedFoundItemId);
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

    for (FoundItem item : foundItemDAO.getAllFoundItems()) {
      foundItemModel.addRow(
          new Object[] {
            item.getFoundItemId(),
            item.getItemType(),
            item.getItemSubtype(),
            item.getItemDescription(),
            item.getLocationDetails(),
            item.getDateTimeFound(),
            item.getStatus().toString(),
            item.getImageIcon(80, 80, 3f)
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
                "Please select a row on both\ntables first before matching",
                "Matching Error",
                JOptionPane.ERROR_MESSAGE);
          } else {
            System.out.println("~~~~~~~~~~~~~");
            System.out.println("MATCHED: ");
            System.out.println("Lost Item ID: " + selectedLostItemId);
            System.out.println("Found Item ID: " + selectedFoundItemId);
            System.out.println();
          }
        });

    panel.add(infoButton);
    panel.add(matchButton);

    return panel;
  }

  private void refreshLostItemTable(DefaultTableModel model, JTable table) {
    // Clear existing rows
    model.setRowCount(0);

    // Re-populate the table with updated data
    for (LostItem item : lostItemDAO.getAllLostItems()) {
      model.addRow(
          new Object[] {
            item.getLostItemId(),
            item.getItemType(),
            item.getItemSubtype(),
            item.getItemDescription(),
            item.getLocationDetails(),
            item.getDateTimeLost(),
            item.getStatus().toString(),
            item.getImageIcon(80, 80, 3f)
          });
    }

    table.getTableHeader().repaint();
    table.repaint();
  }

  private void refreshFoundItemTable(DefaultTableModel model, JTable table) {
    // Clear existing rows
    model.setRowCount(0);

    // Re-populate the table with updated data
    for (FoundItem item : foundItemDAO.getAllFoundItems()) {
      model.addRow(
          new Object[] {
            item.getFoundItemId(),
            item.getItemType(),
            item.getItemSubtype(),
            item.getItemDescription(),
            item.getLocationDetails(),
            item.getDateTimeFound(),
            item.getStatus().toString(),
            item.getImageIcon(80, 80, 3f)
          });
    }

    table.getTableHeader().repaint();
    table.repaint();
  }

  private void showItemFormViewModal(LostItem item) {
    Option option = ModalDialog.createOption().setBackgroundClickType(BackgroundClickType.BLOCK);
    option
        .getLayoutOption()
        .setSize(-1, 1f)
        .setLocation(Location.CENTER, Location.TOP)
        .setAnimateDistance(0.7f, 0);

    SimpleModalBorder modal = new SimpleModalBorder(new ItemFormView(item), "View");
    ModalDialog.showModal(this, modal, option);
  }

  private void showItemFormViewModal(FoundItem item) {
    Option option = ModalDialog.createOption().setBackgroundClickType(BackgroundClickType.BLOCK);
    option
        .getLayoutOption()
        .setSize(-1, 1f)
        .setLocation(Location.CENTER, Location.TOP)
        .setAnimateDistance(0.7f, 0);

    SimpleModalBorder modal = new SimpleModalBorder(new ItemFormView(item), "View");
    ModalDialog.showModal(this, modal, option);
  }

  private void showItemFormUpdateModal(LostItem item) {
    ItemFormUpdate form = new ItemFormUpdate(item);

    Option option = ModalDialog.createOption().setBackgroundClickType(BackgroundClickType.BLOCK);
    option
        .getLayoutOption()
        .setSize(-1, 1f)
        .setLocation(Location.CENTER, Location.TOP)
        .setAnimateDistance(0.7f, 0);

    SimpleModalBorder modal =
        new SimpleModalBorder(
            form,
            "Update",
            SimpleModalBorder.OK_CANCEL_OPTION,
            (controller, action) -> {
              if (action == SimpleModalBorder.OK_OPTION) {
                if (form.updateLostItem(item)) {
                  refreshLostItemTable(lostItemModel, lostItemTable);
                  showToast(Toast.Type.SUCCESS, "Lost Item Updated successfully");
                } else {
                  // showToast(Toast.Type.ERROR, "Please put a proper values in the form");
                  controller.consume();
                }
              } else if (action == SimpleModalBorder.CANCEL_OPTION) {
                System.out.println("Clicked CANCEL");
              }
            });

    ModalDialog.showModal(this, modal, option);
  }

  private void showItemFormUpdateModal(FoundItem item) {
    ItemFormUpdate form = new ItemFormUpdate(item);

    Option option = ModalDialog.createOption().setBackgroundClickType(BackgroundClickType.BLOCK);
    option
        .getLayoutOption()
        .setSize(-1, 1f)
        .setLocation(Location.CENTER, Location.TOP)
        .setAnimateDistance(0.7f, 0);

    SimpleModalBorder modal =
        new SimpleModalBorder(
            form,
            "Update",
            SimpleModalBorder.OK_CANCEL_OPTION,
            (controller, action) -> {
              if (action == SimpleModalBorder.OK_OPTION) {
                if (form.updateFoundItem(item)) {
                  refreshFoundItemTable(foundItemModel, foundItemTable);
                  showToast(Toast.Type.SUCCESS, "Found Item Updated successfully");
                } else {
                  // showToast(Toast.Type.ERROR, "Please put a proper values in the form");
                  controller.consume();
                }
              } else if (action == SimpleModalBorder.CANCEL_OPTION) {
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
