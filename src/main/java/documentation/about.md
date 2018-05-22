## Expected inputs

This program must be passed one (and only one) argument from the command line: an alphanumeric string representing the `user_id` field from a JSON data file.

## Interpreting the output

This program will output a JSON object similar to the following:
```
{
  "user_id" : "6bd5f3c04e6b5279aca633c2a245dd9c",
  "ThreeConsecutiveDays" : 14,
  "MoreThanTenKmInAWeek" : 219,
  "BeatPreviousPersonalRecordForLongestRun" : 8
}
```

- The `user_id` field is the same `user_id` specified in the original data files, and is used to differentiate users
- The value of `ThreeConsecutiveDays` is the number of times the user satisfied the *first* rule.
- The value of `MoreThanTenKmInAWeek` is the number of times the user satisfied the *second* rule.
- The value of `BeatPreviousPersonalRecordForLongestRun` is the number of times the user satisfied the *third* rule.

## Rules

- Rule 1: They ran more than 1km in a single run, 3 days in a row. This should count once per 3 consecutive days. i.e.: having at least one 1k run 6 days in a row counts as 2-times, not 4-times.
- Rule 2: They ran more than 10km in a calendar week. Consider a calendar week as starting on Monday and ending on Sunday.
- Rule 3: They beat their previous personal record for longest run. For example, if the first data file has a `distance` value of 1.0 and the second data file has a `distance` value of 1.5, this would count as one instance of satisfying this rule. Each subsequent record that has a `distance` value greater than the previous personal record is counted as another instance of this rule being met.