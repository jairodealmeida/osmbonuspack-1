package org.osmdroid.bonuspack.overlays;

import java.util.HashMap;

import org.osmdroid.views.MapView;

import android.util.Log;

public class MemoryInfoWindow extends InfoWindow {

	private HashMap<String, String> mExtendedData;
	
	public MemoryInfoWindow(MapView mapView,HashMap<String, String> mExtendedData) {
		super(mapView);
		this.mExtendedData =mExtendedData;
	}
	
	public HashMap<String, String> getMExtendedData(){
		return this.mExtendedData;
	}
	
	@Override public void onOpen(Object item) {
		ExtendedOverlayItem extendedOverlayItem = (ExtendedOverlayItem)item;
		Log.d("debug",extendedOverlayItem.toString());
	}

	@Override public void onClose() {
		//by default, do nothing
	}
	
}
