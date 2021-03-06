/**
* Implementacion de los metodos de la clase TestPuerta
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

package tests;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import estructurasDeDatos.Arbol;
import estructurasDeDatos.Pila;

import personas.Intrusos;
import personas.Lideres;
import personas.Personaje;
import personas.Trabajadores;
import registro.Sistema;
import station.Llave;
import station.Planta;
import station.Puerta;
import station.Sala;
import station.exceptIdLlave;

public class TestPuerta {

	Puerta door = null;
	Planta floor= null;
	Personaje persona = null;
    Arbol <Llave> combinacion = null;
    Pila<Llave> pilaLlaves = null;
    Llave k1 = null;
    Llave k2 = null;
    Llave k3 = null;
    Llave k4 = null;
    Llave k10 = null;

	@Before
	public void setUp () throws exceptIdLlave{

		door = new Puerta();
		floor= Planta.obtenerInstancia( 1,6, 6, 1,35,2);
		persona = new Personaje();
	    combinacion = new Arbol <Llave>();
	    pilaLlaves = new Pila<Llave>();
	    k1 = new Llave (1);
	    k2 = new Llave (2);
	    k3 = new Llave (3);
	    k4 = new Llave (4);
	    k10 = new Llave (10);
		
	
	}
	
	@Test
	public void probrarDoor() throws exceptIdLlave{
		Sistema.aperturaSistema();
		floor.iniciarPlanta();
		
		floor.setPuerta(door);
		
		//Creamos la combinacion y dotamos al personaje de llaves
		combinacion.insertar(k1);
		pilaLlaves.insertarDato(k1);
		
		combinacion.insertar(k2);
		pilaLlaves.insertarDato(k2);
		
		combinacion.insertar(k3);
		pilaLlaves.insertarDato(k3);
		
		combinacion.insertar(k4);
		pilaLlaves.insertarDato(k4);
		
		pilaLlaves.insertarDato(k10);
				
		persona.setPilaLlaves(pilaLlaves);
		persona.setIdSala(floor.getSalaSalida());
		
	   door.setCerrada(true); // cierra la puerta
       door.setCombinacionApertura(combinacion); //configura la puerta
       door.setConfiguarada(true); //me dice que esta configurada la puerta
       
       floor.setPuerta(door);
	
    //Comprobamos que la combinacion se ha construido tal y como pretendemos
	assertFalse(door.formaParteCombinacion(k10));
	assertTrue (door.formaParteCombinacion(k3));
	
	
	while (!persona.getPilaLlaves().estaVacia()){
		//Hacemos que el personaje pruebe todas las llaves que tiene en la cerradura
		persona.accionPuerta();
		
	}
	
	//Comprobamos tras probar llaves que se ha ABIERTO la puerta
	assertFalse (door.isCerrada());
	
	//Comprobamos que la combinacion esta vacia
	assertNull (door.getCombinacionApertura());
		

	System.out.println("------------------------");
	System.out.println("Ejecutado probarDoor");
	

    }
	
}
