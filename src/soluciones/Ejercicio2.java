package soluciones;

//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Ejercicio2 {

	public class Grafo {
		public LinkedList<Arista> aristas;
		public int nodos;

		Grafo(LinkedList<Arista> aristas1, int nodos1) {
			aristas = aristas1;
			nodos = nodos1;
		}
	}

	public static void main(String[] args) throws IOException {
		Run();
	}

	public static void Run() throws IOException {
		// Con esta funcion se lee la entrada
		// y se genera la salida
		Scanner capt = new Scanner(System.in);
		int F = capt.nextInt();
		int C = capt.nextInt();
		char[][] grilla = new char[F][C];
		// String[F][C] grilla;

		for (int i = 0; i < F; i++) { //O(N*M)
			String actual = capt.next(); // Listo
			for (int j = 0; j<C; ++j) {
				grilla[i][j] = actual.charAt(j);
			}
		}

		LinkedList<Arista> aristas = new LinkedList<Arista>();
		LinkedList<Integer> listapuntos = new LinkedList<Integer>();

		for (int i = 0; i < F; i++) { //O(N*M)
			for (int j = 0; j < C; j++) {
				switch (grilla[i][j]) {
				case '#':
					break;
				case '.':
					if (j < C - 1) {
						if (grilla[i][j + 1] == '.') { // lo uno con el de la
														// derecha
							Arista nueva = new Arista(i * C + j, i * C + j + 1, 0);
							aristas.add(nueva);
						}
					}
					if (i < F - 1) { // lo uno con la de abajo
						if (grilla[i + 1][j] == '.') {
							Arista nueva = new Arista(i * C + j, (i + 1) * C + j, 0);
							aristas.add(nueva);
						}
					}
					listapuntos.add(i * C + j);

					break;
				default: // es un numero
					// Hay que buscar los dos puntos que lo rodean!!
					// LinkedList<Integer> nodos = new LinkedLink<Integer>();
					int durezaPared = Character.getNumericValue(grilla[i][j]);
					int punto1 = -1;
					int punto2 = -1;
					if (j > 0 && grilla[i][j - 1] == '.') { // izquierda
						punto1 = i * C + (j - 1);
					}
					if (j < C - 1 && grilla[i][j + 1] == '.') { // tengo derecha
						if(punto1 == -1){
							punto1 = i * C + (j + 1);
						}else{
							punto2 = i * C + (j + 1);
						}
					}
					if (i > 0 && grilla[i - 1][j] == '.') { // tengo arriba;
						if(punto1 == -1){
							punto1 = (i-1) * C + j;
						}else{
							punto2 = (i-1) * C + j;
						}
					}
					if (i < F - 1 && grilla[i + 1][j] == '.') { // tengo abajo;
						if(punto1 == -1){
							punto1 = (i+1) * C + j;
						}else{
							punto2 = (i+1) * C + j;
						}
					}
					Arista nueva = new Arista(punto1, punto2, durezaPared);
					aristas.add(nueva);
					break;
				} // Agrego las aristas de ese punto
			}
		}

		capt.close();

	/*	for(Arista a : aristas){
			System.out.print(a.toString());
		}*/
		Correr(aristas, F * C, aristas.size(), listapuntos);
	}

	private static void Correr(LinkedList<Arista> aristasD, int N, int M, LinkedList<Integer> listapuntos) {
		// Esta funcion lo que hace es correr y acomodar los valores para la
		// salida como dice el enunciado.
		System.out.println(Kruskal(N, aristasD, M, listapuntos));
	}

	// clase chiquita que va a representar a una arista.
	public static class Arista {
		int peso;
		int VerticeA;
		int VerticeB;

		public Arista(int Ai, int Bi, int Li) { // pongo el mas chico primero,
												// asi me indica el camino en
												// orden desde un cuarto a otro
												// (ya que suponemos que
												// empezamos desde el 0 hacia
												// adelante).
			if (Ai < Bi) {
				VerticeA = Ai;
				VerticeB = Bi;
			} else {
				VerticeA = Bi;
				VerticeB = Ai;
			}
			peso = Li;
		}

		public int getVB() {
			return this.VerticeB;
		}

		public int getVA() {
			return this.VerticeA;
		}

		/* Esto es para generar el grafo al azar */
		void decVA() {
			this.VerticeA -= 1;
		}

		void decVB() {
			this.VerticeB -= 1;
		}

		////////////////////////////////////////
		public String toString() {
			String result = "";
			result = "(" + String.valueOf(this.getVA()) + "," + String.valueOf(this.getVB()) + ","
					+ String.valueOf(this.peso) + ")";

			return result;

		}
	}

	private static int Kruskal(int N, LinkedList<Arista> aristasD, int M, LinkedList<Integer> listapuntos) {
		// LOS NODOS VAN DESDE 0 hasta N
		Comparator<Arista> comparator = new Comparator<Arista>() {
			@Override
			public int compare(Arista A1, Arista A2) {
				return A1.peso < A2.peso ? -1 : A1.peso == A2.peso ? 0 : 1; // compara por peso.
			}
		};

		ConjuntoComponentes conj = new ConjuntoComponentes(N,listapuntos); // esto te crea
																	// las
																	// componentes
																	// conexas con
																	// cada vertice.

		int solucion = 0;
    	if(M>0){
	    	PriorityQueue<Arista> minHeap = new PriorityQueue<Arista>(M, comparator);
			Iterator<Arista> it = aristasD.iterator();
			while (it.hasNext()) { // O(m) voy agregando al heap.
				minHeap.add(it.next()); // O(log(m))
			}

			while (minHeap.size() > 0) { // O(m)
				Arista candidata = minHeap.poll(); // O(log(m))
				int V1 = candidata.getVA();
				int V2 = candidata.getVB();
				if (!conj.MismaComponente(V1, V2)) // estan en distintas componentes
												// ( de esta forma evitamos
												// ciclos)
				{
					conj.Unir(V1, V2); // ahora son de la misma componente, porque
									// lo voy a agregar a mi AGM
					solucion += candidata.peso; // agrego a mi solucion.
				}
			}
    	}
		if (!conj.Conexo()) {
			solucion = -1;
		}
		return solucion;
	}
/*
	public static void Promediar(LinkedList<Grafo> prueba, String archivo) throws IOException {
		File file = new File(archivo + ".txt");
		file.createNewFile();
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);

		for (Grafo grafo : prueba) {
			double promedio = 0;
			for (int j = 0; j < 10000; j++) // por el garbage collector
			{
				Kruskal(grafo.nodos, grafo.aristas, grafo.aristas.size());
			}

			for (int j = 0; j < 1000; j++) // para sacar el promedio
			{
				long tInicio = System.nanoTime();
				Kruskal(grafo.nodos, grafo.aristas, grafo.aristas.size());
				long tFin = System.nanoTime();
				long tTotal = (tFin - tInicio);
				promedio += tTotal;
			}
			int tamano = grafo.aristas.size();
			promedio = promedio / 1000;
			bw.write(tamano + " " + promedio);
			bw.newLine();

		}
		bw.close();
	}
*/
}
