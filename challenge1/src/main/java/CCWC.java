import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;
import java.nio.file.Files;

public class CCWC {

    public static long bytesCounter(Path filePath) throws IOException {
        try (FileChannel fileChannel = FileChannel.open(filePath)) {
            return fileChannel.size();
        }
    }

    public static int linesCounter(Path filePath) throws IOException {
        try (Stream<String> fileStream = Files.lines(filePath)) {
            return (int) fileStream.count();
        }
    }

    public static int wordsCounter(String filePath) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            int wordCount = 0;
            while (scanner.hasNext()) {
                scanner.next();
                wordCount++;
            }
            return wordCount;
        }
    }

    public static int charactersCounter(Path filePath) throws IOException {
        String fileContent = Files.readString(filePath, Charset.defaultCharset());
        return fileContent.length();
    }

    public static void myXCounter(String userInput, String filename) throws IOException {
        String regex = "ccwc\\s*-?([clwm])?\\s+(\\S+)";
        Scanner lineScanner = new Scanner(userInput);

        if (lineScanner.hasNextLine()) {
            String line = lineScanner.nextLine();

            if (line.matches(regex)) {
                Scanner matchScanner = new Scanner(line);
                matchScanner.findInLine(regex);
                String option = matchScanner.match().group(1);
                String extractedFilename = matchScanner.match().group(2).trim();
                Path filePath = Paths.get(extractedFilename);

                if (option == null) {
                    System.out.println(bytesCounter(filePath) + " " + linesCounter(filePath) + " " + wordsCounter(filePath.toString()) + " " + filename);
                } else {
                    switch (option) {
                        case "c" -> System.out.println(bytesCounter(filePath) + " " + filename);
                        case "l" -> System.out.println(linesCounter(filePath) + " " + filename);
                        case "w" -> System.out.println(wordsCounter(filePath.toString()) + " " + filename);
                        case "m" -> System.out.println(charactersCounter(filePath) + " " + filename);
                        case "" ->
                                System.out.println(bytesCounter(filePath) + " " + linesCounter(filePath) + " " + wordsCounter(filePath.toString()) + " " + filename);
                        default ->
                                System.out.println("ccwc: invalid option -- '" + option + "'\nTry 'ccwc --help' for more information");
                    }
                }
            } else {
                System.out.println("Invalid input format. Try 'ccwc --help' for more information");
            }
        }
        lineScanner.close();
    }
}
