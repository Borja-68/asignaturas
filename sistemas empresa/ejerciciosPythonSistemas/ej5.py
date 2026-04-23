
num=-1
res=0
numeros=0
while(num!=0):
    num=float(input("introduce in numero (0 para salir): "))
    res+=num
    if(num!=0):
        numeros+=1

print("resultado: ",res)
print("media ",res/numeros)