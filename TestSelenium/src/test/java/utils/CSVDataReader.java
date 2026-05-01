package utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVDataReader {

    public static Object[][] getTestData(String filePath) throws IOException, CsvException {
        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            List<String[]> allData = csvReader.readAll();

            // Skip header row
            Object[][] data = new Object[allData.size() - 1][1];

            for (int i = 1; i < allData.size(); i++) {
                data[i - 1][0] = allData.get(i);   // pass entire row as array
            }
            return data;
        }
    }
}