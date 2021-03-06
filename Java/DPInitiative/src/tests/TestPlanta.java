/**
* Implementacion de los metodos de la clase TestPlanta
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

import registro.Sistema;
import station.Llave;
import station.Planta;
import station.Puerta;
import station.exceptIdLlave;

public class TestPlanta {

	Planta floor1= null;


@Before
public void setUp () throws exceptIdLlave{

	                            // idPlanta, ancho, alto, salaEntrada, salaSalida, alturaCerradura
	floor1= Planta.obtenerInstancia( 0,        6,     6,        0,        30,            4);
	
}


@Test
public void probrarFloors() throws exceptIdLlave{
	
	Sistema.aperturaSistema();
	floor1.iniciarPlanta();
	
	//Compruebo la dimension de la planta
	assertTrue (floor1.devolverDimension() == 36);
	
	//compruebo el numero de salas
	assertTrue (floor1.getVectorSalas().length == 36);
	

	System.out.println("------------------------");
	System.out.println("Ejecutado probarFloors");
}
}