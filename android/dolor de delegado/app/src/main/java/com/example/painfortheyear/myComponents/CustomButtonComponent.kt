package com.example.painfortheyear.myComponents

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
fun CustomButton(textMsg: String, action: () -> Int){
    Box(modifier = Modifier.width(150.dp)
        .height(40.dp))
    {
        Button(onClick = {action.invoke()}, modifier = Modifier.
        fillMaxSize()
        , colors = ButtonColors(
                containerColor = Color.Red,
                contentColor = Color.Red,
                disabledContainerColor = Color.Red,
                disabledContentColor = Color.Red,
            )) {
            Text(text=textMsg, color = Color.White, textAlign = TextAlign.Center, fontSize = 20.sp)
        }
    }
}

@Preview
@Composable
fun PreviewButton(){
    CustomButton(
        textMsg = "pringa",
        action = { Log.e("preview","click boton")})
}