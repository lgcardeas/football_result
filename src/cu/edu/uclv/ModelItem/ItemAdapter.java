package cu.edu.uclv.ModelItem;

import java.util.ArrayList;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cu.edu.uclv.footballresult.R;

public class ItemAdapter extends BaseAdapter{
	protected Activity activity;
	protected ArrayList<Item> items;
	protected int Tipo;
	         
	  public ItemAdapter(Activity activity, ArrayList<Item> items,int Tipo) {
	    this.activity = activity;
	    this.items = items;
	    this.Tipo = Tipo;
	  }
	  public ItemAdapter(Fragment activity, ArrayList<Item> items,int Tipo) {
		    this.activity = activity.getActivity();
		    this.items = items;
		    this.Tipo = Tipo;
		  }
	 
	
	@Override
	  public int getCount() {
	    return items.size();
	  }
	 
	  @Override
	  public Object getItem(int position) {
	    return items.get(position);
	  }
	 
	  @Override
	  public long getItemId(int position) {
	    return items.get(position).getId();
	  }
	 
	  @Override
	  public View getView(int position, View contentView, ViewGroup parent) {
		  if (contentView == null){
			  LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	      	  switch (Tipo){
	      		case R.layout.list_item_liga: 
	      			contentView = inflater.inflate(R.layout.list_item_liga, null);
	      			break;
	      		case R.layout.list_item_noticia:
	      			contentView = inflater.inflate(R.layout.list_item_noticia, null);
	      			break;
	      		case R.layout.list_item_calendario:
	      			contentView = inflater.inflate(R.layout.list_item_calendario, null);
	      			contentView = inflateListCalendario(contentView,position);
	      			break;
	      		case R.layout.list_item_result:
	      			contentView = inflater.inflate(R.layout.list_item_result, null);
	      			contentView = inflateListResult(contentView,position);
	      			break;
	      	}
		  }
			  switch (Tipo){
	      		case R.layout.list_item_liga:
	      			contentView = inflateListLiga(contentView,position);
	      			break;
	      		case R.layout.list_item_noticia:	
	      				contentView = inflateListNoticia(contentView,position);
	      			break;
	      		//case R.layout.list_item_calendario:
	      		//	contentView = inflateListCalendario(contentView,position);
	      		//	break;
	      		case R.layout.list_item_result:
	      			contentView = transformListResult(contentView,position);
	      			break;
			  }
		  
	    return contentView;
	  }
	
/*****************************Inflar la lista de las Ligas***********************************************************************/
	  protected View inflateListLiga(View v, int position){
		  Item item = items.get(position);
	        
		    ImageView image = (ImageView) v.findViewById(R.id.imagen);
		    int imageResource = activity.getResources().getIdentifier(((ItemLiga)item).getRutaImagen(), null, activity.getPackageName());
		    image.setImageDrawable(activity.getResources().getDrawable(imageResource));
		         
		    TextView nombre = (TextView) v.findViewById(R.id.nombre);
		    nombre.setText(item.getNombre());
		         
		    TextView tipo = (TextView) v.findViewById(R.id.description);
		    tipo.setText(((ItemLiga) item).getDescription());
		    
		    
		    return v;
	  }
	  /******************************END inflar la lista de las Ligas******************************************************************/
	  /*****************************Inflar la lista de las Noticias***********************************************************************/	  
	  protected View inflateListNoticia(View v, int position){
		  int sizeImage,sizeLayout;
		  Item item = items.get(position);
		  LinearLayout linerLayout = (LinearLayout) v.findViewById(R.id.item_noticia);
		  LinearLayout linerLayoutNoticia = (LinearLayout) v.findViewById(R.id.text_noticia);
		  TextView titulo_noticias = (TextView) v.findViewById(R.id.titulo_noticia);
		  TextView noticias = (TextView) v.findViewById(R.id.prev_noticia);
		  
		  ImageView image = (ImageView) v.findViewById(R.id.imagen_noticia);
		  
		  
		  sizeLayout = ((ItemNoticia)item).getCurrentHeight();
		  if (sizeLayout > 100 ){
			  sizeImage = sizeLayout/5 +100;
			  linerLayoutNoticia.getLayoutParams().height = sizeImage;
			  if (sizeLayout == 500)
				  linerLayoutNoticia.getLayoutParams().height = LayoutParams.WRAP_CONTENT;
		  }else{
			  sizeImage = 100;
			  linerLayoutNoticia.getLayoutParams().height = sizeImage;
		  }
		  image.getLayoutParams().height  = sizeImage;
		  image.getLayoutParams().width = sizeImage;
		  if (((ItemNoticia)item).getRutaImagen().equals("")){
			  int imageResource = activity.getResources().getIdentifier("drawable/code_error", null, activity.getPackageName());
		  	  image.setImageDrawable(activity.getResources().getDrawable(imageResource));
		  }else{
			  Bitmap bitmap = BitmapFactory.decodeFile(((ItemNoticia)item).getRutaImagen());
			  image.setImageBitmap(bitmap);
		  }
		  titulo_noticias.setText(((ItemNoticia) item).getNombre());
		  noticias.setText(((ItemNoticia) item).getNoticia());
		  noticias.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, ((ItemNoticia) item).getDrawable());
		  
