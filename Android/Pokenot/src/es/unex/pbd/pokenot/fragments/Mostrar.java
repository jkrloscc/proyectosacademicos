package es.unex.pbd.pokenot.fragments;

import java.util.Iterator;

import es.unex.pbd.pokenot.DatabaseOpenHelper;
import es.unex.pbd.pokenot.MainActivity;
import es.unex.pbd.pokenot.PokenotItem;
import es.unex.pbd.pokenot.PokenotItem.Tipo;
import es.unex.pbd.pokenot.adapter.ItemPokenotAdapter;
import es.unex.pbd.pokenot.R;
import android.app.Fragment;
import android.app.ListFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Mostrar extends Fragment {

	ItemPokenotAdapter itemPokenotAdapter;
	ListView listViewPokenot = null;
	View rootView = null;

	// Add a ToDoItem Request Code
	private static final int ADD_POKENOT_ITEM_REQUEST = 0;

	private static final String TAG = "Pokenot";


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);
		rootView = inflater.inflate(R.layout.main_mostrar, container, false);

		listViewPokenot = (ListView) rootView.findViewById(R.id.listMostrar);

		itemPokenotAdapter = new ItemPokenotAdapter(getActivity());
		 Iterator itr = MainActivity.getListaPokenotBD().iterator();
	      while(itr.hasNext()) {
	    	  PokenotItem pokemonAux = (PokenotItem) itr.next();
	    	  itemPokenotAdapter.add(pokemonAux);
	      }

		// Attach the adapter to this Fragment's ListView
		listViewPokenot.setAdapter(itemPokenotAdapter);
		return rootView;

	}

	@Override
	public void onResume() {
		super.onResume();
		//
		// if (mAdapter.getCount() == 0)
		// loadItems();
	}

	private void loadItems() {

		// TODO Load PokenotItem from database and display them on the ListView

	}


}
