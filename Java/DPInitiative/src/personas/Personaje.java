/**
 * Implementacion de los metodos de la clase Personaje.
 * 
 * @version 1.0
 * @author Asignatura Desarrollo de Programas<br/>
 * Grupo: Feli&Carlos <br/>
 * Entrega Junio <br/>
 * <b> Felisa Maria Arroba Alonso </b><br>
 * <b> Juan Carlos Bonilla Bermejo </b><br>
 * Curso 12/13
 */

package personas;

import registro.Sistema;
import station.*;
import estructurasDeDatos.Cola;
import estructurasDeDatos.Pila;
import estructurasDeDatos.Arbol;

public class Personaje {

	/** Marca del personaje */
	protected String marca;

	/** Nombre del personaje */
	protected String nombre;

	/** Turno del personaje */
	protected int turno;

	/** Identificador de la sala en la que se encuentra el personaje */
	protected int idSala;

	/** Identificador de la sala en la que empieza el personaje */
	protected int idSalaInicio;


	/** Estructura de almacenamiento de las llaves del personaje */
	protected Pila<Llave> pilaLlaves = new Pila<Llave>();

	/** Cola que indica la ruta que va a llevar el personaje*/
	private Cola <Dir> colaRuta= new Cola <Dir>();


	public Personaje() {
		nombre = " ";
		marca = " ";
		turno = 0;
		idSala = 0;
		idSalaInicio =0;
	}




	/**
	 * @post Constructor parametrizado de la clase Personaje
	 * @param <b>marca<b> Cadena de caracteres con la marca del personaje
	 * @param <b>nombre<b> Cadena de caracteres con el nombre del personaje
	 * @param <b>turno<b> El turno del personaje
	 * @param <b>idSala<b> Identificador de la sala en la que se encuentra el personaje
	 * @complejidad <b>O(1)<b>
	 */
	public Personaje(String nombre, String marca, int turno, int idSala) {
		this.marca = marca;
		this.nombre = nombre;
		this.turno = turno;
		this.idSala = idSala;
		this.idSalaInicio = idSala;

	}




	/**
	 * @pre Personaje creado correctamente
	 * @post Devuelve la marca del personaje
	 * @return <b>marca<b> La marca del personaje
	 * @complejidad <b>O(1)<b>
	 */
	public String getMarca() {
		return marca;
	}




	/**
	 * @pre Personaje creado correctamente
	 * @post Devuelve el nombre del personaje
	 * @return <b>nombre<b> El nombre del personaje
	 * @complejidad <b>O(1)<b>
	 */
	public String getNombre() {
		return nombre;
	}




	/**
	 * @pre Personaje creado correctamente
	 * @post Devuelve el turno del personaje
	 * @return <b>turno<b> El turno del personaje
	 * @complejidad <b>O(1)<b>
	 */
	public int getTurno() {
		return turno;
	}




	/**
	 * @pre Personaje creado correctamente
	 * @post Devuelve el identificador de la sala en la que se encuentra el personaje
	 * @return <b>idSala<b> El identificador de la Sala
	 * @complejidad <b>O(1)<b>
	 */
	public int getIdSala() {
		return idSala;
	}



	/**
	 * @pre Personaje creado correctamente
	 * @post Devuelve el identificador de la sala en la que empieza el personaje
	 * @return <b>idSala<b> El identificador de la Sala
	 * @complejidad <b>O(1)<b>
	 */
	public int getIdSalaInicio() {
		return idSalaInicio;
	}




	/**
	 * @pre Personaje creado correctamente
	 * @post Devuelve la pila de llaves del personaje
	 * @return <b>pilaLlaves<b> La pila de llaves del personaje
	 * @complejidad <b>O(1)<b>
	 */
	public Pila<Llave> getPilaLlaves() {
		return pilaLlaves;
	}




	/**
	 * @pre Personaje creado correctamente
	 * @post Escribe en el .log la ruta del personaje
	 * @complejidad <b>O(n)<b>
	 */
	public void mostrarRuta(){ 

		Cola <Dir> aux = new Cola <Dir>();// para no perder los datos
		Dir d;


		while (!this.colaRuta.estaVacia()){
			d= this.colaRuta.primero();
			System.out.print("  " + d);
			Sistema.escribirSistema(" " + d);
			aux.encolar(d);
			this.colaRuta.desencolar();
		}

		System.out.println();
		Sistema.escribirSistema(")");

		while (!aux.estaVacia()){
			d= aux.primero();
			this.colaRuta.encolar(d);
			aux.desencolar();
		}

	}



