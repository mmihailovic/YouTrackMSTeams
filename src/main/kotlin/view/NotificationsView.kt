package view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.m2.markdownColor
import com.mikepenz.markdown.m2.markdownTypography
import kotlinx.coroutines.Dispatchers
import model.Notification
import service.YouTrackService
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
@Preview
fun NotificationsView(youTrackService: YouTrackService) {
    var notifications by remember { mutableStateOf<List<Notification>>(emptyList()) }
    var notificationsToShow by remember { mutableStateOf("") }
    val notificationsScrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            while (true) {
                notifications = youTrackService.getAllNotifications()
                notificationsToShow = generateMarkdown(notifications)
                delay(60000)
            }
        }
    }

    Markdown(content = notificationsToShow.trimIndent(), colors = markdownColor(Color(0,0,0)),
        typography = markdownTypography(), modifier = Modifier.verticalScroll(notificationsScrollState))
}

fun generateMarkdown(notifications: List<Notification>): String {
    return buildString {
        println("Generating markdown")
        notifications.forEach {
            append(it.id).append("\n")
            append(it.content).append("\n")
        }
    }
        .replace("<p>", "\n\n")
        .replace("</p>", "\n\n")
}
