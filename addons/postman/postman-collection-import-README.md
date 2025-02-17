## Postman Collection Import Readme

This repository contains a Postman collection for interacting with the Warsaw Public Transport API. <br>
It provides requests to fetch bus timetables, available bus lines, and other transport-related data.

### 🚀 Getting Started <br>

1️⃣ Importing the Collection

- Open Postman.
- Click Import. ![postman-collection-import](readme-assets/collection-import.png)
- Select each .json type file from addons/postman/ directory or choose import whole current folder. ![postman-collection-import](readme-assets/import-options.png)

2️⃣ Setting Up Environment Variables <br>

After that you should have get prepared Warsaw Public Transport API collection.

The environment file (API_UM_Warsaw-env.json) contains predefined variables required for API requests.
After importing, ensure that the environment is selected in Postman:

<span style="color: green;">Click on the Environment selector (top-right in Postman).
Choose "Warsaw Public Transport" from the dropdown.</span> ![choose-environment](readme-assets/choose-environment.png)

### 🔒 Important: The API key is empty by default for security reasons. Update it manually after import. ![before-setup-environment](readme-assets/before-setup-environment.png)

Open the environment settings and update API_KEY with your actual API key. ![after-setup-environment](readme-assets/after-setup-environment.png)<br>

3️⃣ Ready to use

After this, you should be ready to use prepared request or <span style="color: purple;">check the example static response. </span> ![ready-to-use](readme-assets/ready-to-use.png)
