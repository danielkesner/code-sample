package rule;

import model.DateTime;
import model.Record;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class RanForThreeConsecutiveDaysRule implements Rule {

    static Logger logger = LogManager.getLogger(RanForThreeConsecutiveDaysRule.class);

    @Override
    public int satisfiesRule(List<Record> list) {
        int counter = 0;

        // Iterate until the third-to-last element [the last record that can satisfy the rule]
        for (int i = 0; i < list.size() - 2; i++) {
            if (qualifies(list.get(i), list.get(i + 1), list.get(i + 2))) {

//                if (System.getProperty("verbose").equalsIgnoreCase("true")) {
//                    logger.info("Rule satisfied! \nOn " + list.get(i).getStart() + ", you ran " + list.get(i).getDistance()
//                            + " km.\nOn " + list.get(i + 1).getStart() + ", you ran " + list.get(i + 1).getDistance() +
//                            " km.\nOn " + list.get(i + 2).getStart() + ", you ran " + list.get(i + 2).getDistance() + " km.");
//                }


                counter++;
                i += 2;
            }
        }
        return counter;
    }

    private boolean qualifies(Record r1, Record r2, Record r3) {
        // If current record's distance > 1.0 && next two record's distance > 1.0 &&
        // all three days are consecutive, increment counter and move pointer to the next set of records
        return r1.getDistance() > 1.0 && r2.getDistance() > 1.0 && r3.getDistance() > 1.0
                && DateTime.areConsecutiveDays(new DateTime(r1.getStart()), new DateTime(r2.getStart()))
                && DateTime.areConsecutiveDays(new DateTime(r2.getStart()), new DateTime(r3.getStart()));
    }

//    public static void main(String...a) throws Exception {
//        FileSorter sorter = new FileSorter();
//        File file = new File("src/main/resources/targetData/d77908482ed2505ebbf17ef72be2f080.json");
//        sorter.concat();
//        List<Record> list = sorter.sortByDate(file);
//        logger.info(new RanForThreeConsecutiveDaysRule().satisfiesRule(list));
//    }

}
