package com.diligencedojo.tabsitter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;

public class SetTips extends Activity {
	// TinyDB saved value handles: (integer)"percent", (double)"tipAmt"

	Button alarmBtn, lobbyBtn, settingsBtn, perDrinkBtn, percentBtn,
			startTabBtn;
	Double tipAmt = 0.0;
	boolean isDollar = false;
	Switch tipsSwitch;
	LinearLayout tipMethodLayout, tipTextLayout;
	TextView decPoint, tipAmtText, percentText, perDrinkText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_tips_layout);

		perDrinkBtn = (Button) findViewById(R.id.per_drink_button);
		percentBtn = (Button) findViewById(R.id.percent_button);
		startTabBtn = (Button) findViewById(R.id.calculator_button);
		alarmBtn = (Button) findViewById(R.id.alarm_button);
		lobbyBtn = (Button) findViewById(R.id.lobby_button);
		settingsBtn = (Button) findViewById(R.id.settings_button);
		tipsSwitch = (Switch) findViewById(R.id.tips_switch);
		tipMethodLayout = (LinearLayout) findViewById(R.id.tip_method_layout);
		tipTextLayout = (LinearLayout) findViewById(R.id.tip_text_layout);
		tipAmtText = (TextView) findViewById(R.id.tip_amount);
		percentText = (TextView) findViewById(R.id.tips_percent_text);
		perDrinkText = (TextView) findViewById(R.id.tips_per_drink_text);

		tipsSwitch.setChecked(false);
		// TURN TIPPING OPTIONS ON/OFF
		tipsSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					tipMethodLayout.setVisibility(View.VISIBLE);
					tipTextLayout.setVisibility(View.VISIBLE);
				} else {
					tipMethodLayout.setVisibility(View.GONE);
					tipTextLayout.setVisibility(View.GONE);
				}
			}
		});

		// TIP SPECIFIED AMOUNT ON EACH DRINK
		perDrinkBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				isDollar = true;
				chooseDialog(isDollar);
			}
		});

		// TIP PERCENTAGE OF FINAL BILL
		percentBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				isDollar = false;
				chooseDialog(isDollar);
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

	} // end onCreate

	// DISPLAY ALERT DIALOG FOR DOLLAR AMOUNT OR PERCENT
	public void chooseDialog(boolean isDollar) {

		if (isDollar) {
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			LayoutInflater inflater = getLayoutInflater();
			View v = inflater.inflate(R.layout.number_picker, null);

			final NumberPicker dollar = (NumberPicker) v
					.findViewById(R.id.dollar_picker);
			final NumberPicker cent = (NumberPicker) v
					.findViewById(R.id.cent_picker);
			decPoint = (TextView) v.findViewById(R.id.decimal_point);

			alert.setView(v).setPositiveButton("Enter",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							int centActual = cent.getValue() * 25;
							String centActualStr = Integer.toString(centActual);
							String temp = dollar.getValue() + "."
									+ centActualStr;
							tipAmt = Double.parseDouble(temp);
							// Log.d("DBG", "Price is (double): " + tipAmt);
							TinyDB tinydb = new TinyDB(getApplicationContext());
							tinydb.putDouble("tipAmt", tipAmt);

							tipTextLayout.setVisibility(View.VISIBLE);
							tipAmtText.setText("$" + temp);
						}
					});

			String bucks[] = new String[31];
			for (int a = 0; a < 31; a++) {
				bucks[a] = "$ " + a;
			}

			dollar.setDisplayedValues(bucks);
			dollar.setMinValue(0);
			dollar.setMaxValue(30);
			dollar.setWrapSelectorWheel(true);
			dollar.setValue(0);
			dollar.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

			String cents[] = new String[4];
			for (int i = 0; i < 100; i += 25) {
				if (i == 0)
					cents[i / 25] = "0" + i;
				else
					cents[i / 25] = Integer.toString(i);
			}

			cent.setDisplayedValues(cents);
			cent.setMinValue(0);
			cent.setMaxValue(3);
			cent.setWrapSelectorWheel(true);
			cent.setValue(0);
			cent.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

			alert.show();
		} else {
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			LayoutInflater inflater = getLayoutInflater();
			View v = inflater.inflate(R.layout.number_picker, null);

			final NumberPicker percent = (NumberPicker) v
					.findViewById(R.id.dollar_picker);
			final NumberPicker cent = (NumberPicker) v
					.findViewById(R.id.cent_picker);
			decPoint = (TextView) v.findViewById(R.id.decimal_point);

			cent.setVisibility(View.GONE);
			decPoint.setVisibility(View.GONE);

			alert.setView(v).setPositiveButton("Enter",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							int percActual = percent.getValue() * 5;
							String centActualStr = Integer.toString(percActual);
							TinyDB tinydb = new TinyDB(getApplicationContext());
							tinydb.putInt("percent", percActual);

							tipTextLayout.setVisibility(View.VISIBLE);
							perDrinkText.setVisibility(View.GONE);
							percentText.setVisibility(View.VISIBLE);
							tipAmtText.setText(centActualStr + "%");
						}
					});

			String percPoints[] = new String[21];
			for (int a = 0; a < 101; a += 5) {
				percPoints[a / 5] = a + " %";
			}

			percent.setDisplayedValues(percPoints);
			percent.setMinValue(0);
			percent.setMaxValue(20);
			percent.setWrapSelectorWheel(true);
			percent.setValue(0);
			percent.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

			alert.show();
		}

	} // end chooseDialog()

} // end SetTips Class
