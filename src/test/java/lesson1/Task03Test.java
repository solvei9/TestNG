package lesson1;

import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Task03Test {
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
    }

    @Test(groups = "positive")
    public void sameFileCreateTest() throws IOException {
        file = new File(tmpFolder + "\\file.txt");
        file.createNewFile();
        file.createNewFile();
    }

    @Test(groups = "positive")
    public void anotherFileCreateTest() throws IOException {
        file = new File(tmpFolder + "\\file.txt");
        System.out.println(tmpFolder + "\\file.txt");
        file.createNewFile();
        file1 = new File(tmpFolder + "\\file1.txt");
        file1.createNewFile();
    }

    @Test(groups = "negative")
    public void nullFileCreateTest() throws IOException {
        file.createNewFile();
    }

    @Test(groups = "negative")
    public void invalidNameFileCreateTest() throws IOException {
        file = new File(tmpFolder + "\\*");
        file.createNewFile();
    }

    @AfterMethod
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