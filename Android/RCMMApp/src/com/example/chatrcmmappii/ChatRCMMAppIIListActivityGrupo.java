package com.example.chatrcmmappii;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chatrcmmappii.MensajeItem.Tipo;
import com.example.rcmmapp.R;

public class ChatRCMMAppIIListActivityGrupo extends ListActivity {

	private static final String FILE_NAME = "ChatRCMMAppIIListActivityGrupo.txt";
	static final int longMax = 1500;

	// Info rescatada de la Actividad anterior
	private String dir_ip_src;
	private String dir_ip_dst;
	private int puerto;

	// UI
	private MensajeListAdapterGrupo mAdapter;
	private EditText editTextTextoAEnviar;
	private Button buttonEnviarMensaje;
	private TextView textViewConexion;

	// Transferencia de Datos
	private Thread hilo = new Thread(new SocketListener());
	private Handler mHandler = new Handler();
	private boolean zumbido = false;

	private MulticastSocket socketEnviar;
	private MulticastSocket socketRecibir;
	private DatagramPacket paqueteEnviado;
	private DatagramPacket paqueteRecibido;
	private InetAddress dirGrupo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listactivity_chat_rcmmappii);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		// Create a new MensajeListAdapter for this ListActivity's ListView
		mAdapter = new MensajeListAdapterGrupo(getApplicationContext());

		// Para quitar la linea de separacion entre los items de la Lista
		getListView().setDivider(null);
		getListView().setDividerHeight(0);

		// Rescatamos info
		Bundle bundle = getIntent().getExtras();
		dir_ip_src = bundle.getString("dir_ip_src");
		dir_ip_dst = bundle.getString("dir_ip_dst");
		puerto = Integer.valueOf(bundle.getString("puerto"));

		editTextTextoAEnviar = (EditText) findViewById(R.id.editTextTextoAEnviar);
		buttonEnviarMensaje = (Button) findViewById(R.id.buttonEnviarMensaje);
		textViewConexion = (TextView) findViewById(R.id.TextViewConexion);

		textViewConexion.setText("Conectado al grupo: " + dir_ip_dst);

		try {
			socketEnviar = new MulticastSocket(puerto);
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		try {
			dirGrupo = InetAddress.getByName(dir_ip_dst);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}

		try {
			socketEnviar.joinGroup(dirGrupo);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// Lanzamos el escuchador
		hilo.setDaemon(true);
		hilo.start();

		buttonEnviarMensaje.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// Envío de mensajes (UDP)
				try {
					if (editTextTextoAEnviar.getText().toString() != null
							&& !editTextTextoAEnviar.getText().toString()
									.isEmpty()) {

						paqueteEnviado = new DatagramPacket(
								editTextTextoAEnviar.getText().toString()
										.getBytes(), editTextTextoAEnviar
										.getText().toString().length(),
								dirGrupo, puerto);

						socketEnviar.send(paqueteEnviado);

						mAdapter.add(new MensajeItem(editTextTextoAEnviar
								.getText().toString(), Tipo.ENVIADO,
								dir_ip_src, null));

						editTextTextoAEnviar.setText("");
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// - Attach the adapter to this ListActivity's ListView
		getListView().setAdapter(mAdapter);
	}

	class SocketListener implements Runnable {

		private Object pauseLock;
		private boolean paused;
		private boolean finished;
		private String dirSrc;

		public SocketListener() {
			pauseLock = new Object();
			paused = false;
			finished = false;
		}

		public void run() {

			try {
				socketRecibir = new MulticastSocket(puerto);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			try {
				socketRecibir.joinGroup(dirGrupo);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			while (!finished) {

				final byte[] contenedor = new byte[longMax];

				try {

					paqueteRecibido = new DatagramPacket(contenedor,
							contenedor.length);
					socketRecibir.receive(paqueteRecibido);

					dirSrc = paqueteRecibido
							.getAddress()
							.toString()
							.substring(
									1,
									paqueteRecibido.getAddress().toString()
											.length());
					final Bitmap bitMap;

					switch (analizarArray(contenedor)) {
					//
					// case 1: // JPEG
					// bitMap = BitmapFactory.decodeByteArray(contenedor, 0,
					// contenedor.length);
					// break;
					case 2:// PNG
						bitMap = BitmapFactory.decodeByteArray(contenedor, 0,
								contenedor.length);
						break;
					case 3: // GIF
						bitMap = BitmapFactory.decodeByteArray(contenedor, 0,
								contenedor.length);
						break;

					case 4: // Zumbido
						zumbido = true;
						bitMap = null;
						break;
					default:
						bitMap = null;
						break;

					}

					if (contenedor.toString() != null
							&& !contenedor.toString().isEmpty() && !dirSrc.equals(dir_ip_src)) {

						Log.i("dirSrc", dirSrc);
						Log.i("dir_ip_src", dir_ip_src);
						mHandler.post(new Runnable() {
							@Override
							public void run() {

								if (!zumbido)
									mAdapter.add(new MensajeItem(new String(
											contenedor, 0, paqueteRecibido
													.getLength()),
											Tipo.RECIBIDO, dirSrc, bitMap));

								else {
									mAdapter.add(new MensajeItem(
											"Ha recibido un zumbido!!",
											Tipo.RECIBIDO, dirSrc, bitMap));
									Vibrator v = (Vibrator) getApplicationContext()
											.getSystemService(
													Context.VIBRATOR_SERVICE);
									// Vibrate for 1000 milliseconds
									v.vibrate(1000);
									zumbido = false;

								}
							}
						});

					}

				} catch (IOException e) {
					e.printStackTrace();
				}

				synchronized (pauseLock) {
					while (paused) {
						try {
							pauseLock.wait();
						} catch (InterruptedException e) {
							break;
						}
					}
				}

			}
		}

		/*
		 * Imágenes JPEG: siempre van a comenzar por FF D8 FF E0 (cuatro bytes
		 * expresados en hexadecimal; 255 216 255 224 en "decimal"). En ASCII no
		 * son imprimibles. //FIXME NO FUNCIONA, NO CONTEMPLADO
		 * 
		 * Imágenes PNG: siempre van a comenzar por 137 80 78 71 13 10 26 10
		 * (ocho bytes expresados en decimal; el primero es no imprimible, los
		 * tres siguientes son "PNG", los siguientes son no imprimibles).
		 * 
		 * Imágenes GIF: los seis primeros bytes, expresados en ASCII, son
		 * "GIF87a" o "GIF89a", dependiendo de la versión.
		 * 
		 * Zumbidos "ALT+Z": 5 bytes expresados en decimal; 65 76 84 43 90.
		 */

		public int analizarArray(byte[] array) {

			int res = 0;

			// if ((array[0] == 255) && (array[1] == 216) && (array[2] == 255)
			// && (array[3] == 224))
			// res = 1; // JPEG

			if ((array[1] == 80) && (array[2] == 78) && (array[3] == 71)
					&& (array[4] == 13) && (array[5] == 10) && (array[6] == 26)
					&& (array[7] == 10))
				res = 2; // PNG

			if ((array[0] == 71) && (array[1] == 73) && (array[2] == 70)
					&& (array[3] == 56)
					&& ((array[4] == 55) || (array[4] == 57))
					&& (array[5] == 97))
				res = 3; // GIF

			if ((array[0] == 65) && (array[1] == 76) && (array[2] == 84)
					&& (array[3] == 43) && (array[4] == 90))// ALT+Z
				res = 4; // ZUMBIDO

			// Pruebas contenido array
			Log.i("ARRAY[0]= ", String.valueOf(array[0]));
			Log.i("ARRAY[1]= ", String.valueOf(array[1]));
			Log.i("ARRAY[2]= ", String.valueOf(array[2]));
			Log.i("ARRAY[3]= ", String.valueOf(array[3]));
			Log.i("ARRAY[4]= ", String.valueOf(array[4]));
			Log.i("ARRAY[5]= ", String.valueOf(array[5]));
			Log.i("ARRAY[6]= ", String.valueOf(array[6]));
			Log.i("ARRAY[7]= ", String.valueOf(array[7]));

			return res;
		}

		// Llamarlo desde Activity.onPause()
		public void pause() {
			synchronized (pauseLock) {
				paused = true;
			}
		}

		// Llamarlo desde Activity.onResume()
		public void resume() {
			synchronized (pauseLock) {
				paused = false;
				pauseLock.notifyAll();
			}
		}

		// Llamarlo desde Activity.onDestroy()
		public void finish() {
			finished = true;
		}
	}

	@Override
	public void onBackPressed() {
		// FIXME hilo.interrupt();
		super.onBackPressed();
	}

	@Override
	public void onResume() {
		super.onResume();
		// FIXME socketRecibir.disconnect();
		// socketEnviar.disconnect();
		// socketRecibir.close();
		// socketEnviar.close();
		// try {
		// hilo.join();
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// notifyAll();
		// if (mAdapter.getCount() == 0)
		// FIXME loadItems();
	}

	@Override
	protected void onPause() {
		super.onPause();

		// try {
		// hilo.wait();
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		saveItems();
	}

	// Load stored ToDoItems
	private void loadItems() {
		BufferedReader reader = null;
		try {
			FileInputStream fis = openFileInput(FILE_NAME);
			reader = new BufferedReader(new InputStreamReader(fis));

			String texto = null;
			String tipo = null;
			String emisor = null;

			while (null != (texto = reader.readLine())) {
				tipo = reader.readLine();
				emisor = reader.readLine();

				mAdapter.add(new MensajeItem(texto, Tipo.valueOf(tipo), emisor,
						null));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// Save MensajeItems to file
	private void saveItems() {
		PrintWriter writer = null;
		try {
			FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					fos)));

			for (int idx = 0; idx < mAdapter.getCount(); idx++) {

				writer.println(mAdapter.getItem(idx));

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != writer) {
				writer.close();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat_rcmmappii_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		} else if (id == R.id.action_delete) {
			mAdapter.clear();
			return true;
		} else if (id == R.id.action_picture) {
			enviarFoto();
			return true;
		} else if (id == R.id.action_mic) {
			grabarVoz();
			return true;
		} else if (id == R.id.action_zumbido) {
			enviarZumbido();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void enviarFoto() {

		// Nuevo Intent para acceder a la galería:
		Intent i = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

		// Iniciamos una nueva actividad a la espera de resultado:
		startActivityForResult(i, 1);

	}

	private void grabarVoz() {

		// Nuevo Intent para lanzar actividad Grabar Voz
		Intent i = new Intent(ChatRCMMAppIIListActivityGrupo.this,
				AudioRecord.class);

		// Iniciamos una nueva actividad a la espera de resultado:
		startActivityForResult(i, 2);

	}

	private void enviarZumbido() {
		// Envío de mensajes (UDP)
		try {

			paqueteEnviado = new DatagramPacket("ALT+Z".getBytes(),
					"ALT+Z".length(), dirGrupo, puerto);

			socketEnviar.send(paqueteEnviado);

			mAdapter.add(new MensajeItem("Ha enviado un Zumbido!!",
					Tipo.ENVIADO, dir_ip_src, null));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		File foto = null;
		File audio = null;
		FileInputStream fileInputStream = null;
		
		final Bitmap bitMap;

		final byte[] contenedor = new byte[longMax];

		// Recogemos respuesta de FOTO
		if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			foto = new File(picturePath);

			// Creación y envío del paquete que contiene la imagen
			byte[] bFile = new byte[(int) foto.length()];

			try {
				// convert file into array of bytes
				fileInputStream = new FileInputStream(foto);
				fileInputStream.read(bFile);
				fileInputStream.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

			paqueteEnviado = new DatagramPacket(bFile, bFile.length, dirGrupo,
					puerto);

			try {
				socketEnviar.send(paqueteEnviado);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			bitMap = BitmapFactory.decodeByteArray(bFile, 0,
					bFile.length);
			
			mAdapter.add(new MensajeItem(new String(
					contenedor, 0, paqueteEnviado
							.getLength()),
					Tipo.ENVIADO, dir_ip_src, bitMap));

		}

		// Recogemos respuesta de MICRO
		if (requestCode == 2 && resultCode == RESULT_OK && null != data) {

			Uri selectedAudio = data.getData();
			String[] filePathColumn = { MediaStore.Audio.Media.DATA };
			Cursor cursor = getContentResolver().query(selectedAudio,
					filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String audioPath = cursor.getString(columnIndex);
			cursor.close();

			audio = new File(audioPath);

			// Creación y envío del paquete que contiene el audio
			byte[] bFile = new byte[(int) audio.length()];

			try {
				// convert file into array of bytes
				fileInputStream = new FileInputStream(foto);
				fileInputStream.read(bFile);
				fileInputStream.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

			paqueteEnviado = new DatagramPacket(bFile, bFile.length, dirGrupo,
					puerto);

			try {
				socketEnviar.send(paqueteEnviado);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
