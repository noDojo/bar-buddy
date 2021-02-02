package com.diligencedojo.tabsitter;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.text.format.DateUtils;

import com.google.android.gms.location.Geofence;

public class SimpleGeofenceStore {
	private static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;
	private static final long RADIUS = 100;
	public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = GEOFENCE_EXPIRATION_IN_HOURS
			* DateUtils.HOUR_IN_MILLIS;
	protected HashMap<String, SimpleGeofence> geofences = new HashMap<String, SimpleGeofence>();
	private static SimpleGeofenceStore instance = new SimpleGeofenceStore();
	private double latitude;
	private double longitude;

	public static SimpleGeofenceStore getInstance() {
		// mContext = context;
		return instance;
	}

	public void setLatLong(Double mLat, Double mLong) {
		this.latitude = mLat;
		this.longitude = mLong;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	private SimpleGeofenceStore() {
		// geofences.put("The Shire", new SimpleGeofence("The Shire", 51.663398,
		// -0.209118, RADIUS, GEOFENCE_EXPIRATION_IN_MILLISECONDS,
		// Geofence.GEOFENCE_TRANSITION_ENTER
		// | Geofence.GEOFENCE_TRANSITION_DWELL
		// | Geofence.GEOFENCE_TRANSITION_EXIT));

		// HashMap<String, SimpleGeofence> geofences = SimpleGeofenceStore
		// .getInstance().getSimpleGeofences();
		//
		// // set circle around marker
		// for (Map.Entry<String, SimpleGeofence> item : geofences.entrySet()) {
		// SimpleGeofence sg = item.getValue();
		//
		geofences.put("My House", new SimpleGeofence("My House", getLatitude(),
				getLongitude(), RADIUS, GEOFENCE_EXPIRATION_IN_MILLISECONDS,
				Geofence.GEOFENCE_TRANSITION_ENTER
						| Geofence.GEOFENCE_TRANSITION_DWELL
						| Geofence.GEOFENCE_TRANSITION_EXIT));

		// geofences.put("My House", new SimpleGeofence("My House", 34.1926414,
		// -79.7726746, RADIUS, GEOFENCE_EXPIRATION_IN_MILLISECONDS,
		// Geofence.GEOFENCE_TRANSITION_ENTER
		// | Geofence.GEOFENCE_TRANSITION_DWELL
		// | Geofence.GEOFENCE_TRANSITION_EXIT));
	}

	public HashMap<String, SimpleGeofence> getSimpleGeofences() {
		return this.geofences;
	}

} // end SimpleGeofenceStore Class
