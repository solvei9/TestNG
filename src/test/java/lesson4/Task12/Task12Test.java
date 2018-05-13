package lesson4.Task12;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Task12Test {
    private Path tmpFolder;
    private File file;

    @BeforeMethod
    public void setUp() throws IOException {
        tmpFolder = Files.createTempDirectory("TestNG");
    }

    @Test(enabled = true, dataProvider = "excelDataProvider", dataProviderClass = ExcelDataProvider.class)
    @ExcelDataSource(source = "src/test/resources/files.xls")
    public void emptyFileCreateTest(String fileName) throws IOException {
        file = new File(tmpFolder + "/" + fileName + ".txt");
        Assert.assertTrue(file.createNewFile());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws IOException {
        if (!(file==null)) {
            file.delete();
        }
        Files.delete(tmpFolder);
    }
}
