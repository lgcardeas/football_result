package cu.edu.uclv.Storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class StorageSQLiteHelper extends SQLiteOpenHelper{
	private int TipoLiga;
	private String sqlCreateCalendario;
	private String sqlCreateResultados;
	private String sqlCreateNoticia;
	
	public StorageSQLiteHelper(Context context, String name,
			CursorFactory factory, int version, int TipoLiga) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		this.TipoLiga = TipoLiga;
		switch(TipoLiga){
			case (0):
				sqlCreateNoticia = "CREATE TABLE Noticias (id INTEGER PRIMARY KEY , imagen TEXT, titulo TEXT, noticia TEXT)";
				sqlCreateResultados = "CREATE TABLE Resultados (id INTEGER PRIMARY KEY , equipVist TEXT, equipHClub TEXT, cantGEV TEXT, cantGEHC TEXT, golEV TEXT, golEHC TEXT)";
				sqlCreateCalendario = "CREATE TABLE Calendario (id INTEGER PRIMARY KEY , equipVist TEXT, equipHClub TEXT, hora TEXT, fecha TEXT)";
				break;
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		switch(TipoLiga){
		case (0):
				db.execSQL(this.sqlCreateNoticia);
				db.execSQL(this.sqlCreateResultados);
				db.execSQL(this.sqlCreateCalendario);
				break;
		}
	}
	
	

	@Override
	public void onUpgrade(SQLiteDatabase db, int lastVersion, int newVersion) {
		// TODO Auto-generated method stu
			db.execSQL("DROP TABLE IF EXISTS Noticias");
			db.execSQL("DROP TABLE IF EXISTS Resultados");
			db.execSQL("DROP TABLE IF EXISTS Calendario");
			//Se crea de nuevo la nueva version de la tabla
			db.execSQL(this.sqlCreateNoticia);
			db.execSQL(this.sqlCreateResultados);
			db.execSQL(this.sqlCreateCalendario);
		
	}

}
