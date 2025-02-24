# Formatter – How to Use the Automatic Code Formatting Tool
In our project, we use Spotless to automatically format the code.
Below you will find instructions on how to use this tool both locally and within CI/CD.

## Locally

**Checking Code Format:**

To verify that your code is formatted according to the established guidelines, run the following command:

`./gradlew spotlessCheck`

* **Result**:

  If the task fails, it indicates that some files do not meet the formatting standards.

  In such cases, you should either run the automatic formatting or manually adjust the code.


**Automatic Formatting:**

To automatically format the code, execute the following command:

`./gradlew spotlessApply`

* **Result:**

  This command will transform all files according to our configuration by:

  - Removing extraneous whitespace (e.g., at the end of lines)
 
  - Adjusting indentations (following the AOSP Google Java Format, i.e., 4-space indents)

  - Organizing imports by removing unused ones


## In CI/CD

* **Pipeline Integration:**

In our GitHub Actions configuration, the spotlessCheck task is executed as part of the pipeline.

If the code does not conform to the standards, the build will be halted, ensuring that proper formatting is applied before merging a PR.


## Integration with IntelliJ IDEA Environment:

**1. Create an External Tool configuration:**
* open **Settings/Preferences** (Ctrl+Alt+S)
* navigate to **Tools → External Tools** 
* click the **"+"** button to add a new tool

  Fill in the fields:

  **Name**: e.g., Spotless Apply

  **Program**: enter `./gradlew` (or the full path to the script, if necessary)

  **Arguments**: spotlessApply

  **Working directory**: set to `$ProjectFileDir$`


**2. Assign a keyboard shortcut to the new tool:**

* in Settings, go to **Keymap**
* search for the name of the tool you created (e.g., Spotless Apply) under **External Tools**
* right-click the entry and select **Add Keyboard Shortcut**
* set your preferred key combination
(e.g., Ctrl+Alt+L – but note that by default this combination is assigned to IntelliJ’s built-in formatter, so you may need to modify it or choose another shortcut)