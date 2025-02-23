# Formatter – How to Use the Automatic Code Formatting Tool
In our project, we use Spotless to automatically format the code.
Below you will find instructions on how to use this tool both locally and within CI/CD.

## Locally

**Checking Code Format:**

To verify that your code is formatted according to the established guidelines, run the following command:

./gradlew spotlessCheck

* **Result**:

  If the task fails, it indicates that some files do not meet the formatting standards.

  In such cases, you should either run the automatic formatting or manually adjust the code.


**Automatic Formatting:**

To automatically format the code, execute the following command:

./gradlew spotlessApply

* **Result:**

  This command will transform all files according to our configuration by:

  - Removing extraneous whitespace (e.g., at the end of lines)
 
  - Adjusting indentations (following the AOSP Google Java Format, i.e., 4-space indents)

  - Organizing imports by removing unused ones


## In CI/CD

* **Pipeline Integration:**

In our GitHub Actions configuration, the spotlessCheck task is executed as part of the pipeline.

If the code does not conform to the standards, the build will be halted, ensuring that proper formatting is applied before merging a PR.