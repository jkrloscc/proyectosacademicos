package com.example.chatrcmmappii;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;

import org.apache.http.conn.util.InetAddressUtils;

import com.example.rcmmapp.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChatRCMMAppIIActivity extends Activity {

	// IU
	private TextView textViewDirSrcRes;
	private EditText editTextDirIpDst;
	private EditText editTextPuerto;
	private Button buttonAceptar;
	private Button buttonLimpiar;
	private String dirIpSrc;

	// Control de errores datos entrada
	private boolean dirIpOk = false;
	private boolean puertoOk = false;
	private Integer puerto;
	private InetAddress dirDst;
	
	
	public boolean validateIPAddress(String ipAddress) {
		String[] tokens = ipAddress.split("\\.");
		if (tokens.length != 4) {
			return false;
		}
		for (String str : tokens) {
			int i = Integer.parseInt(str);
			if ((i < 0) || (i > 255)) {
				return false;
			}
		}
		return true;
	}
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat_rcmmappii);

		dirIpSrc = getIPAddress(true);

		textViewDirSrcRes = (TextView) findViewById(R.id.textViewDirSrcRes);
		editTextDirIpDst = (EditText) findViewById(R.id.editTextDirIpDst);
		editTextPuerto = (EditText) findViewById(R.id.editTextPuerto);
		buttonAceptar = (Button) findViewById(R.id.buttonAceptar);
		buttonLimpiar = (Button) findViewById(R.id.buttonLimpiar);

		textViewDirSrcRes.setText(dirIpSrc);

		// Log Comprobacion
		Log.i("VALOR IP ORIGEN: ", dirIpSrc);
		Log.i("VALOR IP DESTINO: ", editTextDirIpDst.getText().toString());
		Log.i("VALOR PUERTO: ", editTextPuerto.getText().toString());

		buttonAceptar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Control de Errores DIRECCIÓN IP
				if (editTextDirIpDst.getText().toString() != null
						&& !editTextDirIpDst.getText().toString().isEmpty()) {

					if (validateIPAddress(editTextDirIpDst.getText().toString()))
						dirIpOk = true;
					else {
						mostrarMensaje("El campo DIRECCIÓN IP debe contener un valor válido del tipo: [0-255].[0-255].[0-255].[0-255]");
						editTextDirIpDst.setText("");
					}

				} else
					mostrarMensaje("El campo DIRECCIÓN IP debe contener algún valor");

				// Control de Errores PUERTO
				if (editTextPuerto.getText().toString() != null
						&& !editTextPuerto.getText().toString().isEmpty()) {
					puerto = Integer.valueOf(editTextPuerto.getText()
							.toString());

					// Puertos registrados: Los comprendidos entre 1024 y 49151
					// pueden ser usados por cualquier aplicación.
					if (puerto instanceof Integer
							&& (puerto >= 1024 && puerto <= 49151))
						puertoOk = true;
					else {
						mostrarMensaje("Por favor, teclee un valor entre 1024 y 49151 para el campo PUERTO");
						editTextPuerto.setText("");
					}

				} else
					mostrarMensaje("El campo PUERTO debe contener algún valor");

				// Lanzamos otra Actividad
				if (dirIpOk && puertoOk) {

					
						try {
							dirDst= InetAddress.getByName(editTextDirIpDst.getText().toString());
						} catch (UnknownHostException e) {
							e.printStackTrace();
						}
				
					
					if (!dirDst.isMulticastAddress()){
						Intent i = new Intent(ChatRCMMAppIIActivity.this,
								ChatRCMMAppIIListActivity.class);

						i.putExtra("dir_ip_src", getIPAddress(true));
						i.putExtra("dir_ip_dst", editTextDirIpDst.getText()
								.toString());
						i.putExtra("puerto", editTextPuerto.getText().toString());

						startActivity(i);
					}else{
						
						if (!editTextDirIpDst.getText()
								.toString().equalsIgnoreCase("224.0.0.0")  && !editTextDirIpDst.getText()
								.toString().equalsIgnoreCase("224.0.0.1") && !editTextDirIpDst.getText()
								.toString().equalsIgnoreCase("224.0.0.2")){
							

							Intent i2 = new Intent(ChatRCMMAppIIActivity.this,
									ChatRCMMAppIIListActivityGrupo.class);

							i2.putExtra("dir_ip_src", getIPAddress(true));
							i2.putExtra("dir_ip_dst", editTextDirIpDst.getText()
									.toString());
							i2.putExtra("puerto", editTextPuerto.getText().toString());

							startActivity(i2);
						}else{
							mostrarMensaje("Elija una dir. MULTICAST válida");
							editTextDirIpDst.setText("");
						}
						
					}

				}

			}
		});

		buttonLimpiar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				editTextDirIpDst.setText("");
				editTextPuerto.setText("");
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat_rcmmapp, menu);
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
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * @post Muestra un mensaje al usuario
	 * @param <b>text<b> texto a mostrar
	 * @complejidad <b>O(1)<b>
	 */
	public void mostrarMensaje(CharSequence text) {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

	/**
	 * Get IP address from first non-localhost interface
	 * 
	 * @param ipv4
	 *            true=return ipv4, false=return ipv6
	 * @return address or empty string
	 */
	public static String getIPAddress(boolean useIPv4) {
		try {
			List<NetworkInterface> interfaces = Collections
					.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface intf : interfaces) {
				List<InetAddress> addrs = Collections.list(intf
						.getInetAddresses());
				for (InetAddress addr : addrs) {
					if (!addr.isLoopbackAddress()) {
						String sAddr = addr.getHostAddress().toUpperCase();
						boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
						if (useIPv4) {
							if (isIPv4)
								return sAddr;
						} else {
							if (!isIPv4) {
								int delim = sAddr.indexOf('%'); // drop ip6 port
																// suffix
								return delim < 0 ? sAddr : sAddr.substring(0,
										delim);
							}
						}
					}
				}
			}
		} catch (Exception ex) {
		} // for now eat exceptions
		return "";
	}

}
