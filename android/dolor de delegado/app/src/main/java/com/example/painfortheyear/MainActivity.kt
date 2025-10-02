package com.example.painfortheyear

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.painfortheyear.myComponents.Greeting
import com.example.painfortheyear.myComponents.GreetingPreview
import com.example.painfortheyear.pages.SelectorPage
import com.example.painfortheyear.repo.IStudentsRepo
import com.example.painfortheyear.repo.StudentsRepo
import com.example.painfortheyear.ui.theme.PainForTheYearTheme
import com.example.painfortheyear.viewModel.MainActivityVM

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       val vm = MainActivityVM()
       vm.getAllStudents()

        enableEdgeToEdge()
        setContent {
            PainForTheYearTheme {
                SelectorPage(vm)
                }
            }
        }
    }


