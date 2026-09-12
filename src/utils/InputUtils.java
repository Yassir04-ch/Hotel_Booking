package utils;
import java.math.BigDecimal;
import java.util.Scanner;

public class InputUtils {

    private static Scanner scanner = new Scanner(System.in);

    public static String readString(String message) {

        while (true) {
            System.out.println(message);

            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("Vide");
        }
    }

    public static int readInt(String message) {

        while (true) {

            System.out.println(message);

            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Entrer une nombre."
                );
            }
        }
    }

    public static BigDecimal readBigDecimal(String message) {

        while (true) {

            System.out.println(message);

            String input = scanner.nextLine().trim();

            try {
                return new BigDecimal(input);

            } catch (NumberFormatException e) {

                System.out.println("Prix invalide.");
            }
        }
    }
}
