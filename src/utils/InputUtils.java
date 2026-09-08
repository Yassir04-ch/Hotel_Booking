package utils;
import java.util.Scanner;

public class InputUtils {

    public static String readString(String message) {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(message);

            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("Vide");
        }
    }

    public static int readInt(String message) {

        while (true) {

            System.out.print(message);

            Scanner scanner = new Scanner(System.in);

            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }
}
