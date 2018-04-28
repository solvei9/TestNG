package lesson1;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Task05Test {
    private Path tmpFolder;
    private File file;
    private File file1;

    @BeforeMethod(groups = "positive")
    public void setUp() throws IOException {
        tmpFolder = Files.createTempDirectory("TestNG");
    }

    @Test(groups = "positive")
    public void emptyFileCreateTest() throws IOException {
        file = new File(tmpFolder + "\\file.txt");
        Assert.assertTrue(file.createNewFile());
    }

    @Test(groups = "positive")
    public void sameFileCreateTest() throws IOException {
        file = new File(tmpFolder + "\\file.txt");
        SoftAssert s = new SoftAssert();
        s.assertTrue(file.createNewFile());
        s.assertFalse(file.createNewFile());
        s.assertAll();
    }

    @Test(groups = "positive")
    public void anotherFileCreateTest() throws IOException {
        file = new File(tmpFolder + "\\file.txt");
        file1 = new File(tmpFolder + "\\file1.txt");
        SoftAssert s = new SoftAssert();
        s.assertTrue(file.createNewFile());
        s.assertTrue(file1.createNewFile());
        s.assertAll();
    }

    @Test(groups = "negative")
    public void nullFileCreateTest() throws IOException{
        try {
            file = null;
            boolean result = file.createNewFile();
            Assert.fail("Exception has not received");
        }
        catch (Exception e) {
            Assert.assertEquals(e.getClass().toString(), "class java.lang.NullPointerException");
        }
    }

    @Test(groups = "negative")
    public void invalidNameFileCreateTest() throws IOException {
        try {
            file = new File(tmpFolder + "\\*");
            boolean result = file.createNewFile();
            Assert.fail("Exception has not received");
        }
        catch (IOException e) {
            Assert.assertEquals(e.getMessage(), "Синтаксическая ошибка в имени файла, имени папки или метке тома");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws IOException {
        if (file!=null) {
            boolean result = file.delete();
        }
        if (file1!=null) {
            boolean result = file1.delete();
        }
        Files.delete(tmpFolder);
    }
}