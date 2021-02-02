package com.diligencedojo.tabsitter;

import com.google.android.gms.location.Geofence;

public class SimpleGeofence {
	private final String id;
	private double latitude;
	private double longitude;
	private final float radius;
	private long expirationDuration;
	private int transitionType;
	private int loiteringDelay = 60000;

	public SimpleGeofence(String geofenceId, double latitude, double longitude,
			float radius, long expiration, int transition) {
		this.id = geofenceId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
		this.expirationDuration = expiration;
		this.transitionType = transition;
	}

	public String getId() {
		return id;
	}

	public void setLatitude(Double mLat) {
		this.latitude = mLat;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLongitude(Double mLong) {
		this.longitude = mLong;
	}

	public double getLongitude() {
		return longitude;
	}

	public float getRadius() {
		return radius;
	}

	public long getExpirationDuration() {
		return expirationDuration;
	}

	public int getTransitionType() {
		return transitionType;
	}

	public Geofence toGeofence() {
		Geofence g = new Geofence.Builder().setRequestId(getId())
				.setTransitionTypes(transitionType)
				.setCircularRegion(getLatitude(), getLongitude(), getRadius())
				.setExpirationDuration(expirationDuration)
				.setLoiteringDelay(loiteringDelay).build();
		return g;
	}

} // end SimpleGefence Class
