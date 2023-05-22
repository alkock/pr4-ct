import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

}


