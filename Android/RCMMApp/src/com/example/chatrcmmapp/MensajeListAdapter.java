package com.example.chatrcmmapp;

import java.util.ArrayList;
import java.util.List;

import com.example.chatrcmmapp.MensajeItem.Tipo;
import com.example.rcmmapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MensajeListAdapter extends BaseAdapter {

	// List of MensajeItems
	private final List<MensajeItem> mItems = new ArrayList<MensajeItem>();

	private final Context mContext;

	public MensajeListAdapter(Context context) {

		mContext = context;

	}

	// Add a MensajeItem to the adapter
	// Notify observers that the data set has changed
	public void add(MensajeItem item) {

		mItems.add(item);
		notifyDataSetChanged();

	}

	// Clears the list adapter of all items.
	public void clear() {

		mItems.clear();
		notifyDataSetChanged();

	}

	// Returns the number of MensajeItems
	@Override
	public int getCount() {
		return mItems.size();
	}

	// Retrieve the number of MensajeItems
	@Override
	public Object getItem(int position) {
		return mItems.get(position);
	}

	// Get the ID for the MensajeItem
	// In this case it's just the position
	@Override
	public long getItemId(int position) {
		return position;
	}

	// Create a View to display the MensajeItem
	// at specified position in mItems
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		RelativeLayout itemLayout;

		// Get the current MensajeItem
		final MensajeItem mensajeItem = (MensajeItem) getItem(position);

		// - Inflate the View for this MensajeItem from mensaje_item_r.xml or
		// mensaje_item_l.xml
		if (mensajeItem.getTipo() == Tipo.ENVIADO)
			itemLayout = (RelativeLayout) LayoutInflater.from(mContext)
					.inflate(R.layout.mensaje_item_r, null);
		else
			itemLayout = (RelativeLayout) LayoutInflater.from(mContext)
					.inflate(R.layout.mensaje_item_l, null);

		// - Fill in specific MensajeItem data
		// Remember that the data that goes in this View
		// corresponds to the user interface elements defined
		// in the layout file

		// Display TipoMensaje in TextView
		final TextView textViewTipoMensaje = (TextView) itemLayout
				.findViewById(R.id.TextViewTipoMensaje);
		textViewTipoMensaje.setText(mensajeItem.getTipo().toString());
		
		// Display TextoMensaje in TextView
		final TextView textViewTextoMensaje = (TextView) itemLayout
				.findViewById(R.id.TextViewTextoMensaje);
		textViewTextoMensaje.setText(mensajeItem.getTexto());
		
		
		//TODO Display EmisorMensaje in TextView


		// Return the View you just created
		return itemLayout;
	}

//	public void post(Runnable runnable) {
//		// TODO Auto-generated method stub
//		
//	}
}
