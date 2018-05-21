package sort;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Record;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileSorter {

    private final File sourceDirectory = new File("src/main/resources/sourceData");
    private final File targetDirectory = new File("src/main/resources/targetData");
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

    // Returns a list of Records in descending order by start time (newest record first)
    public List<Record> sortByDate(File targetDataFile) throws IOException {

        List<Record> list = new ArrayList<Record>();
        BufferedReader reader;
        String line;

        try {
            reader = new BufferedReader(new FileReader(targetDataFile));
        } catch (IOException ioe) {
            logger.info(ioe);
            throw new RuntimeException("Couldn't initialize BufferedReader for " + targetDataFile.getAbsolutePath());
        }

        while ((line = reader.readLine()) != null) {
            // Read each record into a JsonNode, then convert to Record object and add to list
            if (! line.startsWith("[") && !line.startsWith("]")) {
                list.add(new Record(objectMapper.readTree(line.substring(0, line.length() - 1))));
            }
        }

        Collections.sort(list, new Record());

        return list;
    }

//        public static void main (String...a) throws Exception {
//            FileSorter sort = new FileSorter();
//            sort.sortByDate(new File("src/main/resources/targetData/6bd5f3c04e6b5279aca633c2a245dd9c.json"));
//        }

    }
