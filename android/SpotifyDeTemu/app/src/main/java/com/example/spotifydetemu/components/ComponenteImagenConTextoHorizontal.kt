package com.example.spotifydetemu.components

import android.R.attr.description
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.FontScaling
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spotifydetemu.R

@Composable
fun ComponenteConImagenHorizontal(@DrawableRes id: Int, textMsg: String, action: () -> Int) {

    Row(
        Modifier.fillMaxWidth().clickable(onClick = { action.invoke() }),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = id),
            modifier = Modifier
            .padding(2.dp)
            .size(width = 100.dp, height = 100.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = Color.White)
            .clickable(onClick = { action.invoke() }),
            contentDescription = description.toString()
        )

        Text(
            text = textMsg,
            modifier = Modifier,
            fontSize = 32.sp,
            color = Color.White
        )
    }
}

@Preview
@Composable
fun PreviuImagenHorizontal() {
    ComponenteConImagenHorizontal(R.drawable.cosa, "sishu", { Log.e("preview", "click boton") })
}