		  LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		  linerLayout.setLayoutParams(layoutParams);
		  return v;
		
	  }
	  
	  public void remove(int position){
		  items.remove(position);
	  }
	  /*****************************END Inflar lista de Noticias*******************************************************************/
	  /*****************************Inflar la lista del Calendario***********************************************************************/
	  public View inflateListCalendario(View view, int position){
		  ImageView image1 = (ImageView)view.findViewById(R.id.imagen_HomeClub);
		  ImageView image2 = (ImageView)view.findViewById(R.id.imagen_Visitor);
		  TextView hora = (TextView)view.findViewById(R.id.hora);
		  TextView fecha = (TextView)view.findViewById(R.id.fecha);
		  TextView nomImage1 = (TextView)view.findViewById(R.id.homeClub);
		  TextView nomImage2 = (TextView)view.findViewById(R.id.visitor);
		  ItemCalendario itemCalendario = ((ItemCalendario)(items.get(position)));
		  
		  
	  	  image1.setImageResource(itemCalendario.getRutaImagen());
	  	  nomImage1.setText(itemCalendario.getNombre());
	  	  
	  	  image2.setImageResource(itemCalendario.getRutaImagen2());
	  	  nomImage2.setText(itemCalendario.getNombre2());
	  	  
	  	  hora.setText(itemCalendario.getHora());
	  	  fecha.setText(itemCalendario.getFecha());
	  	  
	
		  return view;
	  }
	  /*****************************END Inflar la lista del Calendario*******************************************************************/
	  /*****************************Inflar lista de los resultados*********************************************************/
	  public View inflateListResult(View view, int position){
		  LinearLayout linearLayoutHC;
		  LinearLayout linearLayoutV;
		  ImageView image1 = (ImageView)view.findViewById(R.id.imagen_HomeClub_Result);
		  ImageView image2 = (ImageView)view.findViewById(R.id.imagen_Visitor_Result);
		  TextView nombre1 = (TextView)view.findViewById(R.id.homeClubTextResult);
		  TextView nombre2 = (TextView)view.findViewById(R.id.visitorTextResult);
		  TextView golHC = (TextView)view.findViewById(R.id.golClubTextResult);
		  TextView golV = (TextView)view.findViewById(R.id.golVisitorTextResult);
		  ItemResult itemResult= ((ItemResult)(items.get(position)));
		  image1.setImageResource(itemResult.getRutaImagen());
		  image2.setImageResource(itemResult.getRutaImagen2());
		  nombre1.setText(""+itemResult.getNombre());
		  nombre2.setText(""+itemResult.getNombre2());
		  golHC.setText(""+itemResult.getGolNombre());
		  golV.setText(""+itemResult.getGolNombre2());
		  linearLayoutHC = (LinearLayout)view.findViewById(R.id.ContenedorHC);
		  linearLayoutV = (LinearLayout)view.findViewById(R.id.ContenedorV);
		  ArrayList<String> lis = itemResult.getGolHNombre();
		  ArrayList<String> lisGol = itemResult.getCantGolHNombre();
		  TextView tmp;
		  for (int i = 0; i  < lis.size(); i++){
			  tmp  = new TextView(activity);
			  tmp.setText(lisGol.get(i)+"    "+lis.get(i));
			  tmp.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_persona, 0);
			  linearLayoutV.addView(tmp);
		  }
		  lis = itemResult.getGolHNombre2();
		  lisGol = itemResult.getCantGolHNombre2();
		  for (int i = 0; i  < lis.size(); i++){
			  tmp  = new TextView(activity);
			  tmp.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_persona, 0, 0, 0);
			  tmp.setText(lis.get(i)+"    "+lisGol.get(i));
			  linearLayoutHC.addView(tmp);
		  }
		 
		  return view;
	  }
	  
	  //Transformacion del layout
	public View transformListResult(View contentView,int position){
		  RelativeLayout relativo = (RelativeLayout) contentView.findViewById(R.id.itemResult);
		  ItemResult item = (ItemResult)items.get(position);
		  TextView vs = (TextView)contentView.findViewById(R.id.Medio);
		  vs.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, ((ItemResult) item).getDrawable());
		  int size;
		  size = item.getCurrentHeight();
		  LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, size);
		  relativo.setLayoutParams(layoutParams);
		  return contentView;
	  }
	  /****************************END Inflar lista  de los resultados****************************************************/
}
