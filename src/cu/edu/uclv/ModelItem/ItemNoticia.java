package cu.edu.uclv.ModelItem;

import android.widget.ListView;
import cu.edu.uclv.footballresult.R;


public class ItemNoticia extends Item{
	private String noticia;
	protected String rutaImagen;
	private int collapsedHeight, currentHeight, expandedHeight;
	private boolean isOpen;
	private int drawable;
	
	  public ItemNoticia(int id, String nombre, String rutaImagen,	String noticia
			  				,int collapsedHeight, int currentHeight,int expandedHeight) {
	    super(id, nombre);;
	    this.noticia = noticia;
	    this.collapsedHeight = collapsedHeight;
		this.currentHeight = currentHeight;
		this.expandedHeight = expandedHeight;
		this.isOpen = false;
		this.drawable = R.drawable.down;
		this.rutaImagen = rutaImagen;
	  } 
	  
	  public String getNoticia(){
		  return this.noticia;

	  }
	  
	  public int getCollapsedHeight() {
			return collapsedHeight;
		}

		public void setCollapsedHeight(int collapsedHeight) {
			this.collapsedHeight = collapsedHeight;
		}

		public int getCurrentHeight() {
			return currentHeight;
		}

		public void setCurrentHeight(int currentHeight) {
			this.currentHeight = currentHeight;
		}

		public int getExpandedHeight() {
			return expandedHeight;
		}

		public void setExpandedHeight(int expandedHeight) {
			this.expandedHeight = expandedHeight;
		}

		public boolean isOpen() {
			return isOpen;
		}

		public void setOpen(boolean isOpen) {
			this.isOpen = isOpen;
		}


		public int getDrawable() {
			return drawable;
		}

		public void setDrawable(int drawable) {
			this.drawable = drawable;
		}
		
		  
		  public String getRutaImagen() {
		    return rutaImagen;
		  }
		     
		  public void setRutaImagen(String rutaImagen) {
		    this.rutaImagen = rutaImagen;
		  }
}
