package com.example.rcmmapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.monitortrafico.AppItem;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AppListAdapter extends BaseAdapter {

	// Lista de AppItems
	private List<AppItem> mItems = new ArrayList<AppItem>();

	private Context mContext;

	public AppListAdapter(Context context) {

		mContext = context;

	}

	// Anhadimos un AppItem al adaptador
	// Verificamos que los datos han cambiado mediante Notify. Ademas ordenamos
	// por el atributo BytesRecibidos
	public void add(AppItem item) {

		mItems.add(item);

		Collections.sort(mItems, new Comparator<AppItem>() {
			@Override
			public int compare(AppItem app1, AppItem app2) {
				if (app1.getBytesTransmitidos() >= app2.getBytesTransmitidos()) {
					return -1;
				} else {
					return 1;
				}
			}
		});

		// Necesario para que la ListView actualice y se vuelva a pintar con los
		// datos nuevos
		notifyDataSetChanged();

	}

	// Limpia el listAdapter de todos los Items
	public void clear() {

		mItems.clear();
		notifyDataSetChanged();

	}

	// Devuelve el n�mero de elementos de la lista.
	@Override
	public int getCount() {

		return mItems.size();

	}

	// Devuelve el elemento en una determinada posici�n de la lista.
	@Override
	public Object getItem(int pos) {

		return mItems.get(pos);

	}

	// Devuelve el identificador de fila de una determinada posici�n de la
	// lista.
	@Override
	public long getItemId(int pos) {

		return pos;

	}

	/*
	 * POST Nos devuelve la Vista del Item creado para devolverla al
	 * TrafficStatsActivity
	 * 
	 * Este m�todo ha de construir un nuevo objeto View con el layout
	 * correspondiente a la posici�n position y devolverlo. Opcionalmente,
	 * podemos partir de una vista base convertView para generar m�s r�pido las
	 * vistas. El �ltimo par�metro corresponde al padre al que la vista va a ser
	 * a�adida.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// - Get the current ToDoItem
		final AppItem appItem = (AppItem) getItem(position);
		mItems.get(position);

		// Inflar la View para este AppItem
		/*
		 * No podemos usar ese m�todo directamente desde esa clase, En la clase
		 * AppListAdapter tenemos el contexto de la clase TrafficStatsActivity.
		 * Gracias a eso podemos rescatar el LayoutInflater mediante:
		 */
		RelativeLayout itemLayout = (RelativeLayout) LayoutInflater.from(
				mContext).inflate(R.layout.app_item, parent, false);

		// Rellenamos los datos del Item a mostrar

		// Mostramos el nombre en el TextView
		final TextView TextViewNombreApp = (TextView) itemLayout
				.findViewById(R.id.TextViewNombreApp);
		TextViewNombreApp.setText(appItem.getName());

		// Mostramos los bytes transmitidos, en el TextView
		final TextView TextViewBytesApp = (TextView) itemLayout
				.findViewById(R.id.TextViewBytesApp);
		TextViewBytesApp.setText(appItem.getBytesTransmitidos() + " Bytes");

		// Devolvemos la vista que acaba de ser creada
		return itemLayout;

	}

}
