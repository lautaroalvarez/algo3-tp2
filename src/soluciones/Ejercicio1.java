package soluciones;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Ejercicio1{


	public static void main(String[] args){

		//long time_start, time_end;
		//time_start = System.currentTimeMillis();

		/*----------------------------------------------------------------
		--------------------- PROCESAMIENTO DE ENTRADA -------------------
		----------------------------------------------------------------*/
		ArrayList<ArrayList<LinkedList<ArrayList<Integer>>>> grafo = new ArrayList<ArrayList<LinkedList<ArrayList<Integer>>>>();
		
		//-- lee la primera linea de la entrada
		Scanner capt = new Scanner(System.in);
		//------- F
		int cantidad_filas = capt.nextInt();
		//------- C
		int cantidad_columnas = capt.nextInt();
		//------- P
		int maximo_paredes = capt.nextInt();

		//-- inicializa los nodos "desde" y "hasta"
		int desde = -1;
		int hasta = -1;

		//-- esta estructura se va a usar momentaneamente para guardar que nodos son paredes
		//--		1 -> es pared, 0 -> no es pared
		ArrayList<Integer> paredes = new ArrayList<Integer>();

		//-- nodo que usaremos de auxiliar
		ArrayList<Integer> nuevo_nodo_aux;

		//-- inicializa el contador de número de nodo
		int num_nodo = 0;
		
		//-- recorre todas las filas y columnas de la entrada
		for (int i = 0; i < cantidad_filas; i++) {
			String aux = capt.next();
			for (int j = 0; j < cantidad_columnas; j++) {
				//System.out.print(aux.charAt(j));
				//-- filtra las paredes externas (que no nos importan)
				if (i > 0 && j > 0 && i < cantidad_filas - 1 && j < cantidad_columnas - 1) {
					//-- verifica si el nodo es una pared
					if (aux.charAt(j) == '#') {
						paredes.add(num_nodo, 1);
					} else {
						paredes.add(num_nodo, 0);
					}
					//-- verifica si el nodo es "desde" o "hasta"
					if (aux.charAt(j) == 'o') {
						desde = num_nodo;
					} else if (aux.charAt(j) == 'x') {
						hasta = num_nodo;
					}

					//-- crea el nodo
					grafo.add(num_nodo, new ArrayList<LinkedList<ArrayList<Integer>>>());
					for (int k = 0; k <= maximo_paredes; k++) {
						grafo.get(num_nodo).add(k, new LinkedList<ArrayList<Integer>>());
					}

					//-- busca vecinos que ya haya cargado
					//---- el de la izquierda
					if (j > 1) {
						for (int k = 0; k <= maximo_paredes; k++) {
							//--chequea que la cantidad de paredes no excede el maximo
							if (k + paredes.get(num_nodo-1) <= maximo_paredes) {
								//-- agrego como vecino al vecino al nodo actual
								nuevo_nodo_aux = new ArrayList<Integer>();
								nuevo_nodo_aux.add(0, num_nodo-1);
								nuevo_nodo_aux.add(1, k + paredes.get(num_nodo-1));
								grafo.get(num_nodo).get(k).add(nuevo_nodo_aux);
							}
							//--chequea que la cantidad de paredes no excede el maximo
							if (k + paredes.get(num_nodo) <= maximo_paredes) {
								//-- agrego como vecino al nodo actual
								nuevo_nodo_aux = new ArrayList<Integer>();
								nuevo_nodo_aux.add(0, num_nodo);
								nuevo_nodo_aux.add(1, k + paredes.get(num_nodo));
								grafo.get(num_nodo-1).get(k).add(nuevo_nodo_aux);
							}
						}
					}
					//---- el de arriba
					if (i > 1) {
						for (int k = 0; k <= maximo_paredes; k++) {
							//--chequea que la cantidad de paredes no excede el maximo
							if (k + paredes.get(num_nodo - cantidad_columnas+2) <= maximo_paredes) {
								//-- agrego como vecino al vecino al nodo actual
								nuevo_nodo_aux = new ArrayList<Integer>();
								nuevo_nodo_aux.add(0, num_nodo - cantidad_columnas+2);
								nuevo_nodo_aux.add(1, k + paredes.get(num_nodo - cantidad_columnas+2));
								grafo.get(num_nodo).get(k).add(nuevo_nodo_aux);
							}
							//--chequea que la cantidad de paredes no excede el maximo
							if (k + paredes.get(num_nodo) <= maximo_paredes) {
								//-- agrego como vecino al nodo actual
								nuevo_nodo_aux = new ArrayList<Integer>();
								nuevo_nodo_aux.add(0, num_nodo);
								nuevo_nodo_aux.add(1, k + paredes.get(num_nodo));
								grafo.get(num_nodo - cantidad_columnas+2).get(k).add(nuevo_nodo_aux);
							}
						}
					}
					num_nodo++;
				}
			}
			//System.out.println();
		}


		capt.close();
		/*----------------------------------------------------------------
		------------------------ LLAMADA AL BFS- -------------------------
		----------------------------------------------------------------*/		
		int minimo = bfs(grafo, maximo_paredes, desde, hasta);
		//int minimo = 0;
		
		//time_end = System.currentTimeMillis();
		//System.out.println(time_end - time_start);
		System.out.println(minimo);
	}

	public static int bfs(ArrayList<ArrayList<LinkedList<ArrayList<Integer>>>> grafo, int maximo_paredes, int desde, int hasta) {
		//-- cantidad de vértices del grafo
		int cant_vertices = grafo.size();

		//-- array que usaremos de auxiliar
		ArrayList<Integer> tupla_aux;

		// se guarda la distancia al vértice "desde" de cada vertice (-1 => no fue visitado)
		int[][] distanciaVertices = new int[cant_vertices][maximo_paredes+1];

		//-- inicializa todos los visitados en false y las distancias en -1
		for(int i = 0; i < cant_vertices; ++i){
			for (int j = 0; j <= maximo_paredes; j++) {
				//visitado[i][j] = false;
				distanciaVertices[i][j] = -1;
			}
		}

		//-- setea la distancia desde vértice "desde" al vértice "desde" en 0 (es el mismo)
		distanciaVertices[desde][0] = 0;

		//-- inicializa la cola de < vértice, cantidad de paredes > actuales que se van a ver
		LinkedList<ArrayList<Integer>> q = new LinkedList<ArrayList<Integer>>();

		//-- agrega el vértice "desde" con cantidad de paredes rotas igual a 0 a la cola
		tupla_aux = new ArrayList<Integer>();
		tupla_aux.add(0, desde);
		tupla_aux.add(1, 0);
		q.add(tupla_aux);

		//-- mientras haya vértices en la cola...
		while(q.size() != 0) {
			//-- toma un vértice de la cola
			ArrayList<Integer> currentVertex = q.poll();
			//-- guardamos en variables para hacer mas claro el código
			int num_vertice = currentVertex.get(0);
			int cant_paredes_vertice = currentVertex.get(1);

			//-- para cada vecino de este vértice (con esa cantidad de paredes rotas)...
			for(int j = 0; j < grafo.get(num_vertice).get(cant_paredes_vertice).size(); ++j) {
				//-- busca el vecino
				ArrayList<Integer> vecinoActual = grafo.get(num_vertice).get(cant_paredes_vertice).get(j);
				//-- guardamos en variables para hacer mas claro el código
				int num_vecino = vecinoActual.get(0);
				int cant_paredes_vecino = vecinoActual.get(1);

				//-- chequea que no haya sido visitado
				if (distanciaVertices[num_vecino][cant_paredes_vecino] == -1) {
					//-- y se guarda la distancia del actual + 1
					distanciaVertices[num_vecino][cant_paredes_vecino] = distanciaVertices[num_vertice][cant_paredes_vertice] + 1;
					//-- se agrega el vecino actual con la cantidad de paredes correspondiente a la cola
					tupla_aux = new ArrayList<Integer>();
					tupla_aux.add(0, num_vecino);
					tupla_aux.add(1, cant_paredes_vecino);
					q.add(tupla_aux);
				}
			}
		}

		//---calcula el mínimo de los resultados del nodo "hasta"
		int minimo = -1;
		for (int i = 0; i <= maximo_paredes; i++) {
			if(distanciaVertices[hasta][i] >= 0 && (minimo < 0 || minimo > distanciaVertices[hasta][i])) {
				minimo = distanciaVertices[hasta][i];
			}
		}
		return minimo;
	}
}