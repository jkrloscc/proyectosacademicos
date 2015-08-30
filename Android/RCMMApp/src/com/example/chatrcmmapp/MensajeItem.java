package com.example.chatrcmmapp;

import java.util.Date;
import android.content.Intent;

public class MensajeItem {

	public static enum Tipo {
		ENVIADO, RECIBIDO
	};

	public final static String TEXTO = "texto";
	public final static String TIPO = "tipo";
	public final static String EMISOR = "emisor";

	private String texto = new String();
	private Tipo tipo = Tipo.ENVIADO;
	private String emisor = new String();

	// Posible ampliacion: private Date fecha = new Date();

	public MensajeItem(String texto, Tipo tipo, String emisor) {
		super();
		this.texto = texto;
		this.tipo = tipo;
		this.emisor = emisor;
	}

	// Create a new MensajeItem from data packaged in an Intent
	MensajeItem(Intent intent) {

		texto = intent.getStringExtra(MensajeItem.TEXTO);
		tipo = Tipo.valueOf(intent.getStringExtra(MensajeItem.TIPO));
		emisor = intent.getStringExtra(MensajeItem.EMISOR);
	}		


	// Take a set of String data values and
	// package them for transport in an Intent
	public static void packageIntent(Intent intent, String texto, Tipo tipo,
			String emisor) {

		intent.putExtra(MensajeItem.TEXTO, texto);
		intent.putExtra(MensajeItem.TIPO, tipo.toString());
		intent.putExtra(MensajeItem.EMISOR, emisor.toString());

	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public String getEmisor() {
		return emisor;
	}

	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}

}