	/**
	 * @pre Personaje creado correctamente
	 * @post Modifica la marca del personaje
	 * @param <b>marca<b> Nueva marca del personaje
	 * @complejidad <b>O(1)<b>
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}



	/**
	 * @pre Personaje creado correctamente
	 * @post Modifica el nombre del personaje
	 * @param <b>nombre<b> Nueva nombre del personaje
	 * @complejidad <b>O(1)<b>
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}




	/**
	 * @pre Personaje creado correctamente
	 * @post Modifica el turno del personaje
	 * @param <b>turno<b> Nuevo turno del personaje
	 * @complejidad <b>O(1)<b>
	 */
	public void setTurno(int turno) {
		this.turno = turno;
	}




	/**
	 * @pre Personaje creado correctamente
	 * @post Modifica identificador de la sala en la que se encuentra el personaje
	 * @param <b>turno<b> Nuevo turno del personaje
	 * @complejidad <b>O(1)<b>
	 */
	public void setIdSala(int idSala) {
		this.idSala = idSala;
	}




	/**
	 * @pre Personaje creado correctamente
	 * @post Modifica la pila de llaves del personaje
	 * @param <b>pilaLlaves<b> Nueva pila de llaves del personaje
	 * @complejidad <b>O(1)<b>
	 */
	public void setPilaLlaves(Pila<Llave> pilaLlaves) {
		this.pilaLlaves = pilaLlaves;
	}




	/**
	 * @pre Personaje creado correctamente
	 * @post Asigna la ruta a nuestro personaje
	 * @param <b>colaRuta<b> Nueva ruta a seguir por el personaje
	 * @complejidad <b>O(1)<b>
	 **/
	public void setColaRuta(Cola <Dir> colaRuta) {
		this.colaRuta = colaRuta;
	}



	/**
	 * @pre Personaje creado correctamente
	 * @post Realiza todas las posibles acciones del personaje en su turno
	 * @param <b>miPlanta<b> Planta en la que se encuentra el personaje
	 * @complejidad <b>O(1)<b>
	 */
	public void Accion( ) {

		accionPuerta();
		accionMovimiento();
		accionLlaves();

	}



	/**
	 * @pre Personaje creado correctamente
	 * @post Realiza una accion con la puerta
	 * @param <b>miPlanta<b> Planta en la que se encuentra el personaje
	 * @return <b>True<b> si el personaje prueba una llave
	 * 		   <b>False<b> en caso contrario
	 * @complejidad <b>O(1)<b>
	 */
	public boolean accionPuerta( ) {
		boolean enc = false;

		Planta miPlanta = Planta.obtenerInstancia(0);
		// usamos un vector de salas auxiliar
		Sala[] vectorSalas = miPlanta.getVectorSalas();
		Puerta P = miPlanta.getPuerta();
		Llave miLlave = null;
		Arbol <Llave> combinacion = P.getCombinacionApertura();

		if (idSala == miPlanta.getSalaSalida()) {
			if (P.isCerrada()) {
				if (!pilaLlaves.estaVacia()) {
					miLlave = (Llave) pilaLlaves.getDatoCima();// probamos la ultima llave obtenida
					pilaLlaves.sacarDato();
					enc = true;		
					if (!P.estaProbada(miLlave)) {// Comprobamos que no se ha probado anteriormente		
						P.getMemoriaLlavesProbadas().insertar(miLlave);// La agregamos a la memoria de llaves probadas
						System.out.print("Lista de llaves PROBADAS: ");
						P.getMemoriaLlavesProbadas().inOrden();
						System.out.println();
						System.out.println();

						if (P.formaParteCombinacion(miLlave)) {// Probamos la llave en la cerradura			
							combinacion.borrar(miLlave);
							System.out.println("La LLAVE insertada ES CORRECTA");		
							System.out.print("LLaves restantes COMBINACION DE APERTURA: ");
							combinacion.inOrden();
							System.out.println();
							System.out.println();
							if (P.comprobarCondicionApertura(miPlanta.getAlturaCerradura())){// Probamos la condicion de apertura
								P.abrirPuerta();

								vectorSalas[miPlanta.getSalaSalida()].sacarPersonaje();
								this.setIdSala(1111);
								miPlanta.getSalaEscapados().introducirPersonaje(this);
							}
						}else
							System.out.println("La LLAVE insertada NO forma parte de la COMBINACION DE APERTURA");
					}else
						System.out.println("La LLAVE insertada YA ha sido PROBADA");
				}
			}else {// El personaje se escapa pues la puerta esta abierta
				vectorSalas[miPlanta.getSalaSalida()].sacarPersonaje();
				this.setIdSala(1111);
				miPlanta.getSalaEscapados().introducirPersonaje(this);	
			}
		}
		return enc;

	}



