package es.unex.pbd.pokenot.adapter;

import java.util.ArrayList;
import java.util.List;

import es.unex.pbd.pokenot.PokenotItem;
import es.unex.pbd.pokenot.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ItemPokenotAdapter extends BaseAdapter {

	// List of PokenotItems
	private final List<PokenotItem> mItems = new ArrayList<PokenotItem>();

	private final Context mContext;

	public ItemPokenotAdapter(Context context) {
		mContext = context;
	}
	
	
	// Add a PokenotItem to the adapter
	// Notify observers that the data set has changed
	public void add(PokenotItem item) {

		mItems.add(item);
		notifyDataSetChanged();

	}

	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RelativeLayout itemLayout;

		// Get the current PokenotItem
		final PokenotItem pokenotItem = (PokenotItem) getItem(position);

		// - Inflate the View for this PokenotItem from pokenot_item.xml or
		// mensaje_item_l.xml

		itemLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(
				R.layout.mostrar_layout, null);

		// - Fill in specific PokenotItem data
		// Remember that the data that goes in this View
		// corresponds to the user interface elements defined
		// in the layout file

		// Display list_image in ImageView
		final ImageView list_image = (ImageView) itemLayout
				.findViewById(R.id.list_image);
			String foto = pokenotItem.getFoto();
			Log.i("ItemDEbug",foto);

		switch(foto){
		case "pikachu":
			list_image.setImageResource(R.drawable.pikachu);
		break;
		case "jigglypuff":
			list_image.setImageResource(R.drawable.jigglypuff);
			break;
		case "pichu":
			list_image.setImageResource(R.drawable.pichu);
			break;
		case "eevee":
			list_image.setImageResource(R.drawable.eevee);
			break;
		case "piplup":		
			list_image.setImageResource(R.drawable.piplup);
			break;
		case "litwick":
			list_image.setImageResource(R.drawable.litwick);
			break;
		case "cubchoo":
			list_image.setImageResource(R.drawable.cubchoo);
			break;		
		}

		// Display nombre in TextView
		final TextView nombre = (TextView) itemLayout.findViewById(R.id.nombre);
		nombre.setText(pokenotItem.getNombre());

		// Display tipo in TextView
		final TextView tipo = (TextView) itemLayout.findViewById(R.id.tipo);
		tipo.setText(pokenotItem.getTipo().toString());

		// Return the View you just created
		return itemLayout;
	}
}

// (/data/data/nombre_del_paquete/files).