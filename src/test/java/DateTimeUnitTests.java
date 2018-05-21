import model.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DateTimeUnitTests {

    @Test
    void firstYearBeforeSecond() {
        DateTime one = new DateTime("2015-10-29T20:16:08.371000Z");
        DateTime two = new DateTime("2016-10-29T20:16:08.371000Z");
        Assert.assertTrue(one.isBefore(two));
        Assert.assertFalse(two.isBefore(one));
    }

    @Test
    void secondYearBeforeFirst() {
        DateTime one = new DateTime("2016-10-29T20:16:08.371000Z");
        DateTime two = new DateTime("2015-10-29T20:16:08.371000Z");
        Assert.assertFalse(one.isBefore(two));
        Assert.assertTrue(two.isBefore(one));
    }

    @Test
    void firstMonthBeforeSecond() {
        DateTime one = new DateTime("2016-09-29T20:16:08.371000Z");
        DateTime two = new DateTime("2016-10-29T20:16:08.371000Z");
        Assert.assertTrue(one.isBefore(two));
        Assert.assertFalse(two.isBefore(one));
    }

    @Test
    void secondMonthBeforeFirst() {
        DateTime one = new DateTime("2016-10-29T20:16:08.371000Z");
        DateTime two = new DateTime("2016-09-29T20:16:08.371000Z");
        Assert.assertTrue(two.isBefore(one));
        Assert.assertFalse(one.isBefore(two));
    }

    @Test
    void firstDayBeforeSecond() {
        DateTime one = new DateTime("2016-10-28T20:16:08.371000Z");
        DateTime two = new DateTime("2016-10-29T20:16:08.371000Z");
        Assert.assertTrue(one.isBefore(two));
        Assert.assertFalse(two.isBefore(one));
    }

    @Test
    void secondDayBeforeFirst() {
        DateTime one = new DateTime("2016-10-29T20:16:08.371000Z");
        DateTime two = new DateTime("2016-10-28T20:16:08.371000Z");
        Assert.assertTrue(two.isBefore(one));
        Assert.assertFalse(one.isBefore(two));
    }

    @Test
    void firstHourBeforeSecond() {
        DateTime one = new DateTime("2016-10-29T08:16:08.371000Z");
        DateTime two = new DateTime("2016-10-29T09:16:08.371000Z");
        Assert.assertTrue(one.isBefore(two));
        Assert.assertFalse(two.isBefore(one));
    }

    @Test
    void secondHourBeforeFirst() {
        DateTime one = new DateTime("2016-10-29T09:16:08.371000Z");
        DateTime two = new DateTime("2016-10-29T08:16:08.371000Z");
        Assert.assertTrue(two.isBefore(one));
        Assert.assertFalse(one.isBefore(two));
    }

    @Test
    void firstMinuteBeforeSecond() {
        DateTime one = new DateTime("2016-10-29T09:10:08.371000Z");
        DateTime two = new DateTime("2016-10-29T09:16:08.371000Z");
        Assert.assertTrue(one.isBefore(two));
        Assert.assertFalse(two.isBefore(one));
    }

    @Test
    void secondMinuteBeforeFirst() {
        DateTime one = new DateTime("2016-10-29T09:16:08.371000Z");
        DateTime two = new DateTime("2016-10-29T09:10:08.371000Z");
        Assert.assertTrue(two.isBefore(one));
        Assert.assertFalse(one.isBefore(two));
    }

    /**********************************************/

}
