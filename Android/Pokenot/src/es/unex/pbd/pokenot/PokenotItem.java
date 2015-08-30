package es.unex.pbd.pokenot;

import android.content.Intent;
import android.graphics.Bitmap;

public class PokenotItem {

	public final static String ID = "id";
	public final static String NOMBRE = "nombre";
	public final static String ALIAS = "alias";
	public final static String TIPO = "tipo";
	public final static String FOTO = "foto";
	public final static String PESO = "peso";
	public final static String ALTURA = "altura";
	public final static String SEXO = "sexo";

	public enum Tipo {
		ACERO, AGUA, BICHO, DRAGON, ELECTRICO, FANTASMA, FUEGO, HADA, HIELO, LUCHA, NORMAL, PLANTA, PSIQUICO, ROCA, SINIESTRO, TIERRA, VENENO, VOLADOR
	};

	public enum Sexo {
		MACHO, HEMBRA
	};

	private int id;
	private String nombre;
	private String alias;
	private Tipo tipo;
	private String foto;
	private String peso;
	private String altura;
	private Sexo sexo;

	public PokenotItem(int id, String nombre, String alias, Tipo tipo,
			String foto, String peso, String altura, Sexo sexo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.alias = alias;
		this.tipo = tipo;
		this.foto = foto;
		this.peso = peso;
		this.altura = altura;
		this.sexo = sexo;
	}

	// Crear un nuevo PokenotItem a partir de un Intent
	public PokenotItem(Intent intent) {

		this.id = intent.getIntExtra(PokenotItem.ID, 0);
		this.nombre = intent.getStringExtra(PokenotItem.NOMBRE);
		this.alias = intent.getStringExtra(PokenotItem.ALIAS);
		this.tipo = Tipo.valueOf(intent.getStringExtra(PokenotItem.TIPO));
		this.foto = intent.getStringExtra(PokenotItem.FOTO);
		this.peso = intent.getStringExtra(PokenotItem.PESO);
		this.altura = intent.getStringExtra(PokenotItem.ALTURA);
		this.sexo = Sexo.valueOf(intent.getStringExtra(PokenotItem.SEXO));
	}

	// Empaquetamos un objeto en un Intent
	public static void packageIntent(Intent intent, int id, String nombre,
			String alias, Tipo tipo, String foto, String peso, String altura,
			Sexo sexo) {

		intent.putExtra(PokenotItem.ID, id);
		intent.putExtra(PokenotItem.NOMBRE, nombre);
		intent.putExtra(PokenotItem.ALIAS, alias);
		intent.putExtra(PokenotItem.TIPO, tipo.toString());
		intent.putExtra(PokenotItem.FOTO, foto);
		intent.putExtra(PokenotItem.PESO, peso);
		intent.putExtra(PokenotItem.ALTURA, altura);
		intent.putExtra(PokenotItem.SEXO, sexo.toString());

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getPeso() {
		return peso;
	}

	public void setPeso(String peso) {
		this.peso = peso;
	}

	public String getAltura() {
		return altura;
	}

	public void setAltura(String altura) {
		this.altura = altura;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

}
