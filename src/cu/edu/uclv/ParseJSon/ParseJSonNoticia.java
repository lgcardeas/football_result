package cu.edu.uclv.ParseJSon;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import cu.edu.uclv.ModelItem.Item;
import cu.edu.uclv.ModelItem.ItemCalendario;
import cu.edu.uclv.ModelItem.ItemNoticia;
import cu.edu.uclv.ModelItem.ItemResult;

public class ParseJSonNoticia {
	public static ArrayList<Item> getNoticiasJSon(String result, int lastID)
			throws JSONException {
		// The return ArrayList
		ArrayList<Item> lsNoticias = new ArrayList<Item>();
		// Working with JSON result
		String title;
		String body;
		String imagen = "";
		String imagen_name = "";
		int id;
		JSONObject noticia_jsonObject;
		JSONArray jsonArray = new JSONArray(result);
		for (int i = 0; i < jsonArray.length(); i++){
			noticia_jsonObject = jsonArray.getJSONObject(i);
			id = noticia_jsonObject.getInt("id");
			title = noticia_jsonObject.getString("title");
			body = noticia_jsonObject.getString("body");
			imagen = noticia_jsonObject.getString("imagen");
			imagen_name = noticia_jsonObject.getString("imagen_name");
			if (lastID < id){
				if (!imagen_name.equals(""))
					imagen = saveImagen(imagen, imagen_name);
				lsNoticias.add(new ItemNoticia(id, title, imagen, body, 100, 100, 500));
			}
		}
        return lsNoticias;
    }
	
	public static String saveImagen(String ruta, String imagen_name){
		String imageHttpAddress="http://192.168.49.50/FootballResult/LigaEspannola/"+ruta+"/"+imagen_name;
		HttpURLConnection conn = null;
		try {
			URL imageUrl = new URL(imageHttpAddress);
			conn = (HttpURLConnection) imageUrl.openConnection();
			Bitmap loadedImage = null;
		      if (conn != null){
		                // Si hay conexión, conectamos y recogemos la imagen.
		                conn.connect();
		                conn.setConnectTimeout(30000);
		                conn.setReadTimeout(30000);
		                loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
		                
		                File ruta_sd = new File(Environment.getExternalStorageDirectory(), "ImagenesFootbal");
		                if (!ruta_sd.exists())
		                	ruta_sd.mkdir();
		                OutputStream out = new FileOutputStream(ruta_sd.getPath()+"/"+imagen_name);
		                loadedImage.compress(Bitmap.CompressFormat.JPEG, 100, out);
		                return ruta_sd+"/"+imagen_name;
		            }
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			return "";
		}finally{
			if (conn != null){
				conn.disconnect();
			}
		}
		return "";
	}
	
	/******************************************END ParseJsonNoticias****************************************************/
	/******************************************ParseJsonCalendario
	 * @param lastID *******************************************************/
	public static ArrayList<Item> getCalendarioJSon(String result, int lastID)
			throws JSONException {
		Log.i("ID",""+lastID);
		// The return ArrayList
		ArrayList<Item> lsCalendario = new ArrayList<Item>();
		// Working with JSON result
		int id;
		String eHomeClub = "";
		String eVisitor = "";
		String hora = "";
		String fecha = "";
		JSONObject noticia_jsonObject;
		JSONArray jsonArray = new JSONArray(result);
		for (int i = 0; i < jsonArray.length(); i++){
			noticia_jsonObject = jsonArray.getJSONObject(i);
			id = noticia_jsonObject.getInt("id");
			eHomeClub = noticia_jsonObject.getString("eHomeClub");
			eVisitor = noticia_jsonObject.getString("eVisitor");
			fecha = noticia_jsonObject.getString("fecha");
			hora = noticia_jsonObject.getString("hora");
			if (lastID < id)
				lsCalendario.add(new ItemCalendario(id, eHomeClub, eVisitor, 0, 0, hora, fecha));
		}
        return lsCalendario;
    }


/****************************************END Parse Calendario JSon******************************************************/
/****************************************Parse Json Result*************************************************************/
	public static ArrayList<Item> getResultJSon(String result, int lastID)
			throws JSONException {
		// The return ArrayList
		ArrayList<Item> lsResult = new ArrayList<Item>();
		// Working with JSON result
		int id;
		String eHomeClub = "";
		String eVisitor = "";
		int golEHC = 0;
		int golEV = 0;
		String golPEHC = "";
		String golPEV = "";
		JSONObject noticia_jsonObject;
		JSONArray jsonArray = new JSONArray(result);
		for (int i = 0; i < jsonArray.length(); i++){
			noticia_jsonObject = jsonArray.getJSONObject(i);
			id = noticia_jsonObject.getInt("id");
			eHomeClub = noticia_jsonObject.getString("eHomeClub");
			eVisitor = noticia_jsonObject.getString("eVisitor");
			golEHC = noticia_jsonObject.getInt("golEHC");
			golEV = noticia_jsonObject.getInt("golEV");
			golPEHC = noticia_jsonObject.getString("golPEHC");
			golPEV = noticia_jsonObject.getString("golPEV");
			if (lastID < id)
				lsResult.add(new ItemResult(id, eHomeClub, eVisitor, golEHC, golEV,golPEHC, golPEV));
		}
        return lsResult;
    }

/****************************************END Parse Rsult JSon******************************************************/
}