package soluciones;

import java.util.*;

public class Ejercicio3 {

	public static class VecPes {
		public int vecino;
		public int peso;

		VecPes(int v, int p) {
			vecino = v;
			peso = p;
		}
	}

	public static class Nodo {
		public int id;
		public LinkedList<VecPes> vecinos;
		public int distancia;
		public int anterior;

		Nodo(){
			id= -1;
			vecinos = new LinkedList<VecPes>();
			distancia = -1;
			anterior = -1;

		}
		Nodo(int i, LinkedList<VecPes> v, int d) {
			id = i;
			vecinos = v;
			distancia = d;
			anterior = -1;
		}
	}

	public static void main(String[] args) {

		//long time_start, time_end;
		//time_start = System.currentTimeMillis();

		Scanner capt = new Scanner(System.in);
		int n = capt.nextInt();
		int m = capt.nextInt();
		Nodo[] nodos = new Nodo[n];
		for (int i = 0; i < n; ++i) {
			LinkedList<VecPes> vacia = new LinkedList<VecPes>();
			int distancia = 0;
			if (i != 0) {
				distancia = -1;
			}
			nodos[i] = new Nodo(i, vacia, distancia);
		}
		for (int i = 0; i < m; i++) {
			int origen = capt.nextInt() - 1;
			int destino = capt.nextInt() - 1;
			int peso = capt.nextInt();
			VecPes nuevo = new VecPes(destino, peso);
			nodos[origen].vecinos.add(nuevo);
		}
		Run(nodos);
		capt.close();
		//time_end = System.currentTimeMillis();
		//System.out.println(time_end - time_start);
	}

	public static void Run(Nodo[] nodos) {
		// Algoritmo de dijkstra
		LinkedList<Nodo> auxiliar = new LinkedList<Nodo>(); // ver si de array
															// hay un toList
		for (int i = 0; i < nodos.length; ++i) {
			auxiliar.add(nodos[i]);
		}
		while (auxiliar.size() > 0) {
			Nodo minimo = dameMinimo(auxiliar);
			for (VecPes v : minimo.vecinos) {
				relajar(minimo, nodos[v.vecino], v.peso);
			}
		}
		// printeo
		System.out.println(nodos[nodos.length - 1].distancia);
		if (nodos[nodos.length - 1].distancia != -1) {
			LinkedList<Integer> estaciones = new LinkedList<Integer>();
			Nodo actual = nodos[nodos.length - 1];
			estaciones.add(actual.id);
			while (actual != nodos[0]) {
				actual = nodos[actual.anterior];
				estaciones.add(actual.id);
			}
			System.out.println(estaciones.size());
			for (int i = estaciones.size() - 1; i >= 0; --i) {
				if (i != estaciones.size() - 1) {
					System.out.print(" ");
				}
				System.out.print(estaciones.get(i) + 1);
			}
			System.out.println();
		}
	}

	public static Nodo dameMinimo(LinkedList<Nodo> lista) {
		Nodo resultado = lista.get(0);
		for (int i = 1; i < lista.size(); ++i) {
			if (lista.get(i).distancia != -1 && lista.get(i).distancia < resultado.distancia) {
				resultado = lista.get(i);
			}
		}
		lista.remove(resultado);
		return resultado;
	}

	public static void relajar(Nodo minimo, Nodo u, int peso) {
		if (minimo.distancia + peso < u.distancia || u.distancia == -1) {
			u.distancia = minimo.distancia + peso;
			u.anterior = minimo.id;
		}
	}
}
