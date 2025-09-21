package view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            projects = youTrackService.getAllProjects()
            selectedProject = projects.firstOrNull()
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Column {
            Text("Choose project")

            Button(onClick = { expanded = true }, enabled = projects.isNotEmpty()) {
                Text(selectedProject?.name ?: "Loading...")
            }
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

        TextField(
            value = summary,
            onValueChange = { summary = it },
            label = { Text("Enter issue summary") }
        )

        Button(
            onClick = {
                coroutineScope.launch {
                    withContext(Dispatchers.IO) {
                        youTrackService.createIssue(Issue(selectedProject!!, summary))
                    }
                }
            },
            enabled = selectedProject != null && summary.isNotEmpty()
        ) {
            Text("Create issue")
        }
    }

}
