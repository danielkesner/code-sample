## The algorithm

At a high level, the algorithm I wrote does the following:

1. Concatenate all records (I'm referring to the individual JSON data files as 'records') for a given user into a single file that contains all records for that user ID (original files are in `src/main/java/resources/sourceData`, the concatenated files are in `targetData`)
2. Sort these records by start date/time (i.e. when they began their workout) in ascending order and hold this sorted list of records in memory as a Java object
3. Apply one or more rules (represented in the code as a class that implements the `Rule` interface) to the sorted list of records; return the number of times the rule is satisfied 
4. Build JSON object that contains results, and output to standard output

## Assumptions

- All of the records for a user (when stored as Java objects) can fit in memory at once on a typical machine. This means that the number of records per user is below some threshold, which is probably on the order of ~1 million records.
- If this algorithm was used in an environment with billions (or more) of individual records, the record concatenation step would probably need to be redesigned. Currently, it iterates over all data files in the `sourceData` directory and creates `Record` objects for all files that have a specific user ID. Iterating over billions of files would take too long in production, but this problem could be solved by keeping the filesystem in alphabetical sorted order by `user_id`, using binary search or hashing to find the first file with a matching user ID, then iterating forward until you come to a file with a non-matching `user_id`.

## Design decisions
The design decisions that I emphasized when creating this system were the following:

- Speed of computation. For example, after sorting the concatenated list of records by start time, I held the result in memory as a List<Record> object rather than creating a new file that was in sorted order. This facilitates faster queries when implementing rules, since Java only has to dereference an object on the heap rather than making a disk read each time the Rule class checks if the rule has been satisfied.
- Scalability. While this algorithm is still not optimal, it should scale reasonably well in a distributed environment. Both the record concatenation and record sorting steps could be parallelized in an environment with a distributed filesystem and multiple CPU nodes. It should also be possible to parallelize the rule application logic by breaking the sorted list of records into chunks, having a worker process each chunk, and returning each partial sum to a parent thread who computes the total. 
- Clearly separating the pre-processing logic from the rule application logic. This is useful for many reasons:
1. It makes the project more modular and reusable. Since you know that the data will always be in a specific format after the preprocessing step (i.e. a list of all user records, sorted in order of date), it's easy to write new rules that consume this list. This is helpful when working in a team environment, since a developer doesn't need to know anything about how the data is preprocessed -- all they need to know is that it's eventually presented as a sorted list.
2. It makes testing individual pieces of the algorithm simpler -- you can write unit tests for each step of the pre-computation process, and feel confident that the entire process will work as intended
3. It becomes very easy to further optimize of the algorithm -- for example, you could parallelize the concatentation and/or sorting steps to achieve even higher performance
 - Make the output as simple as possible. When I was developing and testing the project, I added extra logging when calculating rules that recorded specific details whenever a rule was satisfied (i.e. when calculating if a user had ran >10 km in a week, I logged each individual date that they ran plus the distance they covered) to help sanity check my code. I ultimately decided to remove all of this extra information to make the program output cleaner, with the understanding that I could always add it back in later if need be.
