# How to run the program
Run the program with command: **gradlew run --args="<YOUTRACK_INSTANCE_URL> <YOUTRACK_USER_TOKEN>"**  
(for example: gradlew run --args="https://my-youtrack-instance.com my_youtrack_token")

> **Note:** On macOS/Linux, the command might be **./gradlew** instead of **gradlew**

# TODOs and Considerations
- I was storing the whole notification content in one variable, but I could parsed it into separate attributes like notification title, issue name, description, etc.  I wasn’t sure if this was necessary.
- I fetch only notifications for the user associated with the provided token.
- I added the option for the user to choose a project from all available ones, since an issue must be created for a specific project.
- Notifications are retrieved every 60 seconds.

# Screenshots
- When program is started
  <img width="1233" height="740" alt="screenshot3" src="https://github.com/user-attachments/assets/5d1ffe56-2c3d-481d-8112-5b8d505eb8c6" />

- Selecting project and entering issue summary
  <img width="1210" height="738" alt="screenshot2" src="https://github.com/user-attachments/assets/251273c8-dc6d-4677-bb28-b732b8734a41" />

- Issue creation in progress
  <img width="1212" height="732" alt="creating_issue" src="https://github.com/user-attachments/assets/c5421099-f6c8-4ce0-bf59-e58e08610674" />

- Issue created successfully
  <img width="1217" height="735" alt="issue_created" src="https://github.com/user-attachments/assets/0a164e2a-f300-43f4-9e79-4cc5c585f881" />



