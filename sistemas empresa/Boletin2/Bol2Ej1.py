cadena=input("escribe una cadena: ")

dic={}
while(cadena!=""):
    dic.update({cadena[0]: cadena.count(cadena[0])})
    cadena=cadena.replace(cadena[0],"")
print(dic)