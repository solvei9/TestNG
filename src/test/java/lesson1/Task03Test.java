package lesson1;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Task03Test {
    private Path tmpFolder;
    private File file;
    private File file1;

    @BeforeMethod
    public void setUp() throws IOException {
        tmpFolder = Files.createTempDirectory("TestNG");
    }

    @Test
    public void emptyFileCreateTest() throws IOException {
        file = new File(tmpFolder + "\\file.txt");
        file.createNewFile();
    }

    @Test
    public void sameFileCreateTest() throws IOException {
        file = new File(tmpFolder + "\\file.txt");
        file.createNewFile();
        file.createNewFile();
    }

    @Test
    public void anotherFileCreateTest() throws IOException {
        file = new File(tmpFolder + "\\file.txt");
        file.createNewFile();
        file1 = new File(tmpFolder + "\\file1.txt");
        file1.createNewFile();
    }

    @Test
    public void nullFileCreateTest() throws IOException {
        file.createNewFile();
    }

    @AfterMethod
    public void tearDown() throws IOException {
        file.delete();
        file1.delete();
        Files.deleteIfExists(tmpFolder);
    }
}