/**
 * Implementacion de los metodos de la clase Planta.
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import personas.Intrusos;
import personas.Personaje;
import registro.Sistema;
import estructurasDeDatos.Arbol;
import estructurasDeDatos.Cola;
import estructurasDeDatos.Grafo;

public class Planta {

	/** El indicador de nuestra sala*/
	private int idPlanta;

	/** El ancho de nuestra sala*/
	private int ancho;

	/** El alto de nuestras sala*/
	private int alto;

	/** Es la sala en la que se encuentra la entrada */
	private int salaEntrada;

	/** Es la sala en la que se encuentra la salida */
	private int salaSalida;

	/** Altura de control del ABB de la cerradura*/
	private int alturaCerradura=5;

	/** Numero de trabajadores Y lideres que trabajan en la planta */
	private int numEmpleados = 0;

	/** Es la sala en la que se almacenan los "escapados" */
	private Sala salaEscapados;

	/** Conjunto de salas que tiene la planta*/
	private Sala[] vectorSalas;

	/** Puerta de salida de la planta */
	private Puerta miPuerta = new Puerta();

	/** Turno del sistema */
	private int turno;

	/** Laberinto que describe la planta*/
	private Laberinto laberinto = new Laberinto ();

	/** Diferentes caminos a tomar desde la entrada hasta la salida de la planta */
	private Caminos caminos = new Caminos();

	/** Atributo de la que representa la unica instancia que existira de la clase Planta (Singleton) */
	private static Planta instancia = null;


	private Planta() {
		this.ancho = 6;
		this.alto = 6;
		salaEntrada = 0;
		salaSalida = devolverDimension() - 1;
		turno=0;

		//Lo hace siempre; en cualquier constructor
		dimensionarPlanta();
		rellenarSalas();
		pintarSalas();


	}




	/**
	 * @post Constructor parametrizado
	 * @param <b>idPlantao<b> Identificador de la planta
	 * @complejidad <b>O(1)<b>
	 */
	private Planta( int idPlanta){
		this.idPlanta = idPlanta;
	}



	/**
	 * @post Constructor parametrizado
	 * @param <b>idPlantao<b> Identificador de la planta
	 * @param <b>ancho<b> Ancho de la planta
	 * @param <b>alto<b> Alto de la planta
	 * @param <b>salaEntrada<b> Sala de entrada a la planta
	 * @param <b>salaSalida<b> Sala de salida de la planta
	 * @param <b>alturaCerradura<b> Altura de control del ABB de la cerradura
	 * @throws exceptIdLlave 
	 * @complejidad <b>O(1)<b>
	 */
	private Planta(int idPlanta, int ancho, int alto, int salaEntrada, int salaSalida, int alturaCerradura) throws exceptIdLlave {
		this.idPlanta = idPlanta;
		this.alto=alto;
		this.ancho=ancho;
		this.salaEntrada= salaEntrada;
		this.salaSalida = salaSalida;
		this.alturaCerradura = alturaCerradura;
		this.turno=0;      
		this.salaEscapados = new Sala (1111);

	}




	/**
	 * @pre Planta creada correctamente
	 * @post Devuelve la unica instancia de la Planta. Si no existe, la crea (una sola vez) y la devuelve
	 * @param <b>idPlantao<b> Identificador de la planta
	 * @param <b>ancho<b> Ancho de la planta
	 * @param <b>alto<b> Alto de la planta
	 * @param <b>salaEntrada<b> Sala de entrada a la planta
	 * @param <b>salaSalida<b> Sala de salida de la planta
	 * @param <b>alturaCerradura<b> Altura de control del ABB de la cerradura
	 * @return <b>instancia<b>
	 * @throws exceptIdLlave 
	 * @complejidad <b>O(1)<b> 
	 */
	public static Planta obtenerInstancia(int idPlanta, int ancho, int alto, int salaEntrada, int salaSalida, int alturaCerradura) throws exceptIdLlave{ 

		System.out.println("[Planta] Invocando instancia");
		if (instancia == null)
			instancia = new Planta( idPlanta, ancho, alto, salaEntrada, salaSalida, alturaCerradura);
		return instancia;
	}



	/**
	 * @pre Planta creada correctamente
	 * @post Devuelve la unica instancia de la Planta. Si no existe, la crea (una sola vez) y la devuelve
	 * @param <b>idPlantao<b> Identificador de la planta
	 * @return <b>instancia<b>
	 * @complejidad <b>O(1)<b> 
	 */
	public static Planta obtenerInstancia(int idPlanta){ 

		if (instancia == null)
			instancia = new Planta( idPlanta); 

		return instancia;
	}



	/**
	 * @pre Planta creada correctamente
	 * @post Actualiza el alto de la planta
	 * @param <b>alto<b> Alto de la planta
	 * @complejidad <b>O(1)<b> 
	 */
	public void setAlto(int alto) {
		this.alto = alto;
	}



	/**
	 * @pre Planta creada correctamente
	 * @post Actualiza el ancho de la planta
	 * @param <b>ancho<b> Ancho de la planta
	 * @complejidad <b>O(1)<b> 
	 */
	public void setAncho(int ancho) {
		this.ancho = ancho;
	}



	/**
	 * @pre Planta creada correctamente
	 * @post Actualiza la sala de salida de la planta
	 * @param <b>salaSalida<b> Sala de salida de la planta
	 * @complejidad <b>O(1)<b> 
	 */
	public void setSalaSalida(int salaSalida) {
		this.salaSalida = salaSalida;
	}



	/**
	 * @pre Planta creada correctamente
	 * @post Aumenta el numero de personajes de la planta
	 * @complejidad <b>O(1)<b> 
	 */
	public void aumentarEmpleados(){
		this.numEmpleados++;
	}



	/**
	 * @pre Planta creada correctamente
	 * @post Actualiza las salas de la planta
	 * @param <b>vectorSalas<b> Las salas de la planta
	 * @complejidad <b>O(1)<b> 
	 */
	public void setVectorSalas(Sala[] vectorSalas) {
		this.vectorSalas=vectorSalas;

	}



	/**
	 * @pre Planta creada correctamente
	 * @post Actualiza la puerta de la planta
	 * @param <b>puerta<b> Nueva puerta de la planta
	 * @complejidad <b>O(1)<b> 
	 */
	public void setPuerta(Puerta puerta) {
		this.miPuerta = puerta;
	}




	/**
	 * @pre Planta creada correctamente
	 * @post Devuelve el valor del identificador de la planta
	 * @return <b>idPlanta<b> Identificador de la planta
	 * @complejidad <b>O(1)<b> 
	 */
	private int getIdPlanta() {
		return this.idPlanta;
	}



	/**
	 * @pre Planta creada correctamente
	 * @post Devuelve el alto la planta
	 * @return <b>alto<b> Alto la planta
	 * @complejidad <b>O(1)<b> 
	 */
	public int getAlto() {
		return this.alto;
	}



	/**
	 * @pre Planta creada correctamente
	 * @post Devuelve el ancho la planta
	 * @return <b>ancho<b> Ancho la planta
	 * @complejidad <b>O(1)<b> 
	 */
	public int getAncho() {
		return this.ancho;
	}



	/**
	 * @pre Planta creada correctamente
	 * @post Devuelve la sala especificada por su posicion
	 * @param <b>pos<b> Posicion de la sala a devolver
	 * @return <b>vectorSalas[pos]<b> Sala especificada
	 * @complejidad <b>O(1)<b> 
	 */
	public Sala getSala ( int pos ) {
		return this.vectorSalas[pos];
	}




	/**
	 * @pre Planta creada correctamente
	 * @post Devuelve la sala de personajes escapados
	 * @return <b>salaEscapados<b> Sala de personajes escapados
	 * @complejidad <b>O(1)<b> 
	 */
	public Sala getSalaEscapados ( ) {
		return this.salaEscapados;
	}



	/**
	 * @pre Planta creada correctamente
	 * @post Devuelve el numero de Lideres y Trabajadores de la planta
	 * @return <b>numEmpleados<b> Resultado de sumar el numero de Lideres y Trabajadores de la planta
	 * @complejidad <b>O(1)<b> 
	 */
	public int getNumeroEmpleados (){
		return this.numEmpleados;
	}


	/**
	 * @pre Planta creada correctamente
	 * @post Devuelve el valor del codigo de la sala entrada
	 * @return <b>salaEntrada<b> Valor del codigo de la sala entrada
	 * @complejidad <b>O(1)<b> 
	 */
	public int getSalaEntrada() {
		return this.salaEntrada;
	}



	/**
	 * @pre Planta creada correctamente
	 * @post Devuelve el valor del codigo de la sala salida
	 * @return <b>salaSalida<b> Valor del codigo de la sala salida
	 * @complejidad <b>O(1)<b> 
	 */
	public int getSalaSalida() {
		return this.salaSalida;
	}



	/**
	 * @pre Planta creada correctamente
	 * @post Devuelve el valor de la Altura de control del ABB de la cerradura
	 * @return <b>alturaCerradura<b>  Altura de control del ABB de la cerradura
	 * @complejidad <b>O(1)<b> 
	 */
	public int getAlturaCerradura() {
		return this.alturaCerradura;
	}



	/**
	 * @pre Planta creada correctamente
	 * @post Devuelve el vector salas de la planta
	 * @return <b>vectorSalas<b>  Vector que contiene las salas de la planta
	 * @complejidad <b>O(1)<b> 
	 */
	public Sala[] getVectorSalas() {
		return this.vectorSalas;
	}



	/**
	 * @pre Planta creada correctamente
	 * @post Devuelve la puerta de la planta
	 * @return <b>miPuerta<b>  Puerta de la planta
	 * @complejidad <b>O(1)<b> 
	 */
	public Puerta getPuerta() {
		return this.miPuerta;
	}



	/**
	 * @pre Planta creada correctamente
	 * @post Devuelve el turno del sistema
	 * @return <b>turno<b>  Turno de la planta (sistema)
	 * @complejidad <b>O(1)<b> 
	 */
	public int getTurno() {
		return this.turno;
	}


	/**
	 * @pre Planta creada correctamente
	 * @post Devuelve el laberinto de la planta
	 * @return <b>laberinto<b>  Laberinto de la planta 
	 * @complejidad <b>O(1)<b> 
	 */
	public Laberinto getLaberinto(){
		return laberinto;
	}


	/**
	 * @pre Planta creada correctamente
	 * @post Devuelve los caminos de salida de la planta
	 * @return <b>caminos<b>  Caminos la planta 
	 * @complejidad <b>O(1)<b> 
	 */
	public Caminos getCaminos(){
		return this.caminos;
	}


	/**
	 * @pre Planta creada correctamente
	 * @post Dimensiona el tamanio de la planta
	 * @complejidad <b>O(1<b>) 
	 */
	private void dimensionarPlanta(){
		vectorSalas = new Sala [devolverDimension()];
	}



	/**
	 * @pre Planta creada correctamente
	 * @post Devuelve el valor de la dimension de la planta
	 * @return <b>ancho * alto<b> Dimension de la planta
	 * @complejidad <b>O(1)<b> 
	 */
	public int devolverDimension(){
		return ancho * alto;
	}



	/**
	 * @pre Planta creada correctamente
	 * @post Rellena el vector de salas, cada una con su identificador, marcador y vecinas corresponbdientes
	 * @complejidad <b>O(n)<b> 
	 */
	public void rellenarSalas(){

		for (int i= 0; i< vectorSalas.length; i++){
			vectorSalas[i]= new Sala();
			vectorSalas[i].setIdSala(i);
			vectorSalas[i].setMarcador(i);
			vectorSalas[i].asignarVecinas(ancho, devolverDimension());
		}
	}



	/**
	 * @pre Planta creada correctamente
	 * @post Limpia las salas vecinas de cada una de nuestras salas de la planta
	 * @complejidad <b>O(n)<b> 
	 */
	public void limpiarVecinas() {
		for(int i=0; i< vectorSalas.length;i++){
			vectorSalas[i].limpiarVecinas();}	

	}




	/**
	 * @pre Planta creada correctamente
	 * @post Metodo que restaura los valores de las Vecinas de todas las salas
	 * @complejidad <b>O(n)<b> 
	 */
	public void restaurarVecinas(){
		for (int i= 0; i< vectorSalas.length; i++){
			vectorSalas[i].asignarVecinas(ancho, devolverDimension());
		}
	}




	/**
	 * @pre Planta creada correctamente
	 * @post Actualiza el valor de los marcadores de las salas que tienen un mismo marcador
	 * @param <b>marcadorActual<b> Marcador que vamos a modificar
	 * @param <b>marcadorFinal<b> Valor del nuevo marcador de las salas
	 * @complejidad <b>O(n)<b> 
	 */
	public void compartirMarcador (int marcadorActual, int marcadorFinal){

		for (int i=0; i< vectorSalas.length;i++){
			if (vectorSalas[i].getMarcador() == marcadorActual)
				vectorSalas[i].setMarcador(marcadorFinal);
		}
	}



	/**
	 * @pre Planta creada correctamente
	 * @post Hace el reparto de las llaves en las salas elegidas
	 * @throws exceptIdLlave 
	 * @complejidad <b>O(n²)<b> 
	 */
	public void repartodeLlaves() throws exceptIdLlave{
		int numIdLlave = 0;
		int []vectorLlaves= new int[45];
		//llenamos el vector de llaves con todas las llaves que vamos a disponer
		for (int i=0; i<vectorLlaves.length && numIdLlave<30; i++){
			vectorLlaves[i]=numIdLlave;
			if(numIdLlave%2 != 0){
				i++;
				vectorLlaves[i]=numIdLlave;
			}
			numIdLlave++;
		}

		// -------- DECIDIMOS LAS SALAS A USAR
		int []idSalasConLlave= new int[9];
		for(int i=0; i< idSalasConLlave.length; i++)
			idSalasConLlave[i]=caminos.devolverSalaFrecuente(i);


		// -------- METEMOS EN LAS SALAS
		int k=0;
		Sistema.escribirSistema("(distribucion llaves)");
		for (int i=0; i<idSalasConLlave.length; i++){
			System.out.print("Sala: "+ vectorSalas[idSalasConLlave[i]].getIdSala()+"    ");
			Sistema.escribirSistema("\n(sala:"+ vectorSalas[idSalasConLlave[i]].getIdSala()+":");
			do{
				vectorSalas[idSalasConLlave[i]].introducirLlave(vectorLlaves[k]);
				System.out.print(vectorLlaves[k]+", ");
				Sistema.escribirSistema(" "+vectorLlaves[k]);
				k++;
			}while(k%5 != 0);
			System.out.println();
			Sistema.escribirSistema(")");
		}
		System.out.println("LLAVES DISTRIBUIDAS");
	}




	/**
	 * @pre Personaje y sala creados correctamente
	 * @post Introduce un Personaje en una sala, si no es un intruso aumenta el numero de empleados de la planta
	 * @param <b>nuevoPersonaje<b> Personaje a mover a una sala
	 * @complejidad <b> O(1)<b>  
	 */
	public void meterPersonaje(Personaje nuevoPersonaje){
		int sala= nuevoPersonaje.getIdSala();
		vectorSalas[sala].introducirPersonaje(nuevoPersonaje);
		if(!(nuevoPersonaje instanceof Intrusos))
			aumentarEmpleados();
	}




	/**
	 * @pre Planta creada correctamente
	 * @post Pinta las salas de la planta con sus marcadores
	 * @complejidad <b>O(n²)<b>
	 */
	public void pintarSalas() {
		System.out.println();
		System.out.println("Pintamos las salas con sus marcadores");

		for(int i=0; i< vectorSalas.length; i++){
			if (vectorSalas[i].getIdSala()%ancho == 0){
				System.out.println();
				System.out.print("          |");
				for (int j=0; j< ancho; j++)
					System.out.print("---");
				System.out.println();
				System.out.print("          |");
			}
			if(vectorSalas[i].getMarcador() < 10)
				System.out.print(vectorSalas[i].getMarcador()+" |");
			else
				System.out.print(vectorSalas[i].getMarcador()+"|");

		}
		System.out.println();
		System.out.print("          |");
		for (int j=0; j< ancho; j++)
			System.out.print("---");
		System.out.println();

	}



	/**
	 * @pre Planta creada correctamente
	 * @post Configura la cerradura con la combinacion de llaves
	 * @throws exceptIdLlave 
	 * @complejidad <b>O(n)<b>
	 */
	public void configurarCerradura() throws exceptIdLlave{
		Arbol <Llave> combinacion = new Arbol <Llave>();
		if (!miPuerta.isConfiguarada() && !miPuerta.isCerrada()){
			//lista de llave que deben insertar en la combinacion de la puerta
			LinkedList <Integer> lista = new LinkedList <Integer>();
			System.out.println();
			System.out.print("La llaves que compondran la combinacion son: ");
			for(int i=0; i<=30; i++){
				if(i%2 != 0){
					lista.addLast(i);
					System.out.print(i+" ,");
				}
			}

			//desordenar para insertar en orden aleatorio en un arbol
			Llave llave;
			System.out.println();
			System.out.print("Las vamos a meter en el siguiente orden: ");
			while(lista.size() > 0){
				int posicion = GenAleatorios.generarNumero(lista.size());
				llave= new Llave(lista.get(posicion).intValue());
				combinacion.insertar(llave);
				lista.remove(posicion);
				System.out.print(llave.getDato()+" ,");
			}
			System.out.println();
			System.out.println();

			miPuerta.setCerrada(true); // cierra la puerta
			miPuerta.setCombinacionApertura(combinacion); //configura la puerta
			miPuerta.setConfiguarada(true); //me dice que esta configurada la puerta
		}
		else{
			if (!miPuerta.isCerrada()){
				miPuerta.setCombinacionApertura(combinacion); //reconfigura la puerta
				miPuerta.setCerrada(true);
			}
		}

	}



	/**
	 * @pre Planta creada correctamente
	 * @post Visualiza por pantalla las llaves que hay en la planta
	 * @complejidad <b>O(n)<b>
	 */
	public void pintarLlavesPlanta(){ 
		System.out.println();
		for (int i=0;i<vectorSalas.length; i++){
			if (!vectorSalas[i].estaVaciaLlaves()){
				System.out.print("Sala "+i+": ");
				Sistema.escribirSistema("(sala:"+i+":");
				vectorSalas[i].mostrarLlaves();
				Sistema.escribirSistema(")\n");
			}
		}
	}



	/**
	 * @pre Planta creada correctamente
	 * @post Muestra los datos de todas las paredes de la planta
	 * @param <b>pared<b> Lista de paredes de la planta
	 * @complejidad <b>O(n)<b>
	 */
	public void mostrarParedes(List <Pared> pared) {
		System.out.println("Planta.mostrarParedes()");
		System.out.println("Total de paredes mostradas   -->  "+pared.size());
		Iterator<Pared> it=pared.iterator();
		for( ;it.hasNext(); )
			it.next().visualizar();



	}


	/**
	 * @pre Planta creada correctamente
	 * @post Pinta la planta con sus personajes
	 * @complejidad <b>O(n)<b>
	 */
	public void pintarPlanta(){ 
		System.out.println();
		//Se pinta la pared de arriba
		System.out.print("           ");
		for (int j=0; j< ancho; j++)
			System.out.print("- ");

		for (int i=0; i< vectorSalas.length; i++){
			vectorSalas[i].pintarSala();
		}

		System.out.println("|");
		System.out.print("           ");
		for (int j=0; j< ancho; j++)
			System.out.print("- ");
		System.out.println();
		System.out.println();
	}




	/**
	 * @pre Planta creada correctamente
	 * @post Pinta la planta con sus paredes y personajes
	 * @complejidad <b>O(n²)<b>
	 */
	public void pintarParedes() {

		int j=0;

		//Pintamos primera linea
		System.out.print("\n ");
		Sistema.escribirSistema("\n");
		for (int k=0; k< ancho; k++){
			System.out.print(" "+(char)95);
			Sistema.escribirSistema(" "+(char)95);
		}

		for(int i=0; i< vectorSalas.length; i++){

			//Pintamos el delimitador de la izquierda
			if (vectorSalas[i].getIdSala()%ancho == 0){
				System.out.print("\n"+(char)124);
				Sistema.escribirSistema("\n"+(char)124);
			}

			//Pintamos a los personajes si hubieran
			if(!vectorSalas[i].estaVacia()){            		
				if (vectorSalas[i].devolverCantidadPersonajes() > 1){
					System.out.print(vectorSalas[i].devolverCantidadPersonajes()+"");
					Sistema.escribirSistema(vectorSalas[i].devolverCantidadPersonajes()+"");
				}
				else{
					System.out.print(vectorSalas[i].devolverPersonaje().getMarca()+"");
					Sistema.escribirSistema(vectorSalas[i].devolverPersonaje().getMarca()+"");
				}
			}
			else{
				//Pintamos vecinas del sur si tiene
				if (vectorSalas[i].getVecinoS()!= vectorSalas[i].getIdSala()){
					System.out.print(" ");
					Sistema.escribirSistema(" ");
				}
				else{
					System.out.print((char)95);
					Sistema.escribirSistema((char)95);
				}
			}
			//Pintamos vecina del este si tiene    	        	
			if (vectorSalas[i].getVecinoE()!= vectorSalas[i].getIdSala()){
				System.out.print(" ");
				Sistema.escribirSistema(" ");
			}
			else{
				System.out.print((char)124);
				Sistema.escribirSistema((char)124);
			}

			//Incremantamos variable j o la ponemos a 0
			if (j== ancho-1) j=0; else j++;

		}

		System.out.println();
		Sistema.escribirSistema("\n");


	}




	/**
	 * @pre Planta creada correctamente
	 * @post Hace que los personajes avances una casilla segun su turno
	 * @complejidad <b>O(n²)<b>
	 */
	private void avanzarPersonajes() {

		//tiene que recorrer mi vector de salas e ir avanzando cada personaje de esa
		//segun lo que diga su cola de ruta

		this.turno ++; // avanzamos un turno el juego
		for (int i=0; i<this.vectorSalas.length; i++){    
			//Mientras que en esa casilla haya personajes y su turno coincida
			this.vectorSalas[i].accionPersonaje( );
			//elegimos la accion adecuada para ese personaje que se ha sacado

		}


	}



	/**
	 * @pre Planta creada correctamente
	 * @post Comprueba si se puede continuar con la simulacion; si quedan empleados en la planta
	 * @return <b>True<b> Si siguen quedando empleados
                <b>False<b> En caso contrario
	 * @complejidad <b>O(1)<b>
	 */
	private boolean comprobarSiContinuar( ) {

		if(getSalaEscapados().devolverCantidadPersonajes()== getNumeroEmpleados())
			return false;
		System.out.println("Personas: "+getSalaEscapados().devolverCantidadPersonajes()+" Empleados: "+ getNumeroEmpleados());

		return true;
	}



	/**
	 * @pre Planta creada correctamente
	 * @post Crea todas las paredes posibles de la planta
	 * @return <b>listaParedes<b>  La lista de paredes creadas
	 * @complejidad <b>O(n)<b>
	 */
	public List<Pared> crearParedes() {
		// Lista que tiene las paredes de toda la planta
		List<Pared> listaParedes= new ArrayList<Pared>();
		Pared P = new Pared ();
		int origen, destino;
		//orden: n, e, s o

		for(int i=0; i<devolverDimension(); i++){
			if(vectorSalas[i].getVecinoN() != i){
				origen =i;
				destino = vectorSalas[i].getVecinoN();
				P = new Pared (origen, destino);
				listaParedes.add(P);
			}
			if(vectorSalas[i].getVecinoE() != i){
				origen =i;
				destino = vectorSalas[i].getVecinoE();
				P = new Pared (origen, destino);
				listaParedes.add(P);
			}
			if(vectorSalas[i].getVecinoS() != i){
				origen =i;
				destino = vectorSalas[i].getVecinoS();
				P = new Pared (origen, destino);
				listaParedes.add(P);
			}
			if(vectorSalas[i].getVecinoO() != i){
				origen =i;
				destino = vectorSalas[i].getVecinoO();
				P = new Pared (origen, destino);
				listaParedes.add(P);
			}
		}
		return listaParedes;
	}    




	/**
	 * @pre Grafo creado correctamente
	 * @post Devolver por referencia un correcto conjunto de Caminos
	 * @param <b>G<b> Grafo de nodos
	 * @param <b>v<b> Sala en la que va ha empezar el lider
	 * @param <b>visitados<b> Auxiliar que se usa en la llamada recursiva para saber los nodos visitados del grafo
	 * @param <b>conjuntoCaminos<b> Conjunto de los caminos solucionados
	 * @param <b>solucion<b> Auxiliar que usa la llamda recursiva para ir guardando las posibles soluciones
	 * @complejidad <b>O(1)<b>
	 */
	public void profundidad(Grafo G, Integer v, Set<Integer> visitados, HashSet<List<Integer>> conjuntoCaminos, List<Integer> solucion ){
		Prof(G, v, solucion,conjuntoCaminos);
	}




	/**
	 * @pre Grafo creado correctamente
	 * @post Devolver por referencia un conjunto de Caminos
	 * @param <b>G<b> Grafo de nodos
	 * @param <b>v<b> Sala en la que va ha empezar el lider
	 * @param <b>visitados<b> Auxiliar que se usa en la llamada recursiva para saber los nodos visitados del grafo
	 * @param <b>conjuntoCaminos<b> Conjunto de los caminos solucionados
	 * @complejidad <b>O(n)<b>
	 */
	public void Prof(Grafo G, Integer v, List<Integer> visitados, HashSet<List<Integer>> conjuntoCaminos){ 			

		Set<Integer> ady= new TreeSet<Integer>();
		Integer w= new Integer(0);
		visitados.add(v);

		Iterator<Integer>  it_conjunto= visitados.iterator();

		int idSala;

		if(v==salaSalida){
			for(;it_conjunto.hasNext();){
				idSala=(Integer) it_conjunto.next();
				vectorSalas[idSala].setFrecuencia();
			}
			conjuntoCaminos.add(new LinkedList<Integer>(visitados));
		}
		else{
			G.adyacentes(v, ady);
			Iterator<Integer> it2= ady.iterator();
			while(it2.hasNext()){
				w=(Integer)it2.next();

				if(!visitados.contains(w))
					Prof(G, w, visitados,conjuntoCaminos);	
			}
		}
		visitados.remove(v);		
	}





	/**
	 * @pre El idSalaAnt y el idSala no coinciden
	 * @post Devuelve el movimiento que se ha realizado
	 * @param <b>idSalaAnt<b> Sala en la que estaba anteriormente
	 * @param <b>idSala<b> Sala en la que estoy ahora
	 * @return <b>Dir<b> El movimiento que se ha realizado
	 * @complejidad <b>O(1)<b>
	 */
	public Dir calcularDireccion(int idSalaAnt, int idSala) {
		int resta= idSalaAnt-idSala;
		if (resta == ancho) resta=2;
		if (resta == -ancho) resta=-2;

		switch(resta){
		case -1: return Dir.E;
		case -2: return Dir.S;
		case 1:	 return Dir.O;
		case 2:  return Dir.N;
		default: System.out.println("Mi case tenia  "+resta);
		}
		return null;
	}




	/**
	 * @pre Lista camino con datos correctos
	 * @post Transforma la lista camino en una ruta de direcciones
	 * @param <b>camino<b> Camino que tenemos atransformar en ruta de direcciones
	 * @return <b>ruta<b> Ruta de direcciones calculada a partir de ese camino
	 * @complejidad <b>O(n)<b>
	 */
	public Cola <Dir>  devolverRutaCalculada(List<Integer> camino) {
		Iterator<Integer>  it= camino.iterator();
		int idSala, idSalaAnt=0;
		Cola <Dir> ruta = new Cola <Dir>();
		boolean primero=false;

		while(it.hasNext()){
			idSala=(Integer) it.next();

			if(primero){
				if (calcularDireccion(idSalaAnt, idSala) != null)
					ruta.encolar(calcularDireccion(idSalaAnt, idSala));

			}

			idSalaAnt=idSala;
			primero=true;
		}
		return ruta;
	}




	/**
	 * @pre 
	 * @post Conversor de Dir a String
	 * @param <b>dir<b> Vector de direcciones a convertir en String
	 * @return <b>result<b> String que contiene el contenido del vector de direcciones
	 * @complejidad <b>O(n)<b>
	 */
	public  String toString(Dir[] dir) {
		String result="";
		for (int i=1; i< dir.length; i++)
			result=result+dir[i]+" ";
		return result;
	}




	/**
	 * @pre Planta creada correctamente
	 * @post Configura la planta para que pueda ser utilizada
	 * @throws exceptIdLlave 
	 * @complejidad <b>O(1)<b>
	 */
	public void iniciarPlanta () throws exceptIdLlave{

		Sistema.escribirSistema("(planta "+getIdPlanta()+")");
		dimensionarPlanta();
		rellenarSalas();

		List <Pared> paredes = crearParedes();

		limpiarVecinas (); 
		this.laberinto.kruskal(paredes,devolverDimension());


		this.getLaberinto().atajos();
		configurarCerradura();
		pintarParedes();        

		Set<Integer> visitados= new TreeSet <Integer>();
		List<Integer> solucion= new LinkedList <Integer>();
		HashSet<List<Integer>> conjuntoCaminos= new HashSet<List<Integer>>();

		profundidad( getLaberinto().getGrafo(), salaEntrada, visitados, conjuntoCaminos, solucion);

		this.caminos.setSalas(this.vectorSalas);

		repartodeLlaves();  


		this.caminos.setConjuntoCaminos(conjuntoCaminos);
		System.out.println("Caminos    ------> "+caminos.getConjuntoCaminos());  	
	}




	/**
	 * @pre Planta creada correctamente
	 * @post Realiza la simulacion. (Programa Principal) 
	 * @complejidad <b>O(n)<b>
	 */
	public void continuarPlanta() {

		int movimientosTotales=99;

		boolean exit=false;
		//pintar los personajes en cada turno
		int i = 0;

		for (int j=0; j< vectorSalas.length; j++){

			vectorSalas[j].rutaPersonaje( );
		}


		while ( i<movimientosTotales && !exit){
			System.out.println();
			System.out.println("TURNO: " +    (getTurno()+1) );

			if(getTurno()==0)Sistema.escribirSistema("\n");

			Sistema.escribirSistema("(turno:"+( getTurno()+1 )+")");
			Sistema.escribirSistema("\n(salaescapados:"+ getSalaEscapados().getIdSala()+")");


			if(!this.getSalaEscapados().estaVacia())
				this.getSalaEscapados().personajesEscapados();	

			Sistema.escribirSistema("\n(planta:"+getIdPlanta()+":"+getSalaEntrada()+":"+getSalaSalida()+")");
			Sistema.escribirSistema("\n(puerta:"+getPuerta().getEstado()+":"+this.alturaCerradura+":"+getPuerta().getMemoriaLlavesProbadas().toString()+")");

			pintarParedes();
			pintarLlavesPlanta();

			avanzarPersonajes();
			if(!comprobarSiContinuar())
				exit=true;
			i++;
		}
		System.out.println("  -------------  FIN DE LA SIMULACION  -------------");
		System.out.println("  TURNO:  "+getTurno());

		Sistema.escribirSistema("(fin de la simulacion)");
		Sistema.escribirSistema("\n(planta:"+getIdPlanta()+":"+getSalaEntrada()+":"+getSalaSalida()+")");
		Sistema.escribirSistema("\n(puerta:"+getPuerta().getEstado()+":"+this.alturaCerradura+":"+getPuerta().getMemoriaLlavesProbadas().toString()+")");

		pintarParedes();
		pintarLlavesPlanta();

		avanzarPersonajes();
		Sistema.escribirSistema("(miembros escapados)");
		this.getSalaEscapados().personajesEscapados();	
		Sistema.escribirSistema("\n");
	}

}