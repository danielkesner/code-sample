## The algorithm

At a high level, the algorithm I wrote does the following:

1. Concatenate all records for a given user into a single file that contains all records for that user ID
2. Sort these records by start date/time (i.e. when they began their workout) in ascending order (oldest records first) and hold this sorted list of records in memory as a Java object
3. Apply a rule (implemented as `? implements Rule` classes) to the sorted list of records; return the number of times the rule is satisfied 
4. Repeat for all rules; build JSON object that contains results, and output to standard output

## Assumptions

- All of the records for a user (when represented as Java objects) can fit in memory at once. This means that no single user has more than, say, a million records associated with their account (which seems like a reasonable assumption to make).

## Design decisions
The design decisions that I emphasized when creating this system were the following:

- Speed of computation. For example, after sorting the concatenated list of records by start time, I held the result in memory as a List<Record> object rather than creating a new file that was in sorted order. This facilitates faster queries when implementing rules, since Java only has to dereference an object on the heap rather than making a disk read each time the Rule class checks if the rule has been satisfied.
- Doing as much pre-processing as possible on the dataset prior to applying rules. Having the data in an easy-to-use format makes it easier to reason about and write code for rules, even complicated/tricky ones.
- Clearly separating the pre-processing logic from the rule application logic. This is useful for many reasons:
1. It makes the code much more modular and reusable. Since you know that the data will always be in a specific format after the preprocessing step (i.e. a list of all user records, sorted in order of date), it's very easy to write new rules that consume this list. This is especially helpful if working in a team environment, since a developer doesn't need to know anything about how the data is preprocessed -- all they need to know is that it's eventually presented as a sorted list.
2. It makes testing individual pieces of the algorithm simpler -- you can write unit tests for each step of the pre-computation process, and feel confident that the entire process will work as intended
3. It becomes very easy to further optimize of the algorithm -- for example, you could parallelize the concatentation and/or sorting steps to achieve even higher performance




