package alexey.odintsov.logstudiolib.testapp

import androidx.compose.material3.Text
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "LogStudioLib test app",
    ) {
        Text("Hey")
    }
}