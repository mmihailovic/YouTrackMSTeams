package view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.Issue
import model.Project
import service.YouTrackService

@Composable
@Preview
fun CreateIssueView(youTrackService: YouTrackService) {
    var projects by remember { mutableStateOf<List<Project>>(emptyList()) }
    var selectedProject by remember { mutableStateOf<Project?>(null) }
    var summary by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var creatingIssue by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("Click the button to create an issue") }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            projects = youTrackService.getAllProjects()
            selectedProject = projects.firstOrNull()
        }
    }

    Row(
        modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text("Choose project", color = Color(211,211,211))

        Button(onClick = { expanded = true }, enabled = projects.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(55, 71, 82), contentColor = Color(211,211,211))) {
            Text(selectedProject?.name ?: "No projects available\nor still loading...")
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            projects.forEach { project ->
                DropdownMenuItem(onClick = {
                    selectedProject = project
                    expanded = false
                }) {
                    Text(project.name)
                }
            }
        }

        Spacer(modifier = Modifier.width(32.dp))

        TextField(
            value = summary,
            onValueChange = { summary = it },
            label = { Text("Enter issue summary", color = Color(69, 101, 122)) },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color(211,211,211),
                focusedIndicatorColor = Color(55,71,82),
            )
        )

        Button(
            onClick = {
                creatingIssue = true
                message = "Creating issue..."
                coroutineScope.launch {
                    withContext(Dispatchers.IO) {
                        val isCreated = youTrackService.createIssue(Issue(selectedProject!!, summary))
                        if(isCreated) message = "Issue created successfully!"
                        else message = "An error occurred!"
                    }
                    creatingIssue = false
                }
            },
            enabled = selectedProject != null && summary.isNotEmpty() && !creatingIssue,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(51, 71, 82), contentColor = Color(211,211,211))
        ) {
            Text("Create issue")
        }
        Text(message, color = Color(211,211,211))
    }

}
