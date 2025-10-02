package com.example.painfortheyear.myComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NombreEstudiante( name: String, elegido: Boolean) {
    val backGroudColor=if(elegido) Color.Green else Color.Cyan

    Box(modifier = Modifier.fillMaxWidth()
        .background(backGroudColor)
        .border(width = 0.5.dp, color = Color.Cyan)
        .padding(all = 4.dp))
    {
        Text(text = name, modifier = Modifier.fillMaxWidth(), color = Color.Black)
    }
}

@Preview
@Composable
fun PreviewNombreEstudiante(){
    NombreEstudiante("Borja",false)
}

@Preview
@Composable
fun PreviewNombreEstudianteTrue(){
    NombreEstudiante("Borja",true)
}