/**
 * Implementacion de los metodos de la clase Grafo.
 *
 * @version 1.0
 * @author
 * Asignatura Desarrollo de Programas<br/>
 * Grupo: Feli&Carlos <br/>
 * Entrega Junio <br/>
 * <b> Felisa Maria Arroba Alonso </b><br>
 * <b> Juan Carlos Bonilla Bermejo </b><br>
 * Curso 12/13
 */

package estructurasDeDatos;


import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;


public class Grafo {

	public int MAXVERT=115;
	public static final int INFINITO = 9999;
	public static final int NOVALOR = -1;


	/** Numero de nodos del grafo */
	private int numNodos;        

	/** Vector que almacena los nodos del grafo */
	private int[] nodos = new int[MAXVERT];           

	/** Matriz de adyacencia, para almacenar los arcos del grafo */
	private int[][] arcos = new int[MAXVERT][MAXVERT];    

	/** Matriz de Camino (Warshall - Path) */
	private boolean [][] warshallC = new boolean[MAXVERT][MAXVERT];    

	/** Matriz de Costes (Floyd - Cost) */
	private int[][] floydC = new int[MAXVERT][MAXVERT];    

	/** Matriz de Camino (Floyd - Path) */
	private int[][] floydP = new int[MAXVERT][MAXVERT];	



	public Grafo() {
		int x,y;
		numNodos=0;

		for (x=0;x<MAXVERT;x++)
			nodos[x]= NOVALOR;

		for (x=0;x<MAXVERT;x++)
			for (y=0;y<MAXVERT;y++){
				arcos[x][y]=INFINITO;
				warshallC[x][y]=false;
				floydC[x][y]=INFINITO;
				floydP[x][y]=NOVALOR;
			}

		// Diagonales
		for (x=0;x<MAXVERT;x++){
			arcos[x][x]=0;
			warshallC[x][x]=false;
			floydC[x][x]=0;
			//floydP[x][x]=NO_VALOR;
		}
	}



	/**
	 * @pre Grafo creado correctamente
	 * @post Inicializa el grafo con un numero determinado de nodos
	 * @param <b>maxNodos<b> El numero de nodos que compondran el grafo
	 * @complejidad <b>O(n²)<b>
	 */
	public void inicializarGrafo ( int maxNodos){

		for(int i=0;i< maxNodos;i++){
			nodos[i]=i;
			if (!nuevoNodo(nodos[i]))
				System.out.println("Error en la insercion del nodo " + i);
		}

		for(int i=0;i< maxNodos;i++)
			for(int j=0;j<maxNodos;j++)
				if (!this.nuevoArco(nodos[i],nodos[j],arcos[i][j]))
					System.out.println("Error en la insercion del arco("+i+","+j+")");


		warshall();
		floyd();
	}



	/**
	 * @pre Grafo creado correctamente
	 * @post Devuelve los arcos del grafo
	 * @return <b>Matriz de enteros<b> Que contiene los arcos del grafo
	 * @complejidad <b>O(1)<b>
	 */
	public int[][] getArcos() {
		return this.arcos;
	}



	/**
	 * @pre Grafo creado correctamente
	 * @post Devuelve la matriz de costes del grafo
	 * @return <b>Matriz de enteros<b> Que contiene los costes del grafo
	 * @complejidad <b>O(1)<b>
	 */
	public int[][] getFloydC (){
		return this.floydC;
	}



	/**
	 * @pre Grafo creado correctamente
	 * @post Comprueba si el grafo esta vacio
	 * @return <b>True<b> si el grafo esta vacio, <b>False<b> en caso contrario
	 * @complejidad <b>O(1)<b>
	 */
	public boolean esVacio () {
		return (numNodos==0);
	}




