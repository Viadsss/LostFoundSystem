package com.appdev.presentation.pages;

import com.appdev.logic.managers.PageManager;
import com.appdev.logic.models.FoundItem;
import com.appdev.logic.models.LostItem;
import com.appdev.logic.models.MatchItem;
import com.appdev.logic.services.EmailService;
import com.appdev.logic.services.ItemService;
import com.appdev.presentation.components.forms.ItemFormView;
import com.appdev.presentation.components.forms.MatchItemFormVerify;
import com.formdev.flatlaf.FlatClientProperties;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import net.miginfocom.swing.MigLayout;
import org.tinylog.Logger;
import raven.modal.Toast;
import raven.modal.Toast.Type;
import raven.modal.toast.option.ToastLocation;
import raven.modal.toast.option.ToastOption;
import raven.modal.toast.option.ToastStyle;
import raven.modal.toast.option.ToastStyle.BackgroundType;
import raven.modal.toast.option.ToastStyle.BorderType;

public class VerificationPage extends JPanel {
  private ItemService itemService = new ItemService();
  private EmailService emailService = new EmailService();
  private JButton approveButton, unmatchButton, cancelButton;

  public VerificationPage(MatchItem item) {
    setLayout(new MigLayout("fillx,wrap,insets 5 30 10 30", "[fill]", ""));
    init(item);
  }

  private void init(MatchItem item) {
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    MatchItemFormVerify verifyPane = new MatchItemFormVerify(item);

    createTitle("Item Verification");
    splitPane.setLeftComponent(itemViewPanel(item));
    splitPane.setRightComponent(verifyPane);
    splitPane.setOneTouchExpandable(true);
    splitPane.setContinuousLayout(true);
    splitPane.setResizeWeight(0.5);
    splitPane.setDividerSize(10);

    approveButton = new JButton("Approve");
    unmatchButton = new JButton("Unmatch");
    cancelButton = new JButton("Cancel");

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(approveButton);
    buttonPanel.add(unmatchButton);
    buttonPanel.add(cancelButton);

    cancelButton.putClientProperty(
        FlatClientProperties.STYLE, "font: +2 bold; margin: 8, 12, 8, 12;");
    unmatchButton.putClientProperty(
        FlatClientProperties.STYLE, "font: +2 bold; margin: 8, 12, 8, 12;");
    approveButton.putClientProperty(
        FlatClientProperties.STYLE,
        ""
            + "font: +2 bold;"
            + "margin: 8, 12, 8, 12;"
            + "background: #741B13;"
            + "borderColor: #741B13;"
            + "borderWidth: 1;"
            + "focusColor: #74211380;"
            + "focusedBorderColor: #681E118D;"
            + "foreground: #F6F6F6;"
            + "hoverBackground: #811E15;"
            + "hoverBorderColor: #681E118D;"
            + "pressedBackground: #A0251A");

    add(splitPane);
    add(buttonPanel, "gapleft push");

    approveButton.addActionListener(
        e -> {
          int result =
              JOptionPane.showConfirmDialog(
                  this,
                  "Are you sure you want to approve this match?",
                  "Confirmation",
                  JOptionPane.YES_NO_OPTION);
          if (result == JOptionPane.YES_OPTION) {
            if (verifyPane.verifyMatchItem(item)) {
              showToast(Toast.Type.SUCCESS, "Match Item Resolved Successfully");
              FoundItem foundItem = itemService.getFoundItem(item.getFoundItemId());
              sendEmail(foundItem);
              PageManager.getInstance().showPage(new AdminPage(true));
            }
          }
        });

    unmatchButton.addActionListener(
        e -> {
          int result =
              JOptionPane.showConfirmDialog(
                  this,
                  "Are you sure you want to unmatch this item?",
                  "Confirmation",
                  JOptionPane.YES_NO_OPTION);
          if (result == JOptionPane.YES_OPTION) {
            itemService.deleteMatchItem(item);
            showToast(Toast.Type.SUCCESS, "Unmatch The Item Successfully");
            PageManager.getInstance().showPage(new AdminPage(true));
          }
        });

    cancelButton.addActionListener(
        e -> {
          PageManager.getInstance().showPage(new AdminPage(true));
        });
  }

  private JTabbedPane itemViewPanel(MatchItem item) {
    LostItem lostItem = itemService.getLostItem(item.getLostItemId());
    FoundItem foundItem = itemService.getFoundItem(item.getFoundItemId());

    JTabbedPane pane = new JTabbedPane();
    pane.putClientProperty(FlatClientProperties.STYLE, "" + "tabType:card");
    pane.addTab("Lost Item", new ItemFormView(lostItem));
    pane.addTab("Found Item", new ItemFormView(foundItem));

    return pane;
  }

  private void createTitle(String title) {
    JLabel lb = new JLabel(title);
    lb.putClientProperty(FlatClientProperties.STYLE, "font:+3");
    add(lb, "gapy 5 0");
    add(new JSeparator(), "height 2!,gapy 0 0");
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

  private void sendEmail(FoundItem foundItem) {
    try {
      // Call to the email service to send the email for a lost item
      emailService.sendEmail(foundItem);
    } catch (IOException e) {
      // Handle the IOException (e.g., network issues, failure to send email)
      showToast(Type.ERROR, "An error occurred while sending the email");
      Logger.error("Error occurred while sending the email: {}", e.getMessage());
    } catch (IllegalStateException e) {
      // Handle the IllegalStateException (e.g., missing configuration or environment variables)
      showToast(Type.WARNING, "Email service is not properly configured. Please contact support.");
      Logger.error("Email service is not properly configured: {}", e.getMessage());
    }
  }
}
