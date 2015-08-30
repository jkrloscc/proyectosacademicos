package com.example.rcmmapp;



import com.example.chatrcmmapp.ChatRCMMAppActivity;
import com.example.chatrcmmappii.ChatRCMMAppIIActivity;
import com.example.estadosmartphonei.EstadoSmartphoneIActivity;
import com.example.estadosmartphoneii.EstadoSmartphoneIIActivity;
import com.example.monitortrafico.MonitorTraficoActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Almacenamos los recursos "string" en un array
		String[] aplicaciones = getResources().getStringArray(
				R.array.aplicaciones);

		// Asociamos el array de recursos a un ListAdapter
		this.setListAdapter(new ArrayAdapter<String>(this,
				R.layout.etiqueta_list_view, R.id.etiqueta, aplicaciones));

		ListView lv = getListView();

		// Nos ponemos a escuchar por si se selecciona alg�n elemento del
		// ListView
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Elemento seleccionado
				String aplicacion = ((TextView) view).getText().toString();

				// Lanzamos una nueva Activity al seleccionar el elemento
				// (aplicacion) en cuestion
				switch (aplicacion) {
			
				case "Estado Smartphone I":

					Intent i1 = new Intent(getApplicationContext(),
							EstadoSmartphoneIActivity.class);
					startActivity(i1);
					break;
					
				case "Estado Smartphone II":

					Intent i2 = new Intent(getApplicationContext(),
							EstadoSmartphoneIIActivity.class);
					startActivity(i2);
					break;
				case "Monitor Trafico":

					Intent i3 = new Intent(getApplicationContext(),
							MonitorTraficoActivity.class);
					startActivity(i3);
					break;
					
				case "Chat RCMM App":

					Intent i4 = new Intent(getApplicationContext(),
							ChatRCMMAppActivity.class);
					startActivity(i4);
					break;
				case "Chat RCMM App II":

					Intent i5 = new Intent(getApplicationContext(),
							ChatRCMMAppIIActivity.class);
					startActivity(i5);
					break;
				default:
					Intent iDefault = new Intent(getApplicationContext(),
							AplicacionSeleccionada.class);
					// Se envian los datos a la Activity por defecto
					iDefault.putExtra("aplicacion", aplicacion);
					startActivity(iDefault);
					break;
				}

			}
		});
	}
}
