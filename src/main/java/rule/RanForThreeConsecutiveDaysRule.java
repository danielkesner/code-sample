package rule;

import model.DateTime;
import model.Record;

import java.util.List;

public class RanForThreeConsecutiveDaysRule implements Rule {

    @Override
    public int satisfiesRule(List<Record> list) {
        int counter = 0;

        // Iterate until the third-to-last element [the last record that can satisfy the rule]
        for (int i = 0; i < list.size() - 2; i++) {
            if (qualifies(list.get(i), list.get(i + 1), list.get(i + 2))) {
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

}