	/**
	 * @pre Grafo creado correctamente
	 * @post Inserta un nuevo arco en el grafo
	 * @param <b>origen<b> El nodo de origen del arco nuevo
	 * @param <b>destino<b> El nodo de destino del arco nuevo
	 * @param <b>valor<b> El peso del arco nuevo 
	 * @return <b>True<b> si se pudo insertar, <b>False<b> en caso contrario
	 * @complejidad <b>O(1)<b>
	 */
	public boolean nuevoArco(int origen, int destino, int valor) {
		boolean resultado= false;
		if ((origen >= 0) && (origen < numNodos) && (destino >= 0) && (destino < numNodos))	{
			arcos[origen][destino]=valor; 
			resultado=true;
		}
		return resultado;
	}




	/**
	 * @pre Grafo creado correctamente
	 * @post Borra un  arco del grafo
	 * @param <b>origen<b> El nodo de origen del arco a borrar
	 * @param <b>destino<b> El nodo de destino del arco a borrar
	 * @return <b>True<b> si se pudo borrar, <b>False<b> en caso contrario
	 * @complejidad <b>O(1)<b>
	 */
	public boolean borraArco(int origen, int destino) {
		boolean resultado = false;
		if ((origen >= 0) && (origen < numNodos) && (destino >= 0) && (destino < numNodos)) {
			arcos[origen][destino]=INFINITO;	
			resultado=true;
		}
		return resultado;
	}




	/**
	 * @pre Grafo creado correctamente
	 * @post Comprueba si dos nodos son adyacentes
	 * @param <b>origen<b> El primer nodo
	 * @param <b>destino<b> El segundo nodo
	 * @return <b>True<b> si los dos nodos son  adyacentes, <b>False<b> en caso contrario
	 * @complejidad <b>O(1)<b>
	 */
	public boolean adyacente (int origen, int destino) {
		boolean resultado= false;
		if ((origen >= 0) && (origen < numNodos) && (destino >= 0) && (destino < numNodos))      
			resultado = (arcos[origen][destino]!=INFINITO); 
		return resultado;
	}



	/**
	 * @pre Grafo creado correctamente
	 * @post Retorna el peso de un arco
	 * @param <b>origen<b> El primer nodo del arco
	 * @param <b>destino<b> El segundo nodo del arco
	 * @return <b>Entero<b> Que contiene el peso del arco
	 * @complejidad <b>O(1)<b>
	 */
	public int getArco (int origen, int destino) {
		int arco=NOVALOR;
		if ((origen >= 0) && (origen < numNodos) && (destino >= 0) && (destino < numNodos)) 	
			arco=arcos[origen][destino];				     
		return arco;
	}




	/**
	 * @pre Grafo creado correctamente
	 * @post Inserta un nuevo nodo en el grafo
	 * @param <b>n<b> El nodo a insertar
	 * @return <b>True<b> si se pudo insertar, <b>False<b> en caso contrario
	 * @complejidad <b>O(1)<b>
	 */
	public boolean nuevoNodo(int n) {
		boolean resultado=false;

		if (numNodos<MAXVERT){
			nodos[numNodos]=n;
			numNodos++;
			resultado=true;
		}
		return resultado;
	}




	/**
	 * @pre Grafo creado correctamente
	 * @post Elimina un nodo del grafo
	 * @param <b>n<b> El nodo a eliminar
	 * @return <b>True<b> si se pudo borrar, <b>False<b> en caso contrario
	 * @complejidad <b>O(n²)<b>
	 */
	public boolean borraNodo(int nodo) {
		boolean resultado=false;
		int pos = nodo; 

		if ((numNodos>0) && (pos <= numNodos)) {
			int x,y;
			// Borrar el nodo
			for (x=pos; x<numNodos-1; x++){		
				nodos[x]=nodos[x+1];
				System.out.println(nodos[x+1]);
			}
			// Borrar en la Matriz de Adyacencia
			// Borra la fila
			for (x=pos; x<numNodos-1; x++)		
				for (y=0;y<numNodos; y++)
					arcos[x][y]=arcos[x+1][y];
			// Borra la columna
			for (x=0; x<numNodos; x++)
				for (y=pos;y<numNodos-1; y++)	
					arcos[x][y]=arcos[x][y+1];
			// Decrementa el numero de nodos
			numNodos--;
			resultado=true;
		}
		return resultado;
	}




