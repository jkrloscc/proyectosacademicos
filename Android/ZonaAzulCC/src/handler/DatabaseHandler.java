/**
 * Implementacion de los metodos de la clase DatabaseHandler.
 *
 * @version 1.0
 * Asignatura: Arquitecturas Software Para Entornos Empresariales <br/>
 * @author
 * <b> Juan Carlos Bonilla Bermejo </b><br>
 * <b> Miguel Angel Holgado Ceballos </b><br>
 * Curso 14/15
 */
package handler;

import java.util.ArrayList;

import model.ItemCalle;

//import com.example.database.Message;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Manejador base de datos Zona Azul
 * @author miguel
 *
 */
public class DatabaseHandler extends SQLiteOpenHelper {
	private Context context;
	// Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "ZonaAzulDB";
    // Zona Azul table names
    private static final String TABLE_NAME_CALLES = "calles";
    // SQL statement to create tables and erase them
    private static final String CREATE_CALLES_TABLE = "CREATE TABLE "+TABLE_NAME_CALLES+" ( "+"calle_id INTEGER PRIMARY KEY autoincrement, "+"calle_nombre_id VARCHAR(250), "+"coste TEXT, "+"horario TEXT, "
    													+"latitud DOUBLE, "+"longitud DOUBLE, "+"precio_anulacion DOUBLE, "+"numero_plazas INTEGER )";
    private static final String DROP_TABLE_CALLES="DROP TABLE IF EXISTS " + TABLE_NAME_CALLES;
    
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context=context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//Message.message(context," onCreate called");
		// create calles table
		try {
			db.execSQL(CREATE_CALLES_TABLE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//Message.message(context, ""+e);
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//Message.message(context," onUpgrade called");
		try {
			// Drop older calles table if existed
			db.execSQL(DROP_TABLE_CALLES);
			// create fresh books table
			this.onCreate(db);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//Message.message(context, ""+e);
			e.printStackTrace();
		}
	}
	//---------------------------------------------------------------------
   
	/**
     * CRUD operations (create "add", read "get", update, delete) calle + get all calles + delete all calles
     */
	
	// Calles table name
    private static final String TABLE_CALLES = "calles";
    // Calles Table Columns names
    private static final String KEY_CALLE_ID ="calle_id";
    private static final String KEY_CALLE_NOMBRE_ID = "calle_nombre_id";
    private static final String KEY_COSTE = "coste";
    private static final String KEY_HORARIO = "horario";
	private static final String KEY_LATITUD = "latitud";
	private static final String KEY_LONGITUD = "longitud";
	private static final String KEY_PRECIO_ANULACION = "precio_anulacion";
    private static final String KEY_NUMERO_PLAZAS = "numero_plazas";
    
    private static final String[] COLUMNS_CALLES = {KEY_CALLE_ID, KEY_CALLE_NOMBRE_ID, KEY_COSTE, KEY_HORARIO, KEY_LATITUD, KEY_LONGITUD, KEY_PRECIO_ANULACION, KEY_NUMERO_PLAZAS};
    /////////////fin definicion calles crud
	public void addCalle(ItemCalle calle){
		Log.d("addCalle", calle.toString());
		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		 
		// 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_CALLE_NOMBRE_ID, calle.getNombreDeCalle()); // get nombre
        values.put(KEY_COSTE, calle.getCoste()); // get coste
        values.put(KEY_HORARIO, calle.getHorario()); // get horario
        values.put(KEY_LATITUD, calle.getLatitud()); // get latitud
        values.put(KEY_LONGITUD, calle.getLongitud()); // get longitud
        values.put(KEY_PRECIO_ANULACION, calle.getCosteAnulacion()); // get coste anulacion
        values.put(KEY_NUMERO_PLAZAS, calle.getNumeroDePlazasCalle()); // get numero de plazas
        // 3. insert
        db.insert(TABLE_CALLES, // table
        		null, //nullColumnHack
        		values); // key/value -> keys = column names/ values = column values
        
        // 4. close
        db.close(); 
	}
	
	// Get All Calles
    public ArrayList<ItemCalle> getAllCalles() {
        ArrayList<ItemCalle> calles = new ArrayList<ItemCalle>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_CALLES;
 
    	// 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
 
        // 3. go over each row, build calle and add it to list
        ItemCalle calle = null;
        if (cursor.moveToFirst()) {
            do {
            	calle = new ItemCalle(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getDouble(4), cursor.getDouble(5), cursor.getDouble(6), cursor.getInt(7));
            	//calle.setIdCalle(cursor.getInt(0));// el id es autogenerado
                // Add calle to callles
            	calles.add(calle);
            } while (cursor.moveToNext());
        }
        
		Log.d("getAllCalles()", calles.toString());

        // return books
        return calles;
    }
}