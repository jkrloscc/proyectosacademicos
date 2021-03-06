/**
* Implementacion de los metodos de la clase TestLlave
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
import station.Llave;
import station.exceptIdLlave;

public class TestLlave {

	Llave key1 = null;
	Llave key2 = null;
	Llave key3 = null;
	Llave key4 = null;

@Before
public void setUp() throws exceptIdLlave{
	key1 = new Llave(2);
	key2 = new Llave(2);
    key3 = new Llave(1);
	key4 = new Llave(6);
	
}

@Test
public void probrarKeys(){

	assertFalse(key1.equals(key3));
	assertTrue (key1.equals(key1));
	assertNotSame("Las llaves key1 y key2 no son iguales", key1,key2);
	assertNotNull (key4);
	key3= null;
	assertNull (key3);
	key1 = key3; // para que sean iguales deben apuntar al mismo sitio, no al mismo valor.
	assertSame (key1,key3);
	System.out.println("------------------------");
	System.out.println("Ejecutado probarKeys");
}


}