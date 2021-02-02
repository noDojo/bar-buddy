package com.diligencedojo.tabsitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartTab extends Activity {
	Button alarmBtn, lobbyBtn, settingsBtn, pickDrinksBtn, setPricesBtn,
			setTipsBtn, setGoalBtn, startTabBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_tab_layout);

		pickDrinksBtn = (Button) findViewById(R.id.pick_drinks_button);
		setPricesBtn = (Button) findViewById(R.id.set_prices_button);
		setTipsBtn = (Button) findViewById(R.id.set_tips_button);
		setGoalBtn = (Button) findViewById(R.id.set_goal_button);
		alarmBtn = (Button) findViewById(R.id.alarm_button);
		lobbyBtn = (Button) findViewById(R.id.lobby_button);
		settingsBtn = (Button) findViewById(R.id.settings_button);
		startTabBtn = (Button) findViewById(R.id.start_tab_button);

		// PICK THE TYPES OF DRINKS THAT WILL BE PURCHASED
		pickDrinksBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toSetDrinks = new Intent(v.getContext(), SetDrinks.class);
				startActivity(toSetDrinks);
			}
		});

		// ASSIGN PRICES TO SELECTED TYPES OF DRINKS
		setPricesBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toSetPrices = new Intent(v.getContext(), SetPrices.class);
				startActivity(toSetPrices);
			}
		});

		// CHOOSE THE METHOD WITH WHICH TO DETERMINE TIPS
		setTipsBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toSetTips = new Intent(v.getContext(), SetTips.class);
				startActivity(toSetTips);
			}
		});

		// SELECT A DRINKING GOAL(poverty, sobriety, tally)
		setGoalBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toSetGoal = new Intent(v.getContext(), SetGoal.class);
				startActivity(toSetGoal);
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

		// TO TAB SUMMARY
		startTabBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toCurrentTab = new Intent(v.getContext(),
						CurrentTab.class);
				startActivity(toCurrentTab);
			}
		});

	}// end onCreate

} // end StartTab Class
