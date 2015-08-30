package zonazulcc;

import java.util.ArrayList;

import com.example.zonaazulcc.R;

import model.ItemCalle;
import adapter.ItemCalleAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
 
public class CallesFragment extends Fragment{
 
	ItemCalleAdapter itemCalleAdapter=null;
	ArrayList<ItemCalle> callesLista =null;

	ListView listViewCalles=null;
	View rootView=null;
	ArrayList<ItemCalle> listFilteredCalles=null;
	MainActivity mainActivity=null;
    
	
	public CallesFragment(MainActivity mainActivity) {
		this.mainActivity=mainActivity;
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		Log.i("CallesFragment", "-------------------oncreteview");
    	super.onCreateView(inflater, container, savedInstanceState);
        rootView=inflater.inflate(R.layout.fragment_calles, container, false);
        
        // 1. pass context and data to the custom adapter
        Log.i("callesBDoncreateview", MainActivity.dataBaseHandler.getAllCalles().toString());
        itemCalleAdapter = new ItemCalleAdapter(getActivity(), MainActivity.dataBaseHandler.getAllCalles());

        //2. setListAdapter
        listViewCalles = (ListView)rootView.findViewById(R.id.list);
        listViewCalles.setAdapter(itemCalleAdapter);
        
        listViewCalles.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View View, int position, long id) {
              
            	//Guardamos la calle seleccionada
            	mainActivity.setCalleActual(itemCalleAdapter.getItem(position));
            	Log.i("Calle seleccionada: ", mainActivity.getCalleActual().getNombreDeCalle() );
            	mainActivity.setUpMap();
            	ViewPager viewPager= (ViewPager) getActivity().findViewById(R.id.viewpager);

            	viewPager.setCurrentItem(1);

            }
        });
        
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Implementing ActionBar Search inside a fragment
        MenuItem item = menu.add(R.string.action_search);
        item.setIcon(R.drawable.ic_action_search); // sets icon
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        SearchView sv = new SearchView(getActivity());

        // modifying the text inside editText component
        int id = sv.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) sv.findViewById(id);
        textView.setHint(R.string.action_search);

        // implementing the listener
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        	
            @Override
            public boolean onQueryTextSubmit(String s) {
                //realizar busqueda
            	//CallesFragment.this.mSearch = new String(s);
            	//doSearch(mSearch);
            	return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
            	//CallesFragment.this.mSearch=new String(newText);
            	doSearch(newText);
                //return true;
                return true;
            }
            
        });
        item.setActionView(sv);
    }
    
    private void doSearch(String s) {
		// TODO Auto-generated method stub;
	    itemCalleAdapter = new ItemCalleAdapter(getActivity(), encontradaDataCalles(s));
	        //2. setListAdapter
	    listViewCalles = (ListView)rootView.findViewById(R.id.list);
	    listViewCalles.setAdapter(itemCalleAdapter);
		
	}

    /**
     * Generar datos a cargar el listViewcalles
     * @return
     */
    private ArrayList<ItemCalle> encontradaDataCalles(String street){
    	
    	listFilteredCalles = new ArrayList<ItemCalle>();
    	callesLista=MainActivity.dataBaseHandler.getAllCalles();
    	if(callesLista!=null){
    		for( ItemCalle calle : callesLista ) {
                if( calle.getNombreDeCalle().toLowerCase().toString().contains(street.toLowerCase().toString()) ) {
                	listFilteredCalles.add(calle);
                }
    		}
        }        
        return listFilteredCalles;
    }

	public View getRootView() {
		return rootView;
	}

	public ListView getListViewCalles() {
		return listViewCalles;
	}

	public ArrayList<ItemCalle> getCallesLista() {
		return callesLista;
	}    
    
	public void setCallesLista(ArrayList<ItemCalle> callesLista) {
		this.callesLista = callesLista;
	}
	

	public ItemCalleAdapter getItemCalleAdapter() {
		return itemCalleAdapter;
	}

	public void setItemCalleAdapter(ItemCalleAdapter itemCalleAdapter) {
		this.itemCalleAdapter = itemCalleAdapter;
	}
}
