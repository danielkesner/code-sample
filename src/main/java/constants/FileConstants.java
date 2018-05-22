package constants;

import java.io.File;

public class FileConstants {

    private static final File sourceDirectory = new File("src/main/resources/sourceData");
    private static final File targetDirectory = new File("src/main/resources/targetData");

    public static File getSourceDirectory() {
        return sourceDirectory;
    }

    public static File getTargetDirectory() {
        return targetDirectory;
    }
}
