package com.example.monitortrafico;

import com.example.rcmmapp.R;
import com.example.rcmmapp.R.id;
import com.example.rcmmapp.R.layout;
import com.example.rcmmapp.R.menu;

import android.app.Activity;
import android.content.Intent;
import android.net.TrafficStats;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MonitorTraficoActivity extends Activity {

	private String UNSUPPORTED = "UNSUPPORTED";

	// WIFI
	TextView textViewBytesRecibidosResW;
	TextView textViewPaquetesRecibidosResW;
	TextView textViewBytesEnviadosResW;
	TextView textViewPaquetesEnviadosResW;

	// RED MOVIL
	TextView textViewBytesRecibidosResM;
	TextView textViewPaquetesRecibidosResM;
	TextView textViewBytesEnviadosResM;
	TextView textViewPaquetesEnviadosResM;

	// Botones
	Button buttonEstadisticas;
	Button buttonTrafico;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_monitor_trafico);

		// Asociamos los recursos
		textViewBytesRecibidosResW = (TextView) findViewById(R.id.textViewBytesRecibidosResW);
		textViewPaquetesRecibidosResW = (TextView) findViewById(R.id.textViewPaquetesRecibidosResW);
		textViewBytesEnviadosResW = (TextView) findViewById(R.id.textViewBytesEnviadosResW);
		textViewPaquetesEnviadosResW = (TextView) findViewById(R.id.textViewPaquetesEnviadosResW);

		textViewBytesRecibidosResM = (TextView) findViewById(R.id.textViewBytesRecibidosResM);
		textViewPaquetesRecibidosResM = (TextView) findViewById(R.id.textViewPaquetesRecibidosResM);
		textViewBytesEnviadosResM = (TextView) findViewById(R.id.textViewBytesEnviadosResM);
		textViewPaquetesEnviadosResM = (TextView) findViewById(R.id.textViewPaquetesEnviadosResM);

		buttonEstadisticas = (Button) findViewById(R.id.buttonEstadisticas);

		buttonEstadisticas.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// Datos m�viles
				long BytesRecibidosM = TrafficStats.getMobileRxBytes();
				long PaquetesRecibidosM = TrafficStats.getMobileRxPackets();
				long BytesEnviadosM = TrafficStats.getMobileTxBytes();
				long PaquetesEnviadosM = TrafficStats.getMobileTxPackets();

				// WIFI (Estrategia Aplicada: Restar del Total los datos
				// m�viles)
				long BytesRecibidosW = TrafficStats.getTotalRxBytes();
				long PaquetesRecibidosW = TrafficStats.getTotalRxPackets();
				long BytesEnviadosW = TrafficStats.getTotalTxBytes();
				long PaquetesEnviadosW = TrafficStats.getTotalTxPackets();

				// ACTUALIZAMOS VISTAS

				if (BytesRecibidosM == TrafficStats.UNSUPPORTED)
					textViewBytesRecibidosResM.setText(UNSUPPORTED);
				else
					textViewBytesRecibidosResM.setText(Long
							.toString(BytesRecibidosM));

				if (PaquetesRecibidosM == TrafficStats.UNSUPPORTED)
					textViewPaquetesRecibidosResM.setText(UNSUPPORTED);
				else
					textViewPaquetesRecibidosResM.setText(Long
							.toString(PaquetesRecibidosM));

				if (BytesEnviadosM == TrafficStats.UNSUPPORTED)
					textViewBytesEnviadosResM.setText(UNSUPPORTED);
				else
					textViewBytesEnviadosResM.setText(Long
							.toString(BytesEnviadosM));

				if (PaquetesEnviadosM == TrafficStats.UNSUPPORTED)
					textViewPaquetesEnviadosResM.setText(UNSUPPORTED);
				else
					textViewPaquetesEnviadosResM.setText(Long
							.toString(PaquetesEnviadosM));

				if (BytesRecibidosW == TrafficStats.UNSUPPORTED)
					textViewBytesRecibidosResW.setText(UNSUPPORTED);
				else
					textViewBytesRecibidosResW.setText(Long
							.toString(BytesRecibidosW - BytesRecibidosM));

				if (PaquetesRecibidosW == TrafficStats.UNSUPPORTED)
					textViewPaquetesRecibidosResW.setText(UNSUPPORTED);
				else
					textViewPaquetesRecibidosResW.setText(Long
							.toString(PaquetesRecibidosW - PaquetesRecibidosM));

				if (BytesEnviadosW == TrafficStats.UNSUPPORTED)
					textViewBytesEnviadosResW.setText(UNSUPPORTED);
				else
					textViewBytesEnviadosResW.setText(Long
							.toString(BytesEnviadosW - BytesEnviadosM));

				if (PaquetesEnviadosW == TrafficStats.UNSUPPORTED)
					textViewPaquetesEnviadosResW.setText(UNSUPPORTED);
				else
					textViewPaquetesEnviadosResW.setText(Long
							.toString(PaquetesEnviadosW - PaquetesEnviadosM));
			}
		});

		buttonTrafico = (Button) findViewById(R.id.buttonTrafico);

		buttonTrafico.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// Creamos un intent para lanzar la segunda actividad
				Intent i = new Intent(MonitorTraficoActivity.this,
						TrafficStatsActivity.class);

				// Lanzamos el intent
				startActivity(i);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.monitor_trafico, menu);
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
}
