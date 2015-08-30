/**
 * Implementacion de la clase que permite generar numeros aleatorios
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
import java.util.Random;

public class GenAleatorios {

	/** Instancia de la clase Random que permite generar los numeros aleatorios. */
	private static Random r;

	/** Constante con la semilla para inicializar la generacion de nœmeros aleatorios. */
	private static final int SEMILLA=1987;		 

	/** Contador de numeros aleatorios generados */
	private static int numGenerados;			

	/** Instancia de la propia clase (SINGLETON) */
	private static GenAleatorios instancia=null;	



	private GenAleatorios(){
		// Inicializacion de la semilla para generar los numeros aleatorios
		r = new Random(GenAleatorios.SEMILLA);
		// Inicializacion del atributo que cuenta cuantos nœmeros aleatorios se han generado
		numGenerados = 0;
	}


	/**
	 * @pre Instancia creada correctamente
	 * @post  Genera un numero aleatorio entre 0 y (limiteRango-1)
	 * @param <b>limiteRango<b> El limite del rango en el que generar los aleatorios
	 * @return <b>entero<b> El numero aleatorio generado 
	 * @complejidad <b>O(1)<b>
	 */
	public static int generarNumero(int limiteRango){
		if (instancia == null) 
			instancia = new GenAleatorios();
		numGenerados++;
		return r.nextInt(limiteRango);
	}



	/**
	 * @pre Instancia creada correctamente
	 * @post  Devuelve el numero de aleatorios generados
	 * @return <b>numGenerados<b> El numero aleatorio generado 
	 * @complejidad <b>O(1)<b>
	 */
	public static int getNumGenerados(){
		return numGenerados;
	}

}
