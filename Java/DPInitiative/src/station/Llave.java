/**
 * Implementacion de los metodos de la clase Llave
 *
 * @version 1.0
 * @author
 * Asignatura Desarrollo de Programas<br/>
 * Grupo: Feli&Carlos<br/>
 * Entrega: Junio <br/>
 * <b> Felisa Maria Arroba Alonso </b><br>
 * <b> Juan Carlos Bonilla Bermejo </b><br>
 * Curso 12/13
 */

package station;



public class Llave implements Comparable <Llave>{

	private int idLlave; // identificador de la llave.



	public Llave() {
		this.idLlave = 0;
	}



	/**
	 * @post Constructor parametrizado
	 * @param <bdato<b> Entero que identifica la llave
	 * @complejidad <b>O(1)<b>
	 * @throws exceptIdLlave
	 *             controla que no se pueda insertar un identificador de llave negativo
	 */
	public Llave (int dato) throws exceptIdLlave{
		this.idLlave=dato;

		if (idLlave < 0) {
			throw (new exceptIdLlave("El identificador de la llave es negativo"));
		}
	}



	/**
	 * @post Constructor por copia
	 * @param <_llaveb> Llave a copiar
	 * @complejidad <b>O(1)<b>
	 */
	public Llave( Llave _llave) {
		this.idLlave =  _llave.getDato();
	}



	/**
	 * @pre Llave creada correctamente
	 * @post Devuelve el identificador de la llave
	 * @return <b>idLlave<b> Entero que identifica la llave
	 * @complejidad <b>(1)<b>
	 */
	public int getDato(){
		return idLlave;
	}




	/**
	 * @pre Llave creada correctamente
	 * @post Inserta un identificador en la clase Llave
	 * @param <b>dato<b> El nuevo Identificador de la llave
	 * @complejidad <b>(1)<b>
	 */
	public void setDato(int dato){
		this.idLlave = dato;
	}




	/**
	 * @pre Llave creada correctamente
	 * @post Compara el identificador de dos llaves
	 * @param <b>dato<b> Llave a comparar con mi atributo
	 * @return <b>0<b> si son iguales
                <b>1<b> si el idLlave de mi atributo es mayor que el del parámetro
                <b>-1<b>si el idLlave de mi atributo es menor que el del parámetro
	 * @complejidad <b>(1)<b>
	 */
	public int compareTo(Llave dato){
		if (this.idLlave == dato.getDato())
			return 0;
		if (this.idLlave < dato.getDato())
			return -1;
		return 1;
	}




	/**
	 * @pre Llave creada correctamente
	 * @post Compara dos llaves
	 * @param <b>dato2<b> Llave a comparar con mi atributo
	 * @return <b>True<b> si son iguales
                <b>False<b> en caso contrario
	 * @complejidad <b>(1)<b>
	 */
	public boolean equals(Object dato2){
		if (this == dato2) 
			return true;
		// Siempre debemos comparar si el objeto pasado por parametro es del mismo tipo.
		if (!(dato2 instanceof Llave))
			return false;

		Llave datoAux = (Llave) dato2;// Hacemos un casting
		return this.idLlave == datoAux.getDato();
	}



	/**
	 * @pre Llave creada correctamente
	 * @post Crea una cadena de caracteres con el idLlave
	 * @return <b>idLlave<b> cadena de caracteres 
	 * @complejidad <b>(1)<b>
	 */
	public String toString(){
		return " "+ idLlave;
	}


}