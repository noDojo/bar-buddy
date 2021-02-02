package com.diligencedojo.tabsitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

public class Alarm extends ActionBarActivity {
	public static String TAG = "lstech.aos.debug";

	static public boolean geofencesAlreadyRegistered = false;
	Button addBtn, lobbyBtn, startTabBtn, settingsBtn;
	Fragment f;
	FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_layout);

		addBtn = (Button) findViewById(R.id.add_geo_button);
		startTabBtn = (Button) findViewById(R.id.calculator_button);
		lobbyBtn = (Button) findViewById(R.id.lobby_button);
		settingsBtn = (Button) findViewById(R.id.settings_button);

		f = new MapFragment();
		fragmentManager = getSupportFragmentManager();

		// SET MAP MARKER AND GEOFENCE
		addBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				fragmentManager.beginTransaction()
						.replace(android.R.id.content, f, "home").commit();
				fragmentManager.executePendingTransactions();

				startService(new Intent(getApplicationContext(),
						GeolocationService.class));
			}
		});

		// TO START TAB
		startTabBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toStartTab = new Intent(v.getContext(), StartTab.class);
				startActivity(toStartTab);
			}
		});

		// TO LOBBY
		lobbyBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toLobby = new Intent(v.getContext(), Lobby.class);
				startActivity(toLobby);
			}
		});

		// TO SETTINGS
		settingsBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toSettings = new Intent(v.getContext(), Settings.class);
				startActivity(toSettings);
			}
		});

	} // end onCreate(Bundle savedInstanceState)

} // end AlarmClass
