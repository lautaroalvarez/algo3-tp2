package soluciones;

import soluciones.Ejercicio3.Nodo;
import soluciones.Ejercicio3.VecPes;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Vector;

public class Ejercicio3Generadores {
	
	
		public boolean unidos(int v1, int v2, Nodo[] grafo){
			boolean esta = false;
			for(VecPes vecino : grafo[v1].vecinos ){
				esta |= (v2 == vecino.vecino);
			}
			return esta;
		}
	
	public LinkedList<Nodo[]> listaGrafoCompleto(int N){
		LinkedList<Nodo[]> res = new LinkedList<Nodo[]>();
		for(int i = 1; i <= N; i++){
			res.add(grafoCompleto(i*5));
		}
		return res;
	}
	
	
	public LinkedList<Nodo[]> listaGrafoAzar(int N){
		
		LinkedList<Nodo[]> res = new LinkedList<Nodo[]>();
		for(int i = 1; i <= N; i++){
			res.add(grafoAzar(i*5));
		}
		return res;
	}
	
	public LinkedList<Nodo[]> listaGrafoArbol(int N){
		LinkedList<Nodo[]> res = new LinkedList<Nodo[]>();
		for(int i = 1; i <= N; i++){
			res.add(grafoArbol(i*25*9));
		}
		return res;
	}
	
	public Nodo[] grafoArbol(int N){
		Nodo[] ar = new Nodo[N];
		for (int i = 0; i < N; ++i) {
			LinkedList<VecPes> vacia = new LinkedList<VecPes>();
			int distancia = 0;
			if (i != 0) {
				distancia = -1;
			}
			ar[i] = new Nodo(i, vacia, distancia);
		}
		
		Random r = new Random();
		int verticeAux = 1;
		while(verticeAux<N){
			int verticeRandom = r.nextInt(verticeAux);
			VecPes vec1 = new VecPes(verticeRandom, r.nextInt(2*N));
			VecPes vec2 = new VecPes(verticeAux, r.nextInt(2*N));
			ar[verticeRandom].vecinos.add(vec2);
			ar[verticeAux].vecinos.add(vec1);

			verticeAux++;
		}
		return ar;
		/*
		LinkedList<Arista> ar = new LinkedList<Arista>();
		Random rnd = new Random();
		int i = 0, j = 1;
		while(j < N)	//nodos van de 0 a N-1 aca
		{
			int p = rnd.nextInt(N-j);	//hago random de cuantos hijos va a tener el nodo i
			while(p >= 0)
			{
				Arista nueva = new Arista(i, j, rnd.nextInt(2*N)); //los hijos siempre son un nodo sin arista, para no formar ciclos
				j++;
				p--;
				ar.add(nueva);
			}
			i++;
		}
		
		Grafo res = new Grafo(ar, N);
		
		return res;
	*/
	}
	
	public Nodo[] grafoCompleto(int N){
		Nodo[] res = new Nodo[N];
		for (int i = 0; i < N; ++i) {
			LinkedList<VecPes> vacia = new LinkedList<VecPes>();
			int distancia = 0;
			if (i != 0) {
				distancia = -1;
			}
			res[i] = new Nodo(i, vacia, distancia);
		}
		
		int actual = 0;
		
		while(actual < N){
			for(int i = actual +1; i< N ; i++){
				Random r = new Random();
				VecPes vec1 = new VecPes(actual, r.nextInt(2*N));
				VecPes vec2 = new VecPes(i, r.nextInt(2*N));
				
				res[actual].vecinos.add(vec2);
				res[i].vecinos.add(vec1);
			}
			actual++;
		}
		return res;
	}
	
	public Nodo[] grafoAzar(int N){
		Nodo[] res = new Nodo[N];
		for (int i = 0; i < N; ++i) {
			LinkedList<VecPes> vacia = new LinkedList<VecPes>();
			int distancia = 0;
			if (i != 0) {
				distancia = -1;
			}
			res[i] = new Nodo(i, vacia, distancia);
		}
		Random r = new Random();
		int M = r.nextInt((N)*(N-1));
		while(M>0){ 
			int v1 = r.nextInt(N);
			int v2= v1;
			while(v2 == v1){
				v2 = r.nextInt(N);
			}
			VecPes vec1 = new VecPes(v2,r.nextInt(2*N));
			if(!unidos(v1,v2,res)){
				res[v1].vecinos.add(vec1);
				M--;
			}
		}
			
	
		return res;
	}
	
	
	/*private  boolean esta(LinkedList<Arista> lst, Arista arista){
		boolean res = false;
		for(Arista a : lst){
			res |= (a.getVA() == arista.getVA() && a.getVB() == arista.getVB());
		}
		return res;
		
	}
	
	private int grado(LinkedList<Arista> lst, int vertice){
		int res = 0;
		for(Arista arista: lst){
			if(arista.getVA() == vertice || arista.getVB() == vertice){
				res++;
			}
		}
		return res;
	}
	
	private boolean conexo(Grafo G){
		Vector<Vector<Integer> > listaVec = new Vector<Vector<Integer>>();
		for(int i = 0; i < G.nodos; i++){
			Vector<Integer> aux = new Vector<Integer>();
			listaVec.add(aux);
		}
		for(Arista a : G.aristas){
			listaVec.get(a.getVA()).add(a.getVB());
			listaVec.get(a.getVB()).add(a.getVA());
		}
		
		boolean[] visitados = new boolean[G.nodos];
		for(int i = 0; i < G.nodos; i++){
			visitados[i] = false;
		}
		
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(0);
		visitados[0] = true;
		
		while(!q.isEmpty()){
			Integer actual = q.poll();
			
			for(Integer vec : listaVec.get(actual)){
				if(!visitados[vec]){
					q.add(vec);
					visitados[vec] = true;
				}
				
			}
		}
		
		for(boolean vis : visitados){
			if(!vis) return false;
		}
		return true;
	}
	*/
	public static void main(String[] args) throws IOException{
		int cantidadCasos = 3;
		Ejercicio3Generadores s = new Ejercicio3Generadores();
		
		Nodo[] a1 = s.grafoArbol(cantidadCasos);
		Nodo[] b1 = s.grafoCompleto(cantidadCasos);
		Nodo[] c1 = s.grafoArbol(cantidadCasos);
		LinkedList<Nodo[]> a = s.listaGrafoAzar(cantidadCasos);
		LinkedList<Nodo[]> b = s.listaGrafoCompleto(cantidadCasos);
		LinkedList<Nodo[]> c = s.listaGrafoArbol(cantidadCasos);

		System.out.println("Fin listas");
		
		
		//Ep5.Promediar(b, "Ej2Azar"+cantidadCasos+"Casos");
		System.out.println("Termine al azar");
			
		
		
	}
	
	//BORRAR ESTO DE ABAJO ALL
	//↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	
	@SuppressWarnings("unused")
	private static void print(Nodo[] g){
		for(Nodo n : g){
			for(VecPes v : n.vecinos){
				System.out.print("{"+v.vecino + ", " + v.peso+ "}");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	//«»«»«»«»«»«»«»«»««»«»«»«»«»«»«»«»

}

