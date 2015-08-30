package cargador;
/**
 * Clase creada para ser usada en la utilidad cargador
 * se utiliza para realizar el mapeo de los distintos tipos de datos que se utilizarán en la aplicación
 * 
 * @version 1.0 -  02/11/2011 
 * @author Profesores DP
 */
public class DatoMapeo {
	private String nombre;
	private int numCampos;


	DatoMapeo()   {
		nombre = new String();
		nombre = "..";
		numCampos = 0;
	}

	/**
	 *  @post constructor parametrizado 
	 *  @param <b>_nombre<b> nombre del tipo de datos
	 *  @param <b>_numCampos<b> numero de campos-atributos que contendra
	 */
	DatoMapeo(String _nombre, int _numCampos)   {
		nombre = new String(_nombre);
		numCampos = _numCampos;
	}

	/**
	 * @post Devuelve el nombre del tipo
	 * @return <b>nombre<b>
	 * @complejidad <b>O(1)<b>
	 */
	public String getNombre()  {
		return nombre;
	}

	/**
	 * @post Devuelve el numero de campos del tipo
	 * @return <b>numCampos<b>
	 * @complejidad <b>O(1)<b>
	 */
	public int getCampos()  {
		return numCampos;
	}


}
