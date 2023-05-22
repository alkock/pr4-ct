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



        String[][] ergebnis = new String[ursprung.length][ursprung[0].length + counter];
        for (int i = 0; i < ursprung.length; i++) {
            for (int j = 0; j < ursprung[i].length; j++) {
                ergebnis[i][j] = ursprung[i][j];
            }
        }




        int counter2 = 0;

        for (Metrik metrik : errechneteMetriken.keySet()) {
            String gegebeneMetrik = Metrik.toString(metrik);
            int[][] metrikArray = errechneteMetriken.get(metrik);

            ergebnis[0][ursprung[0].length + counter2] = gegebeneMetrik;
            ergebnis[1][ursprung[1].length + counter2] = "--";


            for (int i = 0; i <= metrikArray.length - 1; i++) {
                ergebnis[i + 2][ursprung[0].length + counter2] = Integer.toString(metrikArray[i][metrikArray[i].length - 1]);
            }
            ++counter2;
        }


        String resultdateiname = dateiname.replace(".md", "_result.md");
        writeMarkdownTableToFile(ergebnis, resultdateiname);


    }


    public static void writeMarkdownTableToFile(String[][] data, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int row = 0; row < data.length; row++) {
                writer.write("|");
                for (String cell : data[row]) {
                    writer.write(" " + cell + " |");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}


