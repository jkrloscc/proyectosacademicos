/**
* Implementacion de los metodos de la clase TestPersonaje
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

import estructurasDeDatos.Pila;

import personas.Intrusos;
import personas.Lideres;
import personas.Personaje;
import personas.Trabajadores;
import station.Llave;
import station.Sala;
import station.exceptIdLlave;

public class TestPersonaje {

	Personaje person1 = null;
	Personaje person2 = null;
	Personaje person3 = null;
	Pila<Llave> pilaLlaves = null;
    Llave k1 = null;
    Llave k2 = null;
    Llave k3 = null;
    Llave k4 = null;
    Llave k10 = null;
	

	@Before
	public void setUp () throws exceptIdLlave{

		person1 =  new Lideres ("J","Jack",0,0);
		person2 = new Trabajadores ("M","Michael",0, 0);
	    person3 = new Intrusos ("B", "Ben",0,4);
	    pilaLlaves = new Pila<Llave>();
	    k1 = new Llave (1);
	    k2 = new Llave (2);
	    k3 = new Llave (3);
	    k4 = new Llave (4);
	    k10 = new Llave (10);
	}
	
	@Test
	public void probrarPersons(){
		
		
		pilaLlaves.insertarDato(k1);
		pilaLlaves.insertarDato(k2);
		pilaLlaves.insertarDato(k3);
		pilaLlaves.insertarDato(k4);
		pilaLlaves.insertarDato(k10);
				
		person1.setPilaLlaves(pilaLlaves);
		

	//Comprobamos que el Numero de llaves del personaje coincide con la cantidad de llaves introducidas
	assertTrue(5 == (person1.getPilaLlaves().getContador()));
	
	//Comprobamos que la primera llave del Personaje es la ULTIMA que ha cogido.
    assertTrue( k10.equals(person1.getPilaLlaves().getDatoCima()));
	
	System.out.println("------------------------");
	System.out.println("Ejecutado probarPersons");
    }
	
}
