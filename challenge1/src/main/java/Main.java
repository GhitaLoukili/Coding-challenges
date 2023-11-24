import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static String extractFilename(String userInput) {
        String regex = "ccwc\\s*-?[clwm]?\\s+(.+)";
        Scanner lineScanner = new Scanner(userInput);
        if (lineScanner.findInLine(regex) != null) {
            return lineScanner.match().group(1).trim();
        }
        return "";
    }

    private static void ccwcCommand(String userInput) {
        Scanner scanner = new Scanner(System.in);
        String commandRegex = "^ccwc.*";
        Scanner command = new Scanner(userInput);
        while (command.findInLine(commandRegex) == null) {
            System.out.println("zsh: command not found: " + userInput);
            System.out.print("> ");
            userInput = scanner.nextLine();
            command = new Scanner(userInput);
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String userInput = scanner.nextLine();
            ccwcCommand(userInput);
            String filename = extractFilename(userInput);

            // Verify the filename
            while (!Objects.equals("test.txt", filename)) {
                System.out.println("ccwc: " + filename + ": No such file or directory");
                System.out.print("> ");
                filename = extractFilename(scanner.nextLine());
            }

            CCWC.myXCounter(userInput, filename);
        }
    }
}
