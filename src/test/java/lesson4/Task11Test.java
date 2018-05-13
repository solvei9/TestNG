package lesson4;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.HashSet;
import java.util.Set;

public class Task11Test {
    private Path tmpFolder;
    private File file;

    @BeforeMethod
    public void setUp(Method m) throws IOException {
        if (m.getAnnotation(TempDir.class) != null) {
            Set<PosixFilePermission> permissions = new HashSet<>();
            if (m.getAnnotation(TempDir.class).read()) {
                permissions.add(PosixFilePermission.OWNER_READ);
                permissions.add(PosixFilePermission.GROUP_READ);
                permissions.add(PosixFilePermission.OTHERS_READ);
            }

            if (m.getAnnotation(TempDir.class).write()) {
                permissions.add(PosixFilePermission.OWNER_WRITE);
                permissions.add(PosixFilePermission.GROUP_WRITE);
                permissions.add(PosixFilePermission.OTHERS_WRITE);
            }
            FileAttribute<Set<PosixFilePermission>> fileAttributes = PosixFilePermissions.asFileAttribute(permissions);
            tmpFolder = Files.createTempDirectory("TestNG", fileAttributes);
        } else {
            tmpFolder = Files.createTempDirectory("TestNG");
        }
    }

    @Test
    public void emptyFileCreateTest() throws IOException {
        file = new File(tmpFolder + "/file.txt");
        SoftAssert s = new SoftAssert();
        s.assertTrue(file.createNewFile());
        s.assertTrue(file.exists());
        s.assertAll();
    }

    @Test
    @TempDir(read = true, write = false)
    public void cannotCreateFileInAReadOnlyDir() {
        try {
            file = new File(tmpFolder + "/file.txt");
            boolean result = file.createNewFile();
            Assert.fail("Exception has not received");
        }
        catch (IOException e) {
            Assert.assertEquals(e.getMessage(), "Permission denied");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws IOException {
        if (file!=null) {
            boolean result = file.delete();
        }
        Files.delete(tmpFolder);
    }
}