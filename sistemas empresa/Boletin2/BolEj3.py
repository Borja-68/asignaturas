class Persona:
    def __init__(self,nombre=None,edad=0,Dni=None):
        self.nombre=nombre
        self.edad=edad
        self.Dni=Dni



    def EsMayorEdad(self):
        if self.edad>=18:
            print("Es mayor de edad")
        else:
            print("No es mayor de edad")
    def getNombre(self):
        return self.nombre
    
    def getEdad(self):
        return self.edad
    
    def getDni(self):
        return self.Dni
    
    def setDni(self,Dni):
         self.Dni=Dni
    
    def setNombre(self,nombre):
         self.nombre=nombre

    def setEdad(self,edad):
         self.edad=edad
    def mostrar(self):
        print(self.getNombre(),self.getEdad(),self.getDni())


persona=Persona()

persona.mostrar()
persona.EsMayorEdad()