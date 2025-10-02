package com.example.painfortheyear.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.example.painfortheyear.myComponents.CustomButton
import com.example.painfortheyear.myComponents.CustomList
import com.example.painfortheyear.myComponents.EstudentData
import com.example.painfortheyear.viewModel.MainActivityVM
import androidx.compose.runtime.livedata.observeAsState
@Composable
fun SelectorPage(vm: MainActivityVM){

    val studentsState by vm.students.observeAsState()
    var st=studentsState
    if(st == null){
        st = ArrayList()
    }

    Column(modifier = Modifier.fillMaxSize().fillMaxHeight()) {
        CustomList(names = st)
        CustomButton(textMsg = "Random") {
            vm.chooseDelegado()
            return@CustomButton 0
        }

    }
}
@Preview
@Composable
fun PreviewSElectorPage(){
    val vm= MainActivityVM()
    vm.getAllStudents()
 SelectorPage(vm)
}
