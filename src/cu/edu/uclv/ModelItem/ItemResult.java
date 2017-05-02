package cu.edu.uclv.ModelItem;

import java.util.ArrayList;

import cu.edu.uclv.footballresult.R;

public class ItemResult extends Item{
	int rutaImagen;
	int rutaImagen2;
	int golNombre;
	int golNombre2;
	ArrayList<String> golHNombre;
	ArrayList<String> golHNombre2;
	ArrayList<String> cantGolHNombre;
	ArrayList<String> cantGolHNombre2;
	String golPEHC;
	String golPEV;
	String nombre2;
	private int collapsedHeight, currentHeight, expandedHeight;
	private boolean isOpen;
	private int drawable;
	
	
	public ItemResult(int id, String nombre, 
							String nombre2, int golNombre, int golNombre2, ArrayList<String> golHNombre, 
									ArrayList<String> golHNombre2, ArrayList<String> cantGolHNombre, ArrayList<String> cantGolHNombre2,  
										int rutaImagen, int rutaImagen2
										,int collapsedHeight, int currentHeight,int expandedHeight) {
		super(id, nombre);
		this.nombre2  = nombre2;
		this.golNombre = golNombre;
		this.golNombre2 = golNombre2;
		this.golHNombre = golHNombre;
		this.golHNombre2 = golHNombre2;
		this.cantGolHNombre = cantGolHNombre;
		this.cantGolHNombre2 = cantGolHNombre2;
		this.rutaImagen = rutaImagen;
		this.rutaImagen2 = rutaImagen2;
		this.collapsedHeight = collapsedHeight;
		this.currentHeight = currentHeight;
		this.expandedHeight = expandedHeight;
		this.isOpen = false;
		this.drawable = R.drawable.down;
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
	
	public ItemResult(int id, String nombre, String nombre2, 
									int golNombre, int golNombre2, String golPEHC, String golPEV){
		super(id, nombre);
		this.nombre2  = nombre2;
		this.golNombre = golNombre;
		this.golNombre2 = golNombre2;
		this.golPEHC = golPEHC;
		this.golPEV = golPEV;
	}
	
	public String getNombre2(){
		return this.nombre2;
	}

	public int getGolNombre() {
		return golNombre;
	}

	public int getGolNombre2() {
		return golNombre2;
	}

	public ArrayList<String> getGolHNombre() {
		return golHNombre;
	}

	public ArrayList<String> getGolHNombre2() {
		return golHNombre2;
	}

	public ArrayList<String> getCantGolHNombre() {
		return cantGolHNombre;
	}

	public ArrayList<String> getCantGolHNombre2() {
		return cantGolHNombre2;
	}

	public int getRutaImagen() {
		return rutaImagen;
	}

	public int getRutaImagen2() {
		return rutaImagen2;
	}

	public String getGolPEHC() {
		return golPEHC;
	}

	public String getGolPEV() {
		return golPEV;
	}
	
	
}
