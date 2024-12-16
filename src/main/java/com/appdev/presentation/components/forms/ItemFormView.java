package com.appdev.presentation.components.forms;

import com.appdev.logic.models.FoundItem;
import com.appdev.logic.models.LostItem;
import com.appdev.presentation.components.labels.DescriptionLabel;
import com.formdev.flatlaf.FlatClientProperties;
import java.time.format.DateTimeFormatter;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import net.miginfocom.swing.MigLayout;

public class ItemFormView extends JScrollPane {
  private JPanel panel;

  public ItemFormView(LostItem item) {
    panel = new JPanel(new MigLayout("fillx,wrap,insets 5 30 5 30, width 500", "[fill]", ""));
    setViewportView(panel);
    setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
    setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
    init(item);
  }

  public ItemFormView(FoundItem item) {
    panel = new JPanel(new MigLayout("fillx,wrap,insets 5 30 5 30, width 500", "[fill]", ""));
    setViewportView(panel);
    setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
    setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
    init(item);
  }

  private void init(LostItem item) {
    createTitle("Lost Item Information");
    panel.add(new DescriptionLabel("Item Type: ", item.getItemType()), "gapy 5 0");
    panel.add(new DescriptionLabel("Item Subtype: ", item.getItemSubtype()), "gapy 5 0");
    panel.add(new DescriptionLabel("Item Description: ", item.getItemDescription()), "gapy 5 0");
    panel.add(
        new DescriptionLabel(
            "Where do you think you might have lost the item: ", item.getLocationDetails()),
        "gapy 5 0");
    panel.add(
        new DescriptionLabel(
            "When do you think you might have lost the item: ",
            item.getDateTimeLost().format(DateTimeFormatter.ofPattern("MMMM d, yyyy hh:mm a"))),
        "gapy 5 0");
    panel.add(new DescriptionLabel("Item Photo: ", ""), "gapy 5 0");
    panel.add(new JLabel(item.getImageIcon(350, 350, 0)), "gapy 0 10");

    createTitle("Reporter Information");
    panel.add(new DescriptionLabel("Name: ", item.getReporterName()), "gapy 5 0");
    panel.add(new DescriptionLabel("Email Address: ", item.getReporterEmail()), "gapy 5 0");
    panel.add(new DescriptionLabel("Phone Number: ", item.getReporterPhone()), "gapy 5 0");
  }

  private void init(FoundItem item) {
    createTitle("Found Item Information");
    panel.add(new DescriptionLabel("Item Type: ", item.getItemType()), "gapy 5 0");
    panel.add(new DescriptionLabel("Item Subtype: ", item.getItemSubtype()), "gapy 5 0");
    panel.add(new DescriptionLabel("Item Description: ", item.getItemDescription()), "gapy 5 0");
    panel.add(
        new DescriptionLabel("Where do did you find the item: ", item.getLocationDetails()),
        "gapy 5 0");
    panel.add(
        new DescriptionLabel(
            "When did you find the item: ",
            item.getDateTimeFound().format(DateTimeFormatter.ofPattern("MMMM d, yyyy hh:mm a"))),
        "gapy 5 0");
    panel.add(new DescriptionLabel("Item Photo: ", ""), "gapy 5 0");
    panel.add(new JLabel(item.getImageIcon(350, 350, 0)), "gapy 0 10");

    createTitle("Reporter Information");
    panel.add(new DescriptionLabel("Name: ", item.getReporterName()), "gapy 5 0");
    panel.add(new DescriptionLabel("Email Address: ", item.getReporterEmail()), "gapy 5 0");
    panel.add(new DescriptionLabel("Phone Number: ", item.getReporterPhone()), "gapy 5 0");
  }

  private void createTitle(String title) {
    JLabel lb = new JLabel(title);
    lb.putClientProperty(FlatClientProperties.STYLE, "font:+2");
    panel.add(lb, "gapy 5 0");
    panel.add(new JSeparator(), "height 2!,gapy 0 0");
  }
}
