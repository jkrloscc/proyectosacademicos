/**
 * Implementacion de arbol binario de busqueda.
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

import station.Llave;
import station.exceptIdLlave;

public class Arbol<T extends Comparable<T>> {

	/** Dato almacenado en cada nodo del arbol. */
	private T datoRaiz;

	/** Atributo que indica si el arbol esta� vacio. */
	boolean esVacio;

	/** Hijo izquierdo del nodo actual */
	private Arbol<T> hIzq;

	/** Hijo derecho del nodo actual */
	private Arbol<T> hDer;


	public Arbol() {
		this.esVacio = true;
		this.hIzq = null;
		this.hDer = null;

	}


	/**
	 * @post Crea un nuevo arbol a partir de los datos pasados por parametro. 
	 * 		(2 arboles ya creados y un dato)
	 * @param <b>hIzq<b> El hijo izquierdo del arbol que se esta creando
	 * @param <b>datoRaiz<b> Raiz del arbol que se esta creando
	 * @param <b>hDer<b> El hijo derecho del arbol que se esta creando
	 * @complejidad <b>O(1)<b>
	 */
	public Arbol(Arbol<T> hIzq, T datoRaiz, Arbol<T> hDer) {
		this.datoRaiz = datoRaiz;
		this.hIzq = hIzq;
		this.hDer = hDer;
		esVacio = false;
	}


	/**
	 * @pre Arbol creado correctamente
	 * @post Devuelve el hijo izquierdo del arbol
	 * @return <b>hIzq<b> El hijo izquierdo
	 * @complejidad <b>O(1)<b>
	 */
	public Arbol<T> getHijoIzq() {
		return hIzq;
	}



	/**
	 * @pre Arbol creado correctamente
	 * @post Devuelve el hijo derecho del arbol
	 * @return <b>hDer<b> El hijo derecho
	 * @complejidad <b>O(1)<b>
	 */
	public Arbol<T> getHijoDer() {
		return hDer;
	}



	/**
	 * @pre Arbol creado correctamente
	 * @post Devuelve la raiz del arbol
	 * @return <b>datoRaiz<b> Raiz
	 * @complejidad <b>O(1)<b>
	 */
	public T getRaiz() {
		return datoRaiz;
	}



	/**
	 * @pre Arbol creado correctamente
	 * @post Comprueba si el arbol esta vacio
	 * @return <b>True<b> si el arbol esta vacio, <b>False<b> en caso contrario
	 * @complejidad <b>O(1)<b>
	 */
	public boolean vacio() {
		return esVacio;
	}



	/**
	 * @pre Arbol creado correctamente
	 * @post Inserta un nuevo dato en el arbol
	 * @param <b>dato<b> El dato a insertar
	 * @return <b>True<b> si el dato se ha insertado correctamente, <b>False<b> en caso contrario
	 * @complejidad <b>O(1)<b>
	 */
	public boolean insertar(T dato) {
		boolean resultado = true;
		if (vacio()) {
			datoRaiz = dato;
			esVacio = false;
		}

		else {
			if (!(this.datoRaiz.equals(dato))) { // comparamos el dato con la raiz
				Arbol<T> aux;
				if (((T) dato).compareTo((T) this.datoRaiz) < 0) { // dato < datoRaiz
					if ((aux = getHijoIzq()) == null)
						hIzq = aux = new Arbol<T>();
				} else { // dato > datoRaiz
					if ((aux = getHijoDer()) == null)
						hDer = aux = new Arbol<T>();
				}
				resultado = aux.insertar(dato);
			} else
				resultado = false;
		}
		return resultado;
	}



	/**
	 * @pre Arbol creado correctamente
	 * @post Comprueba si un dato se encuentra almacenado en el arbol
	 * @param <b>dato<b> El dato a buscar
	 * @return <b>True<b> si el dato se encuentra en el arbol, <b>False<b> en caso contrario
	 * @complejidad <b>O(1)<b>
	 */
	public boolean pertenece(T dato) {
		Arbol<T> aux = null;
		boolean encontrado = false;
		if (!vacio()) {
			if (this.datoRaiz.equals(dato))
				encontrado = true;
			else {
				if (((T) dato).compareTo((T) this.datoRaiz) < 0) // dato < datoRaiz
					aux = getHijoIzq();
				else
					aux = getHijoDer();// dato > datoRaiz
				if (aux != null)
					encontrado = aux.pertenece(dato);
			}
		}
		return encontrado;
	}



	/**
	 * @pre Arbol creado correctamente
	 * @post Borrar un dato del arbol
	 * @param <b>dato<b> El dato a borrar
	 * @complejidad <b>O(1)<b>
	 */
	public void borrar(T dato) {
		if (!vacio()) {
			if (((T) dato).compareTo((T) this.datoRaiz) < 0) { // dato<datoRaiz
				if (hIzq != null)
					hIzq = hIzq.borrarOrden(dato);
			} else if (((T) dato).compareTo((T) this.datoRaiz) > 0) { // dato>datoRaiz
				if (hDer != null)
					hDer = hDer.borrarOrden(dato);
			} else // En este caso el dato es datoRaiz
			{
				if (hIzq == null && hDer == null) {
					esVacio = true;
				} else
					borrarOrden(dato);
			}
		}
	}



	/**
	 * @pre Arbol creado correctamente
	 * @post Borra un dato. Este metodo es utilizado por el metodo borrar anterior
	 * @param <b>dato<b> El dato a borrar
	 * @return El Arbol resultante despues de haber realizado el borrado
	 * @complejidad <b>O(1)<b>
	 */
	private Arbol<T> borrarOrden(T dato) {
		T datoaux;
		Arbol<T> retorno = this;
		Arbol<T> aborrar, candidato, antecesor;

		if (!vacio()) {
			//	System.out.println("procesando el dato" + this.datoRaiz);
			if (((T) dato).compareTo((T) this.datoRaiz) < 0) { // dato<datoRaiz
				if (hIzq != null)
					hIzq = hIzq.borrarOrden(dato);
			} else if (((T) dato).compareTo((T) this.datoRaiz) > 0) { // dato>datoRaiz
				if (hDer != null)
					hDer = hDer.borrarOrden(dato);
			} else {
				aborrar = this;
				if ((hDer == null) && (hIzq == null)) { /* si es hoja */
					retorno = null;
				} else {
					if (hDer == null) { /* Solo hijo izquierdo */
						aborrar = hIzq;
						datoaux = this.datoRaiz;
						datoRaiz = hIzq.getRaiz();
						hIzq.datoRaiz = datoaux;
						hIzq = hIzq.getHijoIzq();
						hDer = aborrar.getHijoDer();

						retorno = this;
					} else if (hIzq == null) { /* Solo hijo derecho */
						aborrar = hDer;
						datoaux = datoRaiz;
						datoRaiz = hDer.getRaiz();
						hDer.datoRaiz = datoaux;
						hDer = hDer.getHijoDer();
						hIzq = aborrar.getHijoIzq();

						retorno = this;
					} else { /* Tiene dos hijos */
						candidato = this.getHijoIzq();
						antecesor = this;
						while (candidato.getHijoDer() != null) {
							antecesor = candidato;
							candidato = candidato.getHijoDer();
						}

						/* Intercambio de datos de candidato */
						datoaux = datoRaiz;
						datoRaiz = (T) candidato.getRaiz();
						candidato.datoRaiz = datoaux;
						aborrar = candidato;
						if (antecesor == this)
							hIzq = candidato.getHijoIzq();
						else
							antecesor.hDer = candidato.getHijoIzq();
					} // Eliminar solo ese nodo, no todo el subarbol
					aborrar.hIzq = null;
					aborrar.hDer = null;
				}
			}
		}
		return retorno;
	}



	/**
	 * @pre Arbol creado correctamente
	 * @post Recorrido inOrden del Arbol
	 * @complejidad <b>O(1)<b>
	 */
	public void inOrden() {
		Arbol<T> aux = null;
		if (!vacio()) {
			if ((aux = getHijoIzq()) != null) {
				aux.inOrden();
			}

			System.out.print(this.datoRaiz + " "); // procesamiento de la raiz

			if ((aux = getHijoDer()) != null) {
				aux.inOrden();
			}
		}
	}



	/**
	 * @pre Arbol creado correctamente
	 * @post Calcula la profundidad del arbol
	 * @return El <b>entero<b> resultante despues de haber realizado el calculo de la profundidad
	 * @complejidad <b>O(1)<b>
	 */
	public int profundidad() {

		int P = 0, pHijoI = 0, pHijoD = 0;
		Arbol<T> aux = null;
		if (!vacio()) {

			if ((aux = getHijoIzq()) != null) {
				pHijoI = aux.profundidad();
			}

			if ((aux = getHijoDer()) != null) {
				pHijoD = aux.profundidad();
			}
		}
		if (pHijoI > pHijoD)
			P = pHijoI + 1;
		else
			P = pHijoD + 1;

		return P;
	}



	/**
	 * @pre Arbol creado correctamente
	 * @post Comprueba si un nodo es hoja
	 * @return <b>True<b> si el nodo es hoja, <b>False<b> en caso contrario
	 * @complejidad <b>O(1)<b>
	 */
	public boolean esHoja(T dato) {

		Arbol<T> aux;
		return (aux = getHijoIzq()) != null && (aux = getHijoDer()) != null;
	}



	/**
	 * @pre Arbol creado correctamente
	 * @post Calcula el numero de nodos de un ABB
	 * @return el numero de nodos del arbol
	 * @complejidad <b>O(1)<b>
	 */
	public int numNodos() {

		Arbol<T> aux = null;
		int cont = 0;

		if (!vacio()) {
			cont = 1;
			if ((aux = getHijoIzq()) != null) {
				cont = cont + aux.numNodos();
			}

			if ((aux = getHijoDer()) != null) {
				cont = cont + aux.numNodos();
			}
		}
		return cont;
	}



	/**
	 * @pre Arbol creado correctamente
	 * @post Calcula el numero de hojas de un ABB
	 * @return el numero de hojas del arbol
	 * @complejidad <b>O(1)<b>
	 */
	public int numHojas() {

		Arbol<T> aux = null;
		int cont = 0;

		if (!vacio()) {
			cont = 1;
			if ((aux = getHijoIzq()) != null && (aux = getHijoDer()) != null)
				cont = cont + aux.numHojas();
		}
		return cont;
	}



	/**
	 * @pre Arbol creado correctamente
	 * @post Devuelve el contenido del arbol en cadenas de caracteres
	 * @return La Raiz del arbol en forma de cadena de caracteres
	 * @complejidad <b>O(1)<b>
	 */
	public String toString() {
		String result="";
		Arbol<T> aux = null;
		if (!vacio()) {
			if ((aux = getHijoIzq()) != null) {
				result= result+aux.toString();
			}

			result= result+this.datoRaiz + "";

			if ((aux = getHijoDer()) != null) {
				result= result+aux.toString();
			}
		}
		return result;
	}



	/**
	 * @throws exceptIdLlave 
	 * @pre Arbol creado correctamente
	 * @post Realiza las pruebas con el arbol
	 * @complejidad <b>O(n)<b>
	 */
	public static void main(String[] args) throws exceptIdLlave {

		Arbol<Llave> arbol = new Arbol<Llave>();
		System.out.println("Juego de Pruebas Arbol");

		// definimos un array de objetos y lo vamos rellenando
		Llave[] datos = { new Llave(20), new Llave(7), new Llave(18),
				new Llave(6), new Llave(5), new Llave(1), new Llave(22) };

		// pasamos el array al arbol
		for (int i = 0; i < datos.length; i++) {
			arbol.insertar(datos[i]);
		}

		System.out.println("La profundidad del arbol es: "
				+ arbol.profundidad());
		System.out.println("El n� de nodos  del arbol es: " + arbol.numNodos());
		System.out.println("El n� de hojas  del arbol es: " + arbol.numHojas());

		// Insertando datos repetidos
		if (arbol.insertar(new Llave(22)) == false)
			System.out.println("El ABB no admite elementos duplicados");

		// Pertenencia de un dato
		if (arbol.pertenece(new Llave(22)))
			System.out.println("Pertenece");
		else
			System.out.println("NO Pertenece");

		// Recorrido en inOrden
		System.out.println("InOrden");
		arbol.inOrden();

		System.out.println("Probando el borrado de diferentes datos");
		arbol.borrar(new Llave(15));
		System.out.println("Borrado " + 15);

		// Borrando datos del arbol
		for (int i = 0; i < datos.length; i++) {
			arbol.borrar(datos[i]);
			System.out.println("Borrado " + datos[i]);
			arbol.inOrden();
		}
	}


}
