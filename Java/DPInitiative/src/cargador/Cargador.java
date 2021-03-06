package cargador;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import estructurasDeDatos.Cola;
import station.Dir;
import station.Planta;
import station.exceptIdLlave;
import personas.*;
/**
 * Implementacion de los metodos de la clase Cargador.
 * Clase creada para ser usada en la utilidad cargador
 * contiene el main del cargador. Se crea una instancia de la clase Planta, una instancia 
 * de la clase Cargador  y se procesa el fichero de inicio, es decir, se leen todas las 
 * lineas y se van creando todas las instancias de la simulacion
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



public class Cargador {

	/** Numero de elementos distintos que tendra la simulacion - Planta, Intruso, Trabajador y Lider */
	static final int NUMELTOSCONF  = 4;

	/** Atributo para almacenar el mapeo de los distintos elementos */
	static private DatoMapeo [] mapeo;

	/** Referencia a la instancia del patron Singleton */
	private Planta planta;

	/**
	 *  constructor parametrizado 
	 *  @param e referencia a la instancia del patron Singleton
	 */
	Cargador(Planta p)  {
		mapeo = new DatoMapeo[NUMELTOSCONF];
		//identificador, ancho, alto,sala entrada, sala salida y altura de control del ABB de la cerradura
		mapeo[0]= new DatoMapeo("PLANTA", 7); // El 7 es el n� de campos de la linea en el fichero.txt

		//nombre, marca y turno en el que comienza a moverse.
		mapeo[1]= new DatoMapeo("LIDER", 4);

		//nombre, marca y turno en el que comienza a moverse.
		mapeo[2]= new DatoMapeo("INTRUSO", 4);

		// nombre, marca y turno
		mapeo[3]= new DatoMapeo("TRABAJADOR", 4);
		planta = p;
	}


	/**
	 *  @pre
	 *  @post Busca en mapeo el elemento leido del fichero inicio.txt y devuelve la posicion en la que esta 
	 *  @param <b>elto<b> Elemento a buscar en el array
	 *  @return <b>res<b> posicion en mapeo de dicho elemento
	 *  @complejidad <b>O(n)<b>
	 */
	private int queElemento(String elto)  {
		int res=-1;
		boolean enc=false;

		for (int i=0; (i < NUMELTOSCONF && !enc); i++)  {
			if (mapeo[i].getNombre().equals(elto))      {
				res=i;
				enc=true;
			}
		}
		return res;
	}


	/**
	 *  @post  Crea las distintas instancias de la simulacion 
	 *  @param <b>elto<b> nombre de la instancia que se pretende crear
	 *  @param <b>numCampos<b> numero de atributos que tendra la instancia
	 *  @param <b>vCampos<b> array que contiene los valores de cada atributo de la instancia
	 *  @complejidad <b>O(1)<b>
	 * @throws exceptIdLlave 
	 * @throws NumberFormatException 
	 * @throws IOException 
	 */
	public void crear(String elto, int numCampos, String vCampos[]) throws NumberFormatException, exceptIdLlave, IOException	{
		//Si existe elemento y el numero de campos es correcto, procesarlo... si no, error
		int numElto = queElemento(elto);

		//Comprobacion de datos basicos correctos
		if ((numElto!=-1) && (mapeo[numElto].getCampos() == numCampos))   {
			//procesar
			switch(queElemento(elto))
			{
			case 0:	   
				crearPlanta(numCampos,vCampos);
				break;
			case 1:
				crearLider(numCampos,vCampos);
				break;
			case 2:
				crearIntruso(numCampos,vCampos);
				break;
			case 3:
				crearTrabajador(numCampos,vCampos);
				break;
			}
		}
		else
			System.out.println("ERROR Cargador::crear: Datos de configuracion incorrectos... " + elto + "," + numCampos+"\n");
	}


	/**
	 *  @pre Crea una instancia de la clase Planta
	 *  @param <b>numCampos<b> numero de atributos que tendra la instancia
	 *  @param <b>vCampos<b> array que contiene los valores de cada atributo
	 *  @complejidad <b>O(1)<b>
	 * @throws exceptIdLlave 
	 */
	private void crearPlanta(int numCampos, String[] vCampos) throws exceptIdLlave{

		//Registrar la planta
		Planta p = Planta.obtenerInstancia (Integer.parseInt(vCampos[1]),Integer.parseInt(vCampos[2]), Integer.parseInt(vCampos[3]),
				Integer.parseInt(vCampos[4]), Integer.parseInt(vCampos[5]), Integer.parseInt(vCampos[6]));


		p.iniciarPlanta();

		System.out.println("Creada planta: " + vCampos[1] + "\n");

	}



	/** 
	 * 	@post  Crea una instancia de la clase Lider
	 *  @param <b>numCampos<b> numero de atributos que tendra la instancia
	 *  @param <b>vCampos<b> array que contiene los valores de cada atributo
	 *  @complejidad <b>O(1)<b>
	 */
	private void crearLider(int numCampos, String[] vCampos){     

		Lideres l = new Lideres (vCampos[1],vCampos[2], Integer.parseInt(vCampos[3]),Integer.parseInt(vCampos[4]));
		Planta p = Planta.obtenerInstancia(0);

		Set<Integer> visitados= new TreeSet <Integer>();
		List<Integer> solucion= new LinkedList <Integer>();
		HashSet<List<Integer>> conjuntoCaminos= new HashSet<List<Integer>>();

		p.profundidad( p.getLaberinto().getGrafo(), p.getSala(0).getIdSala(), visitados, conjuntoCaminos, solucion);
		p.getCaminos().setConjuntoCaminos(conjuntoCaminos);

		List<Integer> caminoLider= new LinkedList<Integer>();

		Cola <Dir> direccionesL1= new Cola <Dir>();


		l.caminoLider(p.getLaberinto().getGrafo(), p.getSala(0).getIdSala(),0, visitados, solucion, caminoLider);

		direccionesL1= p.devolverRutaCalculada(caminoLider);  //pasa una lista a dir


		l.setColaRuta(direccionesL1);
		p.meterPersonaje(l); //Registrar lider en la estacion

		System.out.println("Creado lider: " + vCampos[1] + "\n");
	}


	/**
	 *  @post Crea una instazncia de la clase Intruso
	 *  @param <b>numCampos<b> numero de atributos que tendra la instancia
	 *  @param <b>vCampos<b> array que contiene los valores de cada atributo
	 *  @complejidad <b>O(1)<b>
	 * @throws exceptIdLlave 
	 * @throws NumberFormatException 
	 * @throws IOException 
	 */
	private void crearIntruso(int numCampos, String[] vCampos) throws NumberFormatException, exceptIdLlave, IOException{


		Planta p = Planta.obtenerInstancia(0);
		Set<Integer> visitados= new TreeSet <Integer>();
		List<Integer> caminoIntruso= new LinkedList<Integer>();
		int salaInicio= p.getAncho()-1;

		Intrusos i = new Intrusos (vCampos[1],vCampos[2], Integer.parseInt(vCampos[3]), salaInicio);
		Cola <Dir> direccionesI1= new Cola <Dir>();


		i.caminoCompletoIntruso(p.getLaberinto().getGrafo(), caminoIntruso, visitados);


		direccionesI1= p.devolverRutaCalculada(caminoIntruso);


		i.setColaRuta(direccionesI1);
		p.meterPersonaje(i); //Registrar intruso en la estacion

		System.out.println("Creado intruso: " + vCampos[1] + "\n");
	}	



	/**
	 *  @post Crea una instancia de la clase Trabajador
	 *  @param <b>numCampos<b> numero de atributos que tendra la instancia
	 *  @param <b>vCampos<b> array que contiene los valores de cada atributo
	 *  @complejidad <b>O(1)<b>
	 */
	private void crearTrabajador(int numCampos, String[] vCampos){

		Trabajadores t = new Trabajadores (vCampos[1],vCampos[2], Integer.parseInt(vCampos[3]),Integer.parseInt(vCampos[4]));
		Planta p = Planta.obtenerInstancia(0);
		List<Integer> caminoTrabajador= new LinkedList<Integer>();

		caminoTrabajador = t.caminoTrabajador();


		Cola <Dir> direccionesT1= new Cola <Dir>();


		direccionesT1= p.devolverRutaCalculada(caminoTrabajador);

		t.setColaRuta(direccionesT1);

		p.meterPersonaje(t); //Registrar trabajador en la estacion
		System.out.println("Creado trabajador: " + vCampos[1] + "\n");
	}	
}
