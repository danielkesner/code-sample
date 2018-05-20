package daniel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.HashSet;

public class INeedData {

    private static  final File sourceDirectory = new File("src/main/resources/sourceData");
    private static final Logger logger = LogManager.getLogger(INeedData.class);
    static HashSet<String> z = new HashSet<String>();

    public static void main(String...a) throws Exception {

        // How many files are we working with? 930
        logger.info("Number of files: " + sourceDirectory.listFiles().length);

        // Number of unique IDs? 6
        for (File each : sourceDirectory.listFiles()) {
            JsonNode fileNode = new ObjectMapper().readTree(each);
            String userId = fileNode.get("user_id").asText();
            if (!z.contains(userId)) {
                z.add(userId);
            }
        }
        logger.info("Unique user IDs: " + z.size());

    }
}
