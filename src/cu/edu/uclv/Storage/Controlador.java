package cu.edu.uclv.Storage;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;
import cu.edu.uclv.ModelItem.Item;
import cu.edu.uclv.ModelItem.ItemAdapter;
import cu.edu.uclv.ModelItem.ItemCalendario;
import cu.edu.uclv.ModelItem.ItemNoticia;
import cu.edu.uclv.ModelItem.ItemResult;
import cu.edu.uclv.footballresult.R;

public class Controlador {
	// Base de dato
	private StorageSQLiteHelper storageSQLiteHelper;
	private SQLiteDatabase dataBase;
	private Cursor cursor;
	private int lastNoticia;
	private int lastGame;
	private int lastResult;
	private Context context;

	ArrayList<Item> listNoticias;
	ArrayList<Item> listCalendario;
	ArrayList<Item> listResult;

	public Controlador(Context context, int liga) {
		this.context = context;
			storageSQLiteHelper = new StorageSQLiteHelper(context,
					"LigaEspannola", null, 1, 0);
			LastNoticia();
			LastResult();
			LastGame();
		listNoticias = new ArrayList<Item>();
		listCalendario = new ArrayList<Item>();
		listResult = new ArrayList<Item>();
	}

	/************************************************************* Noticias ***********************************************************/
	public ArrayList<Item> getNoticias() {
		listNoticias = new ArrayList<Item>();
		obtenerNoticias();
		return this.listNoticias;
	}

	public void obtenerNoticias() {
		dataBase = storageSQLiteHelper.getWritableDatabase();
		ItemNoticia itemNoticia;
		int id;
		String imagen, titulo, noticia;
		if (dataBase != null) {
			cursor = dataBase
					.rawQuery(
							"SELECT id, imagen, titulo, noticia FROM Noticias id ORDER BY 1 DESC",
							null);
			if (cursor.moveToFirst()) {
				// Recorremos el cursor hasta que no haya más registros
				do {
					id = cursor.getInt(0);
					imagen = cursor.getString(1);
					titulo = cursor.getString(2);
					noticia = cursor.getString(3);

					itemNoticia = new ItemNoticia(id, titulo, imagen, noticia,
							100, 100, 500);
					listNoticias.add(itemNoticia);

				} while (cursor.moveToNext());
			}
			dataBase.close();
		}
	}

	public boolean addNoticia(ArrayList<Item> addListNoticia) {
		dataBase = storageSQLiteHelper.getWritableDatabase();
		int id;
		String imagen, titulo, noticia;
		if (dataBase != null) {
			for (Item item : addListNoticia) {
				id = item.getId();
				if (lastNoticia == 0 || lastNoticia < id) {
					imagen = ((ItemNoticia) item).getRutaImagen();
					titulo = ((ItemNoticia) item).getNombre();
					noticia = ((ItemNoticia) item).getNoticia();
					dataBase.execSQL("INSERT INTO Noticias (id,imagen, titulo, noticia) "
							+ "VALUES ("
							+id+ 
							", '"
							+ imagen
							+ "', '"
							+ titulo
							+ "', '"
							+ noticia + "')");
					this.lastNoticia = id;
				}
			}
			dataBase.close();
		}
		return false;
	}

	public void LastNoticia() {
		dataBase = storageSQLiteHelper.getWritableDatabase();
		int id;
		if (dataBase != null) {
			cursor = dataBase.rawQuery("SELECT MAX(id) FROM Noticias ", null);
			if (cursor.moveToFirst()) {
				id = cursor.getInt(0);
				this.lastNoticia = id;
			} else {
				this.lastNoticia = 0;
			}
		}
		dataBase.close();
	}

	public int getLastNoticia() {
		return this.lastNoticia;
	}
	
	public void removeNoticia(int id, ItemAdapter adapter, int position){
		if (id != lastNoticia){
			dataBase = storageSQLiteHelper.getWritableDatabase();
			if (dataBase != null){
					   	dataBase.execSQL("DELETE FROM Noticias WHERE id ="+ id);
					   	adapter.remove(position);
					   	adapter.notifyDataSetChanged();
					}
				dataBase.close();
			}else{
				Toast toast1 = Toast.makeText(context, "No se puede eliminar la última noticia", Toast.LENGTH_SHORT);        
				 toast1.show();
			}
	}

	/********************************************************* End Noticias *********************************************************/
	/********************************************************* Calendario de los Juegos *********************************************/
	public int getLastGame(){
		return this.lastGame;
	}
	public void LastGame() {
		dataBase = storageSQLiteHelper.getWritableDatabase();
		int id;
		if (dataBase != null) {
			cursor = dataBase.rawQuery("SELECT MAX(id) FROM Calendario ", null);
			if (cursor.moveToFirst()) {
				id = cursor.getInt(0);
				this.lastGame = id;
			} else {
				this.lastGame = 0;
			}
		}
		dataBase.close();
	}

