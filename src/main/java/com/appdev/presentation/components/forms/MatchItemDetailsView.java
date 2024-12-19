package com.appdev.presentation.components.forms;

import com.appdev.logic.models.MatchItem;
import com.appdev.presentation.components.labels.DescriptionLabel;
import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import net.miginfocom.swing.MigLayout;

public class MatchItemDetailsView extends JScrollPane {
  private JPanel panel;

  public MatchItemDetailsView(MatchItem item) {
    panel = new JPanel(new MigLayout("fillx,wrap,insets 5 30 10 30, width 500", "[fill]", ""));
    setViewportView(panel);
    setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
    setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
    getVerticalScrollBar().setUnitIncrement(16);
    init(item);
  }

  private void init(MatchItem item) {
    createTitle("Match Item Information");
    panel.add(new DescriptionLabel("Verification ID: ", ""), "gapy 5 0");
    panel.add(new JLabel(item.getIdPhotoIcon(350, 350, 0)), "gapy 0 40");

    panel.add(new DescriptionLabel("On-site Photo: ", ""), "gapy 5 0");
    panel.add(new JLabel(item.getProfileIcon(350, 350, 0)), "gapy 0 40");
  }

  private void createTitle(String title) {
    JLabel lb = new JLabel(title);
    lb.putClientProperty(FlatClientProperties.STYLE, "font:+2");
    panel.add(lb, "gapy 5 0");
    panel.add(new JSeparator(), "height 2!,gapy 0 0");
  }
}
