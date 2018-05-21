import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Record;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class ComparatorUnitTests {

    Logger logger = LogManager.getLogger(ComparatorUnitTests.class);

    @Test
    void fourDifferentYears() throws Exception {
        ArrayList<Record> list = new ArrayList<Record>();
        ObjectMapper mapper = new ObjectMapper();

        // 2016-07-09T00:41:03.927000Z
        JsonNode one = mapper.readTree(new File("src/main/resources/sourceData/0a3e4286711e55349eecdf914716ec5a.json"));
        Record r1 = new Record(one);

        // 2018-01-17T05:15:24.743000Z
        JsonNode two = mapper.readTree(new File("src/main/resources/sourceData/0a04e01b77965cbf99217809dd803a77.json"));
        Record r2 = new Record(two);

        // 2017-06-07T18:27:15Z
        JsonNode three = mapper.readTree(new File("src/main/resources/sourceData/0a59f13574b957f399b15b5d6a69fb2e.json"));
        Record r3 = new Record(three);

        // 2016-12-30T16:35:12Z
        JsonNode four = mapper.readTree(new File("src/main/resources/sourceData/0a69b9ba317e5dc1976e26ed675e8add.json"));
        Record r4 = new Record(four);

        list.add(r1);
        list.add(r2);
        list.add(r3);
        list.add(r4);

        Collections.sort(list, new Record());

        logger.info("List[0] == " + list.get(0).getStart());
        logger.info("List[1] == " + list.get(1).getStart());
        logger.info("List[2] == " + list.get(2).getStart());
        logger.info("List[3] == " + list.get(3).getStart());

    }
}