	/**
	 * @pre Grafo creado correctamente
	 * @post Muestra el vector de nodos del grafo
	 * @complejidad <b>O(n)<b>
	 */
	public void mostrarNodos() {
		System.out.println("NODOS:");
		for (int x=0;x<numNodos;x++)
			System.out.print(nodos[x] + " ");
		System.out.println();
	}




	/**
	 * @pre Grafo creado correctamente
	 * @post Muestra los arcos del grafo (la matriz de adyacencia)
	 * @complejidad <b>O(n²)<b>
	 */
	public void mostrarArcos(){
		int x,y;

		System.out.println("ARCOS:");
		for (x=0;x<numNodos;x++) {
			for (y=0;y<numNodos;y++) {
				//cout.width(3);
				if (arcos[x][y]!=INFINITO)
					System.out.format("%4d",arcos[x][y]);
				else
					System.out.format("%4s","#");
			}
			System.out.println();
		}
	}




	/**
	 * @pre Grafo creado correctamente
	 * @post Devuelve el conjunto (en una cola) de nodos adyacentes al nodo actual
	 * @param <b>origenb> El nodo actual
	 * @param <b>ady<b> En este conjunto se almacenarian los nodos adyacentes al nodo origen
	 * @complejidad <b>O(n)<b>
	 */
	public void adyacentes(int origen, Set<Integer> ady){
		if ((origen >= 0) && (origen < numNodos)) {
			for (int i=0;i<numNodos;i++) {
				if ((arcos[origen][i]!=INFINITO) && (arcos[origen][i]!=0))	{
					ady.add(i);	
				}
			}
		}
	}




	/**
	 * @pre Grafo creado correctamente
	 * @post Devuelve el conjunto (en una cola) de nodos adyacentes al nodo actual de manera NESO
	 * @param <b>origenb> El nodo actual
	 * @param <b>ady<b> En este conjunto se almacenarian los nodos adyacentes al nodo origen
	 * @complejidad <b>O(n)<b>
	 */
	public void adyacentesNESO(int origen, int anterior, LinkedHashSet<Integer> ady, int ancho){
		//PONERLO EN NESO
		int N=-1, S=-1, E=-1, O=-1;

		Set<Integer> adyAux= new TreeSet<Integer>();

		if ((origen >= 0) && (origen < numNodos)) {

			for (int i=0;i<numNodos;i++) {
				if ((arcos[origen][i]!=INFINITO) && (arcos[origen][i]!=0))	{

					adyAux.add(i);

					//buscamos la vecina norte
					if(i==origen-ancho)
						N=i;
					//buscamos la vecina este
					else if(i==origen+ancho)
						S=i;
					//buscamos la vecina sur
					else if(i==origen+1)
						E=i;
					//buscamos la vecina oeste
					else if(i==origen-1)
						O=i;
				}

			}//fin del for

			if (origen!=anterior /*&& ady.contains(origen)*/){

				//Contruimos sabiendo como fue el ultimo movimiento
				int resta= origen-anterior;
				if(resta == ancho) resta=2;
				if(resta == -ancho)resta=-2;

				switch (resta) {
				case 1: //me he movido al este
					if(S!=-1) ady.add(S);
					if(O!=-1) ady.add(O);
					if(N!=-1) ady.add(N);
					if(E!=-1) ady.add(E);
					break;

				case 2: //me he movido al sur
					if(O!=-1) ady.add(O);
					if(N!=-1) ady.add(N);
					if(E!=-1) ady.add(E);
					if(S!=-1) ady.add(S);	
					break;

				case -1: //me he movido al oeste
					if(N!=-1) ady.add(N);
					if(E!=-1) ady.add(E);
					if(S!=-1) ady.add(S);
					if(O!=-1) ady.add(O);	
					break;

				case -2: //me he movido al norte
					if(E!=-1) ady.add(E);
					if(S!=-1) ady.add(S);
					if(O!=-1) ady.add(O);
					if(N!=-1) ady.add(N);	
					break;
				}	    		
			}
			else{ //CASO BASE 
				if(N!=-1) ady.add(N);
				if(E!=-1) ady.add(E);
				if(S!=-1) ady.add(S);
				if(O!=-1) ady.add(O);
			}
		}
	}



