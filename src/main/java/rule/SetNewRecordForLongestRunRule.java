package rule;

import model.Record;
import java.util.List;

public class SetNewRecordForLongestRunRule implements Rule {

    /* Number of times they set a new PR for longest run */
    @Override
    public int satisfiesRule(List<Record> list) {
        int counter = 0;
        double longestRunSoFar = 0.0;

        for (Record eachRecord : list) {
            if (eachRecord.getDistance() > longestRunSoFar) {
                counter++;
                longestRunSoFar = eachRecord.getDistance();
            }
        }
        return counter;
    }

}
