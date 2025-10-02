package com.example.painfortheyear.repo

import com.example.painfortheyear.myComponents.EstudentData

interface IStudentsRepo {

   fun addStudents(student: EstudentData): EstudentData
    fun getStudents(): List<EstudentData>
    fun choose(rnd: Int): List<EstudentData>
}