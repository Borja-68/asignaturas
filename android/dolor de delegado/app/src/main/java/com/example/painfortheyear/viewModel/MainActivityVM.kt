package com.example.painfortheyear.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.painfortheyear.myComponents.EstudentData
import com.example.painfortheyear.repo.IStudentsRepo
import com.example.painfortheyear.repo.StudentsRepo
import kotlin.random.Random

class MainActivityVM: ViewModel() {
    val students: MutableLiveData<List<EstudentData>> = MutableLiveData()

    val repo: IStudentsRepo= StudentsRepo()

    fun getAllStudents(){
       students.postValue( repo.getStudents())
    }

    fun chooseDelegado(){
       var stds= repo.getStudents()

        val rnd: Int = Math.floor(Math.random()*stds.size).toInt()
        students.postValue(repo.choose(rnd))
    }
}