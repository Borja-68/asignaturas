def EsMultiplo(a,b):
    mensaje=""
    if(a%b==0):
        mensaje="el primer numero es multiplo del segundo"
    else:
        mensaje="el primer numero no es multiplo del segundo"
    print(mensaje)
numero1=int(input("inserta un un numero:"))
numero2=int(input("inserta un un numero:"))
EsMultiplo(numero1,numero2)