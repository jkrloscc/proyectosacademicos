/**
 * Implementacion de los metodos de la clase CargaFichero
 * 
 * Clase creada para ser usada en la utilidad cargador, contiene el main del cargador.
 * Se crea una instancia de la clase Planta, una instancia de la clase Cargador
 * y se procesa el fichero de inicio, es decir, se leen todas las lineas y se van creando todas las instancias de la simulacion
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

package cargador;
import java.io.FileNotFoundException;
import java.io.IOException;

import registro.Sistema;
import station.Planta;
import station.exceptIdLlave;

public class CargaFichero {

	/**
	 * @post Programa Principal 
	 * @param <b>args<b>
	 * @complejidad <b>O(1)<b>
	 * @throws NumberFormatException
	 * @throws exceptIdLlave
	 */
	public static void main(String[] args) throws NumberFormatException, exceptIdLlave {
		Sistema.aperturaSistema(); 

		/** Planta es la instancia del patron Singleton */
		Planta planta = null;

		/** Instancia asociada al fichero de entrada inicio.txt */	
		Cargador cargador = new Cargador(planta);

		try {

			/**  Metodo que procesa linea a linea el fichero de entrada inicio.txt */
			FicheroCarga.procesarFichero("inicio.txt", cargador);

			planta= Planta.obtenerInstancia(0);

			planta.continuarPlanta();	
		}
		catch (FileNotFoundException valor)  {
			System.err.println ("Excepcion capturada al procesar fichero: "+valor.getMessage());
		}
		catch (IOException valor)  {
			System.err.println ("Excepcion capturada al procesar fichero: "+valor.getMessage());
		}

		System.runFinalization();
		Sistema.cierreSistema();
	}
}
