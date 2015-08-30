package com.example.estadosmartphoneii;

import java.util.List;

import com.example.rcmmapp.R;
import com.example.rcmmapp.R.drawable;
import com.example.rcmmapp.R.id;
import com.example.rcmmapp.R.layout;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.telephony.*;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EstadoSmartphoneIIActivity extends Activity {

	private TextView textViewEstadoLlamadaRes;
	private TextView textViewEstadoConexionRes;
	private TextView textViewEstadoServicioRes;
	private TextView textViewLocalizacionRes;

	private final PhoneStateListener listenerTelefono = new PhoneStateListener() {

		/**
		 * @post Monitoriza el estado de la LLAMADA del dispositivo
		 * @param <b>estado<b> Entero que indica el estado de la llamada
		 * @param <b>numeroEntrante<b> Cadena con el n� entrante, en caso de que
		 *        lo hubiera
		 * @complejidad <b>O(1)<b>
		 */
		public void onCallStateChanged(int estado, String numeroEntrante) {

			String estadoTelefono = "Desconocido";

			switch (estado) {

			case TelephonyManager.CALL_STATE_IDLE:
				estadoTelefono = "En REPOSO";
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				estadoTelefono = "Llamada SALIENTE";
				break;
			case TelephonyManager.CALL_STATE_RINGING:
				estadoTelefono = "Llamada ENTRANTE: " + numeroEntrante;
				break;
			}

			textViewEstadoLlamadaRes.setText(estadoTelefono);
			super.onCallStateChanged(estado, numeroEntrante);
		}

		/**
		 * @post Monitoriza el estado de la CONEXION DE LOS DATOS del
		 *       dispositivo
		 * @param <b>estado<b> Entero que indica el estado de la conexion
		 * @complejidad <b>O(1)<b>
		 */
		public void onDataConnectionStateChanged(int estado) {

			String estadoTelefono = "Desconocido";

			switch (estado) {
			// Trafico IP disponible
			case TelephonyManager.DATA_CONNECTED:
				estadoTelefono = "CONECTADO";
				break;
			// La conexion esta actualmente levantandose
			case TelephonyManager.DATA_CONNECTING:
				estadoTelefono = "CONECTANDO";
				break;
			// El trafico IP no esta disponible
			case TelephonyManager.DATA_DISCONNECTED:
				estadoTelefono = "DESCONECTADO";
				break;
			// Hay conexion activada pero el trafico IP no esta disponible
			// temporalmente
			case TelephonyManager.DATA_SUSPENDED:
				estadoTelefono = "SUSPENDIDA";
				break;
			}

			textViewEstadoConexionRes.setText(estadoTelefono);
			super.onDataConnectionStateChanged(estado);
		}

		/**
		 * @post Monitoriza el estado del SERVICIO del dispositivo
		 * @param <b>estadoServicio<b> Instancia de la clase ServiceState que
		 *        indica el estado del servicio del dispositivo
		 * @complejidad <b>O(1)<b>
		 */
		public void onServiceStateChanged(ServiceState estadoServicio) {

			String estadoTelefono = "Desconocido";

			switch (estadoServicio.getState()) {
			// Solo se permiten llamadas de Emergencia (112)
			case ServiceState.STATE_EMERGENCY_ONLY:
				estadoTelefono = "SOLO LLAMADAS DE EMERGENCIA";
				break;
			// Estado normal
			case ServiceState.STATE_IN_SERVICE:
				estadoTelefono = "EN SERVICIO";
				break;
			// Se encuentra sin se�al
			case ServiceState.STATE_OUT_OF_SERVICE:
				estadoTelefono = "FUERA DE SERVICIO";
				break;
			// La radiose�al se encuentra apagada
			case ServiceState.STATE_POWER_OFF:
				estadoTelefono = "APAGADO";
				break;
			}

			textViewEstadoServicioRes.setText(estadoTelefono);
			super.onServiceStateChanged(estadoServicio);
		}

		/**
		 * @post Monitoriza la LOCALIZACI�N del dispositivo
		 * @param <b>localizacion<b> Instancia de la clase CellLocation que
		 *        representa la localizacion del dispositivo
		 * @complejidad <b>O(1)<b>
		 */
		public void onCellLocationChanged(CellLocation localizacion) {

			String estadoTelefono = "Desconocido";

			estadoTelefono = localizacion.toString();
			textViewLocalizacionRes.setText(estadoTelefono);
			super.onCellLocationChanged(localizacion);

		}

		
		
		/**
		 * @post Monitoriza la DIRECCI�N DEL TR�FICO de datos en el dispositivo
		 * @param <b>direction<b> Entero que indica la direcci�n del tr�fico de
		 *        datos
		 * @complejidad <b>O(1)<b>
		 */
		public void onDataActivity(int direction) {

			int id = -1; // ID imagen

			switch (direction) {
			// Trafico entrante
			case TelephonyManager.DATA_ACTIVITY_IN:
				id = R.drawable.indata;
				break;
			// Trafico saliente
			case TelephonyManager.DATA_ACTIVITY_OUT:
				id = R.drawable.outdata;
				break;
			// Trafico bidireccional
			case TelephonyManager.DATA_ACTIVITY_INOUT:
				id = R.drawable.bidata;
				break;
			// No hay tr�fico
			case TelephonyManager.DATA_ACTIVITY_NONE:
				id = R.drawable.nodata;
				break;
			}
			((ImageView) findViewById(R.id.imageViewTrafico))
					.setImageResource(id);

			super.onDataActivity(direction);
		}

		/**
		 * @post Monitoriza la INTENSIDAD DE LA SE�AL DE TR�FICO de datos en el
		 *       dispositivo
		 * @param <b>signalStrength<b> Instancia de la Clase SignalStrength
		 * @complejidad <b>O(1)<b>
		 */
		public void onSignalStrengthsChanged(SignalStrength signalStrength) {

			int id = -1; // ID imagen

			int signal = signalStrength.getGsmSignalStrength();

			// La se�al va de 0dB a 31db
			if (signal > 0 && signal <= 12)
				id = R.drawable.level1;
			else {
				if (signal > 12 && signal <= 18)
					id = R.drawable.level2;
				else {
					if (signal > 18 && signal <= 24)
						id = R.drawable.level3;
					else {
						if (signal > 24 && signal <= 30)
							id = R.drawable.level4;
						else {
							if (signal > 30 && signal != 99)
								id = R.drawable.level5;
							else {
								// Si signal == 99 se�al no detectable
								if (signal == 99)
									id = R.drawable.level0;
							}
						}
					}

				}
			}

			Log.i("Valor de Intensidad Se�al", Integer.toString(signal));
			((ImageView) findViewById(R.id.imageViewSignal))
					.setImageResource(id);
			super.onSignalStrengthsChanged(signalStrength);
		}

	};

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
		setContentView(R.layout.estado_smartphone_ii);

		// Vinculamos variables a los recursos de nuestro proyecto
		textViewEstadoLlamadaRes = (TextView) findViewById(R.id.textViewEstadoLlamadaRes);
		textViewEstadoConexionRes = (TextView) findViewById(R.id.textViewEstadoConexionRes);
		textViewEstadoServicioRes = (TextView) findViewById(R.id.textViewEstadoServicioRes);
		textViewLocalizacionRes = (TextView) findViewById(R.id.textViewLocalizacionRes);

		// Llamamos al metodo Listener
		iniciarListener();

	}

	
	/**
	 * @post Muestra un mensaje al usuario informando que tiene el GPS deshabilitado
	 * @complejidad <b>O(1)<b>
	 */
	public  void mostrarAvisoGpsDeshabilitado() {
		Context context = getApplicationContext();
		CharSequence text = "GPS esta desactivado. Conectelo manualmente, por favor";
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
	
	/**
	 * @post Monitoriza el estado del telefono
	 * @complejidad <b>O(1)<b>
	 */
	private void iniciarListener() {

		// accedemos a los servicios del tel�fono
		TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		
		//Proveedores de Localizacion
		LocationManager locManager = (LocationManager)getSystemService(LOCATION_SERVICE);
		List<String> listaProviders = locManager.getAllProviders();
		
		LocationProvider provider = locManager.getProvider(listaProviders.get(0));
		int precision = provider.getAccuracy();
		boolean obtieneAltitud = provider.supportsAltitude();
		int consumoRecursos = provider.getPowerRequirement();
		
		
		//Elegimos el mejor proveedor
		Criteria req = new Criteria();
		req.setAccuracy(Criteria.ACCURACY_FINE);
		req.setAltitudeRequired(true);
		
		//Mejor proveedor por criterio
		String mejorProviderCrit = locManager.getBestProvider(req, false);
		 
		//Lista de proveedores por criterio
		List<String> listaProvidersCrit = locManager.getProviders(req, false);
		
		//Si el GPS no esta habilitado
		if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			 mostrarAvisoGpsDeshabilitado();
		}

		
		
		// Indicamos los eventos que esperamos que el telefono escuche
		int eventos = PhoneStateListener.LISTEN_DATA_ACTIVITY
				| PhoneStateListener.LISTEN_CELL_LOCATION
				| PhoneStateListener.LISTEN_CALL_STATE
				| PhoneStateListener.LISTEN_CALL_FORWARDING_INDICATOR
				| PhoneStateListener.LISTEN_DATA_CONNECTION_STATE
				| PhoneStateListener.LISTEN_SIGNAL_STRENGTHS
				| PhoneStateListener.LISTEN_SERVICE_STATE;

		// Ponemos el telefono a escuchar
		tm.listen(listenerTelefono, eventos);
	}

}
