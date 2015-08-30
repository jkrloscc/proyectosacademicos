/**
 * Implementacion de los metodos de la clase Pared.
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


package station;

public class Pared {

	/** Identificador de la Sala de origen de la pared */
	private int origen;
	/** Identificador de la Sala de destino de la pared */
	private int destino;


	public Pared(){
		this.origen=0;
		this.destino=0;
	}



	/**
	 * @post Constructor parametrizado de la pared
	 * @param <b>origen<b> Identificador de la sala de origen de la pared
	 * @param <b>destino<b> Identificador de la sala destino de la pared
	 * @complejidad O(1) 
	 */
	public Pared (int origen, int destino){

		this.origen = origen;
		this.destino = destino;
	}



	/**
	 * @post Constructor por copia 
	 * @param <b>pared<b> Objeto a copiar
	 * @complejidad O(1) 
	 */
	public Pared (Pared pared){
		this.origen=pared.getOrigen();
		this.destino =pared.getDestino();
	}



	/**
	 * @pre Pared creada correctamente
	 * @post Actualiza el origen de la pared
	 * @param <b>origen<b> Origen nuevo de la pared
	 * @complejidad O(1) 
	 */
	public void setOrigen(int origen) {
		this.origen = origen;
	}




	/**
	 * @pre Pared creada correctamente
	 * @post Actualiza el destino de la pared
	 * @param <b>destino<b> Destino nuevo de la pared
	 * @complejidad O(1) 
	 */
	public void setDestino(int destino) {
		this.destino = destino;
	}



	/**
	 * @pre Pared creada correctamente
	 * @post Devuelve el origen de la pared
	 * @return <b>origen<b> El origen de la pared
	 * @complejidad O(1) 
	 */
	public int getOrigen() {
		return origen;
	}



	/**
	 * @pre Pared creada correctamente
	 * @post Devuelve el destino de la pared
	 * @return <b>destino<b> El destino de la pared
	 * @complejidad O(1) 
	 */
	public int getDestino() {
		return destino;
	}



	/**
	 * @pre Pared creada correctamente
	 * @post Visualiza los datos de la pared
	 * @complejidad O(1) 
	 */
	public void visualizar() {
		System.out.println("("+origen+"-"+destino+")");

	}

}
