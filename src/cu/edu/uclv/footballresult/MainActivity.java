package cu.edu.uclv.footballresult;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import cu.edu.uclv.ModelItem.Item;
import cu.edu.uclv.ModelItem.ItemAdapter;
import cu.edu.uclv.ModelItem.ItemLiga;

public class MainActivity extends Activity {
	private ArrayList<Item> listItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		actualizarListaLiga();  
	}

	
	 private ArrayList<Item> obtenerItems() {
		    ArrayList<Item> items = new ArrayList<Item>();
		         
		    items.add(new ItemLiga(0, "BBVA", "drawable/bbva", "Liga Española"));
		    items.add(new ItemLiga(1, "BundesLiga", "drawable/bundesliga", "Liga Alemana"));
		    items.add(new ItemLiga(2, "Eredivisie", "drawable/holandesa", "Liga Holandesa"));
		    items.add(new ItemLiga(3, "Premier League", "drawable/inglesa", "Liga Inglesa"));
		    items.add(new ItemLiga(4, "Serie A", "drawable/italiana", "Liga Italiana"));
		    items.add(new ItemLiga(5, "Ligue 1", "drawable/ligafrancesa", "Liga Francesa"));
		    items.add(new ItemLiga(6, "Liga portugal", "drawable/portugal", "Liga Portuguesa"));
		    items.add(new ItemLiga(7, "Champions", "drawable/champions", "Champions League"));
		    items.add(new ItemLiga(8, "Europa", "drawable/euroliga", "Europa League"));
		         
		    return items;
    }
	 
	 public void actualizarListaLiga(){
		 ListView lv = (ListView)findViewById(R.id.listView);
	        lv.setOnItemClickListener(new OnItemClickListener() {
	        	@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
	        		if (position == 0){
	        			Intent intent = new Intent(MainActivity.this, Navegation_Activity.class);
	        			Bundle bundle = new Bundle();
	        			bundle.putInt("LIGA", position);
	        			intent.putExtras(bundle);
	        			startActivity(intent);
	        		}else{
	        			Toast toast1 = Toast.makeText(MainActivity.this, "Aun no disponible", Toast.LENGTH_SHORT);        
						 toast1.show(); 
	        		}
	        	}
			});
			listItem = obtenerItems();
		         
		    ItemAdapter adapter = new ItemAdapter(this, listItem, R.layout.list_item_liga);
		    lv.setAdapter(adapter);  
	 }
	 

	 
	 

}
