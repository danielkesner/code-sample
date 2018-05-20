package sort;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Record;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

public class FileSorter {

    private final File sourceDirectory = new File("src/main/resources/sourceData");
    private final File targetDirectory = new File("src/main/resources/targetData");
    private final File tmpDirectory = new File("src/main/resources/tmp");
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LogManager.getLogger(FileSorter.class);

    public boolean concat() throws IOException {

        for (File eachSourceFile : sourceDirectory.listFiles()) {

            JsonNode fileNode = objectMapper.readTree(eachSourceFile);
            if (fileNode == null) {
                return false;
            }
            Record record = new Record(fileNode);
            String userId = fileNode.get("user_id").asText();
            File userFile = new File(targetDirectory + "/" + userId + ".json");

            // If there isn't already a file for this user, create one and write initial "[" for JSON array body
            if (!userFile.exists()) {
                if (userFile.createNewFile()) {
                    FileUtils.writeStringToFile(userFile, "[" + System.getProperty("line.separator"),
                            Charset.defaultCharset(), true);
                } else {
                    return false;
                }
            }

            // Append individual record to user's file
            objectMapper.writeValue(new PrintWriter(
                    new BufferedWriter(new FileWriter(userFile, true))), record);
            // Make the file a little more pretty (end each array node with a comma and newline)
            FileUtils.writeStringToFile(userFile, "," + System.getProperty("line.separator"),
                    Charset.defaultCharset(), true);
        }

        // Write "]" to each output file to close the JSON array
        for (File eachOutputFile : targetDirectory.listFiles()) {
            FileUtils.writeStringToFile(eachOutputFile, "]", Charset.defaultCharset(), true);
        }
        return true;
    }

    public boolean sortByDate() {

        for (File eachFile : targetDirectory.listFiles()) {

        }

        return true;
    }

    public static void main(String... a) throws Exception {
        FileSorter sort = new FileSorter();
        sort.concat();
    }

}
