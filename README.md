# Nike Coding Challenge

## How to build and run
The only dependencies you need locally to build and run this project are Maven (3.x.x or higher) and Java (1.8 or higher). Follow these steps to build and run this code:

1. Clone this repository locally (`git clone https://github.com/danielkesner/code-sample.git`).
2. Copy the data files from the `applicant-package/data` folder to the `src/main/resources/sourceData` folder (I left them out so they wouldn't be visible to anyone else)
3. Go to the root directory (`~/some/path/code-sample`) and build the project with `mvn clean install`.
4. Give the shell script permission to execute: `chmod +x run.sh`.
5. Run the program with a specific user ID by passing it as a parameter to the `run.sh` script.
For example: `./run.sh "6bd5f3c04e6b5279aca633c2a245dd9c"` (make sure to surround the string with quotes!)

## Documentation
See the documentation folder for more information about the project.
