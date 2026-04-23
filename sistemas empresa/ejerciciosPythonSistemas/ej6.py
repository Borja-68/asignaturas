cadena=input("introduce una cadena: ")
subcadena=input("introduce la subcadena: ")
mensaje=""
if(cadena.startswith(subcadena)):
    mensaje="contiene subcadena"
else: 
    mensaje="no contiene subcadena"
print(mensaje)