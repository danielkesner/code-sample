package model;

import java.time.YearMonth;

public class DateTime {

    //i.e. 2015-10-29T20:16:08.371000Z
    // or
    // 2016-08-17T22:07:04Z
    private String _data;

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;
    private int millisecond;

    public DateTime(String data) {
        this._data = data;
        if (!parseData()) {
            throw new RuntimeException("Failed to parse data for DateTime object: " + this._data);
        }
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public int getMillisecond() {
        return millisecond;
    }

    public static boolean areConsecutiveDays(DateTime first, DateTime second) {
        if (first.year == second.year) {
            // If same month and year, return true if second is one day ahead
            if (first.month == second.month) {
                return second.day - first.day == 1;
            }
            // Same year, different month
            else {
                // If the first object is the last day of month n and the second
                // object is the first day of month n+1, return true
                int lengthOfFirstMonth = YearMonth.of(first.year, first.month).lengthOfMonth();
                return first.day == lengthOfFirstMonth && second.day == 1 && second.month - first.month == 1;
            }
        }
        // Different years can only be consecutive days if 12/31 && 1/1
        else {
            return first.month == 12 && first.day == 31 && second.month == 1 && second.day == 1
                    && second.year - first.year == 1;
        }
    }

    public boolean isBefore(DateTime toCompare) {
        if (this.year < toCompare.year) {
            return true;
        } else if (this.year > toCompare.year) {
            return false;
        }
        // Years are equal, check months
        else {
            if (this.month < toCompare.month) {
                return true;
            } else if (this.month > toCompare.month) {
                return false;
            }
            // Months are equal, check day
            else {
                if (this.day > toCompare.day) {
                    return false;
                } else if (this.day < toCompare.day) {
                    return true;
                }
                // Days are equal, check hour
                else {
                    if (this.hour > toCompare.hour) {
                        return false;
                    } else if (this.hour < toCompare.hour) {
                        return true;
                    }
                    // Hours are equal, check minute
                    else {
                        if (this.minute > toCompare.minute) {
                            return false;
                        } else if (this.minute < toCompare.minute) {
                            return true;
                        }
                        // Minutes are equal, check seconds
                        else {
                            if (this.second > toCompare.second) {
                                return false;
                            } else if (this.second < toCompare.second) {
                                return true;
                            }
                            // Seconds are equal -- see if milliseconds exist for both and compare
                            else {
                                if (this.millisecond != -1 && toCompare.millisecond != -1) {
                                    if (this.millisecond > toCompare.millisecond) {
                                        return false;
                                    } else if (this.millisecond < toCompare.millisecond) {
                                        return true;
                                    } else {
                                        throw new RuntimeException("Trying to compare two identical DateTime objects!\n"
                                            + "this: " + this.toString() + "\ntoCompare: " + toCompare.toString());
                                    }
                                } else {
                                    throw new RuntimeException("Could not compare DateTime objects -- all fields up until seconds are the same, and " +
                                            "at least one of the objects doesn't have a milliseconds field to compare");
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean parseData() {
        if (this._data == null) {
            return false;
        }

        int startIdx = 0;
        int endIdx = this._data.indexOf("-", startIdx);

        try {
            this.year = Integer.parseInt(this._data.substring(startIdx, endIdx));
        } catch (NumberFormatException nfe) {
            return false;
        }
        startIdx = this._data.indexOf("-", endIdx) + 1; // move pointer past the next '-' delimiter
        endIdx = this._data.indexOf("-", startIdx);

        try {
            this.month = Integer.parseInt(this._data.substring(startIdx, endIdx));
        } catch (NumberFormatException nfe) {
            return false;
        }
        startIdx = this._data.indexOf("-", endIdx) + 1; // move pointer past the next '-' delimiter
        endIdx = this._data.indexOf("T", startIdx);

        try {
            this.day = Integer.parseInt(this._data.substring(startIdx, endIdx));
        } catch (NumberFormatException nfe) {
            return false;
        }
        startIdx = this._data.indexOf("T", endIdx) + 1;
        endIdx = this._data.indexOf(":", startIdx);

        try {
            this.hour = Integer.parseInt(this._data.substring(startIdx, endIdx));
        } catch (NumberFormatException nfe) {
            return false;
        }
        startIdx = this._data.indexOf(":", endIdx) + 1;
        endIdx = this._data.indexOf(":", startIdx);

        try {
            this.minute = Integer.parseInt(this._data.substring(startIdx, endIdx));
        } catch (NumberFormatException nfe) {
            return false;
        }
        startIdx = this._data.indexOf(":", endIdx) + 1;
        if (this._data.indexOf(".", startIdx) != -1) {
            endIdx = this._data.indexOf(".", startIdx);
        } else {
            endIdx = startIdx + 2;
        }

        try {
            this.second = Integer.parseInt(this._data.substring(startIdx, endIdx));
        } catch (NumberFormatException nfe) {
            return false;
        }

        // If the DateTime source string includes milliseconds, parse it
        if (this._data.substring(endIdx).length() > 1) {
            startIdx = this._data.indexOf(".", endIdx) + 1;
            endIdx = this._data.length() - 1;
            try {
                this.millisecond = Integer.parseInt(this._data.substring(startIdx, endIdx));
            } catch (NumberFormatException nfe) {
                return false;
            }
        } else {
            this.millisecond = -1;
        }

        return true;
    }

    public String toString() {
        return this._data;
    }
}
