package lesson1;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Task06Test {
    private Path tmpFolder;
    private File file;
    private File file1;

    @BeforeMethod
    public void setUp() throws IOException {
        tmpFolder = Files.createTempDirectory("TestNG");
    }

    @Test(dataProvider = "loadFilesFronFile")
    public void emptyFileCreateTest(String fileName) throws IOException {
        file = new File(tmpFolder + "\\" + fileName + ".txt");
        Assert.assertTrue(file.createNewFile());
    }

    @Test(dataProvider = "loadFilesFronFile")
    public void sameFileCreateTest(String fileName) throws IOException {
        file = new File(tmpFolder + "\\" + fileName + ".txt");
        SoftAssert s = new SoftAssert();
        s.assertTrue(file.createNewFile());
        s.assertFalse(file.createNewFile());
        s.assertAll();
    }

    @Test(dataProvider = "loadFilesFronFile")
    public void anotherFileCreateTest(String fileName) throws IOException {
        file = new File(tmpFolder + "\\" + fileName + ".txt");
        file1 = new File(tmpFolder + "\\" + fileName + "1.txt");
        SoftAssert s = new SoftAssert();
        s.assertTrue(file.createNewFile());
        s.assertTrue(file1.createNewFile());
        s.assertAll();
    }

    @DataProvider
    public Iterator<Object[]> files() {
        List<Object[]> data = new ArrayList<Object[]>();
        for (int i = 0; i < 5; i++) {
            data.add(new Object[]{
                    generateRandomFileName()
            });
        }
        return data.iterator();
    }

    @DataProvider
    public Iterator<Object[]> loadFilesFronFile() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                DataProvider.class.getResourceAsStream("/files.data")));
        List<Object[]> fileData = new ArrayList<Object[]>();
        String line = reader.readLine();
        while (line != null) {
            fileData.add( new Object[] {line});
            line = reader.readLine();
        }
        return fileData.iterator();
    }

    public String generateRandomFileName() {
        return "input" + new Random().nextInt();
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