package application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import constants.FileConstants;
import model.Record;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import rule.MoreThanTenKmPerWeekRule;
import rule.RanForThreeConsecutiveDaysRule;
import rule.SetNewRecordForLongestRunRule;
import sort.FileSorter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ApplicationLogic {

    private static ObjectNode output;
    private static ObjectMapper objectMapper = new ObjectMapper();

    // Usage: java Application <userId>
    public static void run(String[] programArgs) throws IOException {

        if (! FileConstants.getTargetDirectory().exists()) {
            FileConstants.getTargetDirectory().mkdir();
        }

        if (! FileConstants.getSourceDirectory().exists()) {
            FileConstants.getSourceDirectory().mkdir();
        }

        if (programArgs == null || programArgs.length == 0) {
            throw new IllegalArgumentException("You must specify a user id!");
        }

        // Clear the contents of the target directory folder on each run
        FileUtils.cleanDirectory(FileConstants.getTargetDirectory());

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
        int numberOfNewPersonalBestsForLongestRun = new SetNewRecordForLongestRunRule().satisfiesRule(sortedRecords);

        output = objectMapper.createObjectNode();
        output.put("user_id", userId);
        output.put("ThreeConsecutiveDays", ranForThreeConsecutiveDays);
        output.put("MoreThanTenKmInAWeek", moreThanTenKmPerWeek);
        output.put("BeatPreviousPersonalRecordForLongestRun", numberOfNewPersonalBestsForLongestRun);

        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(output));
    }

}
