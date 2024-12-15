package com.appdev.logic.validations;

public class ReporterValidator {
  // Validate Name (e.g., not null, no numbers, and within length constraints)
  public static boolean isName(String name) {
    return name != null
        && name.matches("[a-zA-Z\\s]+")
        && name.length() >= 2
        && name.length() <= 50;
  }

  // Validate Email (e.g., standard email format, within length constraints)
  public static boolean isEmail(String email) {
    return email != null
        && email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")
        && email.length() <= 50;
  }

  // Validate Phone (e.g., 11 digit numbers)
  public static boolean isPhone(String phone) {
    return phone != null && phone.matches("\\d{11}");
  }
}
