package com.diligencedojo.tabsitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CurrentTab extends Activity {
	Button alarmBtn, lobbyBtn, settingsBtn, startTabBtn, editTabBtn,
			closeTabBtn;
	TextView currText1, currAmt1, currText2, currAmt2, maxAmt;
	boolean isSobriety, isSaver, isCount = false;
	int drinksMax, maxDollars = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.current_tab_layout);

		currText1 = (TextView) findViewById(R.id.current_text_1);
		currAmt1 = (TextView) findViewById(R.id.current_amount_1);
		currText2 = (TextView) findViewById(R.id.current_text_2);
		currAmt2 = (TextView) findViewById(R.id.current_amount_2);
		maxAmt = (TextView) findViewById(R.id.max_amount);
		editTabBtn = (Button) findViewById(R.id.edit_tab_button);
		closeTabBtn = (Button) findViewById(R.id.close_tab_button);
		alarmBtn = (Button) findViewById(R.id.alarm_button);
		lobbyBtn = (Button) findViewById(R.id.lobby_button);
		settingsBtn = (Button) findViewById(R.id.settings_button);
		startTabBtn = (Button) findViewById(R.id.start_tab_button);

		editTabBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		closeTabBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		// TO GEOFENCE ALARM
		alarmBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toAlarm = new Intent(v.getContext(), Alarm.class);
				startActivity(toAlarm);
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

		// TO START TAB
		startTabBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toStartTab = new Intent(v.getContext(), StartTab.class);
				startActivity(toStartTab);
			}
		});
	} // end onCreate

	public void setView() {
		TinyDB tinydb = new TinyDB(getApplicationContext());
		isSobriety = tinydb.getBoolean("isSobriety");
		isSaver = tinydb.getBoolean("isSaver");
		isCount = tinydb.getBoolean("isCount");
		drinksMax = tinydb.getInt("drinksMax");
		maxDollars = tinydb.getInt("maxDollars");

		// currText1, currAmt1, currText2, currAmt2, maxAmt;

		if (isSobriety) {
			currText1
					.setText(getResources().getString(R.string.sobriety_title));
			currText2.setText(getResources().getString(
					R.string.current_tab_text));
			maxAmt.setText(Integer.toString(drinksMax));
		} else if (isSaver) {
			currText1.setText(getResources().getString(R.string.saver_title));
			currText2.setText(getResources().getString(
					R.string.current_num_drinks_text));
			maxAmt.setText("$" + Integer.toString(maxDollars) + ".00");
		} else if (isCount) {
			;
		} // if all boolean values are false, set the sobriety
			// display as the default
		else {
			;
		}
	}

} // end CurrentTab Class
