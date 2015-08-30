package es.unex.pbd.pokenot;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

	public final static String TABLE_NAME = "pokenots";
	public final static String TABLE_NAME_2 = "cazados";
	public final static String TABLE_NAME_3 = "tipos";

	final static String POKENOT_NOMBRE = "nombre";
	final static String POKENOT_ALIAS = "alias";
	final static String POKENOT_TIPO = "tipo";
	final static String POKENOT_FOTO = "foto";
	final static String POKENOT_PESO = "peso";
	final static String POKENOT_ALTURA = "altura";
	final static String POKENOT_SEXO = "sexo";
	final static String TIPO_NOMBRE = "nombre";

	final static String _ID = "_id";

	public final static String[] columns = { _ID, POKENOT_NOMBRE, POKENOT_TIPO,
			POKENOT_FOTO, POKENOT_PESO, POKENOT_ALTURA };

	public final static String[] columns2 = { _ID, POKENOT_NOMBRE,
			POKENOT_ALIAS, POKENOT_FOTO, POKENOT_SEXO };

	public final static String[] columns3 = { _ID, TIPO_NOMBRE };

	final private static String CREATE_CMD_1 =

	"CREATE TABLE pokenots (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ POKENOT_NOMBRE + " TEXT NOT NULL, " + POKENOT_TIPO + " TEXT, "
			+ POKENOT_FOTO + " TEXT, " + POKENOT_PESO + " TEXT, "
			+ POKENOT_ALTURA + " TEXT )";

	final private static String CREATE_CMD_2 =

	"CREATE TABLE cazados (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ POKENOT_NOMBRE + " TEXT NOT NULL, " + POKENOT_ALIAS
			+ " TEXT NOT NULL, " + POKENOT_FOTO + " TEXT NOT NULL, "
			+ POKENOT_SEXO + " TEXT NOT NULL)";

	final private static String CREATE_CMD_3 =

	"CREATE TABLE tipos (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ TIPO_NOMBRE + " TEXT NOT NULL )";

	final private static String NAME = "pokenot_db";// sera el nombre de nuestro
													// archivo de base de datos
	final private static Integer VERSION = 1; // la versión de nuestra base de
												// datos
	final private Context mContext;

	public DatabaseOpenHelper(Context context) {
		super(context, NAME, null, VERSION);
		this.mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// Nos pide como parametro una unica sentencia SQL que no sea un SELECT
		// o cualquier otra sentencia que devuelva datos.
		db.execSQL(CREATE_CMD_1);
		ContentValues values = new ContentValues();
		values.put(POKENOT_NOMBRE, "Pikachu");
		values.put(POKENOT_TIPO, "ELECTRICO");
		values.put(POKENOT_FOTO, "pikachu"); // id del recurso .PNG
		values.put(POKENOT_PESO, "6");
		values.put(POKENOT_ALTURA, "0.40");
		db.insert(TABLE_NAME, null, values);
		values.clear();

		values.put(POKENOT_NOMBRE, "Jigglypuff");
		values.put(POKENOT_TIPO, "NORMAL");
		values.put(POKENOT_FOTO, "jigglypuff"); // id del recurso .PNG
		values.put(POKENOT_PESO, "5.50");
		values.put(POKENOT_ALTURA, "0.50");
		db.insert(TABLE_NAME, null, values);
		values.clear();

		values.put(POKENOT_NOMBRE, "Pichu");
		values.put(POKENOT_TIPO, "ELECTRICO");
		values.put(POKENOT_FOTO, "pichu"); // id del recurso .PNG
		values.put(POKENOT_PESO, "2.00");
		values.put(POKENOT_ALTURA, "0.30");
		db.insert(TABLE_NAME, null, values);
		values.clear();

		values.put(POKENOT_NOMBRE, "Eevee");
		values.put(POKENOT_TIPO, "NORMAL");
		values.put(POKENOT_FOTO, "eevee"); // id del recurso .PNG
		values.put(POKENOT_PESO, "6.50");
		values.put(POKENOT_ALTURA, "0.30");
		db.insert(TABLE_NAME, null, values);
		values.clear();

		values.put(POKENOT_NOMBRE, "Piplup");
		values.put(POKENOT_TIPO, "AGUA");
		values.put(POKENOT_FOTO, "piplup"); // id del recurso .PNG
		values.put(POKENOT_PESO, "5.20");
		values.put(POKENOT_ALTURA, "0.40");
		db.insert(TABLE_NAME, null, values);
		values.clear();

		values.put(POKENOT_NOMBRE, "Litwick");
		values.put(POKENOT_TIPO, "FANTASMA");
		values.put(POKENOT_FOTO, "litwick"); // id del recurso .PNG
		values.put(POKENOT_PESO, "3.10");
		values.put(POKENOT_ALTURA, "0.30");
		db.insert(TABLE_NAME, null, values);
		values.clear();

		values.put(POKENOT_NOMBRE, "Cubchoo");
		values.put(POKENOT_TIPO, "HIELO");
		values.put(POKENOT_FOTO, "cubchoo"); // id del recurso .PNG
		values.put(POKENOT_PESO, "8.50");
		values.put(POKENOT_ALTURA, "0.50");
		db.insert(TABLE_NAME, null, values);
		values.clear();

		// -----------TABLA CAZADOS-----------------
		db.execSQL(CREATE_CMD_2);

		// -----------TABLA TIPOS-----------------
		db.execSQL(CREATE_CMD_3);
		PokenotItem.Tipo.values();

		for (int i = 0; i < PokenotItem.Tipo.values().length; i++) {
			values.put(TIPO_NOMBRE, PokenotItem.Tipo.values()[i].toString());

		}
		db.insert(TABLE_NAME_3, null, values);
		values.clear();

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// borramos las tablas si existen y creamos unas nuevas.
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_3);

		onCreate(db);
	}

	void deleteDatabase() {
		mContext.deleteDatabase(NAME);
	}

	public void insertarPokenot(PokenotItem pokenot) {
		SQLiteDatabase db = getWritableDatabase();
		if (db != null) {
			ContentValues valores = new ContentValues();

			valores.put(POKENOT_NOMBRE, pokenot.getNombre());
			valores.put(POKENOT_ALIAS, pokenot.getAlias());
			valores.put(POKENOT_FOTO, pokenot.getFoto());
			valores.put(POKENOT_SEXO, pokenot.getSexo().toString());
			db.insert(TABLE_NAME_2, null, valores);
			valores.clear();
			db.close();
		}
	}

	public void modificarPokenot(String oldAlias, String newAlias) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues valores = new ContentValues();
		valores.put(POKENOT_ALIAS, newAlias);
		db.update(TABLE_NAME_2, valores, "alias='" + oldAlias + "'", null);
		db.close();
	}

	public void borrarPokenot(String alias) {
		SQLiteDatabase db = getWritableDatabase();
		db.delete(TABLE_NAME_2, "alias='" + alias + "'", null);
		db.close();
	}
}
