package cu.edu.uclv.footballresult.navegation;


import java.util.ArrayList;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cu.edu.uclv.ModelItem.Item;
import cu.edu.uclv.ModelItem.ItemAdapter;
import cu.edu.uclv.ModelItem.ItemNoticia;
import cu.edu.uclv.ModelItem.Animation.ResizeAnimation;
import cu.edu.uclv.Storage.Controlador;
import cu.edu.uclv.footballresult.R;

@SuppressLint("ValidFragment")
public class Frag_Noticas extends Fragment{
	private ArrayList<Item> listItem;
	ListView lsNoticias;
	ItemAdapter adapter;
	Controlador control;
	
	public Frag_Noticas(ArrayList<Item> listItem, Controlador control){
		this.listItem = listItem;
		this.control = control;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.frag_noticias, container, false);
	}
	
	@Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        if (listItem != null){
        	lsNoticias = (ListView)getActivity().findViewById(R.id.ListNoticias);
        	registerForContextMenu(lsNoticias);
        	actualizarListaNoticia();
        }
    }
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo)
	{
	    super.onCreateContextMenu(menu, v, menuInfo);
	    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
	    menu.setHeaderTitle("Borrar Noticia: "+ ((ItemNoticia)lsNoticias.getAdapter().getItem(info.position)).getNombre());
	    MenuInflater inflater = getActivity().getMenuInflater();    
	    inflater.inflate(R.menu.menu_contextual, menu);
	
	}
	
	
		@Override
	public boolean onContextItemSelected(MenuItem item) {
	 
	    switch (item.getItemId()) {
	        case R.id.CtxBorrar:
	        	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	        	ItemNoticia tmp = (ItemNoticia)adapter.getItem(info.position);
	        	control.removeNoticia(tmp.getId(), adapter, info.position);
	            return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
	}
 
	
	 public void actualizarListaNoticia(){
		    
		    adapter = new ItemAdapter(this, listItem, R.layout.list_item_noticia);
		    lsNoticias.setAdapter(adapter);
		    lsNoticias.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					toggle(view, position);
					
				}
			});
	 }

	 private void toggle(View view, final int position) {
			ItemNoticia item = (ItemNoticia)listItem.get(position);

			int fromHeight = 0;
			int toHeight = 0;

			if (item.isOpen()) {
				fromHeight = item.getExpandedHeight();
				toHeight = item.getCollapsedHeight();
			} else {
				fromHeight = item.getCollapsedHeight();
				toHeight = item.getExpandedHeight();
			}

			toggleAnimation(item, position, fromHeight, toHeight, true, view);
		}

				private void toggleAnimation(final ItemNoticia item, final int position,
				final int fromHeight, final int toHeight, final boolean goToItem, View view) {

			ResizeAnimation resizeAnimation = new ResizeAnimation(adapter,
					item, 0, fromHeight, 0, toHeight, view);
			resizeAnimation.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
						 
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
					
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					
					item.setOpen(!item.isOpen());	
					item.setDrawable(item.isOpen() ? R.drawable.up
							: R.drawable.down);
					item.setCurrentHeight(toHeight);
					adapter.notifyDataSetChanged();		
				}
			});

			view.startAnimation(resizeAnimation);
		}

		
}
