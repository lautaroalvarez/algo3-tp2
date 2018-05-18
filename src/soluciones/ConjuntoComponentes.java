package soluciones;
// Clase que implementa el disjointSet, lo que hace es agrupar componentes con un representante, el cual indica que todos los elementos que pertenecen a ese subconjunto son de la misma componente.
import java.util.LinkedList;

public class ConjuntoComponentes {

	static int padre[];   //Este arreglo contiene el padre del i-esimo nodo
	static int rango[];   //Arreglo donde guardo la altura de cada vertice
	public LinkedList<Integer> listaPuntos;
	ConjuntoComponentes(int cantVertices, LinkedList<Integer> list) {
		padre = new int[cantVertices];
		rango = new int[cantVertices];
		listaPuntos = list;
	    for(int i = 0 ; i < cantVertices ; i++) {
	        padre[i] = i;      //el padre de cada vertice es el mismo vertice
	        rango[i] = 0;      //la altura de cada vertice es 0
	    }
	}
	
	 int Representante(int x) { //O(log(n)) Porque el arbol queda semi balanceado, por como se va uniendo.
	    if(x == padre[x]){          //Si estoy en la raiz..
	        return x;                   
	    }
	    else return padre[x] = Representante(padre[x]); //Compresion de caminos
	}
	 void Unir(int x ,int y) { //O(1)
	    int RaizX = Representante(x);    //Obtengo el representante de los vertices.
	    int RaizY = Representante(y);
	    if(rango[RaizX] > rango[RaizY]) { //en este caso la altura de la componente del vertice X es
	                                           //mayor que la altura de la componente del vertice Y
	        padre[RaizY] = RaizX;            //el padre de ambas componentes sera el de mayor altura
	    }
	    else{                    //en este caso la altura de la componente del vertice Y es mayor o igual que la de X
	        padre[RaizX] = RaizY;            //el padre de ambas componentes sera el de mayor altura
	        if(rango[RaizX] == rango[RaizY]) { //si poseen la misma altura
	            rango[RaizY]++;              //incremento el rango de la nueva raiz
	        }
	    }	    
	}

	 boolean MismaComponente(int x ,int y) {
	    if(Representante(x) == Representante(y)) return true;   //si poseen el mismo representante
	    return false;
	}

	boolean Conexo(){
		if(listaPuntos.isEmpty()) return true;

		int aux = listaPuntos.peek();
		for (int a : listaPuntos) {
			if (Representante(a) != Representante(aux)) {
				return false;
			}
		}
		return true;
	}
}