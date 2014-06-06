package org.osmdroid.bonuspack.overlays;

import java.util.ArrayList;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/** View that can be displayed on an OSMDroid map, associated to a GeoPoint. 
 * Typical usage: cartoon-like bubbles displayed when clicking an overlay item. 
 * It mimics the InfoWindow class of Google Maps JavaScript API V3. 
 * Main differences are: 
 * <ul>
 * <li>Structure and content of the view is let to the responsibility of the caller. </li>
 * <li>The same InfoWindow can be associated to many items. </li>
 * </ul>
 * 
 * This is an abstract class. 
 * @see MarkerInfoWindow
 * @author M.Kergall
 */
public abstract class InfoWindow {

	protected View mView;
    protected boolean mIsVisible = false;
	protected MapView mMapView;

    // hack: default marker position in list
    private int position = 1;
	
	/**
	 * @param layoutResId	the id of the view resource. 
	 * @param mapView	the mapview on which is hooked the view
	 */
	public InfoWindow(int layoutResId, MapView mapView) {
		mMapView = mapView;
		mIsVisible = false;
		ViewGroup parent=(ViewGroup)mapView.getParent();
		Context context = mapView.getContext();
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = inflater.inflate(layoutResId, parent, false);
		mView.setTag(this);
	}

	/**
	 * Returns the Android view. This allows to set its content. 
	 * @return the Android view
	 */
	public View getView() {
		return(mView);
	}

	/**
	 * open the InfoWindow at the specified GeoPosition + offset. 
	 * If it was already opened, close it before reopening. 
	 * @param object the graphical object on which is hooked the view
	 * @param position to place the window on the map
	 * @param offsetX (&offsetY) the offset of the view to the position, in pixels. 
	 * This allows to offset the view from the object position. 
	 */
    public void open(ExtendedOverlayItem item, int offsetX, int offsetY) {
        onOpen(item);
        GeoPoint position = item.getPoint();
		MapView.LayoutParams lp = new MapView.LayoutParams(
				MapView.LayoutParams.WRAP_CONTENT,
				MapView.LayoutParams.WRAP_CONTENT,
				position, MapView.LayoutParams.BOTTOM_CENTER, 
				offsetX, offsetY);
        close(false); //if it was already opened
		mMapView.addView(mView, lp);
        this.position = item.getPosition();
		mIsVisible = true;
	}
    
    public void close(boolean isNeedOnClose) {
		if (mIsVisible) {
			mIsVisible = false;
			((ViewGroup)mView.getParent()).removeView(mView);
            if (isNeedOnClose) onClose(this.position);
		}
	}
	
	public boolean isOpen(){
		return mIsVisible;
	}
	
    //Abstract methods to implement:
    public abstract void onOpen(ExtendedOverlayItem item);
	
    public abstract void onClose(int value);
	
}
