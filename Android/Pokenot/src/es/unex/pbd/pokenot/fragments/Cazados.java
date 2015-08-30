package es.unex.pbd.pokenot.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import es.unex.pbd.pokenot.AddPokenotActivity;
import es.unex.pbd.pokenot.DatabaseOpenHelper;
import es.unex.pbd.pokenot.MainActivity;
import es.unex.pbd.pokenot.PokenotDetails;
import es.unex.pbd.pokenot.PokenotItem;
import es.unex.pbd.pokenot.PokenotItem.Sexo;
import es.unex.pbd.pokenot.PokenotItem.Tipo;
import es.unex.pbd.pokenot.R;
import es.unex.pbd.pokenot.adapter.ItemCazadosAdapter;

public class Cazados extends Fragment {

	ItemCazadosAdapter itemCazadosAdapter;
	ListView listViewPokenot = null;
	View rootView = null;

	private static final int ADD_POKENOT_ITEM_REQUEST = 0;

	private DatabaseOpenHelper mDbHelper;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);
		rootView = inflater.inflate(R.layout.main_cazados, container, false);

		listViewPokenot = (ListView) rootView.findViewById(R.id.listCazados);

		listViewPokenot.setFooterDividersEnabled(true);

		itemCazadosAdapter = new ItemCazadosAdapter(getActivity());

		// Open the database
		mDbHelper = new DatabaseOpenHelper(getActivity());
		mDbHelper.getReadableDatabase();

		if (MainActivity.getListaCazados().isEmpty()) {

			// Create a new SimpleCursorAdapter for this Fragmenf t's ListView
			Cursor c = mDbHelper.getWritableDatabase().query(
					DatabaseOpenHelper.TABLE_NAME_2,
					DatabaseOpenHelper.columns2, null, new String[] {}, null,
					null, null);
			while (c.moveToNext()) {

				// Rescatamos los valores de las columnas de la TUPLA
				int id = c.getInt(c
						.getColumnIndex(DatabaseOpenHelper.columns2[0]));
				String nombre = c.getString(c
						.getColumnIndex(DatabaseOpenHelper.columns2[1]));
				String alias = c.getString(c
						.getColumnIndex(DatabaseOpenHelper.columns2[2]));
				String foto = c.getString(c
						.getColumnIndex(DatabaseOpenHelper.columns2[3]));
				String sex = c.getString(c
						.getColumnIndex(DatabaseOpenHelper.columns2[4]));

				foto = foto.toLowerCase();
				String tip = "";
				String peso = "";
				String altura = "";

				// El tipo, peso y altura lo rescatamos de la Tabla Pokenot
				Cursor cursor = mDbHelper.getWritableDatabase().rawQuery(
						"SELECT tipo, peso, altura FROM pokenots WHERE nombre ="
								+ "'" + nombre + "'", null);
				while (cursor.moveToNext()) {

					tip = cursor.getString(0);
					peso = cursor.getString(1);
					altura = cursor.getString(2);
				}

				Tipo tipo = Tipo.valueOf(tip);
				Sexo sexo = Sexo.valueOf(sex);

				Log.i("PESO:", peso);
				Log.i("ALTURA:", altura);

				PokenotItem pokemonAux = new PokenotItem(id, nombre, alias,
						tipo, foto, peso, altura, sexo);
				MainActivity.listaCazados.add(pokemonAux);
				itemCazadosAdapter.add(pokemonAux);

			}
		} else {
			itemCazadosAdapter.setmItems(MainActivity.getListaCazados());
		}

		// Inflate footerView for footer_view.xml file
		TextView footerView = (TextView) inflater.inflate(R.layout.footer_view,
				null);

		// Add footerView to ListView
		listViewPokenot.addFooterView(footerView);

		footerView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// start activity AddToDoActivity
				Intent startNewActivity = new Intent(getActivity(),
						AddPokenotActivity.class);
				startActivityForResult(startNewActivity,
						ADD_POKENOT_ITEM_REQUEST);

			}
		});

		// Attach the adapter to this Fragment's ListView
		listViewPokenot.setAdapter(itemCazadosAdapter);

		listViewPokenot.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getActivity(), PokenotDetails.class);

				/*
				 * Añadimos al intent la informacion, en este caso, el id del
				 * pokemon que hemos clickeado.
				 */
				i.putExtra("pokenotPosition", position);

				// Lanzamos la activity
				startActivity(i);

			}

		});
		return rootView;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == ADD_POKENOT_ITEM_REQUEST) {
			if (resultCode == getActivity().RESULT_OK) {
				PokenotItem item = new PokenotItem(data);
				addItem(item);
				MainActivity.listaCazados.add(item);
			}
		}

	}

	@Override
	public void onResume() {
		super.onResume();
		itemCazadosAdapter.setmItems(MainActivity.getListaCazados());

		if (itemCazadosAdapter.getCount() == 0)
			loadItems();
	}

	private Cursor loadItems() {

		// Cargamos los objetos de la BDpara mostrarlos en el ListView
		return mDbHelper.getWritableDatabase().query(
				DatabaseOpenHelper.TABLE_NAME_2, DatabaseOpenHelper.columns2,
				null, new String[] {}, null, null, null);

	}

	private void addItem(PokenotItem item) {

		// Insert a PokenotItem in the database
		mDbHelper.insertarPokenot(item);
		itemCazadosAdapter.notifyDataSetChanged();
	}

	// Delete all records
	private void clearAll() {

		// Delete all the PokenotItem from database
		mDbHelper.getWritableDatabase().delete(DatabaseOpenHelper.TABLE_NAME_2,
				null, null);
		// itemCazadosAdapter.getCursor().requery();
		itemCazadosAdapter.notifyDataSetChanged();
	}

}