	public boolean addGames(ArrayList<Item> addListGame) {
		dataBase = storageSQLiteHelper.getWritableDatabase();
		int id;
		String eHomeClub, eVisitor, hora, fecha;
		if (dataBase != null) {
			for (Item item : addListGame) {
				id = item.getId();
				if (lastGame == 0 || lastGame < id) {
					eHomeClub = ((ItemCalendario) item).getNombre();
					eVisitor = ((ItemCalendario) item).getNombre2();
					hora = ((ItemCalendario) item).getHora();
					fecha = ((ItemCalendario) item).getFecha();
					dataBase.execSQL("INSERT INTO Calendario (id,equipVist,equipHClub, hora, fecha) "
							+ "VALUES ("
							+id
							+",'"
							+ eVisitor
							+ "', '"
							+ eHomeClub
							+ "', '" + hora + "', '" + fecha + "')");
					this.lastGame = id;
				}
			}
			dataBase.close();
		}
		return false;
	}


	public ArrayList<Item> getCalendario() {
		listCalendario = new ArrayList<Item>();
		obtenerCalendario();
		return this.listCalendario;
	}

	public void obtenerCalendario() {
		dataBase = storageSQLiteHelper.getWritableDatabase();
		ItemCalendario itemCalendario;
		int id;
		String eVisitor, eHomeClub, hora, fecha;
		int rutaImagenV, rutaImagenHC;
		if (dataBase != null) {
			cursor = dataBase
					.rawQuery(
							"SELECT id, equipVist, equipHClub, hora, fecha FROM Calendario id ORDER BY 1 DESC",
							null);
			if (cursor.moveToFirst()) {
				// Recorremos el cursor hasta que no haya más registros
				do {
					id = cursor.getInt(0);
					eVisitor = cursor.getString(1);
					eHomeClub = cursor.getString(2);
					hora = cursor.getString(3);
					fecha = cursor.getString(4);

					rutaImagenHC = findRuta(eHomeClub);
					rutaImagenV = findRuta(eVisitor);

					itemCalendario = new ItemCalendario(id, eVisitor,
							eHomeClub, rutaImagenV, rutaImagenHC, hora, fecha);
					listCalendario.add(itemCalendario);
				} while (cursor.moveToNext());
			}
			dataBase.close();
		}
	}

	public int findRuta(String image) {
		if (image.equals("Barca"))
			return R.drawable.barca;
		else if (image.equals("Almeria"))
			return R.drawable.almeria;
		else if (image.equals("Athletic"))
			return R.drawable.athletic;
		else if (image.equals("Atletico"))
			return R.drawable.atletico;
		else if (image.equals("Betis"))
			return R.drawable.betis;
		else if (image.equals("Granada"))
			return R.drawable.granada;
		else if (image.equals("Celta"))
			return R.drawable.celta;
		else if (image.equals("Elche"))
			return R.drawable.elche;
		else if (image.equals("Español"))
			return R.drawable.espanol;
		else if (image.equals("Getafe"))
			return R.drawable.getafe;
		else if (image.equals("Levante"))
			return R.drawable.levante;
		else if (image.equals("Malaga"))
			return R.drawable.malaga;
		else if (image.equals("Osasuna"))
			return R.drawable.osasuna;
		else if (image.equals("Rayo"))
			return R.drawable.rayo;
		else if (image.equals("Real Madrid"))
			return R.drawable.realmadrid;
		else if (image.equals("Real Sociedad"))
			return R.drawable.realsociedad;
		else if (image.equals("Sevilla"))
			return R.drawable.sevilla;
		else if (image.equals("Valencia"))
			return R.drawable.valencia;
		else if (image.equals("Valladolid"))
			return R.drawable.valladolid;
		else
			return R.drawable.villareal;
	}
	
	public void removeCalendario(int id, ItemAdapter adapter, int position){
		if (id != lastGame){
			dataBase = storageSQLiteHelper.getWritableDatabase();
			if (dataBase != null){
					   	dataBase.execSQL("DELETE FROM Calendario WHERE id ="+ id);
					   	adapter.remove(position);
					   	adapter.notifyDataSetChanged();
					}
				dataBase.close();
			}else{
				Toast toast1 = Toast.makeText(context, "No se puede eliminar el último juego", Toast.LENGTH_SHORT);        
				 toast1.show();
			}
	}


	/*********************************************** END CALENDARIOS ****************************************************************/
	/******************************************** Resultados de los Juegos *********************************************************/

	public void LastResult() {
		dataBase = storageSQLiteHelper.getWritableDatabase();
		int id;
		if (dataBase != null) {
			cursor = dataBase.rawQuery("SELECT MAX(id) FROM Resultados ", null);
			if (cursor.moveToFirst()) {
				id = cursor.getInt(0);
				this.lastResult = id;
			} else {
				this.lastResult = 0;
			}
		}
		dataBase.close();
	}

