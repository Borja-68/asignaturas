package com.example.calendariointentintento

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.provider.CalendarContract.Events
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import com.example.calendariointentintento.ui.theme.CalendarioIntentIntentoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                EmailButton(
                    onSendEmail = {
                        // Construimos el intent para abrir la app de correo
                        val intent = Intent(Intent.ACTION_INSERT).apply {
                            data = Events.CONTENT_URI // Garantiza que solo apps de correo lo manejen

                            putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY,"allDay")
                            putExtra(Events.TITLE, "titulo")
                            putExtra(Events.EVENT_LOCATION, "aqui")
                            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, 12)
                            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, 30)}

                        // Verifica que exista una app de correo instalada
                        if (intent.resolveActivity(packageManager) != null) {
                            startActivity(intent)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun EmailButton(onSendEmail: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onSendEmail,
            modifier = Modifier.Companion.padding(20.dp)
        ) {
            Text("nuevo evento")
        }
    }
}