	/**
	 * @pre Personaje creado correctamente
	 * @post Realiza una accion con la llave. (Por defecto coje una llave, si la hay)
	 * @return <b>True<b> si el personaje coje una llave
	 * 		   <b>False<b> en caso contrario
	 * @complejidad <b>O(1)<b>
	 */
	public boolean accionLlaves( ) {

		boolean enc = false;
		Llave miLlave;
		Planta miPlanta = Planta.obtenerInstancia(0);

		if (idSala!=1111  && !miPlanta.getSala(idSala).estaVaciaLlaves() ){// Si la sala tiene llaves y no esta escapado
			miLlave = miPlanta.getSala(idSala).sacarLlave();// Coje la llave de la sala
			pilaLlaves.insertarDato(miLlave); // Apila la llave en el personaje
			enc = true;
		}
		return enc;
	}



	/**
	 * @pre Personaje creado correctamente
	 * @post Mueve al personaje por la planta
	 * @return <b>True<b> si el personaje se mueve, 
	 *         <b>False<b> en caso contrario
	 * @complejidad <b>O(n)<b>
	 */
	public boolean accionMovimiento() {


		Planta miplanta = Planta.obtenerInstancia(0);
		// usamos un vector de salas auxiliar
		Sala[] vectorSalas = miplanta.getVectorSalas();
		// indica el codigo de sala a la que se va mover mi personaje
		int codSala =idSala;
		// variable que indica la siguiente ruta de mi personaje
		Dir siguienteRuta=null;
		// variable que indica la sala en la que estoy
		int i = getIdSala();
		// Calculamos hacia donde se va a mover mi peresonaje
		boolean seMueve=false;

		if(idSala!=1111){

			siguienteRuta = this.colaRuta.primero();
			this.colaRuta.desencolar();

			//La ruta del intruso es ciclica
			if( this instanceof Intrusos)
				this.colaRuta.encolar(siguienteRuta);

			codSala = vectorSalas[i].devolverSala(siguienteRuta);
			seMueve=true;

			if (siguienteRuta != null){

				seMueve= true;	


				// sacamos el personaje de la sala
				vectorSalas[i].sacarPersonaje();
				// Modificamos los datos del personaje
				setIdSala(codSala);

				// Metemos el personaje en su nuva sala
				vectorSalas[codSala].introducirPersonaje(this);
				System.out.println("El personaje " + getMarca() + " con turno "
						+ (getTurno() - 1) + " se va a mover hacia el "
						+ siguienteRuta + "  Sala: " + codSala);
				System.out.print("Las llaves del personaje " + getMarca() + " son: ");
				System.out.println();
			}

			else{

				// sacamos el personaje de la sala
				vectorSalas[i].sacarPersonaje();
				// Modificamos los datos del personaje
				setIdSala(codSala);
				vectorSalas[codSala].introducirPersonaje(this);
				System.out.println("El personaje " + getMarca() + " con turno "
						+ (getTurno() - 1) + " permanece en la"
						+ " Sala: " + codSala);
				System.out.print("Las llaves del personaje " + getMarca() + " son: ");
				System.out.println();
			}
		}

		this.turno++;	
		return seMueve;
	}




	/**
	 * @post Devuelve una cadena con el tipo de personaje
	 * @return <b>"lider"<b> si el personaje es un Lider
	 * @return <b>"trabajador"<b> si el personaje es un Trabajador
	 * @return <b>"intruso"<b> si el personaje es un Intruso
	 * @complejidad <b>O(1)<b>
	 */
	public String toString() {
		if (this instanceof Lideres)
			return "lider";
		if (this instanceof Trabajadores)
			return "trabajador";
		if (this instanceof Intrusos)
			return "intruso";
		return null;
	}




	/**
	 * @pre Personaje creado correctamente
	 * @post Muestra por pantalla las llaves del personaje
	 * @complejidad <b>O(n)<b>
	 */
	public final void mostrarLlaves() {

		Pila<Llave> aux =  new Pila<Llave>();// para no perder los datos
		Llave dato = null;

		while (!this.pilaLlaves.estaVacia()){
			dato = this.pilaLlaves.getDatoCima();
			System.out.print("  " + dato.getDato());
			Sistema.escribirSistema(" " + dato.getDato());
			aux.insertarDato(dato);
			this.pilaLlaves.sacarDato();
		}

		System.out.println();

		while (!aux.estaVacia()){
			dato = aux.getDatoCima();
			this.pilaLlaves.insertarDato(dato);
			aux.sacarDato();
		}
	}




	/**
	 * @pre Personaje creado correctamente
	 * @post  Escribe en el .log toda la informacion acerca del personaje
	 * @complejidad <b>O(1)<b>
	 */
	public void mostrarPersonaje() {
		Sistema.escribirSistema("("+this.toString()+":"+this.getMarca()+":"+this.getIdSala()+":"+this.getTurno()+":");
		mostrarLlaves();
		Sistema.escribirSistema(")\n");


	}
}