package utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DateUtils {

    public static LocalDate readDate(String message) {

        while (true) {

            String input = InputUtils.readString(message);

            try {
                return LocalDate.parse(input);

            } catch (DateTimeParseException e) {
                System.out.println("Date invalide Utilisez le format yyyy-MM-dd");
            }
        }
    }
}
