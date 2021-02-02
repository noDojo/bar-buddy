package com.diligencedojo.tabsitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SetDrinks extends Activity {
	// TinyDB saved value handles: (boolean)"beer", (boolean)"liquor",
	// (boolean)"shot", (boolean)"wine", (boolean)"other"

	Button alarmBtn, lobbyBtn, settingsBtn, beerBtn, liquorBtn, shotBtn,
			wineBtn, otherBtn;
	boolean beerIsSel, liquorIsSel, shotIsSel, wineIsSel, otherIsSel = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_drinks_layout);

		beerBtn = (Button) findViewById(R.id.beer_button);
		liquorBtn = (Button) findViewById(R.id.liquor_drink_button);
		shotBtn = (Button) findViewById(R.id.shot_button);
		wineBtn = (Button) findViewById(R.id.wine_button);
		otherBtn = (Button) findViewById(R.id.other_button);
		alarmBtn = (Button) findViewById(R.id.alarm_button);
		lobbyBtn = (Button) findViewById(R.id.lobby_button);
		settingsBtn = (Button) findViewById(R.id.settings_button);

		TinyDB tinydb = new TinyDB(getApplicationContext());
		beerIsSel = tinydb.getBoolean("beer");
		liquorIsSel = tinydb.getBoolean("liquor");
		shotIsSel = tinydb.getBoolean("shot");
		wineIsSel = tinydb.getBoolean("wine");
		otherIsSel = tinydb.getBoolean("other");

		// whatever kinds of drinks are activated, change the text color
		// to green and the text to the button_yes string
		if (beerIsSel) {
			beerBtn.setText(R.string.beer_button_yes);
			beerBtn.setTextColor(getResources().getColor(R.color.green));
		}
		if (liquorIsSel) {
			liquorBtn.setText(R.string.liquor_drink_button_yes);
			liquorBtn.setTextColor(getResources().getColor(R.color.green));
		}
		if (shotIsSel) {
			shotBtn.setText(R.string.shot_button_yes);
			shotBtn.setTextColor(getResources().getColor(R.color.green));
		}
		if (wineIsSel) {
			wineBtn.setText(R.string.wine_button_yes);
			wineBtn.setTextColor(getResources().getColor(R.color.green));
		}
		if (otherIsSel) {
			otherBtn.setText(R.string.other_button_yes);
			otherBtn.setTextColor(getResources().getColor(R.color.green));
		}

		beerBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String temp = beerBtn.getText().toString();

				if (temp.endsWith(")")) {
					beerBtn.setText(R.string.beer_button);
					beerBtn.setTextColor(getResources().getColor(R.color.white));
				} else {
					beerBtn.setText(R.string.beer_button_yes);
					beerBtn.setTextColor(getResources().getColor(R.color.green));
				}
			}
		});

		liquorBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String temp = liquorBtn.getText().toString();

				if (temp.endsWith(")")) {
					liquorBtn.setText(R.string.liquor_drink_button);
					liquorBtn.setTextColor(getResources().getColor(
							R.color.white));
				} else {
					liquorBtn.setText(R.string.liquor_drink_button_yes);
					liquorBtn.setTextColor(getResources().getColor(
							R.color.green));
				}
			}
		});

		shotBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String temp = shotBtn.getText().toString();

				if (temp.endsWith(")")) {
					shotBtn.setText(R.string.shot_button);
					shotBtn.setTextColor(getResources().getColor(R.color.white));
				} else {
					shotBtn.setText(R.string.shot_button_yes);
					shotBtn.setTextColor(getResources().getColor(R.color.green));
				}
			}
		});

		wineBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String temp = wineBtn.getText().toString();

				if (temp.endsWith(")")) {
					wineBtn.setText(R.string.wine_button);
					wineBtn.setTextColor(getResources().getColor(R.color.white));
				} else {
					wineBtn.setText(R.string.wine_button_yes);
					wineBtn.setTextColor(getResources().getColor(R.color.green));
				}
			}
		});

		otherBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String temp = otherBtn.getText().toString();

				if (temp.endsWith(")")) {
					otherBtn.setText(R.string.other_button);
					otherBtn.setTextColor(getResources()
							.getColor(R.color.white));
				} else {
					otherBtn.setText(R.string.other_button_yes);
					otherBtn.setTextColor(getResources()
							.getColor(R.color.green));
				}
			}
		});

		alarmBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toAlarm = new Intent(v.getContext(), Alarm.class);
				startActivity(toAlarm);
			}
		});

		lobbyBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toLobby = new Intent(v.getContext(), Lobby.class);
				startActivity(toLobby);
			}
		});

		settingsBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toSettings = new Intent(v.getContext(), Settings.class);
				startActivity(toSettings);
			}
		});

	}// end onCreate

	@Override
	public void onBackPressed() {
		TinyDB tinydb = new TinyDB(getApplicationContext());

		if (beerBtn.getText().toString().endsWith(")"))
			tinydb.putBoolean("beer", true);
		else
			tinydb.putBoolean("beer", false);

		if (liquorBtn.getText().toString().endsWith(")"))
			tinydb.putBoolean("liquor", true);
		else
			tinydb.putBoolean("liquor", false);

		if (shotBtn.getText().toString().endsWith(")"))
			tinydb.putBoolean("shot", true);
		else
			tinydb.putBoolean("shot", false);

		if (wineBtn.getText().toString().endsWith(")"))
			tinydb.putBoolean("wine", true);
		else
			tinydb.putBoolean("wine", false);

		if (otherBtn.getText().toString().endsWith(")"))
			tinydb.putBoolean("other", true);
		else
			tinydb.putBoolean("other", false);

		// Toast.makeText(getApplicationContext(),
		// "Beer: " + tinydb.getBoolean("beer"), Toast.LENGTH_SHORT)
		// .show();

		super.onBackPressed();
	}

} // end SetDrinks Class
