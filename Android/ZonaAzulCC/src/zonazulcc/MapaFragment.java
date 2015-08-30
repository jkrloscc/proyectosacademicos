package zonazulcc;

import com.example.zonaazulcc.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class MapaFragment extends Fragment {

	private Button botonInfo;
	private Button botonCerca;
	private LinearLayout info;
	private boolean pressInfo = false;

	MainActivity mainActivity = null;

	public MapaFragment(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("MapaFragment", "-------------------oncreteview");
		super.onCreateView(inflater, container, savedInstanceState);

		View rootView = inflater.inflate(R.layout.fragment_mapa, container,
				false);

		info = (LinearLayout) rootView.findViewById(R.id.LinearLayoutInfo);
		info.setVisibility(View.INVISIBLE);

		botonInfo = (Button) rootView.findViewById(R.id.informacion);
		botonInfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (botonInfo.isPressed() && !pressInfo) {
					info.setVisibility(View.VISIBLE);
					pressInfo = true;
				} else {
					info.setVisibility(View.INVISIBLE);
					pressInfo = false;
				}

			}
		});

		botonCerca = (Button) rootView.findViewById(R.id.cerca);
		botonCerca.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				mainActivity.calcularMasCercana();

			}
		});

		mainActivity.setUpMapIfNeeded();
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();

		Log.i("onResume()",
				"onresume -----entrando en setUpMapIfNeeded()------------------");
		mainActivity.setUpMapIfNeeded();
	}

}