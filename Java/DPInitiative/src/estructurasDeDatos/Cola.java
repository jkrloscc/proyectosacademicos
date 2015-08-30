/**
 * Implementacion de los metodos de la clase Cola.
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

import personas.Personaje;

public class Cola <T>{

	/**Tipo de dato abstracto */
	private T dato;

	/** Puntero al primer elemento de la Cola*/
	private Nodo<T> first;

	/** Puntero al ultimo elemento de la Cola*/
	private Nodo<T> last;

	/** Contador de  elementos de la Cola*/
	private int contador;

	private static class Nodo <T>{
		/** Dato almacenado en cada nodo */
		private T dato;
		/** Enlace al siguiente elemento */
		private Nodo next;

		Nodo (T dato) {
			this.dato = dato;
			this.next = null;
		}
	}//class Nodo


	public Cola() {
		first=null;
		last=null;
		dato=null;
		contador = 0;
	}


	/**
	 * @pre Cola creada correctamente
	 * @post Comprueba si la cola esta vacia
	 * @return <b>True<b> si la cola esta vacia, <b>False<b> en caso contrario
	 * @complejidad <b>O(1)<b>
	 */
	public boolean estaVacia (){
		return (first == null);
	}



	/**
	 * @pre Cola creada correctamente
	 * @post Aniade un nuevo dato a la cola
	 * @param <b>dato<b> El nuevo nodo que habra en la cola
	 * @complejidad <b>O(1)<b>
	 */
	public void encolar(T dato){
		Nodo<T> nuevo_nodo = new Nodo<T>(dato);
		nuevo_nodo.dato = dato;
		nuevo_nodo.next = null;
		if (estaVacia()) 
			first = nuevo_nodo;
		else 
			last.next = nuevo_nodo;
		last = nuevo_nodo;
		contador++;
	}



	/**
	 * @pre Cola creada correctamente
	 * @post Elimina un dato de la cola
	 * @complejidad <b>O(1)<b>
	 */
	public void desencolar () {
		if (!estaVacia()){
			first = first.next;
			contador--;
		}

	}



	/**
	 * @pre Cola creada correctamente
	 * @post Devuelve el primer dato de la cola
	 * @return El primer dato de mi cola
	 * @complejidad <b>O(1)<b>
	 */
	public T primero (){
		if (!estaVacia())
			return first.dato;
		return null;
	}



	/**
	 * @pre Cola creada correctamente
	 * @post Devuelve el numero de elementos de la cola
	 * @return El numero de elementos de la cola
	 * @complejidad <b>O(1)<b>
	 */
	public int getContador(){
		return this.contador;
	}




	/**
	 * @pre Cola creada correctamente
	 * @post Hace pruebas de inserciones y borrados en la cola y muestra el resultado obtenido
	 * @param <b>args<b> Argumentos que recibe el programa principal
	 * @complejidad <b>O(n)<b>
	 */
	public static void main (String args[]) {

		Cola <Personaje> cola1 = new Cola <Personaje>();

		Personaje P1 = new Personaje ("J", "Jack",1, 0);
		Personaje P2 = new Personaje ("B", "Ben",1,1);
		Personaje P3 = new Personaje ("M", "Mike",1, 2);
		Personaje P4 = new Personaje ("J", "John",1,6);
		Personaje dato = null;


		//Pruebas de inserciones en la cola
		cola1.encolar (P1);
		cola1.encolar (P2);
		cola1.encolar (P3);
		cola1.encolar (P4);

		//Mostrando la cola
		System.out.println("Contenido de la Cola");

		while (!cola1.estaVacia()){
			dato = (Personaje) cola1.primero();
			System.out.println(dato.getNombre());
			cola1.desencolar();
		}

		if (cola1.estaVacia())
			System.out.println("Cola Vacia");
	}
}