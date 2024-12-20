package com.appdev.logic.services;

import com.appdev.logic.models.FoundItem;
import com.appdev.logic.models.LostItem;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import org.tinylog.Logger;

public class EmailService {
  private static final Dotenv dotenv = Dotenv.load();
  private static final String SENDER_EMAIL = dotenv.get("SENDER_EMAIL");
  private static final String SENDGRID_API_KEY = dotenv.get("SENDGRID_API_KEY");
  private static final String LOST_ITEM_TEMPLATE_ID = dotenv.get("LOST_ITEM_TEMPLATE_ID");
  private static final String FOUND_ITEM_TEMPLATE_ID = dotenv.get("FOUND_ITEM_TEMPLATE_ID");
  private boolean canSendEmail;
  private SendGrid sendGridClient;

  public EmailService() {
    this.canSendEmail = hasEnvVariables();
    this.sendGridClient = new SendGrid(SENDGRID_API_KEY);
  }

  /**
   * Sends an email notification for a reported lost item.
   *
   * @param item the lost item containing reporter details and email information
   * @throws IOException if an error occurs while sending the email
   * @throws IllegalStateException if the email service is not properly configured
   */
  public void sendEmail(LostItem item) throws IOException, IllegalStateException {
    if (!canSendEmail) {
      Logger.error("Email service is not configured properly. Missing environment variables.");
      throw new IllegalStateException(
          "Email service is not configured properly. Missing environment variables.");
    }
    Mail mail = new Mail();
    Email from = new Email(SENDER_EMAIL);
    Email to = new Email(item.getReporterEmail());

    mail.setFrom(from);
    mail.setTemplateId(LOST_ITEM_TEMPLATE_ID);

    Personalization personalization = new Personalization();
    personalization.addTo(to);
    personalization.addDynamicTemplateData("reporterName", item.getReporterName());

    sendMail(mail);
  }

  /**
   * Sends an email notification for a reported found item.
   *
   * @param item the found item containing reporter details and email information
   * @throws IOException if an error occurs while sending the email
   * @throws IllegalStateException if the email service is not properly configured
   */
  public void sendEmail(FoundItem item) throws IOException, IllegalStateException {
    if (!canSendEmail) {
      Logger.error("Email service is not configured properly. Missing environment variables.");
      throw new IllegalStateException(
          "Email service is not configured properly. Missing environment variables.");
    }

    Mail mail = new Mail();
    Email from = new Email(SENDER_EMAIL);
    Email to = new Email(item.getReporterEmail());

    mail.setFrom(from);
    mail.setTemplateId(FOUND_ITEM_TEMPLATE_ID);

    Personalization personalization = new Personalization();
    personalization.addTo(to);
    personalization.addDynamicTemplateData("reporterName", item.getReporterName());

    sendMail(mail);
  }

  private void sendMail(Mail mail) throws IOException {
    Request request = new Request();
    request.setMethod(Method.POST);
    request.setEndpoint("mail/send");
    request.setBody(mail.build());
    Response response = sendGridClient.api(request);

    if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
      Logger.info("Email sent successfully!");
    } else {
      Logger.error("Failed to send email: {}", response.getBody());
    }
  }

  private boolean hasEnvVariables() {
    boolean status = true;

    if (SENDGRID_API_KEY == null || SENDGRID_API_KEY.isEmpty()) {
      System.err.println("Warning: SENDGRID_API_KEY is missing from the .env file.");
      status = false;
    }

    if (LOST_ITEM_TEMPLATE_ID == null || LOST_ITEM_TEMPLATE_ID.isEmpty()) {
      System.err.println("Warning: LOST_ITEM_TEMPLATE_ID is missing from the .env file.");
      status = false;
    }

    if (FOUND_ITEM_TEMPLATE_ID == null || FOUND_ITEM_TEMPLATE_ID.isEmpty()) {
      System.err.println("Warning: FOUND_ITEM_TEMPLATE_ID is missing from the .env file.");
      status = false;
    }

    return status;
  }
}
