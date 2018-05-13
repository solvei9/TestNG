package lesson4.Task12;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.testng.annotations.DataProvider;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelDataProvider {
    @DataProvider
    public Iterator<Object[]> excelDataProvider(Method m) throws IOException {
        List<Object[]> sourceData = new ArrayList<Object[]>();
        if (m.isAnnotationPresent(ExcelDataSource.class)) {
            String source = m.getAnnotation(ExcelDataSource.class).source();
            try {
                Workbook workbook = WorkbookFactory.create(new File(source));
                Sheet sheet = workbook.getSheetAt(0);
                for (Row row: sheet) {
                    for(Cell cell: row) {
                        sourceData.add( new Object[] {cell.getStringCellValue()});
                    }
                }
                workbook.close();
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            }
        }
        return sourceData.iterator();
    }
}