	public boolean addResult(ArrayList<Item> addListResult) {
		dataBase = storageSQLiteHelper.getWritableDatabase();
		int id;
		String eHomeClub, eVisitor, golEHC, golEV;
		int cantGEHC, cantGEV;
		if (dataBase != null) {
			for (Item item : addListResult) {
				id = item.getId();
				if (lastResult == 0 || lastResult < id) {
					eHomeClub = ((ItemResult) item).getNombre();
					eVisitor = ((ItemResult) item).getNombre2();
					golEHC = ((ItemResult) item).getGolPEHC();
					golEV = ((ItemResult) item).getGolPEV();
					cantGEHC = ((ItemResult) item).getGolNombre();
					cantGEV = ((ItemResult) item).getGolNombre2();

					dataBase.execSQL("INSERT INTO Resultados (id,equipVist, equipHClub, cantGEV, cantGEHC, golEV, golEHC) "
							+ "VALUES ("
							+id
							+",'"
							+ eVisitor
							+ "', '"
							+ eHomeClub
							+ "','"
							+ cantGEV
							+ "', '"
							+ cantGEHC
							+ "','"
							+ golEHC + "', '" + golEV + "')");
					this.lastResult = id;
				}
			}
			dataBase.close();
		}
		return false;
	}

	public ArrayList<Item> getResult() {
		listResult = new ArrayList<Item>();
		obtenerResult();
		return this.listResult;
	}

	public void obtenerResult() {
		dataBase = storageSQLiteHelper.getWritableDatabase();
		ItemResult itemResult;
		int id;
		String eVisitor, eHomeClub, golEV, golEHC;
		int cantGEV, cantGEHC;
		int rutaImagenEV, rutaImagenHC;
		ArrayList<String> nombreGEV = new ArrayList<String>(); // Separar los
																// nombres que
																// colaron gol
																// en el equipo
																// visitante
		ArrayList<String> nombreGEHC = new ArrayList<String>();
		; // Goles que corresponden a los hombres del equipo visitante
		ArrayList<String> cantNGEV = new ArrayList<String>();
		; // Separar los nombres que colaron gol en el equipo HomeClub
		ArrayList<String> cantNGEHC = new ArrayList<String>();
		; // Goles que corresponden a los hombres del equipo HomeClub
		String[] temp;
		if (dataBase != null) {
			cursor = dataBase
					.rawQuery(
							"SELECT id, equipVist, equipHClub, cantGEV, cantGEHC, golEV, golEHC FROM Resultados id ORDER BY 1 DESC",
							null);
			if (cursor.moveToFirst()) {
				// Recorremos el cursor hasta que no haya más registros
				do {
					id = cursor.getInt(0);
					eVisitor = cursor.getString(1);
					eHomeClub = cursor.getString(2);
					cantGEV = cursor.getInt(3);
					cantGEHC = cursor.getInt(4);
					golEV = cursor.getString(5);
					if (!golEV.equals("")) {
						temp = golEV.split(",");
						for (int i = 0; i < temp.length; i += 2) {
							nombreGEV.add(temp[i]);
							cantNGEV.add(temp[i + 1]);
						}
					}
					golEHC = cursor.getString(6);
					if (!golEHC.equals("")) {
						temp = golEHC.split(",");
						for (int i = 0; i < temp.length; i += 2) {
							nombreGEV.add(temp[i]);
							cantNGEV.add(temp[i + 1]);
						}
					}

					rutaImagenHC = findRuta(eHomeClub);
					rutaImagenEV = findRuta(eVisitor);
					itemResult = new ItemResult(id, eHomeClub, eVisitor,
							cantGEHC, cantGEV, nombreGEHC, nombreGEV,
							cantNGEHC, cantNGEV, rutaImagenHC, rutaImagenEV
							,100,100,cantNGEHC.size()*20+cantNGEV.size()*20+ 105);
					listResult.add(itemResult);

				} while (cursor.moveToNext());
			}
			dataBase.close();
		}
	}

	public int getLastResult() {
		return this.lastResult;
	}
	
	public void removeResult(int id, ItemAdapter adapter, int position){
		if (id != lastResult){
			dataBase = storageSQLiteHelper.getWritableDatabase();
			if (dataBase != null){
					   	dataBase.execSQL("DELETE FROM Resultados WHERE id ="+ id);
					   	adapter.remove(position);
					   	adapter.notifyDataSetChanged();
					}
				dataBase.close();
			}else{
				Toast toast1 = Toast.makeText(context, "No se puede eliminar el último resultado", Toast.LENGTH_SHORT);        
				 toast1.show();
			}
	}


	/************************************************* END RESULTADOS **************************************************************/
}
