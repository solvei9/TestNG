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
        file.createNewFile();
        Assert.assertTrue(file.exists());
    }

    @Test(groups = "positive")
    public void sameFileCreateTest() throws IOException {
        file = new File(tmpFolder + "\\file.txt");
        file.createNewFile();
        file.createNewFile();
        Assert.assertTrue(file.exists());
    }

    @Test(groups = "positive")
    public void anotherFileCreateTest() throws IOException {
        file = new File(tmpFolder + "\\file.txt");
        file.createNewFile();
        file1 = new File(tmpFolder + "\\file1.txt");
        file1.createNewFile();
        SoftAssert s = new SoftAssert();
        s.assertTrue(file.exists());
        s.assertTrue(file1.exists());
        s.assertAll();
    }

    @Test(groups = "negative")
    public void nullFileCreateTest() throws IOException{
        try {
            file = null;
            file.createNewFile();
        }
        catch (Exception e) {
            Assert.assertEquals(e.getClass().toString(), "class java.lang.NullPointerException");
        }
    }

    @Test(groups = "negative")
    public void invalidNameFileCreateTest() throws IOException {
        try {
            file = new File(tmpFolder + "\\*");
            file.createNewFile();
        }
        catch (IOException e) {
            Assert.assertEquals(e.getMessage(), "Синтаксическая ошибка в имени файла, имени папки или метке тома");
            Assert.assertFalse(file.exists());
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws IOException {
        if (!(file==null)) {
            file.delete();
        }
        if (!(file1==null)) {
            file1.delete();
        }
        Files.delete(tmpFolder);
    }
}