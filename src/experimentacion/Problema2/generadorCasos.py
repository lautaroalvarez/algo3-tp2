import sys
import numpy as np
from random import randint

def cantVecinosParedes(x,y):
	cant = 0
	if mapa[x-1,y] != ".":
		cant = cant + 1
	if mapa[x+1,y] != ".":
		cant = cant + 1
	if mapa[x,y-1] != ".":
		cant = cant + 1
	if mapa[x,y+1] != ".":
		cant = cant + 1
	return cant

def puedoPonerParedEn(x, y):
	puedo = 0
	#posicion actual
	if mapa[x,y] == ".":
		#vecinos directos
		if cantVecinosParedes(x,y) == 0:
			#vecinos de vecinos
			if cantVecinosParedes(x-1,y) == 0 and cantVecinosParedes(x+1,y) == 0 and cantVecinosParedes(x,y-1) == 0 and cantVecinosParedes(x,y+1) == 0:
				puedo = 1
	return puedo


archivo_salida = sys.argv[1]
filas = int(sys.argv[2])
columnas = int(sys.argv[3])
tipo = 0
if len(sys.argv) > 4:
	tipo = int(sys.argv[4])
extra = 0
if len(sys.argv) > 5:
	extra = int(sys.argv[5])
	if extra == 0:
		extra = randint(1, filas * columnas / 4)

f = open(archivo_salida, 'w')

f.write(str(filas)+" "+str(columnas)+"\n")

mapa = np.chararray((filas,columnas))

mapa[:] = "."

for i in xrange(0,filas):
	mapa[i,0] = "#"
	mapa[i,columnas-1] = "#"
for j in xrange(0,columnas):
	mapa[0,j] = "#"
	mapa[filas-1,j] = "#"

for i in xrange(1,filas-1):
	for j in xrange(1,columnas-1):
		if tipo == 1:
			#--> arma salas
			if j%2 == 0 and j/2 <= extra and j < columnas-2:
				mapa[i,j] = randint(1,10)
		elif tipo == 2:
			#--> cantidad de paredes
			if extra > 0 and puedoPonerParedEn(i,j):
				rand = randint(0,11)
				if rand < 2:
					#paredes arriba y abajo
					mapa[i,j] = randint(1,10)
					mapa[i-1,j] = "#"
					mapa[i+1,j] = "#"
				elif rand < 4:
					#paredes izquierda y derecha
					mapa[i,j] = randint(1,10)
					mapa[i,j-1] = "#"
					mapa[i,j+1] = "#"
				elif rand < 6:
					#paredes izquierda y arriba
					mapa[i,j] = randint(1,10)
					mapa[i,j-1] = "#"
					mapa[i-1,j] = "#"
				elif rand < 8:
					#paredes derecha y arriba
					mapa[i,j] = randint(1,10)
					mapa[i,j+1] = "#"
					mapa[i-1,j] = "#"
				elif rand < 10:
					#paredes derecha y abajo
					mapa[i,j] = randint(1,10)
					mapa[i,j+1] = "#"
					mapa[i+1,j] = "#"
				else:
					#paredes izquierda y abajo
					mapa[i,j] = randint(1,10)
					mapa[i,j-1] = "#"
					mapa[i+1,j] = "#"
				extra = extra - 1


print mapa


for i in xrange(0,filas):
	for j in xrange(0,columnas):
			f.write(mapa[i,j])
	if (i!=filas-1):
		f.write("\n")