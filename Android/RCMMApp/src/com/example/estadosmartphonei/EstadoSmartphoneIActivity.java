package com.example.estadosmartphonei;

import com.example.rcmmapp.R;
import com.example.rcmmapp.R.id;
import com.example.rcmmapp.R.layout;
import com.example.rcmmapp.R.menu;

import android.telephony.TelephonyManager;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class EstadoSmartphoneIActivity extends Activity {

	private TextView textViewIdDeviceRes;
	private TextView textViewLineNumberRes;
	private TextView textViewSoftwareVersionRes;
	private TextView textViewOperatorNameRes;
	private TextView textViewSimCountryIsoRes;
	private TextView textViewSimOperatorRes;
	private TextView textViewSimSerialNumberRes;
	private TextView textViewSubscriberIdRes;
	private TextView textViewNetworkTypeRes;
	private TextView textViewPhoneTypeRes;

	private Button buttonObtener;

	/**
	 * @post Inicializa los componentes.(Primer m�todo en el ciclo de vida de
	 *       una Activity)
	 * @param <b>savedInstanceState<b> Bundle que permite guardar datos y
	 *        recuperlarlos posteriormente
	 * @complejidad <b>O(1)<b>
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.estado_smartphone_i);

		// Vinculamos variables a los recursos de nuestro proyecto
		textViewIdDeviceRes = (TextView) findViewById(R.id.textViewIdDeviceRes);
		textViewLineNumberRes = (TextView) findViewById(R.id.textViewLineNumberRes);
		textViewSoftwareVersionRes = (TextView) findViewById(R.id.textViewSoftwareVersionRes);
		textViewOperatorNameRes = (TextView) findViewById(R.id.textViewOperatorNameRes);
		textViewSimCountryIsoRes = (TextView) findViewById(R.id.textViewSimCountryIsoRes);
		textViewSimOperatorRes = (TextView) findViewById(R.id.textViewSimOperatorRes);
		textViewSimSerialNumberRes = (TextView) findViewById(R.id.textViewSimSerialNumberRes);
		textViewSubscriberIdRes = (TextView) findViewById(R.id.textViewSubscriberIdRes);
		textViewNetworkTypeRes = (TextView) findViewById(R.id.textViewNetworkTypeRes);
		textViewPhoneTypeRes = (TextView) findViewById(R.id.textViewPhoneTypeRes);

		buttonObtener = (Button) findViewById(R.id.buttonObtener);

		buttonObtener.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mostrarInformacionTelefono();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	 * @post Devuelve el tipo de Red M�vil del tel�fono
	 * @param <b>i<b> Entero con el c�digo de red M�vil
	 * @return <b>res<b> Cadena de caracteres con el tipo de red m�vil
	 * @complejidad <b>O(1)<b>
	 */
	public String calcularTipoRed(int i) {

		String res = "";

		switch (i) {
		case 7:
			res = "1xRTT";
			break;
		case 4:
			res = "CDMA";
			break;
		case 2:
			res = "EDGE";
			break;
		case 14:
			res = "EHRPD";
			break;
		case 5:
			res = "EVDO 0";
			break;
		case 6:
			res = "EVDO A";
			break;
		case 8:
			res = "HSDPA";
			break;
		case 10:
			res = "HSPA";
			break;
		case 15:
			res = "HSPAP";
			break;
		case 9:
			res = "HSUPA";
			break;
		case 11:
			res = "IDEN";
			break;
		case 13:
			res = "LTE";
			break;
		case 3:
			res = "UMTS";
			break;
		case 1:
			res = "GPRS";
			break;
		case 12:
			res = "EVDO B";
			break;
		default:
			res = "UNKNOWN";
			break;
		}
		return res;
	}

	/**
	 * @post Devuelve el tipo de Radio Voz del tel�fono
	 * @param <b>i<b> Entero con el c�digo de radio voz
	 * @return <b>res<b> Cadena de caracteres con el tipo de radio voz
	 * @complejidad <b>O(1)<b>
	 */
	public String calcularTipoTelefono(int i) {

		String res = "";
		switch (i) {
		case 0:
			res = "NONE";
			break;
		case 1:
			res = "GSM";
			break;
		case 2:
			res = "CDMA";
			break;
		case 3:
			res = "SIP";
			break;
		default:
			res = "UNKNOWN";
			break;
		}
		return res;
	}

	
	/**
	 * @post Muestra info consultada al dispositivo
	 * @complejidad <b>O(1)<b>
	 */
	public void mostrarInformacionTelefono() {

		//Clase que nos permite acceder a los servicios del dispositivo consultado
		TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

		// Variable aux para guardar el numero de telefono del dispositivo  consultado
		//solo si est� configurado as� en la SIM, que no es obligatorio.
		String no = tm.getLine1Number();
		
		textViewIdDeviceRes.setText(tm.getDeviceId());

		if (no != null)
			textViewLineNumberRes.setText(no);
		else
			textViewLineNumberRes.setText("UNKNOWN");

		textViewSoftwareVersionRes.setText(tm.getDeviceSoftwareVersion());
		textViewOperatorNameRes.setText(tm.getNetworkOperatorName());
		textViewSimCountryIsoRes.setText(tm.getSimCountryIso());
		textViewSimOperatorRes.setText(tm.getSimOperator());
		textViewSimSerialNumberRes.setText(tm.getSimSerialNumber());
		textViewSubscriberIdRes.setText(tm.getSubscriberId());

		textViewNetworkTypeRes.setText(calcularTipoRed(tm.getNetworkType()));
		textViewPhoneTypeRes.setText(calcularTipoTelefono(tm.getPhoneType()));

	}

}
