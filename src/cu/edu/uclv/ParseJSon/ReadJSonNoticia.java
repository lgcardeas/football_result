package cu.edu.uclv.ParseJSon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.util.Log;
import android.widget.Toast;
import cu.edu.uclv.ModelItem.Item;
import cu.edu.uclv.Storage.Controlador;
import cu.edu.uclv.footballresult.MainActivity;
import cu.edu.uclv.footballresult.Navegation_Activity;

public class ReadJSonNoticia extends AsyncTask<String, Void, String> {

	public static String json;
	private Context context;
	private Controlador control;
	private int tipoJSon; // 0-Noticia 1-Calendario 2-Resultados
	private boolean error;
	Thread thread;
	ProgressDialog progress;
	int lasNoticia;
	int lastGame;
	int lastResult;

	
	public ReadJSonNoticia(Context context, int tipoJSon, Controlador control,
			Thread thread) throws InterruptedException {
		this.context = context;
		this.tipoJSon = tipoJSon;
		this.control = control;
		progress = ProgressDialog.show(context, "Cargando Datos",
				"Espere mientras se cargan los datos del Servidor", true);
		this.thread = thread;
		error = false;
		lastResult = control.getLastResult();
		lastGame = control.getLastGame();
		lasNoticia  = control.getLastNoticia();
		

	}

	@SuppressWarnings("finally")
	@Override
	protected String doInBackground(String... urls) {
		String result = "";
		try {
			
			result = readJSONFeed(urls[0]); //JSon Noticias.json
			
			ArrayList<Item> listNoticias = ParseJSonNoticia
					.getNoticiasJSon(result, lasNoticia);
			
			result = readJSONFeed(urls[1]); //Json Calendario.json
			ArrayList<Item> listCalendario = ParseJSonNoticia
					.getCalendarioJSon(result, lastGame);
			
			result = readJSONFeed(urls[2]); //Json Result.json
			ArrayList<Item> listResutl = ParseJSonNoticia
					.getResultJSon(result, lastResult);
			
			control.addNoticia(listNoticias);
			control.addGames(listCalendario);
			control.addResult(listResutl);
		} catch (Exception e) {
			this.cancel(true);
		}finally{
			return result;
		}
	}



	protected void onPostExecute(String result) {
		progress.dismiss();
		this.thread.start();
	}
	
	protected void onCancelled(String result){
		progress.dismiss();
		this.thread.start();
		Toast toast1 = Toast.makeText(context, "Conexion no Disponible", Toast.LENGTH_SHORT);        
		 toast1.show(); 
	}

	public String readJSONFeed(String URL) {
		StringBuilder stringBuilder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(URL);
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					stringBuilder.append(line);
				}
			} else {
				Log.e("JSON", "Failed to download file");
			}
		} catch (ClientProtocolException e) {
			this.cancel(true);
		} catch (IOException e) {
			this.cancel(true);
		}
		return stringBuilder.toString();
	}
}
