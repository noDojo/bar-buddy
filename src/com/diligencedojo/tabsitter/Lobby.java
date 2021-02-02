package com.diligencedojo.tabsitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Lobby extends Activity {
	Button alarmBtn, startTabBtn, settingsBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lobby_layout);
		
//		TinyDB tinydb = new TinyDB(this);
//		tinydb.clear(); // test value //

		alarmBtn = (Button) findViewById(R.id.alarm_button);
		startTabBtn = (Button) findViewById(R.id.calculator_button);
		settingsBtn = (Button) findViewById(R.id.settings_button);

		alarmBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toAlarm = new Intent(v.getContext(), Alarm.class);
				startActivity(toAlarm);
			}
		});

		startTabBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toCalc = new Intent(v.getContext(), StartTab.class);
				startActivity(toCalc);
			}
		});

		settingsBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toSettings = new Intent(v.getContext(), Settings.class);
				startActivity(toSettings);
			}
		});

	} // end onCreate

} // end Lobby Class
