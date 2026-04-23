
numero=float(input("dame un numero: "))
mensaje=""
if(numero<0):
 mensaje="menor que 0"
if(numero>0):
 mensaje="mayor que 0"
if(numero==0):
 mensaje="igual que 0"
print(" el numero era ",mensaje,numero)