package com.example.activitiestest

import android.content.Intent
import android.media.metrics.LogSessionId
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.activitiestest.ui.theme.ActivitiesTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("log", "on create" )
        enableEdgeToEdge()
        setContent {
            ActivitiesTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)

                    )
                }
            }
        }
    }
    override fun onStart(){
        super.onStart()
        Log.i("log", "on start" )
    }

    override fun onResume() {
        super.onResume()
        Log.i("log", "on resume" )
    }

    override fun onPause() {
        super.onPause()
        Log.i("log", "on pause" )
    }

    override fun onStop() {
        super.onStop()
        Log.i("log", "on stop" )
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("log", "on restart" )

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("log", "on destroyed" )

    }
}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val context= LocalContext.current
    Column {
        Button(onClick = {
            var intent= Intent(context, SecondActivity::class.java)
            intent.putExtra("NAME","abuela")
            context.startActivity(intent)
        }) {
            Text(text = "cambio a otra actividda")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ActivitiesTestTheme {
        Greeting("Android")
    }
}