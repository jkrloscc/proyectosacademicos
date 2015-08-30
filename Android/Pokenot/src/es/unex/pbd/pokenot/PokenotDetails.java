package es.unex.pbd.pokenot;

import es.unex.pbd.pokenot.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PokenotDetails extends Activity {

	int position;
	PokenotItem pokenot;

	ImageView image;
	TextView alias;
	TextView nombre;
	TextView tipo;
	TextView peso;
	TextView altura;
	TextView sexo;

	private DatabaseOpenHelper mDbHelper;

	private static final int EDIT_POKENOT_ITEM_REQUEST = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pokenot_layout);

		// Open the database
		mDbHelper = new DatabaseOpenHelper(this);
		mDbHelper.getReadableDatabase();

		// Obtenemos de la lista estatica, por lo tanto cada vez que
		// insertemos debemos añadir a la
		// lista general de elementos.
		// Obtenemos la posicion del intent y así obtenemos el pokemon cazado.
		Intent i = getIntent();
		position = i.getExtras().getInt("pokenotPosition");
		pokenot = MainActivity.listaCazados.get(position);

		image = (ImageView) findViewById(R.id.imagepokenot);
		alias = (TextView) findViewById(R.id.aliasdetail);
		nombre = (TextView) findViewById(R.id.nombredetail);
		tipo = (TextView) findViewById(R.id.tipodetail);
		peso = (TextView) findViewById(R.id.pesodetail);
		altura = (TextView) findViewById(R.id.alturadetail);
		sexo = (TextView) findViewById(R.id.sexodetail);

		String foto = pokenot.getFoto();
		Log.i("ItemDEbug", foto);

		switch (foto) {
		case "pikachu":
			image.setImageResource(R.drawable.pikachu);
			break;
		case "jigglypuff":
			image.setImageResource(R.drawable.jigglypuff);
			break;
		case "pichu":
			image.setImageResource(R.drawable.pichu);
			break;
		case "eevee":
			image.setImageResource(R.drawable.eevee);
			break;
		case "piplup":
			image.setImageResource(R.drawable.piplup);
			break;
		case "litwick":
			image.setImageResource(R.drawable.litwick);
			break;
		case "cubchoo":
			image.setImageResource(R.drawable.cubchoo);
			break;
		default:

			Uri myUri = Uri.parse(foto);

			image.setImageURI(myUri);

			break;
			
			/*//Redimensaionar la imagen
			Bitmap myBitmap= BitmapFactory.decodeFile(foto);
			
			int width = myBitmap.getWidth();
	        int height = myBitmap.getHeight();
	        int newWidth = 200;
	        int newHeight = 200;

	        // calculate the scale - in this case = 0.4f
	        float scaleWidth = ((float) newWidth) / width;
	        float scaleHeight = ((float) newHeight) / height;

	        // createa matrix for the manipulation
	        Matrix matrix = new Matrix();
	        // resize the bit map
	        matrix.postScale(scaleWidth, scaleHeight);
	        // rotate the Bitmap
	        matrix.postRotate(45);

	        // recreate the new Bitmap
	        Bitmap resizedBitmap = Bitmap.createBitmap(myBitmap, 0, 0, 
	                          width, height, matrix, true); 
			
			Uri myUri = Uri.parse(foto);
			
			image.setImageBitmap(resizedBitmap);

			break;*/
		}
		alias.setText(pokenot.getAlias());
		nombre.setText(pokenot.getNombre());
		tipo.setText(pokenot.getTipo().toString());
		peso.setText(pokenot.getPeso());
		altura.setText(pokenot.getAltura());
		sexo.setText(pokenot.getSexo().toString());

		Button borrar = (Button) findViewById(R.id.boton_borrar);

		borrar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// Borramos de la BD
				mDbHelper.borrarPokenot(pokenot.getAlias());

				// Eliminamos del ListView
				MainActivity.getListaCazados().remove(pokenot);

				finish();

			}

		});

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == EDIT_POKENOT_ITEM_REQUEST) {
            // Modificar un pokenot en la BD
            if (resultCode == Activity.RESULT_OK)
                mDbHelper.modificarPokenot(pokenot.getAlias(), data.getExtras()
                        .getString("newAlias"));
		}

	}
}
