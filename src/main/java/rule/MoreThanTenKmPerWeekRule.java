package rule;

import model.Record;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class MoreThanTenKmPerWeekRule implements Rule {

    private static Logger logger = LogManager.getLogger(MoreThanTenKmPerWeekRule.class);
    private ArrayList<Record> recordsInCalendarWeek = new ArrayList<>();

    @Override
    public int satisfiesRule(List<Record> list) {
        int counter = 0;

        for (int i = 0; i < list.size() - 6; i++) {
            if (qualifies(list, i)) {
                counter++;
            }
        }
        return counter;
    }

    private DateTime getEndOfCalendarWeek(Record record) {
        model.DateTime modelDateTime = new model.DateTime(record.getStart());
        org.joda.time.DateTime current = new DateTime(
                modelDateTime.getYear(), modelDateTime.getMonth(), modelDateTime.getDay(),
                modelDateTime.getHour(), modelDateTime.getMinute()
        );

        int dayOfWeek = current.getDayOfWeek();
        org.joda.time.DateTime nextSunday = current.plusDays(7 - dayOfWeek);

        return nextSunday;
    }

    /**
     * Returns true if first is on or before second
     * Ex: (02-08-2015).isOnOrBefore(02-08-2015) == TRUE
     * (02-08-2015).isOnOrBefore(02-07-2015) == FALSE
     * (02-08-2015).isOnOrBefore(01-08-2015) == FALSE
     * (02-08-2015).isOnOrBefore(02-08-2016) == TRUE
     */
    private boolean isOnOrBefore(org.joda.time.DateTime first, org.joda.time.DateTime second) {

        if (first.getYear() < second.getYear()) {
            return true;
        } else if (first.getYear() > second.getYear()) {
            return false;
        }
        // Same year
        else {
            if (first.getMonthOfYear() < second.getMonthOfYear()) {
                return true;
            } else if (first.getMonthOfYear() > second.getMonthOfYear()) {
                return false;
            }
            // Same year and month
            else {
                if (first.getDayOfMonth() < second.getDayOfMonth()) {
                    return true;
                } else if (first.getDayOfMonth() > second.getDayOfMonth()) {
                    return false;
                }
                // Else, the two objects have the same year/month/day
                // --> they refer to the same day, so first is on or before second
                else {
                    return true;
                }
            }
        }
    }

    private ArrayList<Record> getAllRecordsInCalendarWeek(List<Record> list, int index,
                                                          org.joda.time.DateTime endOfCalendarWeek) {
        ArrayList<Record> ret = new ArrayList<>();
        // Starting with the next record
        for (int i = index + 1; i < list.size(); i++) {
            // Continue until you see a record outside of calendar week
            model.DateTime modelDateTime = new model.DateTime(list.get(i).getStart());
            org.joda.time.DateTime currentDateTime = new DateTime(
                    modelDateTime.getYear(), modelDateTime.getMonth(), modelDateTime.getDay(),
                    modelDateTime.getHour(), modelDateTime.getMinute()
            );

            if (isOnOrBefore(currentDateTime, endOfCalendarWeek)) {
                ret.add(list.get(i));
            }
        }
        return ret;
    }

    private double sumDistance(ArrayList<Record> list) {
        double total = 0.0d;
        for (Record record : list) {
            total += record.getDistance();
        }
        return total;
    }

    // They ran more than 10km in a calendar week. Consider a calendar week as starting on Monday and ending on Sunday.
    // Mon = 1, ... Sat = 6, Sun = 7
    private boolean qualifies(List<Record> list, int index) {
        org.joda.time.DateTime endOfCalendarWeek = getEndOfCalendarWeek(list.get(index));

        recordsInCalendarWeek.clear();
        recordsInCalendarWeek = getAllRecordsInCalendarWeek(list, index, endOfCalendarWeek);
        recordsInCalendarWeek.add(0, list.get(index));  // put the first record at the beginning of the list

        return sumDistance(recordsInCalendarWeek) > 10.0;
    }
}
