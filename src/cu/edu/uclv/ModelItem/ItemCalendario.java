package cu.edu.uclv.ModelItem;

public class ItemCalendario extends Item{
	private int rutaImagen2;
	private String nombre2;
	private String hora;
	private String fecha;
	private int rutaImagen;
	
	public ItemCalendario(int id, String nombre, String nombre2,int rutaImagen, int rutaImagen2, String hora, String fecha){			
		super(id, nombre);
		this.rutaImagen = rutaImagen;
		this.rutaImagen2 = rutaImagen2;
		this.nombre2 = nombre2;
		this.hora = hora;
		this.fecha = fecha;
	}
	
	public String getHora(){
		return this.hora;
	}
	
	public String getFecha(){
		return this.fecha;
	}
	
	public String getNombre2(){
		return this.nombre2;
	}
	
	public int getRutaImagen2(){
		return this.rutaImagen2;
	}
	public int getRutaImagen(){
		return this.rutaImagen;
	}

}
