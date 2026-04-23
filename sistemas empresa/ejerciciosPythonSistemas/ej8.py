sumaNotas=0
list=[]

for i in range(5):
    num=-1
    while(num<0 or num>10):
        num=float(input("introduce la nota: "))
    sumaNotas+=num
    list.append(num)

print("notas: ",list)
print("nota media: ",sumaNotas/5)
list.sort()
print("nota mas alta: ",list[0])
list.reverse()
print("nota mas baja: ",list[0])