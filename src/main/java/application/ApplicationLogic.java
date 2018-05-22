package application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.Record;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rule.MoreThanTenKmPerWeekRule;
import rule.RanForThreeConsecutiveDaysRule;
import sort.FileSorter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ApplicationLogic {

    static ObjectNode output;
    static ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LogManager.getLogger(ApplicationLogic.class);

    // Usage: java Application <userId> [optional] -v
    public static void run(String[] programArgs) throws IOException {

        if (programArgs == null || programArgs.length == 0) {
            throw new IllegalArgumentException("You must specify at least one user id!");
        }

        String userId = programArgs[0];

        FileSorter sorter = new FileSorter();
        if (!sorter.concat(userId)) {
            throw new RuntimeException("FileSorter.concat() call failed for user id: " + userId);
        }

        List<Record> sortedRecords = sorter.sortByDate(new File(
                "src/main/resources/targetData/" + userId + ".json"
        ));

        int ranForThreeConsecutiveDays = new RanForThreeConsecutiveDaysRule().satisfiesRule(sortedRecords);
        int moreThanTenKmPerWeek = new MoreThanTenKmPerWeekRule().satisfiesRule(sortedRecords);

        output = objectMapper.createObjectNode();
        output.put("user_id", userId);
        output.put("ThreeConsecutiveDays", ranForThreeConsecutiveDays);
        output.put("MoreThanTenKmInAWeek", moreThanTenKmPerWeek);

        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(output));
    }

}
