package com.example.spotifydetemu.components
import android.R.attr.description
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spotifydetemu.R

@Composable
fun ComponentePerfil(id: Int, action: () -> Int){

            Image(
                painter = painterResource(id = id),
                modifier = Modifier
                    .padding(3.dp)
                    .size(width = 50.dp, height = 50.dp)
                    .clip(CircleShape).background(color = Color.White)
                    .clickable(onClick = {action.invoke()}),
                contentDescription = description.toString()
            )
        }



@Preview
@Composable
fun PreviewButtonPerfil(){
    Row(Modifier.fillMaxSize()) {
    ComponentePerfil(
        id = R.drawable.ic_launcher_background,
        action = { Log.e("preview","click boton")})

    ComponentePerfil(id = R.drawable.cosa,action = { Log.e("preview","click boton")})
    }
}