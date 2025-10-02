package com.example.painfortheyear.myComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
class EstudentData(var name: String,var elegido: Boolean){
}

@Composable
fun CustomList(names: List<EstudentData>){
    Column(modifier = Modifier.fillMaxWidth()){
        names.forEach {
            estudentData ->
        NombreEstudiante(estudentData.name,estudentData.elegido)
        }
    }
}
@Preview
@Composable
fun ListaPreview(){
    val estudiantes= ArrayList<EstudentData>()
    estudiantes.add(EstudentData("paco",false))
    estudiantes.add(EstudentData("paci",false))
    estudiantes.add(EstudentData("pacn",false))
    estudiantes.add(EstudentData("pacz",true))
    Box(modifier = Modifier.fillMaxHeight().fillMaxWidth().background(color = Color.Black)) {
        CustomList(estudiantes)
    }
}