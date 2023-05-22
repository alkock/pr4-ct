import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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

    public static void main(String args[])
    {
        write(null, "exercise1.md");
    }
    public static void write(Map<Metrik, int[][]> errechneteMetriken, String dateiname) {

        try (BufferedReader br = new BufferedReader(new FileReader(dateiname))) {
            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null && lineNumber < 2) {
                System.out.println(line);
                lineNumber++;
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Lesen der Datei: " + e.getMessage());
        }





    }
}


