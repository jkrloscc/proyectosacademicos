/**
 * Implementacion de los metodos de la clase Trabajadores.
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

package personas;

import java.util.Iterator;
import java.util.List;

import station.Planta;

public class Trabajadores extends Personaje{


	public Trabajadores() {
		super();
	}



	/**
	 * @post Constructor parametrizado de la clase Trabajadores
	 * @param <b>marca<b> Cadena de caracteres con la marca del trabajador
	 * @param <b>nombre<b> Cadena de caracteres con el nombre del trabajador
	 * @param <b>turno<b> El turno del trabajador
	 * @param <b>idSala<b> Identificador de la sala en la que se encuentra el trabajador
	 * @complejidad <b>O(1)<b>
	 */
	public Trabajadores (String nombre,String marca, int turno,int idSala) {
		super(nombre, marca,turno,idSala);

	}



	/**
	 * @pre Haya un conjunto de caminos con datos
	 * @post Devolver el camino mas corto de todo ese conjunto
	 * @param <b>caminoTrabajador<b> el camino mas corto del conjunto de caminos
	 * @complejidad <b>O(n)<b>
	 **/
	public List<Integer>caminoTrabajador( ) {
		List<Integer> aux= null;
		List<Integer> caminoTrabajador= null;

		Planta mip = Planta.obtenerInstancia(0);

		Iterator i = mip.getCaminos().getConjuntoCaminos().iterator();

		if (i.hasNext()) 
			caminoTrabajador=(List<Integer>) i.next();		

		for(;i.hasNext();){
			aux = (List<Integer>) i.next();
			if (aux.size() < caminoTrabajador.size())
				caminoTrabajador = aux;	
		}

		return caminoTrabajador;
	}

}
