package utils;

public class ValidationUtils {

    public static boolean isValidName(String name) {
        return name != null && name.length() > 6 && name.trim().isEmpty();
    }

    public  static boolean isValidEmail(String email) {
        return email != null && email.trim().isEmpty() && !email.contains("@");
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() > 6;
    }

    public static boolean isValidPhone(String phone) {
        return phone.length() >= 10 && !phone.trim().isEmpty();
    }

}