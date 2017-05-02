package cu.edu.uclv.footballresult;

import java.util.ArrayList;


import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import cu.edu.uclv.ModelItem.Item;
import cu.edu.uclv.ParseJSon.ReadJSonNoticia;
import cu.edu.uclv.Storage.Controlador;
import cu.edu.uclv.footballresult.navegation.Frag_Calendario;
import cu.edu.uclv.footballresult.navegation.Frag_Noticas;
import cu.edu.uclv.footballresult.navegation.Frag_Result;
import cu.edu.uclv.footballresult.navegation.Navegation_Listener;

public class Navegation_Activity extends Activity {
	private Controlador control;
	private ArrayList<Item> listNoticias;
	private ArrayList<Item> listCalendario;
	private ArrayList<Item> listResult;
	
	Fragment noticia;
	Fragment result ;
	Fragment calender ;
	ActionBar actionBar;
	
	ReadJSonNoticia Json;
	ProgressDialog progress;
	Handler handler;
	Thread thread;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handler = new Handler();//Para mostrar los resultados
		Bundle bundle = getIntent().getExtras();
		int code = bundle.getInt("LIGA");
		control = new Controlador(Navegation_Activity.this, 0);
		runThread();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.navegation, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
			case  R.id.action_settings:
				item.setIcon(R.drawable.actualizando);
				runThread();
				item.setIcon(R.drawable.actualizar);
				return true;
			default:
				return  super.onOptionsItemSelected(item);
		}
	}
	
	
	
	public void runThread(){
    	 thread = new Thread(){
    		public void run(){
					cargarDatos();
	    			handler.post(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							inicializar();
						}
					});
    		}
    		
    		public void cargarDatos(){
    			listNoticias = (ArrayList<Item>)control.getNoticias();
    			listCalendario = (ArrayList<Item>)control.getCalendario();
    			listResult = (ArrayList<Item>)control.getResult();
    		}
    		
    		public void inicializar(){
    			//Obtener una referencia a la ActionBar
    			if (actionBar != null)
    				actionBar.removeAllTabs();
    			actionBar = getActionBar();
    			
    			//Establecer el modo de navegacion por pestannas
    			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    			
    			//Para Ocultar el titulo de la Actividad, por si da bateo
    			//actionBar.setDisplayShowTitleEnabled(false);
    			
    			//Creamos las pertannas que vamos a usar
    			ActionBar.Tab tab1 = actionBar.newTab().setText("Noticias");
    			ActionBar.Tab tab2 = actionBar.newTab().setText("Resultados");
    			ActionBar.Tab tab3 = actionBar.newTab().setText("Calendario");
    			
    			
    			//Creamos los fragmentos de cada pestanna
    			if(noticia != null && result!=null && calender!=null){
    				
    				noticia.onDestroyView();
    				result.onDestroyView();
    				calender.onDestroyView();
    			}
    			
    			 noticia = new Frag_Noticas(listNoticias, control);
    			 result = new Frag_Result(listResult, control);
    			 calender = new Frag_Calendario(listCalendario, control);
    			
    			//Asociamos los listener a las pestannas
    			tab1.setTabListener(new Navegation_Listener(noticia));
    			tab2.setTabListener(new Navegation_Listener(result));
    			tab3.setTabListener(new Navegation_Listener(calender));
    			
    			
    		//Annadimos las pestannas al ActionBar
    			actionBar.addTab(tab1);
    			actionBar.addTab(tab2);
    			actionBar.addTab(tab3);
    			
    		}
    	};
    	try {
			//Json = (ReadJSonNoticia) new ReadJSonNoticia(Navegation_Activity.this, 0, control, thread)
			//															.execute("http://10.0.2.2/FootballResult/LigaEspannola/Noticias.json",
			//																	"http://10.0.2.2/FootballResult/LigaEspannola/Calendario.json",
    		//																	"http://10.0.2.2/FootballResult/LigaEspannola/Result.json");
			Json = (ReadJSonNoticia) new ReadJSonNoticia(Navegation_Activity.this, 0, control, thread)
															.execute("http://192.168.49.50/FootballResult/LigaEspannola/Noticias.json",
																	"http://192.168.49.50/FootballResult/LigaEspannola/Calendario.json",
																	"http://192.168.49.50/FootballResult/LigaEspannola/Result.json");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}
	
	
	
	
}
