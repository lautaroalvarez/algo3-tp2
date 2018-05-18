import matplotlib.pyplot as plt
import sys, csv, math
import numpy as np

#-----Formato esperado-------------------------------
#--filas, columnas, paredes, num_repeticion, tiempo--
#----------------------------------------------------

entrada_normal = csv.reader(open("normal.csv", "rb"))
entrada_variada = csv.reader(open("variada.csv", "rb"))

labelx = "Cantidad de filas"
labely = "Tiempo de ejecucion (ms)"
titulo = "Cantidad de filas vs Tiempo de ejecucion"

ejex = np.empty((0))
cotaLog = np.empty((0))
cotaNLog = np.empty((0))
ejey1 = np.empty((0))
ejey2 = np.empty((0))

maximo = 0;

filas_actual = -1
datos_actual = np.empty((0))
for row in entrada_normal:
	if filas_actual!=row[0]:
		if filas_actual!=-1:
			datos_actual = np.delete(datos_actual, np.argmax(datos_actual))
			datos_actual = np.delete(datos_actual, np.argmin(datos_actual))
			ejex = np.append(ejex, np.array([filas_actual]), axis=0)
			if datos_actual[np.argmax(datos_actual)] > maximo:
				maximo = datos_actual[np.argmax(datos_actual)]
			cotaLog = np.append(cotaLog, np.array([200 + math.log(float(filas_actual))]), axis=0)
			cotaNLog = np.append(cotaNLog, np.array([200 + float(filas_actual) * math.log(float(filas_actual))]), axis=0)
			ejey1 = np.append(ejey1, np.array([np.mean(datos_actual)]), axis=0)
		filas_actual = row[0]
		datos_actual = np.empty((0))
	datos_actual = np.append(datos_actual, [float(row[3])])

if filas_actual!=-1:
	datos_actual = np.delete(datos_actual, np.argmax(datos_actual))
	datos_actual = np.delete(datos_actual, np.argmin(datos_actual))
	ejex = np.append(ejex, np.array([filas_actual]), axis=0)
	if datos_actual[np.argmax(datos_actual)] > maximo:
		maximo = datos_actual[np.argmax(datos_actual)]
	cotaLog = np.append(cotaLog, np.array([200 + math.log(float(filas_actual))]), axis=0)
	cotaNLog = np.append(cotaNLog, np.array([200 + float(filas_actual) * math.log(float(filas_actual))]), axis=0)
	ejey1 = np.append(ejey1, np.array([np.mean(datos_actual)]), axis=0)



filas_actual = -1
datos_actual = np.empty((0))
for row in entrada_variada:
	if filas_actual!=row[0]:
		if filas_actual!=-1:
			datos_actual = np.delete(datos_actual, np.argmax(datos_actual))
			datos_actual = np.delete(datos_actual, np.argmin(datos_actual))
			if datos_actual[np.argmax(datos_actual)] > maximo:
				maximo = datos_actual[np.argmax(datos_actual)]
			ejey2 = np.append(ejey2, np.array([np.mean(datos_actual)]), axis=0)
		filas_actual = row[0]
		datos_actual = np.empty((0))
	datos_actual = np.append(datos_actual, [float(row[3])])

if filas_actual!=-1:
	datos_actual = np.delete(datos_actual, np.argmax(datos_actual))
	datos_actual = np.delete(datos_actual, np.argmin(datos_actual))
	if datos_actual[np.argmax(datos_actual)] > maximo:
		maximo = datos_actual[np.argmax(datos_actual)]
	ejey2 = np.append(ejey2, np.array([np.mean(datos_actual)]), axis=0)


plt.plot(ejex, ejey1)
plt.plot(ejex, ejey2)
plt.plot(ejex, cotaNLog)
plt.plot(ejex, cotaLog)

x1,x2,y1,y2 = plt.axis()
#plt.axis((x1,x2,0,maximo * 1.05))
plt.axis((x1,x2,0,3000))

plt.xlabel(labelx)
plt.ylabel(labely)
plt.title(titulo)
plt.legend(["Mapas sin paredes", "Mapas con paredes variadas", "f(n) = 200 + n * log(n)", "f(n) = 200 + log(n)"], loc='right')

plt.savefig("salida.png")