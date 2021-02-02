package com.diligencedojo.tabsitter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;

public class Settings extends Activity {
	// TinyDB saved value handles: (boolean)"alarmSoundOn", (float)"geoRadius",
	// (boolean)"pwrSaverOn"

	Button alarmBtn, lobbyBtn, startTabBtn, alarmDistBtn;
	Switch alarmSoundSwitch, alarmDistSwitch, pwrSaveSwitch;
	TextView alarmDistTv, decPoint;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_layout);

		alarmDistBtn = (Button) findViewById(R.id.alarm_distance_button);
		alarmBtn = (Button) findViewById(R.id.alarm_button);
		lobbyBtn = (Button) findViewById(R.id.lobby_button);
		startTabBtn = (Button) findViewById(R.id.calculator_button);
		alarmSoundSwitch = (Switch) findViewById(R.id.alarm_sound_switch);
		alarmDistTv = (TextView) findViewById(R.id.alarm_distance_text);
		pwrSaveSwitch = (Switch) findViewById(R.id.power_saver_switch);

		alarmDistBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				chooseDialog();
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

		// TO START TAB
		startTabBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toStartTab = new Intent(v.getContext(), StartTab.class);
				startActivity(toStartTab);
			}
		});
	} // end onCreate

	@Override
	public void onBackPressed() {
		TinyDB tinydb = new TinyDB(getApplicationContext());

		// save alarm sound and power save state
		// (geoRadius is saved in chooseDialog())
		if (alarmSoundSwitch.isChecked())
			tinydb.putBoolean("alarmSoundOn", true);
		else
			tinydb.putBoolean("alarmSoundOn", false);

		if (pwrSaveSwitch.isChecked())
			tinydb.putBoolean("pwrSaverOn", true);
		else
			tinydb.putBoolean("pwrSaverOn", false);

		super.onBackPressed();
	}

	public void chooseDialog() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		LayoutInflater inflater = getLayoutInflater();
		View v = inflater.inflate(R.layout.number_picker, null);

		final NumberPicker distance = (NumberPicker) v
				.findViewById(R.id.dollar_picker);
		final NumberPicker cent = (NumberPicker) v
				.findViewById(R.id.cent_picker);
		decPoint = (TextView) v.findViewById(R.id.decimal_point);

		cent.setVisibility(View.GONE);
		decPoint.setVisibility(View.GONE);

		final String radiusSizes[] = new String[4];
		radiusSizes[0] = 100 + " meters";
		radiusSizes[1] = 250 + " meters";
		radiusSizes[2] = 500 + " meters";
		radiusSizes[3] = 1000 + " meters";

		alert.setView(v).setPositiveButton("Enter",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						int index = distance.getValue();
						String distStr = radiusSizes[index];

						if (index == 3) // radius is 1000 (4 digits)
							distStr = distStr.substring(0, 4);
						else
							distStr = distStr.substring(0, 3);

						Float geoRadius = Float.parseFloat(distStr);
						// Log.d("DBG", "distStr (after): " + distStr);
						TinyDB tinydb = new TinyDB(getApplicationContext());
						tinydb.putFloat("geoRadius", geoRadius);

						alarmDistTv.setText(distStr + " meters");
					}
				});

		distance.setDisplayedValues(radiusSizes);
		distance.setMinValue(0);
		distance.setMaxValue(3);
		distance.setWrapSelectorWheel(true);
		distance.setValue(0);
		distance.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

		alert.show();

	} // end chooseDialog()

} // end Settings Class
