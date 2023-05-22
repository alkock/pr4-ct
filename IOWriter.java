import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class IOWriter {
    public static int[][] readMarkdownFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        int rowCount = 0;
        int columnCount = 0;

        // Find the number of rows and columns in the markdown table
        while ((line = reader.readLine()) != null) {
            if (line.contains("|")) {
                String[] cells = line.split("\\|");
                columnCount = Math.max(columnCount, cells.length - 1); // Exclude the first and last empty cells
                rowCount++;
            }
        }

        reader.close();

        // Create the int[][] array with the correct dimensions
        int[][] data = new int[rowCount][columnCount];

        // Read the markdown table again to populate the data array
        reader = new BufferedReader(new FileReader(filePath));
        int rowIndex = 0;

        while ((line = reader.readLine()) != null) {
            if (line.contains("|")) {
                String[] cells = line.split("\\|");

                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    String cellValue = cells[columnIndex].trim();

                    // Parse only if the cell value is numeric
                    if (cellValue.matches("-?\\d+")) {
                        data[rowIndex][columnIndex - 1] = Integer.parseInt(cellValue);
                    }
                }

                rowIndex++;
            }
        }

        reader.close();

        int[][] trimmedArray = new int[data.length - 2][];
        System.arraycopy(data, 2, trimmedArray, 0, data.length - 2);
        printMatrix(trimmedArray);
        System.out.println("---");
        return trimmedArray;
    }


    public static int countOccurrences(String input, char target) {
        int count = 0;
        for (char c : input.toCharArray()) {
            if (c == target) {
                count++;
            }
        }
        return count;
    }

    public static void write(Map<Metrik, int[][]> errechneteMetriken, String dateiname) {

        String[][] ursprung = readMarkdownTable(dateiname);

        int counter = errechneteMetriken.size();

        System.out.println("counter: " + (counter));
        System.out.println("ursprung.length: " + (ursprung.length));

        String[][] ergebnis = new String[ursprung.length][ursprung[0].length + counter];
        for (int i = 0; i < ursprung.length; i++) {
            for (int j = 0; j < ursprung[i].length; j++) {
                ergebnis[i][j] = ursprung[i][j];
            }
        }

        System.out.println("neues Array ganz");

        IOWriter.printMatrix(ergebnis);


        int counter2 = 0;

        for (Metrik metrik : errechneteMetriken.keySet()) {
            String gegebeneMetrik = Metrik.toString(metrik);
            int[][] metrikArray = errechneteMetriken.get(metrik);
            System.out.println("---");
            IOWriter.printMatrix(metrikArray);
            ergebnis[0][ursprung[0].length + counter2] = gegebeneMetrik;
            ergebnis[1][ursprung[1].length + counter2] = "--";

            System.out.println("metrikArray.length: " + metrikArray.length);

            for (int i = 0; i <= metrikArray.length - 1; i++) {
                ergebnis[i + 2][ursprung[0].length + counter2] = Integer.toString(metrikArray[i][metrikArray[i].length - 1]);
            }
            ++counter2;
        }


        String resultdateiname = dateiname.replace(".md", "_result.md");
        writeMarkdownTableToFile(ergebnis, resultdateiname);


    }

    private static void printMatrix(String[][] ergebnis) {
        for (int i = 0; i < ergebnis.length; i++) {
            for (int j = 0; j < ergebnis[i].length; j++) {
                System.out.print(ergebnis[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void writeMarkdownTableToFile(String[][] data, String fileName) {
        System.out.println(data.length);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int row = 0; row < data.length; row++) {
                writer.write("|");
                for (String cell : data[row]) {
                    writer.write(" " + cell + " |");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("An error occurred while writing the file: " + e.getMessage());
        }
    }

    public static int countLines(String fileName) {
        int lineCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while (reader.readLine() != null) {
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineCount;
    }


    public static String[][] readMarkdownTable(String filePath) {
        List<String[]> lines = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.forEach(line -> {
                // Split the line at the pipe characters, trimming whitespace
                String[] parts = line.split("\\s*\\|\\s*");
                // Remove empty strings at the start and end, caused by leading/trailing pipe characters
                parts = Arrays.stream(parts)
                        .filter(part -> !part.isEmpty())
                        .toArray(String[]::new);
                lines.add(parts);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convert the list of lines to a 2D array
        String[][] table = new String[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            table[i] = lines.get(i);
        }

        return table;
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}


