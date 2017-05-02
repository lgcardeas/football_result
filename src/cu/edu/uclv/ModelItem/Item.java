package cu.edu.uclv.ModelItem;

public abstract class Item {
	  protected int id;
	  protected String nombre;
	         
	  public Item() {
	    this.nombre = "";
	  }
	     
	     
	  public Item(int id, String nombre) {
	    this.id = id;
	    this.nombre = nombre;
	  }
	     
	  public int getId() {
	    return id;
	  }
	     
	  public void setId(int id) {
	    this.id = id;
	  }
	  
	  public int getID(){
		  return this.id;
	  }
	   
	     
	  public String getNombre() {
	    return nombre;
	  }
	     
	  public void setNombre(String nombre) {
	    this.nombre = nombre;
	  }
	}