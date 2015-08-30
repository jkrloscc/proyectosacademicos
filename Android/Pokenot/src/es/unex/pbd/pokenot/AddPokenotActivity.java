package es.unex.pbd.pokenot;

import java.util.Random;

import es.unex.pbd.pokenot.PokenotItem.Sexo;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.os.Environment;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Environment;

import android.provider.MediaStore;

import es.unex.pbd.pokenot.adapter.ItemPokenotAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddPokenotActivity extends Activity {
	TextView pregunta;
	EditText alias;
	ItemPokenotAdapter itemAdapter;

	private PokenotItem pokenot = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_layout);
		pregunta = (TextView) findViewById(R.id.formulario_alias);
		alias = (EditText) findViewById(R.id.formulario_alias_respuesta);

		Random rand = new Random();
		// Funcionamiento del random -> rand.nextInt((max - min) + 1) + min;
		int randomNum = rand.nextInt(7);
		this.pokenot = MainActivity.listaPokenotBD.get(randomNum);
		if (pokenot == null) {
			Log.e("Error", "El pokenot es nulo");
		}

		// Hacer random Sexo
		int randomSexo = rand.nextInt(2);
		if (randomSexo == 0)
			pokenot.setSexo(Sexo.MACHO);
		else
			pokenot.setSexo(Sexo.HEMBRA);

		// AÑADIR FOTOGRAFIA

		Button hacerFoto = (Button) findViewById(R.id.boton_foto);
		Button cancelar = (Button) findViewById(R.id.boton_cancelar);
		Button guardar = (Button) findViewById(R.id.boton_guardar);

		hacerFoto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// camera stuff

				Intent imageIntent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

				String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
						.format(new Date());

				// folder stuff

				File imagesFolder = new File(Environment
						.getExternalStorageDirectory(), "Pokenot");

				imagesFolder.mkdirs();

				String filePath = "/storage/emulated/0/Pokenot/Pokenot_" + timeStamp + ".png";

				
				
				File image = new File(imagesFolder, "Pokenot_" + timeStamp
						+ ".png");
				
				Uri uriSavedImage = Uri.fromFile(image);

				imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);

				startActivityForResult(imageIntent, 100);
				
				pokenot.setFoto(filePath);

			}

		});

		guardar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// Package PokenotItem data into an Intent
				Intent data = new Intent();

				PokenotItem.packageIntent(data, pokenot.getId(),
						pokenot.getNombre(), alias.getText().toString(),
						pokenot.getTipo(), pokenot.getFoto(),
						pokenot.getPeso(), pokenot.getAltura(),
						pokenot.getSexo());

				setResult(RESULT_OK, data);
				finish();

			}
		});

		cancelar.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});
	}

}
