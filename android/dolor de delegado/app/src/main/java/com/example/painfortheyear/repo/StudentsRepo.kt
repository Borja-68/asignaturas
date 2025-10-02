package com.example.painfortheyear.repo

import com.example.painfortheyear.myComponents.EstudentData

class StudentsRepo : IStudentsRepo{

    val studentsMock= ArrayList<EstudentData>()

    init {
        studentsMock.add(EstudentData("cholo",false))
        studentsMock.add(EstudentData("cholo",false))
    }

    override fun addStudents(student: EstudentData): EstudentData {
        studentsMock.add(student)
        return student
    }

    override fun getStudents(): List<EstudentData>{
        return studentsMock
    }

    override fun choose(rnd: Int): List<EstudentData> {
        studentsMock[rnd].elegido=true
        return studentsMock
    }


}