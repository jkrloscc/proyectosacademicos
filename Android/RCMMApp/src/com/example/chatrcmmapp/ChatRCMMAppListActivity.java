package com.example.chatrcmmapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.example.rcmmapp.R;
import com.example.chatrcmmapp.MensajeItem.Tipo;

import android.app.ListActivity;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChatRCMMAppListActivity extends ListActivity {

	private static final String FILE_NAME = "ChatRCMMAppListActivity.txt";
	static final int longMax = 1500;

	// Info rescatada de la Actividad anterior
	private String dir_ip_src;
	private String dir_ip_dst;
	private int puerto;

	// UI
	private MensajeListAdapter mAdapter;
	private EditText editTextTextoAEnviar;
	private Button buttonEnviarMensaje;
	private TextView textViewConexion;

	// Transferencia de Datos
	private SocketListenerTask task;
	private DatagramSocket socketEnviar;
	private DatagramSocket socketRecibir;
	private DatagramPacket paqueteEnviado;
	private DatagramPacket paqueteRecibido;
	private InetAddress dirDst;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		task = new SocketListenerTask();

		// Create a new MensajeListAdapter for this ListActivity's ListView
		mAdapter = new MensajeListAdapter(getApplicationContext());

		// Put divider between MensajeItems and FooterView
		getListView().setFooterDividersEnabled(true);
		getListView().setHeaderDividersEnabled(true);

		// Inflate footerView for footer_view.xml file
		LinearLayout footerView = (LinearLayout) LayoutInflater.from(
				getApplicationContext()).inflate(R.layout.footer_view, null);

		LinearLayout headerView = (LinearLayout) LayoutInflater.from(
				getApplicationContext()).inflate(R.layout.header_view, null);
		// Para quitar la linea de separacion entre los items de la Lista
		getListView().setDivider(null);
		getListView().setDividerHeight(0);

		// Add footerView to ListView
		getListView().addFooterView(footerView);
		getListView().addHeaderView(headerView);

		// Rescatamos info
		Bundle bundle = getIntent().getExtras();
		dir_ip_src = bundle.getString("dir_ip_src");
		dir_ip_dst = bundle.getString("dir_ip_dst");
		puerto = Integer.valueOf(bundle.getString("puerto"));

		// Lanzamos el escuchador
		task.execute();

		editTextTextoAEnviar = (EditText) findViewById(R.id.editTextTextoAEnviar);
		buttonEnviarMensaje = (Button) findViewById(R.id.buttonEnviarMensaje);
		textViewConexion = (TextView) findViewById(R.id.TextViewConexion);

		textViewConexion.setText("Conexión: " + dir_ip_src + " <-> "
				+ dir_ip_dst);

		try {
			socketEnviar = new DatagramSocket();
		} catch (SocketException e1) {
			e1.printStackTrace();
		}

		try {
			dirDst = InetAddress.getByName(dir_ip_dst);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}

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
										.getText().toString().length(), dirDst,
								puerto);

						socketEnviar.send(paqueteEnviado);

						mAdapter.add(new MensajeItem(editTextTextoAEnviar
								.getText().toString(), Tipo.ENVIADO, dir_ip_src));

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

	// Escucha a la espera de mensajes (UDP):
	private class SocketListenerTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			try {
				socketRecibir = new DatagramSocket(puerto);
			} catch (SocketException e1) {
				e1.printStackTrace();
			}

			byte[] contenedor = new byte[longMax];


			while (true) {


				try {
							
					paqueteRecibido = new DatagramPacket(contenedor,
							contenedor.length);
					socketRecibir.receive(paqueteRecibido);
					

					if (contenedor.toString() != null
							&& !contenedor.toString().isEmpty())
						publishProgress();
					
					
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}

		@Override
		protected void onProgressUpdate(Void... arg0) {

			int i=0;
			
			byte[] contenedor = new byte[longMax];

			
			while (paqueteRecibido.getData()[i]!=0 && i<paqueteRecibido.getData().length){
				
				contenedor[i] =paqueteRecibido.getData()[i];
				i++;
			}
			mAdapter.add(new MensajeItem(new String(contenedor,
					0, contenedor.length), Tipo.RECIBIDO, dir_ip_src));
		
		}

	}

	@Override
	public void onResume() {
		super.onResume();
		// if (mAdapter.getCount() == 0)
		// FIXME loadItems();
	}

	@Override
	protected void onPause() {
		super.onPause();

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

				mAdapter.add(new MensajeItem(texto, Tipo.valueOf(tipo), emisor));
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
		getMenuInflater().inflate(R.menu.chat_rcmmapp_list, menu);
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
		}else if (id == R.id.action_delete) {
			mAdapter.clear();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


}
