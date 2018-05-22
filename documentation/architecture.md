## The algorithm

At a high level, the algorithm I wrote does the following:

1. Concatenate all records for a given user into a single file that contains all records for that user ID (original files are in src/main/java/resources/sourceData, the concatenated files are in /targetData)
2. Sort these records by start date/time (i.e. when they began their workout) in ascending order (oldest records first) and hold this sorted list of records in memory as a Java object
3. Apply a rule (represented in the code as a class that implements the `Rule` interface) to the sorted list of records; return the number of times the rule is satisfied 
4. Repeat for all rules; build JSON object that contains results, and output to standard output

## Assumptions

- All of the records for a user (when represented as Java objects) can fit in memory at once. This means that no single user has more than, say, a million records associated with their account (which seems like a reasonable assumption to make).
- Each original 

## Design decisions
The design decisions that I emphasized when creating this system were the following:

- Speed of computation. For example, after sorting the concatenated list of records by start time, I held the result in memory as a List<Record> object rather than creating a new file that was in sorted order. This facilitates faster queries when implementing rules, since Java only has to dereference an object on the heap rather than making a disk read each time the Rule class checks if the rule has been satisfied.
- Scalability. While this algorithm is still not optimal, it should scale reasonably well in a distributed environment. Both the record concatenation and record sorting steps could easily be parallelized in an environment with a distributed filesystem (i.e. Amazon S3) and multiple CPU nodes (i.e. more EC2 instances). It should be possible to parallelize the rule application logic by breaking the sorted list of records into chunks, having a worker process each chunk, and returning each partial sum to a parent thread who computes the total. 
- Presenting the data in a simple and useful format. For example, many of the rules involve checking whether all of the records in a short timeframe (i.e. three days, a calendar week, etc.) satisfy some condition; because of this, it makes sense to sort the data by date. Having the data in an easy-to-use format makes it easier to reason about and write code for rules, even complicated/tricky ones.
- Clearly separating the pre-processing logic from the rule application logic. This is useful for many reasons:
1. It makes the project much more modular and reusable. Since you know that the data will always be in a specific format after the preprocessing step (i.e. a list of all user records, sorted in order of date), it's very easy to write new rules that consume this list. This is especially helpful if working in a team environment, since a developer doesn't need to know anything about how the data is preprocessed -- all they need to know is that it's eventually presented as a sorted list.
2. It makes testing individual pieces of the algorithm simpler -- you can write unit tests for each step of the pre-computation process, and feel confident that the entire process will work as intended
3. It becomes very easy to further optimize of the algorithm -- for example, you could parallelize the concatentation and/or sorting steps to achieve even higher performance
