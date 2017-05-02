package cu.edu.uclv.footballresult.navegation;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentTransaction;

public class Navegation_Listener implements ActionBar.TabListener {

	private Fragment fragment;

	public Navegation_Listener(Fragment fg)
	{
		this.fragment = fg;
	}

	
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		
	}

	
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		ft.replace(android.R.id.content ,fragment);
	}

	
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		ft.remove(fragment);
	}
	
}