	/**
	 * @pre Grafo creado correctamente
	 * @post Muestra la matriz de Warshall
	 * @complejidad <b>O(n²)<b>
	 */
	public void mostrarPW(){
		int x,y;

		System.out.println("warshallC:");
		for (x=0;x<numNodos;x++) {
			for (y=0;y<numNodos;y++)
				System.out.format("%6b",warshallC[x][y]);
			System.out.println();
		}
	}




	/**
	 * @pre Grafo creado correctamente
	 * @post Muestra las matrices de coste y camino de Floyd
	 * @complejidad <b>O(n²)<b>
	 */
	public void mostrarFloydC(){
		int x,y;
		System.out.println("floydC:");
		for (y=0;y<numNodos;y++) {
			for (x=0;x<numNodos;x++) {
				//cout.width(3);
				System.out.format("%4d",floydC[x][y]);
			}
			System.out.println();
		}

		System.out.println("floydP:");
		for (x=0;x<numNodos;x++) {
			for (y=0;y<numNodos;y++) {
				//cout.width(2);
				System.out.format("%4d",floydP[x][y]);
			}
			System.out.println();
		}
	}



	/**
	 * @pre Grafo creado correctamente
	 * @post Realiza el algoritmo de Warshall sobre el grafo
	 * @complejidad <b>O(n3)<b>
	 */
	public void warshall() {
		int i,j,k;

		// Obtener la matriz de adyacencia en P
		for (i=0;i<numNodos;i++)
			for (j=0;j<numNodos;j++)
				warshallC[i][j]=(arcos[i][j]!=INFINITO);

		// Iterar
		for (k=0;k<numNodos;k++)
			for (i=0;i<numNodos;i++)
				for (j=0;j<numNodos;j++)
					warshallC[i][j]=(warshallC[i][j] || (warshallC[i][k] && warshallC[k][j]));
	}




	/**
	 * @pre Grafo creado correctamente
	 * @post Realiza el algoritmo de Floyd sobre el grafo
	 * @complejidad <b>O(n3)<b>
	 */
	public void floyd (){
		int i,j,k;

		// Obtener la matriz de adyacencia en P
		for (i=0;i<numNodos;i++)
			for (j=0;j<numNodos;j++){
				floydC[i][j]=arcos[i][j];
				floydP[i][j]=NOVALOR; 	
			}

		// Iterar
		for (k=0;k<numNodos;k++)
			for (i=0;i<numNodos;i++)
				for (j=0;j<numNodos;j++)
					if (i!=j)
						if ((floydC[i][k]+floydC[k][j] < floydC[i][j])) {
							floydC[i][j]=floydC[i][k]+floydC[k][j];		
							floydP[i][j]=k;
						}
	}




	/**
	 * @pre Grafo creado correctamente
	 * @post Devuelve el siguiente nodo en la ruta entre un origen y un destino
	 * @param <b>origen<b> El primer nodo
	 * @param <b<destino<b> El segundo nodo
	 * @param <b>sig<b> parametro de entrada salida que devuelve el siguiente nodo en la ruta entre origen y destino
	 * @complejidad <b>O(1)<b>
	 */
	public int siguiente(int origen, int destino){
		int sig=-1; // Si no hay camino posible
		if ((origen >= 0) && (origen < numNodos) && (destino >= 0) && (destino < numNodos)) {
			if (warshallC[origen][destino]){ // Para comprobar que haya camino
				if (floydP[origen][destino]!=NOVALOR)
					sig = siguiente(origen, floydP[origen][destino]);	
				else
					sig=destino;
			}
		}
		return sig;
	}




}

