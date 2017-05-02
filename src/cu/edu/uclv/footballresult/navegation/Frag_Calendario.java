package cu.edu.uclv.footballresult.navegation;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import cu.edu.uclv.ModelItem.Item;
import cu.edu.uclv.ModelItem.ItemAdapter;
import cu.edu.uclv.ModelItem.ItemCalendario;
import cu.edu.uclv.Storage.Controlador;
import cu.edu.uclv.footballresult.R;

@SuppressLint("ValidFragment")
public class Frag_Calendario extends Fragment{
	ListView lsCalendario;
	private ArrayList<Item> listItem;
	ItemAdapter adapter;
	Controlador control;
	
	
	public Frag_Calendario(ArrayList<Item> listItem, Controlador control){
		this.listItem = listItem;
		this.control = control;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.frag_calendario, container, false);
	}
	
	@Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        if (listItem != null){
        	lsCalendario = (ListView)getActivity().findViewById(R.id.ListCalendario);
        	registerForContextMenu(lsCalendario);
        	actualizarListaLiga();
        }
    }
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo)
	{
	    super.onCreateContextMenu(menu, v, menuInfo);
	    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
	    menu.setHeaderTitle("Borrar Juego: "+ ((ItemCalendario)lsCalendario.getAdapter().getItem(info.position)).getNombre()
	    										+" VS "+((ItemCalendario)lsCalendario.getAdapter().getItem(info.position)).getNombre2());
	    MenuInflater inflater = getActivity().getMenuInflater();    
	    inflater.inflate(R.menu.menu_contextual, menu);
	
	}
	
	
		@Override
	public boolean onContextItemSelected(MenuItem item) {
	 
	    switch (item.getItemId()) {
	        case R.id.CtxBorrar:
	        	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	        	ItemCalendario tmp = (ItemCalendario)adapter.getItem(info.position);
	        	control.removeCalendario(tmp.getId(), adapter, info.position);
	            return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
	}
 
	
	 public void actualizarListaLiga(){
		    ListView lv = (ListView)getActivity().findViewById(R.id.ListCalendario);  
		    adapter = new ItemAdapter(this, listItem, R.layout.list_item_calendario);
		    lv.setAdapter(adapter);
	 }
}
