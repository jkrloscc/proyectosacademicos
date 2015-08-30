/**
* Implementacion de los metodos de la clase TestSala
*
* @version 1.0
* @author
* Asignatura Desarrollo de Programas<br/>
* Grupo: Feli&Carlos<br/>
* Entrega: Junio <br/>
* <b> Felisa Maria Arroba Alonso </b><br>
* <b> Juan Carlos Bonilla Bermejo </b><br>
* Curso 12/13
*/

package tests;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import personas.*;
import registro.Sistema;
import station.Planta;
import station.Sala;
import station.Llave;
import station.exceptIdLlave;


public class TestSala {

	Sala room1  = null;
	Sala room2  = null;
	Planta floor= null;
	

@Before
public void setUp () throws exceptIdLlave{

	room1 = new Sala(1);
	room2 = new Sala(2);
	floor= Planta.obtenerInstancia( 1,6, 6, 1,35,2);
	
	
}

@Test
public void probrarRooms() throws exceptIdLlave{
	
	Sistema.aperturaSistema();
	floor.iniciarPlanta();
	floor.getVectorSalas()[1]= room1; 
	floor.getVectorSalas()[2]= room2; 

	Personaje lider =  new Lideres ("J","Jack",0,0);
	Personaje trabajador = new Trabajadores ("M","Michael",0, 0);
    Personaje intruso = new Intrusos ("B", "Ben",0,4);
    
   Llave aux = new Llave();
    
    //Metemos los 3 personajes en la misma Sala
    room2.introducirPersonaje(intruso);
	room2.introducirPersonaje(lider);
	room2.introducirPersonaje(trabajador);
	
	
	lider.setIdSala(room2.getIdSala());
	trabajador.setIdSala(room2.getIdSala());
	intruso.setIdSala(room2.getIdSala());


	//Comprobamos la cantidad de Personajes introducidos
	assertTrue (room2.devolverCantidadPersonajes() == 3);
	
	room2.sacarPersonaje();
	assertFalse(room2.devolverCantidadPersonajes() == 3);
	
	assertNotSame("Las salas room1 y room2 no son iguales", room1,room2);


	//Comprobamos que la Sala 1 no tiene personajes
	assertNull (room1.devolverPersonaje());
	
	System.out.println("");
	System.out.println("Numero de llaves de la sala "+ room2.getIdSala()+ ": " +room2.getListaLlaves().size());


	// comprobamos que la sala 2 tiene llaves
	assertTrue(room2.estaVaciaLlaves());
	
	//Metemos dos llaves en la Sala 2
	room2.introducirLlave(4);
	room2.introducirLlave(1);
	
	System.out.println("Numero de llaves de la sala "+ room2.getIdSala()+ ": " +room2.getListaLlaves().size());

	aux = room2.getListaLlaves().getFirst(); // hacemos una copia de la primera llave
											 // para probar que el Lider coge la primera
	
	assertTrue(room2.getListaLlaves().size() == 2);// Comprobamos num de llaves
	lider.accionLlaves(); // El lider debe coger una llave de la Sala
	System.out.println("Numero de llaves de la sala "+ room2.getIdSala()+ ": " +room2.getListaLlaves().size());

	assertSame (aux, lider.getPilaLlaves().getDatoCima());
	assertFalse(room2.getListaLlaves().size() == 2); //Num de llaves en sala cambia
	assertFalse(room2.estaVaciaLlaves()); 
	
	intruso.accionLlaves();// El intruso debe dejar una llave en la sala
						   //(porque el identificador de la sala es PAR)
		
	System.out.println("Numero de llaves de la sala "+ room2.getIdSala()+ ": " +room2.getListaLlaves().size());
	
	assertTrue(room2.getListaLlaves().size() == 2);// Comprobamos num de llaves
	
	trabajador.accionLlaves();// El trabajador debe cojer una llave de la sala 
	                        
	
	room1.introducirLlave(5);
	room1.introducirLlave(6);
	
	room1.introducirPersonaje(room2.sacarPersonaje()); // Pasamos al intruso de la Sala2 a la Sala1

	System.out.println("Numero de llaves de la sala "+ room1.getIdSala()+ ": " +room1.getListaLlaves().size());

	intruso.accionLlaves();// El intruso  NO debe dejar una llave en la sala
						   //(porque el identificador de la sala es IMPAR)

	assertTrue(room1.getListaLlaves().size() == 2); //Num de llaves en sala cambia

	System.out.println("Numero de llaves de la sala "+ room1.getIdSala()+ ": " +room1.getListaLlaves().size());

	System.out.println("------------------------");
	System.out.println("Ejecutado probarRooms");
}


}

