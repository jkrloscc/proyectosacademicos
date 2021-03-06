/**
 * Implementacion de los metodos de la clase Sala.
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
import personas.Intrusos;
import personas.Personaje;
import registro.Sistema;
import estructurasDeDatos.Cola;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class Sala {

	/** Identificador de la sala*/
	private int idSala;

	/** Marcador de la sala */
	private int marcador;

	/**Salas adyacentes (vecinas) a la Sala: Norte, Sur, Oeste y Este */
	private int[] salasVecinas= new int[4];

	/** Lista de las llaves que hay en la sala*/
	private LinkedList <Llave> listaLlaves= new LinkedList<Llave>();

	/** Cola de personajes que van llegando a la sala*/
	private Cola <Personaje> colaPersonajes= new Cola <Personaje>();

	/** Entero que almacena la frecuencia de paso de la sala en el calculo de los caminos*/
	private Integer frecuencia=0;


	public Sala() {

	}


	/**
	 * @post Constructor parametrizado de la Sala
	 * @param <b>idSala<b> Identificador de la sala
	 * @throws <b>exceptIdSala<b> Controla que no se pueda insertar un id de sala negativo
	 * @complejidad <b>O(1)<b> 
	 */
	public Sala(int idSala){
		this.idSala=idSala;
		this.marcador=idSala;  //Al principio cada marcador es el id de Sala

		if (idSala < 0) {
			try {
				throw (new exceptIdSala("idSala es negativo"));
			} catch (exceptIdSala e) {
				e.printStackTrace();
			}
		}
	}




	/**
	 * @pre Sala creada correctamente
	 * @post Asigna un nuevo valor al identificador de sala
	 * @param <b>idSala<b> Nuevo Identificador de la sala
	 * @complejidad <b>O(1)<b> 
	 */
	public void setIdSala(int idSala){
		this.idSala=idSala;
	}




	/**
	 * @pre Sala creada correctamente
	 * @post Asigna el nuevo valor al marcador de sala
	 * @param <b>marcador<b> Nuevo marcador de la sala
	 * @complejidad <b>O(1)<b> 
	 */
	public void setMarcador(int marcador){
		this.marcador=marcador;
	}




	/**
	 * @pre Sala creada correctamente
	 * @post Incrementa el valor de la Frecuencia de sala
	 * @complejidad <b>O(1)<b> 
	 */
	public void setFrecuencia() {
		this.frecuencia++;
	}



	/**
	 * @pre Sala creada correctamente
	 * @post Devuelve el identificador de sala
	 * @return <b>idSala<b> el identificador de sala
	 * @complejidad <b>O(1)<b> 
	 */
	public int getIdSala(){
		return this.idSala;
	}





	/**
	 * @pre Sala creada correctamente
	 * @post Devuelve el marcador de sala
	 * @return <b>marcador<b> el marcador de sala
	 * @complejidad <b>O(1)<b> 
	 */
	public int getMarcador(){
		return marcador;
	}




	/**
	 * @pre Sala creada correctamente
	 * @post Devuelve el vector de las salas vecinas
	 * @return <b>salasVecinas<b> vector que almacena las salas adyacentes
	 * @complejidad <b>O(1)<b> 
	 */
	public int[] getSalasVecinas (){
		return salasVecinas;
	}




	/**
	 * @pre Sala creada correctamente
	 * @post Devuelve el identificador de sala de la vecina Norte
	 * @return <b>salasVecinas[0]<b> vecina Norte
	 * @complejidad <b>O(1)<b> 
	 */
	public int getVecinoN(){
		return salasVecinas[0];
	}




	/**
	 * @pre Sala creada correctamente
	 * @post Devuelve el identificador de sala de la vecina Sur 
	 * @return <b>salasVecinas[1]<b> vecina Sur 
	 * @complejidad <b>O(1)<b> 
	 */
	public int getVecinoS(){
		return salasVecinas[1];
	}




	/**
	 * @pre Sala creada correctamente
	 * @post Devuelve el identificador de sala de la vecina Oeste 
	 * @return <b>salasVecinas[2]<b> vecina Oeste 
	 * @complejidad <b>O(1)<b> 
	 */
	public int getVecinoO(){
		return salasVecinas[2];
	}




	/**
	 * @pre Sala creada correctamente
	 * @post Devuelve el identificador de sala de la vecina Este 
	 * @return <b>salasVecinas[3]<b> vecina Este 
	 * @complejidad <b>O(1)<b> 
	 */
	public int getVecinoE(){
		return salasVecinas[3];
	}




	/**
	 * @pre Sala creada correctamente
	 * @post Devuelve la frecuencia de paso por la Sala al calcular los caminos 
	 * @return <b>frecuencia<b> frecuencia de paso
	 * @complejidad <b>O(1)<b> 
	 */
	public Integer getFrecuencia() {
		return this.frecuencia;
	}




	/**
	 * @pre Sala creada correctamente
	 * @post Devuelve la lista de llaves de la sala
	 * @return <b>listaLlaves<b> lista de llaves de la sala
	 * @complejidad <b>O(1)<b> 
	 */
	public LinkedList <Llave> getListaLlaves (){
		return this.listaLlaves;
	}




	/**
	 * @pre Sala creada correctamente
	 * @post Devuelve la cola de personajes de la sala
	 * @return <b>colaPersonajes<b> cola de personajes de la sala
	 * @complejidad <b>O(1)<b> 
	 */
	public Cola <Personaje> getColaPersonajes (){
		return this.colaPersonajes;
	}




	/**
	 * @pre Sala creada correctamente
	 * @post Indica si la Sala tiene alguna Sala Ayacente (Vecina)
	 * @return <b>True<b> si tiene alguna sala adyacente
	 *         <b>False<b> en caso contario 
	 * @complejidad <b>O(n)<b> 
	 */
	public boolean tieneVecinas(){

		int i=0;
		boolean enc = false;

		do{
			if (salasVecinas[i] !=idSala){
				enc = true;}
			i++;

		}while (i<4 && !enc);

		return enc;
	}




	/**
	 * @pre Sala creada correctamente
	 * @post Asigna una nueva vecina determinada, a una sala determinada
	 * @param <b>ancho<b> ancho de la planta en la que se encuentra la Sala
	 * @param <b>alto<b> alto de la planta en la que se encuentra la Sala
	 * @complejidad <b>O(1)<b> 
	 */
	public void crearVecina(int ancho, int idVecina){

		//Vecina NORTE
		if (idSala-ancho == idVecina)
			salasVecinas[0]= idVecina;

		//Vecina SUR
		if (idSala + ancho == idVecina)
			salasVecinas[1]= idVecina;

		//Vecina OESTE
		if(idSala-1 == idVecina )
			salasVecinas[2]= idVecina;   

		//Vecina ESTE
		if (idSala+1 == idVecina)
			salasVecinas[3]= idVecina;
	}




	/**
	 * @pre Sala creada correctamente
	 * @post Asigna las cuatro salas vecinas a una sala determinada
	 * @param <b>ancho<b> ancho de la planta en la que se encuentra la Sala
	 * @param <b>dimension<b> dimension de la planta en la que se encuentra la Sala
	 * @complejidad <b<O(1)<b> 
	 */
	public void asignarVecinas(int ancho, int dimension){
		//Estoy en las salas del NORTE
		if (idSala < ancho)
			salasVecinas[0]= idSala;
		else
			salasVecinas[0]= idSala - ancho;

		//Estoy en las salas del SUR
		if (idSala + ancho >= dimension)
			salasVecinas[1]= idSala;
		else
			salasVecinas[1]= idSala + ancho;

		//Estoy en la sala del ESTE
		if (idSala%ancho == 0)
			salasVecinas[2]= idSala;
		else
			salasVecinas[2]= idSala - 1;

		//Estoy en mis salas del OESTE
		if((idSala+1)%ancho == 0 )
			salasVecinas[3]= idSala;
		else
			salasVecinas[3]= idSala + 1;

	}




	/**
	 * @pre Sala creada correctamente
	 * @post Borra las vecinas de la Sala
	 * @complejidad <b>O(n)<b> 
	 */
	public void limpiarVecinas(){
		for (int i=0;i<4;i++)
			salasVecinas[i]=idSala;
	}





	/**
	 * @pre Sala creada correctamente
	 * @post Inserta una llave en la lista de llaves de la sala
	 * @param <b>idLlave<b> Identificador de la llave a guardar
	 * @throws exceptIdLlave 
	 * @complejidad <b>O(1)<b> 
	 */
	public void introducirLlave(int idLlave) throws exceptIdLlave{
		Llave miLlave= new Llave(idLlave);
		listaLlaves.add(miLlave);
	}




	/**
	 * @pre Sala creada correctamente
	 * @post Saca y devuelve la primera llave de la lista de llaves de la sala
	 * @return <b>Llave<b> Llave devuelta
	 * @complejidad <b>O(1)<b> 
	 */
	public Llave sacarLlave( ){

		//copia de la llave a sacar
		Llave miLlave= listaLlaves.getFirst();

		//La borramos de la lista de llaves de mi sala
		listaLlaves.remove(miLlave);

		//la devolvemos
		return miLlave;
	}





	/**
	 * @pre Sala creada correctamente
	 * @post Devuelve el primer personaje de la Sala
	 * @return <b>Personaje<b>  Personaje devuelto
	 * @complejidad <b>O(1)<b> 
	 */
	public Personaje devolverPersonaje() {
		return this.colaPersonajes.primero();
	}




	/**
	 * @pre Sala creada correctamente
	 * @post Inserta un nuevp personaje en la sala
	 * @param <b>miPersonaje<b>  Personaje introducido
	 * @complejidad <b>O(1)<b> 
	 */
	public void introducirPersonaje(Personaje miPersonaje){
		colaPersonajes.encolar(miPersonaje);
	}




	/**
	 * @pre Sala creada correctamente
	 * @post Saca el primer personaje de la Sala
	 * @return <b>miPer<b>  Personaje sacado
	 * @complejidad <b>O(1)<b> 
	 */
	public Personaje sacarPersonaje(){

		Personaje miPer = colaPersonajes.primero();
		colaPersonajes.desencolar();

		return miPer;
	}




	/**
	 * @pre Sala creada correctamente
	 * @post Comprueba si la sala tiene Llaves
	 * @return <b>True<b> si tiene alguna Llave
	 *         <b>False<b> en caso contario 
	 * @complejidad <b>O(1)<b> 
	 */  
	public boolean estaVaciaLlaves(){
		return listaLlaves.isEmpty();
	}





	/**
	 * @pre Sala creada correctamente
	 * @post Comprueba si la sala tiene Personajes
	 * @return <b>True<b> si tiene algun Personaje
	 *         <b>False<b> en caso contario 
	 * @complejidad <b>O(1)<b> 
	 */  
	public boolean estaVacia(){
		return colaPersonajes.estaVacia();
	}




	/**
	 * @pre Sala creada correctamente
	 * @post Devuelve el identificador de la Sala adyacente segun una determinada Direccion
	 * @param <b>siguienteRuta<b> Direccion que determina que adyacente devolver
	 * @return <b>id<b> Identificador de la Sala adyacente 
	 * @complejidad <b>O(1)<b> 
	 */  
	public int devolverSala(Dir siguienteRuta) {

		int id = this.idSala;

		if(siguienteRuta==Dir.N)
			id = getVecinoN();
		if(siguienteRuta==Dir.S)
			id = getVecinoS();
		if(siguienteRuta==Dir.E)
			id = getVecinoE();
		if(siguienteRuta==Dir.O)
			id = getVecinoO();

		return id;
	}





	/**
	 * @pre Sala creada correctamente
	 * @post Escribe en el .log las llaves de la sala
	 * @complejidad <b>O(n)<b> 
	 */  
	public void mostrarLlaves() {
		List<Llave> lista2 = new ArrayList<Llave>(listaLlaves);
		Iterator<Llave> it = lista2.iterator();
		Llave aux;
		while (it.hasNext()){
			aux=it.next();
			Sistema.escribirSistema(aux+"");
			System.out.print(aux+" ");
		}
		System.out.println();
	}




	/**
	 * @pre Sala creada correctamente
	 * @post Devuelve la cantidad de personajes que hay en la Sala
	 * @return <b>contador<b> numero de personajes encolados en la Sala 
	 * @complejidad <b>O(1)<b> 
	 */  
	public int devolverCantidadPersonajes() {

		return this.colaPersonajes.getContador();
	}





	/**
	 * @pre Sala creada correctamente
	 * @post Muetra por pantalla el personaje o cantidad de personajes de la Sala, si los tiene
	 * @complejidad <b>O(1)<b> 
	 */  
	public void pintarSala( ){ 

		String marca;

		//Se comprueba si en esa sala hay algun personaje; si es asi se pinta, sino se pone "  "
		if (!estaVacia()){

			marca = devolverPersonaje().getMarca();

			if (devolverCantidadPersonajes() > 1){
				System.out.print(devolverCantidadPersonajes()+" ");
			}
			else{
				System.out.print(marca+" ");
			}
		}	
		else{
			System.out.print("  ");
		}


	}




	/**
	 * @pre Sala creada correctamente
	 * @post Ejecuta las acciones de cada uno de los personajes de la Sala, si los tiene
	 * @complejidad <b>O(n)<b> 
	 */  
	public void accionPersonaje( ){
		Planta mip = Planta.obtenerInstancia(0);
		int turnoPlanta = mip.getTurno();
		int turnoPer;
		int size = colaPersonajes.getContador();
		int i=0;
		Personaje per;

		//comprobamos que la sala tiene algun personaje
		while (i<size){ 

			per=devolverPersonaje();
			turnoPer = per.getTurno();

			if (turnoPer == turnoPlanta ||( per.getIdSala() == per.getIdSalaInicio() && !(per instanceof Intrusos)))
				per.mostrarPersonaje();

			//comprobamos si se ha movido ya o no el personaje EN ESTE TURNO
			if (turnoPer == turnoPlanta)
				per.Accion();  
			else{
				colaPersonajes.desencolar();
				colaPersonajes.encolar(per);       
			}

			i++;
		}    
	}




	/**
	 * @pre Sala creada correctamente
	 * @post Escribe en el .log la Ruta de cada uno de los personajes de la Sala, si los tiene.
	 * @complejidad <b>O(n)<b> 
	 */  
	public void rutaPersonaje( ){

		int size = colaPersonajes.getContador();
		int i=0;
		Personaje per;

		//comprobamos que la sala tiene algun personaje
		while (i<size){ 

			per=devolverPersonaje();

			Sistema.escribirSistema("\n(ruta:"+per.getMarca()+":");  
			per.mostrarRuta();

			colaPersonajes.desencolar();
			colaPersonajes.encolar(per);       

			i++;
		}    
	}





	/**
	 * @pre Sala creada correctamente
	 * @post Escribe en el .log el contenido de la Sala de Personajes Escapados
	 * @complejidad <b>O(n)<b> 
	 */  
	public void personajesEscapados(){   
		Sala salaAux= new Sala();

		if (this.idSala==1111){
			System.out.println("Info personajes ESCAPADOS");
			while (!this.estaVacia()){
				Personaje P = this.sacarPersonaje();
				System.out.print("PERSONAJE: " +    P.getNombre()+ "   LLAVES: " );
				Sistema.escribirSistema("\n("+P.toString()+ ":"+ P.getMarca()+ ":"+ P.getIdSala()+ ":"+ P.getTurno()+ ":");
				P.mostrarLlaves();
				Sistema.escribirSistema(")");

				salaAux.introducirPersonaje(P);
			} // fin while

			while(!salaAux.estaVacia())
				this.introducirPersonaje(salaAux.sacarPersonaje());

		}//fin if
	}

}	
