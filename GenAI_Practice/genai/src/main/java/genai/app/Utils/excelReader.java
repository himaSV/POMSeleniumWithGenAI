package genai.app.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;

public class excelReader {

    private Workbook workbook;

    public excelReader(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(new File(filePath));
        workbook = WorkbookFactory.create(fis);
    }

    public String getCellData(String sheetName, int rowNum, int colNum) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) return null;
        Row row = sheet.getRow(rowNum);
        if (row == null) return null;
        Cell cell = row.getCell(colNum);
        if (cell == null) return null;
        return cell.toString();
    }

    public void close() throws IOException {
        if (workbook != null) workbook.close();
    }
}
