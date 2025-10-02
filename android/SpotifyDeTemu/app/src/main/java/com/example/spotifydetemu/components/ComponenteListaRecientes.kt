package com.example.spotifydetemu.components

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spotifydetemu.R
class RecientesData(var imagen: Int,var msgTexto: String,var funcion: () -> Int){
}
@Composable
fun ComponenteListaRecientes(listaDatos: ArrayList<RecientesData>) {

    Box(modifier = Modifier.fillMaxSize()) {
        LazyHorizontalGrid(
            rows = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                start = 4.dp, top = 4.dp, end = 4.dp, bottom = 4.dp
            )
        ) {
            items(count = listaDatos.size) { index ->
                Card(modifier = Modifier.fillMaxSize()) {
                    ComponenteConImagenHorizontal(
                       id = listaDatos[index].imagen, listaDatos[index].msgTexto, listaDatos[index].funcion)
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewCOmponenteListaRecientes() {
    val listaDatos= ArrayList<RecientesData>()
    listaDatos.add(RecientesData(R.drawable.cosa, "sishu", { Log.e("preview", "click boton")}))
    listaDatos.add(RecientesData(R.drawable.cosa, "sishu", { Log.e("preview", "click boton")}))
    listaDatos.add(RecientesData(R.drawable.cosa, "sishu", { Log.e("preview", "click boton")}))
    listaDatos.add(RecientesData(R.drawable.cosa, "sishu", { Log.e("preview", "click boton")}))
    listaDatos.add(RecientesData(R.drawable.cosa, "sishu", { Log.e("preview", "click boton")}))
    listaDatos.add(RecientesData(R.drawable.cosa, "sishu", { Log.e("preview", "click boton")}))
    listaDatos.add(RecientesData(R.drawable.cosa, "sishu", { Log.e("preview", "click boton")}))
    listaDatos.add(RecientesData(R.drawable.cosa, "sishu", { Log.e("preview", "click boton")}))
    listaDatos.add(RecientesData(R.drawable.cosa, "sishu", { Log.e("preview", "click boton")}))
    listaDatos.add(RecientesData(R.drawable.cosa, "sishu", { Log.e("preview", "click boton")}))
    ComponenteListaRecientes(listaDatos)
}