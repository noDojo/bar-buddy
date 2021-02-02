package com.diligencedojo.tabsitter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class GeolocationService extends Service implements ConnectionCallbacks,
		OnConnectionFailedListener, LocationListener, ResultCallback<Status> {
	// TinyDB saved value handles: (boolean)"mapReady"

	private static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;
	private static final long RADIUS = 100;
	public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = GEOFENCE_EXPIRATION_IN_HOURS
			* DateUtils.HOUR_IN_MILLIS;
	public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
	public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 5;
	protected GoogleApiClient mGoogleApiClient;
	protected LocationRequest mLocationRequest;
	private PendingIntent mPendingIntent;
	List<Geofence> mGeofenceList;
	static public List<Geofence> mGeofenceListPass;
	SimpleGeofence simpleGeo;
	static public SimpleGeofence geoFence;
	Location mLocation;
	Intent intent;

	// ON START
	// ***********
	@Override
	public void onStart(Intent intent, int startId) {
		buildGoogleApiClient();
		mGoogleApiClient.connect();
	}

	// ON DESTROY
	// *************
	@Override
	public void onDestroy() {
		super.onDestroy();

		if (mGoogleApiClient.isConnected())
			mGoogleApiClient.disconnect();
	}

	// REGISTER GEOFENCES
	// *********************
	protected void registerGeofences(Location location) {
		if (Alarm.geofencesAlreadyRegistered)
			return;

		Log.d(Alarm.TAG, "Registering Geofences");

		String geoId = "geoId";
		simpleGeo = new SimpleGeofence(geoId, location.getLatitude(),
				location.getLongitude(), RADIUS,
				GEOFENCE_EXPIRATION_IN_MILLISECONDS,
				Geofence.GEOFENCE_TRANSITION_ENTER
						| Geofence.GEOFENCE_TRANSITION_DWELL
						| Geofence.GEOFENCE_TRANSITION_EXIT);

		// mGeofenceList.add(new Geofence.Builder()
		// // Set the request ID of the geofence. This is a string to
		// // identify this geofence.
		// .setRequestId(simpleGeo.getId())
		// .setCircularRegion(simpleGeo.getLatitude(),
		// simpleGeo.getLongitude(), simpleGeo.getRadius())
		// .setExpirationDuration(simpleGeo.getExpirationDuration())
		// .setTransitionTypes(
		// Geofence.GEOFENCE_TRANSITION_ENTER
		// | Geofence.GEOFENCE_TRANSITION_EXIT).build());
		//
		// // GeofencingRequest.Builder builder = new
		// GeofencingRequest.Builder();
		// //
		// builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
		// // builder.addGeofences(mGeofenceList);

		// Log.d(Alarm.TAG,
		// simpleGeo.getLatitude() + " " + simpleGeo.getLongitude());

		HashMap<String, SimpleGeofence> geofences = SimpleGeofenceStore
				.getInstance().getSimpleGeofences();

		GeofencingRequest.Builder builder = new GeofencingRequest.Builder();

		for (Map.Entry<String, SimpleGeofence> item : geofences.entrySet()) {
			SimpleGeofence sg = item.getValue();
			sg.setLatitude(simpleGeo.getLatitude());
			sg.setLongitude(simpleGeo.getLongitude());
			builder.addGeofence(sg.toGeofence());

			SimpleGeofenceStore store = SimpleGeofenceStore.getInstance();
			store.setLatLong(simpleGeo.getLatitude(), simpleGeo.getLongitude());
			// Log.d(Alarm.TAG, sg.getLatitude() + " " + sg.getLongitude());
		}

		TinyDB tinydb = new TinyDB(getApplicationContext());
		tinydb.putBoolean("mapReady", false);

		GeofencingRequest geofencingRequest = builder.build();

		mPendingIntent = requestPendingIntent();

		LocationServices.GeofencingApi.addGeofences(mGoogleApiClient,
				geofencingRequest, mPendingIntent).setResultCallback(this);

		Alarm.geofencesAlreadyRegistered = true;
	} // end registerGeofences()

	// REQUEST PENDING INTENT
	// *************************
	private PendingIntent requestPendingIntent() {

		if (null != mPendingIntent)
			return mPendingIntent;

		Intent intent = new Intent(this, GeofenceReceiver.class);
		return PendingIntent.getService(this, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);

	} // end requestPendingIntent()

	// START LOCATION UPDATES
	// *************************
	protected void startLocationUpdates() {
		LocationServices.FusedLocationApi.requestLocationUpdates(
				mGoogleApiClient, mLocationRequest, this);
	} // end startLocationUpdates()

	// STOP LOCATION UPDATES
	// ************************
	protected void stopLocationUpdates() {
		LocationServices.FusedLocationApi.removeLocationUpdates(
				mGoogleApiClient, this);
	} // end stopLocationUpdates()

	// ON CONNECTED
	// ***************
	@Override
	public void onConnected(Bundle connectionHint) {
		Log.i(Alarm.TAG, "Connected to GoogleApiClient");
		startLocationUpdates();
	} // end onConnected(Bundle connectionHint)

	// ON LOCATION CHANGED
	// **********************
	@Override
	public void onLocationChanged(Location location) {
		Log.d(Alarm.TAG, "new location : " + location.getLatitude() + ", "
				+ location.getLongitude() + ". " + location.getAccuracy());
		broadcastLocationFound(location);

		// // error here
		if (!Alarm.geofencesAlreadyRegistered)
			registerGeofences(location);

	} // end onLocationChanged(Location location)

	// ON CONNECTION SUSPENDED
	// **************************
	@Override
	public void onConnectionSuspended(int cause) {
		Log.i(Alarm.TAG, "Connection suspended");
		mGoogleApiClient.connect();
	} // end onConnectionSuspended(int cause)

	// ON CONNECTION FAILED
	// ***********************
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		Log.i(Alarm.TAG,
				"Connection failed: ConnectionResult.getErrorCode() = "
						+ result.getErrorCode());
	} // end onConnectionFailed(ConnectionResult result)

	// BUILD GOOGLE API CLIENT
	// **************************
	protected synchronized void buildGoogleApiClient() {
		Log.i(Alarm.TAG, "Building GoogleApiClient");

		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API).build();

		createLocationRequest();
	} // end buildGoogleApiClient()

	// CREATE LOCATION REQUEST
	// **************************
	protected void createLocationRequest() {
		mLocationRequest = new LocationRequest();
		mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
		mLocationRequest
				.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	} // end createLocationRequest()

	// ON BIND
	// **********
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	// BROADCAST LOCATION FOUND
	// ***************************
	public void broadcastLocationFound(Location location) {
		Intent intent = new Intent(
				"com.diligencedojo.tabsitter.geolocation.service");
		intent.putExtra("latitude", location.getLatitude());
		intent.putExtra("longitude", location.getLongitude());
		intent.putExtra("done", 1);

		sendBroadcast(intent);
	} // end broadcastLocationFound(Location location)

	// ON RESULT
	// ************
	public void onResult(Status status) {
		if (status.isSuccess()) {
			Toast.makeText(getApplicationContext(),
					getString(R.string.geofences_added), Toast.LENGTH_SHORT)
					.show();

			TinyDB tinydb = new TinyDB(getApplicationContext());
			tinydb.putBoolean("mapReady", true);
		} else {
			Alarm.geofencesAlreadyRegistered = false;
			String errorMessage = getErrorString(this, status.getStatusCode());
			Toast.makeText(getApplicationContext(), errorMessage,
					Toast.LENGTH_SHORT).show();
		}
	} // end onResult(Status status)

	// GET ERROR STRING
	// *******************
	public static String getErrorString(Context context, int errorCode) {
		Resources mResources = context.getResources();

		switch (errorCode) {
		case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
			return mResources.getString(R.string.geofence_not_available);
		case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
			return mResources.getString(R.string.geofence_too_many_geofences);
		case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
			return mResources
					.getString(R.string.geofence_too_many_pending_intents);
		default:
			return mResources.getString(R.string.unknown_geofence_error);

		} // end switch (errorCode)
	} // end getErrorString(Context context, int errorCode)

} // end GeolocationService Class
