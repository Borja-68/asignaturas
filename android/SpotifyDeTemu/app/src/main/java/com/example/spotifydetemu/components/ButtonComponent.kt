package com.example.spotifydetemu.components

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonComponent(textMsg: String, activo: Boolean, action: () -> Int){

        Button(onClick = {action.invoke()}, enabled = activo, modifier = Modifier.padding(3.dp)
            , colors = ButtonColors(
                containerColor = Color.Black,
                contentColor = Color.White,
                disabledContainerColor = Color.Green,
                disabledContentColor = Color.Black,
            )) {
            Text(text=textMsg, textAlign = TextAlign.Center, fontSize = 20.sp)
       }

}


@Preview
@Composable
fun PreviewButton(){
    Row {
        ButtonComponent(
            textMsg = "pringa", false,
            action = { Log.e("preview", "click boton") })

        ButtonComponent(
            textMsg = "pringa", true,
            action = { Log.e("preview", "click boton") })
    }
}