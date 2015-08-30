package es.unex.pbd.pokenot;

import java.util.LinkedList;
import java.util.List;
import es.unex.pbd.pokenot.PokenotItem.Tipo;
import es.unex.pbd.pokenot.adapter.ItemPokenotAdapter;
import es.unex.pbd.pokenot.fragments.Cazados;
import es.unex.pbd.pokenot.fragments.Home;
import es.unex.pbd.pokenot.fragments.Mostrar;
import es.unex.pbd.pokenot.fragments.NavigationDrawerFragment;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

public class MainActivity extends Activity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	// Lista Objetos Modelo
	public static List<PokenotItem> listaPokenotBD = new LinkedList<PokenotItem>();
	public static List<PokenotItem> listaCazados = new LinkedList<PokenotItem>();

	private DatabaseOpenHelper mDbHelper;

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Open the database
		mDbHelper = new DatabaseOpenHelper(this);
		mDbHelper.getReadableDatabase();

		// Create a new SimpleCursorAdapter for this Fragment's ListView
		Cursor c = mDbHelper.getWritableDatabase().query(
				DatabaseOpenHelper.TABLE_NAME, DatabaseOpenHelper.columns,
				null, new String[] {}, null, null, null);

		while (c.moveToNext()) {

			// Rescatamos de la TUPLA aquello que nos interese mostrar
			int id = c.getInt(c.getColumnIndex(DatabaseOpenHelper.columns[0]));
			String nombre = c.getString(c
					.getColumnIndex(DatabaseOpenHelper.columns[1]));
			String tipo = c.getString(c
					.getColumnIndex(DatabaseOpenHelper.columns[2]));
			String foto = c.getString(c
					.getColumnIndex(DatabaseOpenHelper.columns[3]));

			foto = foto.toLowerCase();
			String peso = c.getString(c
					.getColumnIndex(DatabaseOpenHelper.columns[4]));
			String altura = c.getString(c
					.getColumnIndex(DatabaseOpenHelper.columns[5]));

			PokenotItem pokemonAux = new PokenotItem(id, nombre, "alias",
					Tipo.valueOf(tipo), foto, peso, altura, null);
			listaPokenotBD.add(pokemonAux);
		

		}

		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getFragmentManager();

		Fragment Fragment = null;

		switch (position) {

		case 0:

			Fragment = new Home();
			break;

		case 1:

			Fragment = new Cazados();
			break;

		case 2:
			Fragment = new Mostrar();
			break;

		}
		fragmentManager.beginTransaction().replace(R.id.container, Fragment)
				.commit();
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
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
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}
	}

	public static List<PokenotItem> getListaPokenotBD() {
		return listaPokenotBD;
	}

	public void setListaPokenotBD(List<PokenotItem> listaPokenotBD) {
		this.listaPokenotBD = listaPokenotBD;
	}

	public static List<PokenotItem> getListaCazados() {
		return listaCazados;
	}

	public void setListaCazados(List<PokenotItem> listaCazados) {
		this.listaCazados = listaCazados;
	}

}
