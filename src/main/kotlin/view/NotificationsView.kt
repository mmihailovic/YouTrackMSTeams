package view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
                println("Retrieving notifications")
                notifications = youTrackService.getAllNotifications()
                notificationsToShow = generateMarkdown(notifications)
                delay(60000)
            }
        }
    }
    Text("Notifications", modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp), color = Color(211,211,211))
    Markdown(content = notificationsToShow.trimIndent(), colors = markdownColor(Color(211,211,211),
        codeBackground = Color(55,71,82)), typography = markdownTypography(),
        modifier = Modifier.padding(start = 32.dp, end = 32.dp, bottom = 16.dp).border(width = 2.dp, color = Color(55,71,82),
            shape = RoundedCornerShape(12.dp)
        ).padding(16.dp).verticalScroll(notificationsScrollState))
}

fun generateMarkdown(notifications: List<Notification>): String {
    return buildString {
        notifications.forEach {
            append("ID ").append(it.id).append("\n")
            append(it.content).append("\n")
        }
    }
        .replace("<p>", "\n")
        .replace("</p>", "\n")
}
