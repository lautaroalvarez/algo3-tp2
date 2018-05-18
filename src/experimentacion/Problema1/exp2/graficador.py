import matplotlib.pyplot as plt
import sys, csv
import numpy as np

#-----Formato esperado-------------------------------
#--filas, columnas, paredes, num_repeticion, tiempo--
#----------------------------------------------------

entrada_normal = csv.reader(open("normal.csv", "rb"))
entrada_variada = csv.reader(open("variada.csv", "rb"))

labelx = "Cantidad de columnas"
labely = "Tiempo de ejecucion (ms)"
titulo = "Cantidad de columnas vs Tiempo de ejecucion"

ejex = np.empty((0))
cotaLineal = np.empty((0))
cotaCuadratica = np.empty((0))
ejey1 = np.empty((0))
ejey2 = np.empty((0))

maximo = 0;

columnas_actual = -1
datos_actual = np.empty((0))
for row in entrada_normal:
	if columnas_actual!=row[1]:
		if columnas_actual!=-1:
			datos_actual = np.delete(datos_actual, np.argmax(datos_actual))
			datos_actual = np.delete(datos_actual, np.argmin(datos_actual))
			ejex = np.append(ejex, np.array([columnas_actual]), axis=0)
			if datos_actual[np.argmax(datos_actual)] > maximo:
				maximo = datos_actual[np.argmax(datos_actual)]
			cotaLineal = np.append(cotaLineal, np.array([500 + 15 * float(columnas_actual)]), axis=0)
			cotaCuadratica = np.append(cotaCuadratica, np.array([500 + float(columnas_actual)*float(columnas_actual)]), axis=0)
			ejey1 = np.append(ejey1, np.array([np.mean(datos_actual)]), axis=0)
		columnas_actual = row[1]
		datos_actual = np.empty((0))
	datos_actual = np.append(datos_actual, [float(row[4])])

if columnas_actual!=-1:
	datos_actual = np.delete(datos_actual, np.argmax(datos_actual))
	datos_actual = np.delete(datos_actual, np.argmin(datos_actual))
	ejex = np.append(ejex, np.array([columnas_actual]), axis=0)
	if datos_actual[np.argmax(datos_actual)] > maximo:
		maximo = datos_actual[np.argmax(datos_actual)]
	cotaLineal = np.append(cotaLineal, np.array([500 + 15 * float(columnas_actual)]), axis=0)
	cotaCuadratica = np.append(cotaCuadratica, np.array([500 + float(columnas_actual)*float(columnas_actual)]), axis=0)
	ejey1 = np.append(ejey1, np.array([np.mean(datos_actual)]), axis=0)



columnas_actual = -1
datos_actual = np.empty((0))
for row in entrada_variada:
	if columnas_actual!=row[1]:
		if columnas_actual!=-1:
			datos_actual = np.delete(datos_actual, np.argmax(datos_actual))
			datos_actual = np.delete(datos_actual, np.argmin(datos_actual))
			if datos_actual[np.argmax(datos_actual)] > maximo:
				maximo = datos_actual[np.argmax(datos_actual)]
			ejey2 = np.append(ejey2, np.array([np.mean(datos_actual)]), axis=0)
		columnas_actual = row[1]
		datos_actual = np.empty((0))
	datos_actual = np.append(datos_actual, [float(row[4])])

if columnas_actual!=-1:
	datos_actual = np.delete(datos_actual, np.argmax(datos_actual))
	datos_actual = np.delete(datos_actual, np.argmin(datos_actual))
	if datos_actual[np.argmax(datos_actual)] > maximo:
		maximo = datos_actual[np.argmax(datos_actual)]
	ejey2 = np.append(ejey2, np.array([np.mean(datos_actual)]), axis=0)


plt.plot(ejex, ejey1)
plt.plot(ejex, ejey2)
plt.plot(ejex, cotaLineal)
plt.plot(ejex, cotaCuadratica)

x1,x2,y1,y2 = plt.axis()
plt.axis((x1,x2,0,maximo * 1.05))

plt.xlabel(labelx)
plt.ylabel(labely)
plt.title(titulo)
plt.legend(["Mapas sin paredes", "Mapas con paredes variadas", "f(n) = 500 + 15n", "f(n) = 500 + n^2"], loc='lower right')

plt.savefig("salida.png")