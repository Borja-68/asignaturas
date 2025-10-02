package com.example.spotifydetemu.components

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.spotifydetemu.R

@Composable
fun ComponenteElementoSuperior(){
    Row{
        ComponentePerfil(R.drawable.cosa,{ Log.e("preview","click boton")})
        ButtonComponent(
            textMsg = "pringa", false,
            action = { Log.e("preview", "click boton") })
        ButtonComponent(
            textMsg = "pringa", true,
            action = { Log.e("preview", "click boton") })
        ButtonComponent(
            textMsg = "pringa", true,
            action = { Log.e("preview", "click boton") })
    }
}

@Preview
@Composable
fun PreviewFilaSuperior(){
    ComponenteElementoSuperior()
}