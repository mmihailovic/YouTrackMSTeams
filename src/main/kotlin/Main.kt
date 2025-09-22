import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import service.YouTrackServiceImplementation
import util.DefaultUtils
import view.CreateIssueView
import view.NotificationsView
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
@Preview
fun App(args: Array<String>) {
    val youTrackService = YouTrackServiceImplementation(args[0], args[1], DefaultUtils())

    Column(modifier = Modifier.fillMaxSize().background(Color(51,51,51))) {
        CreateIssueView(youTrackService)
        NotificationsView(youTrackService)
    }
}

fun main(args: Array<String>) = application {
    Window(onCloseRequest = ::exitApplication) {
        App(args)
    }
}
