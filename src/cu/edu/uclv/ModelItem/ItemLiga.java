package cu.edu.uclv.ModelItem;

public class ItemLiga extends Item{
	String description;
	String rutaImagen;
	public ItemLiga() {
	    super();
	    this.description = "";
	  }
	     	     
	  public ItemLiga(int id, String nombre, String rutaImagen, String description) {
	    super(id, nombre);
	    this.description = description;
	    this.rutaImagen = rutaImagen;
	  }
	  
	  public String getDescription(){
		  return this.description;
	  }
	  
	  public String getRutaImagen(){
		  return this.rutaImagen;
	  }

}
