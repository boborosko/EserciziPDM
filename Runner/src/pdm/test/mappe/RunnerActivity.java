package pdm.test.mappe;

import com.RadiusOverlay;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class RunnerActivity extends MapActivity {
    /** Called when the activity is first created. */
	MapView mapView;
	MyLocationOverlay myLocationOverlay;
	LocationManager locationmanager;
	RadiusOverlay one,two,three,four;
	PendingIntent mPendingTermini,mPendingRepubblica,mPendingColosseo,mPendingRomoloRemo;
	ProximityBroadcast mProximityBroadcast;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mapView = (MapView)findViewById(R.id.mapview);
        mapView.setClickable(true);
        mapView.setBuiltInZoomControls(true);
        mapView.setSatellite(true);
        mapView.getOverlays().add(myLocationOverlay);
        mapView.getOverlays().add(one);
        mapView.getOverlays().add(two);
        mapView.getOverlays().add(three);
        mapView.getOverlays().add(four);
        
        GeoPoint termini = new GeoPoint(41902022, 12500882);
        one = new RadiusOverlay(termini, 400, Color.BLUE);
        
        GeoPoint repubblica = new GeoPoint(41902622, 12495482);
        two = new RadiusOverlay(repubblica, 300, Color.BLUE);
        
        GeoPoint colosseo = new GeoPoint(41890310, 12492410);
        three = new RadiusOverlay(colosseo, 500, Color.BLUE);
        
        GeoPoint romoloremo = new GeoPoint(41890492, 12484823);
        four = new RadiusOverlay(romoloremo, 450, Color.BLUE);
       
        myLocationOverlay = new MyLocationOverlay(this, mapView);
        myLocationOverlay.runOnFirstFix(new Runnable () {

			public void run() {
				// TODO Auto-generated method stub
				mapView.getController().animateTo(myLocationOverlay.getMyLocation());
				
			}
       });
        
       
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	} 
	public void onResume() {
		  super.onResume();
		myLocationOverlay.enableMyLocation();
		
		Intent intentTermini = new Intent("pdm.test.mappe");
		intentTermini.putExtra("overlay", 1);
		mPendingTermini = PendingIntent.getBroadcast(this, 1, intentTermini, PendingIntent.FLAG_CANCEL_CURRENT);
		
		Intent intentRepubblica = new Intent("pdm.test.mappe");
		intentRepubblica.putExtra("overlay", 2);
		mPendingRepubblica = PendingIntent.getBroadcast(this, 2, intentRepubblica, PendingIntent.FLAG_CANCEL_CURRENT);
		
		Intent intentColosseo = new Intent("pdm.test.mappe");
		intentColosseo.putExtra("overlay", 3);
		mPendingColosseo = PendingIntent.getBroadcast(this, 3, intentColosseo, PendingIntent.FLAG_CANCEL_CURRENT);
		
		Intent intentRomoloRemo = new Intent("pdm.test.mappe");
		intentRomoloRemo.putExtra("overlay", 4);
		mPendingRomoloRemo = PendingIntent.getBroadcast(this, 4, intentRomoloRemo, PendingIntent.FLAG_CANCEL_CURRENT);
		
		
		locationmanager = (LocationManager) getSystemService (LOCATION_SERVICE);
		locationmanager.addProximityAlert(41901222 * 0.000001, 12500882 * 0.000001, 400, -1, mPendingTermini);//Termini
		locationmanager.addProximityAlert(41902622 * 0.000001, 12495482 * 0.000001, 300, -1, mPendingRepubblica);//P.zza Repubblica
		locationmanager.addProximityAlert(41890310 * 0.000001, 12492410 * 0.000001, 500, -1, mPendingColosseo);//Colosseo
		locationmanager.addProximityAlert(41890492 * 0.000001, 12484823 * 0.000001, 450, -1, mPendingRomoloRemo);//CasaRomolo&Remo
		
		registerReceiver(mProximityBroadcast, new IntentFilter("pdm.test.mappe"));
				
	}
	public void onPause() {
		super.onPause();
		myLocationOverlay.disableMyLocation();
		unregisterReceiver(mProximityBroadcast);
		locationmanager.removeProximityAlert(mPendingTermini);
		locationmanager.removeProximityAlert(mPendingRepubblica);
		locationmanager.removeProximityAlert(mPendingColosseo);
		locationmanager.removeProximityAlert(mPendingRomoloRemo);
	}
	class ProximityBroadcast extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			Log.d("TAG", "Proximity Alert");
		Toast.makeText(getApplicationContext(), "Alert di Prossimità", Toast.LENGTH_LONG);
		int area = arg1.getIntExtra("overlay", -1);
		boolean stoEntrando = arg1.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, true);
		if (stoEntrando){
			switch (area) {
			case 1:
				Toast.makeText(getApplicationContext(), "Benvenuto", Toast.LENGTH_SHORT).show();
				one.setColor(Color.GREEN);
				break;
			case 2:
				Toast.makeText(getApplicationContext(), "Benvenuto", Toast.LENGTH_SHORT).show();
				two.setColor(Color.GREEN);
				break;
			case 3:
				Toast.makeText(getApplicationContext(), "Benvenuto", Toast.LENGTH_SHORT).show();
				three.setColor(Color.GREEN);
				break;
			case 4:
				Toast.makeText(getApplicationContext(), "Benvenuto", Toast.LENGTH_SHORT).show();
				four.setColor(Color.GREEN);
				break;
			}
			
		} else {
			Toast.makeText(getApplicationContext(), "Arrivederci", Toast.LENGTH_SHORT);
			switch (area) {
			case 1:
				one.setColor(Color.GRAY);
				break;
			case 2:
				two.setColor(Color.GRAY);
			case 3:
				three.setColor(Color.GRAY);
				break;
			case 4:
				four.setColor(Color.GRAY);
				break;
				     }
		         }
		   }
	}
}