package com.diligencedojo.tabsitter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class SetGoal extends Activity {
	// TinyDB saved value handles: (integer)"drinksMax", (integer)"maxDollars",
	// (boolean)"isSobriety", (boolean)"isSaver", (boolean)"isCount"

	Button alarmBtn, lobbyBtn, settingsBtn, sobrietyBtn, saverBtn, countBtn,
			changeMaxBtn, startTabBtn, sobrietyBtnSel, saverBtnSel,
			countBtnSel;
	TextView goalExpl, maxText, maxNumber, decPoint;
	EditText inputMax;
	boolean isSobriety, isSaver, isCount = false;
	View view;
	int drinksMax, maxDollars = 0;
	String[] modeExpl = {
			"You will be alerted when you reach the number of drinks you have set as your limit.",
			"You will be alerted when you reach the amount of money you have set as your limit.",
			"This allows you to keep a count of the drinks you have purchased.  There is no tab limit "
					+ "and there will be no alerts sent at any time." };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_goal_layout);

		sobrietyBtn = (Button) findViewById(R.id.sobriety_button);
		saverBtn = (Button) findViewById(R.id.saver_button);
		countBtn = (Button) findViewById(R.id.count_button);
		changeMaxBtn = (Button) findViewById(R.id.change_max_button);
		alarmBtn = (Button) findViewById(R.id.alarm_button);
		lobbyBtn = (Button) findViewById(R.id.lobby_button);
		settingsBtn = (Button) findViewById(R.id.settings_button);
		startTabBtn = (Button) findViewById(R.id.calculator_button);
		goalExpl = (TextView) findViewById(R.id.goal_explanation);
		maxText = (TextView) findViewById(R.id.max_text);
		maxNumber = (TextView) findViewById(R.id.max_number);
		sobrietyBtnSel = (Button) findViewById(R.id.sobriety_button_sel);
		saverBtnSel = (Button) findViewById(R.id.saver_button_sel);
		countBtnSel = (Button) findViewById(R.id.count_button_sel);

		getSavedValues();

		sobrietyBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goalExpl.setVisibility(View.VISIBLE);
				maxText.setVisibility(View.VISIBLE);
				maxNumber.setVisibility(View.VISIBLE);
				changeMaxBtn.setVisibility(View.VISIBLE);
				maxNumber.setText(getResources().getString(
						R.string.number_default));
				goalExpl.setText(modeExpl[0]);

				if (sobrietyBtnSel.getVisibility() == View.VISIBLE)
					sobrietyBtnSel.setVisibility(View.INVISIBLE);
				else if (sobrietyBtnSel.getVisibility() == View.INVISIBLE) {
					sobrietyBtnSel.setVisibility(View.VISIBLE);
					saverBtnSel.setVisibility(View.INVISIBLE);
					countBtnSel.setVisibility(View.INVISIBLE);
				}
			}
		});

		saverBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goalExpl.setVisibility(View.VISIBLE);
				maxText.setVisibility(View.VISIBLE);
				maxNumber.setVisibility(View.VISIBLE);
				changeMaxBtn.setVisibility(View.VISIBLE);
				maxNumber.setText(getResources().getString(
						R.string.dollar_default));
				goalExpl.setText(modeExpl[1]);

				if (saverBtnSel.getVisibility() == View.VISIBLE)
					saverBtnSel.setVisibility(View.INVISIBLE);
				else if (saverBtnSel.getVisibility() == View.INVISIBLE) {
					saverBtnSel.setVisibility(View.VISIBLE);
					countBtnSel.setVisibility(View.INVISIBLE);
					sobrietyBtnSel.setVisibility(View.INVISIBLE);
				}
			}
		});

		countBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goalExpl.setVisibility(View.VISIBLE);
				maxText.setVisibility(View.GONE);
				maxNumber.setVisibility(View.GONE);
				changeMaxBtn.setVisibility(View.GONE);
				goalExpl.setText(modeExpl[2]);

				if (countBtnSel.getVisibility() == View.VISIBLE) {
					countBtnSel.setVisibility(View.INVISIBLE);
				} else if (countBtnSel.getVisibility() == View.INVISIBLE) {
					countBtnSel.setVisibility(View.VISIBLE);
					sobrietyBtnSel.setVisibility(View.INVISIBLE);
					saverBtnSel.setVisibility(View.INVISIBLE);
				}
			}
		});

		// CHANGE THE MAX YOU INTEND TO DRINK OR THE MAX YOU
		// INTEND TO SPEND
		changeMaxBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ToastShooter tShoot = new ToastShooter();

				if (sobrietyBtnSel.getVisibility() == View.VISIBLE) {
					isSobriety = true;
					isSaver = isCount = false;
				} else if (saverBtnSel.getVisibility() == View.VISIBLE) {
					isSaver = true;
					isSobriety = isCount = false;
				} else if (countBtnSel.getVisibility() == View.VISIBLE) {
					isSaver = true;
					isSobriety = isCount = false;
				}

				chooseDialog(isSobriety, isSaver, isCount);

				// if (goalExpl.getText().toString().matches(modeExpl[0])) {
				// isSobriety = true;
				// isSaver = isCount = false;
				// chooseDialog(isSobriety, isSaver, isCount);
				// } else if
				// (goalExpl.getText().toString().matches(modeExpl[1])) {
				// isSaver = true;
				// isSobriety = isCount = false;
				// chooseDialog(isSobriety, isSaver, isCount);
				// } else if
				// (goalExpl.getText().toString().matches(modeExpl[2])) {
				// isCount = true;
				// isSaver = isSobriety = false;
				// chooseDialog(isSobriety, isSaver, isCount);
				// } else
				// Toast.makeText(
				// getApplicationContext(),
				// "Select a drinking goal from the top row of buttons.",
				// Toast.LENGTH_SHORT).show();
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

	public void getSavedValues() {
		TinyDB tinydb = new TinyDB(getApplicationContext());
		isSobriety = tinydb.getBoolean("isSobriety");
		isSaver = tinydb.getBoolean("isSaver");
		isCount = tinydb.getBoolean("isCount");
		drinksMax = tinydb.getInt("drinksMax");
		maxDollars = tinydb.getInt("maxDollars");

		// display goal explanation and pressed button indicated
		// by the saved boolean values above
		if (isSobriety) {
			sobrietyBtnSel.setVisibility(View.VISIBLE);
			goalExpl.setText(modeExpl[0]);
			maxNumber.setText(drinksMax);
		} else if (isSaver) {
			saverBtnSel.setVisibility(View.VISIBLE);
			goalExpl.setText(modeExpl[1]);
			maxNumber.setText("$" + maxDollars + ".00");
		} else if (isCount) {
			countBtnSel.setVisibility(View.VISIBLE);
			goalExpl.setText(modeExpl[2]);
			maxText.setVisibility(View.GONE);
		}

	} // end getSavedValues()

	public void OnBackPressed() {
		TinyDB tinydb = new TinyDB(getApplicationContext());

		// if (goalExpl.getText().toString().matches(modeExpl[0])) {
		// isSobriety = true;
		// isSaver = isCount = false;
		// } else if (goalExpl.getText().toString().matches(modeExpl[1])) {
		// isSaver = true;
		// isSobriety = isCount = false;
		// chooseDialog(isSobriety, isSaver, isCount);
		// } else if (goalExpl.getText().toString().matches(modeExpl[2])) {
		// isCount = true;
		// isSaver = isSobriety = false;
		// chooseDialog(isSobriety, isSaver, isCount);
		// }

		if (sobrietyBtnSel.getVisibility() == View.VISIBLE)
			// sobrietyBtnSel, saverBtnSel,

			tinydb.putBoolean("isSobriety", isSobriety);
		tinydb.putBoolean("isSaver", isSaver);
		tinydb.putBoolean("isCount", isCount);

		// Toast.makeText(getApplicationContext(),
		// "Beer: " + tinydb.getBoolean("beer"), Toast.LENGTH_SHORT)
		// .show();

		super.onBackPressed();
	}

	public void chooseDialog(boolean isSobriety, boolean isSaver,
			boolean isCount) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		LayoutInflater inflater = getLayoutInflater();
		view = inflater.inflate(R.layout.number_picker, null);

		final NumberPicker cent = (NumberPicker) view
				.findViewById(R.id.cent_picker);
		cent.setVisibility(View.GONE);
		decPoint = (TextView) view.findViewById(R.id.decimal_point);
		decPoint.setVisibility(View.GONE);

		if ((isSobriety == false) && (isSaver == false) && (isCount == false)) {
			Toast.makeText(getApplicationContext(),
					"Select a drinking goal from the top row of buttons.",
					Toast.LENGTH_SHORT).show();
			return;
		}

		// IF IT'S IN SOBRIETY MODE
		if (isSobriety) {
			final NumberPicker drinks = (NumberPicker) view
					.findViewById(R.id.dollar_picker);

			alert.setView(view).setPositiveButton("Enter",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							drinksMax = drinks.getValue();
							String drinksMaxStr = Integer.toString(drinksMax);
							// Log.d("DBG", "Price is (double): " + tipAmt);
							TinyDB tinydb = new TinyDB(getApplicationContext());
							tinydb.putInt("drinksMax", drinksMax);

							maxNumber.setText(drinksMaxStr + " drinks");
						}
					});

			String numDrinks[] = new String[201];
			for (int a = 0; a < 201; a++)
				numDrinks[a] = a + " drinks";

			drinks.setDisplayedValues(numDrinks);
			drinks.setMinValue(0);
			drinks.setMaxValue(200);
			drinks.setWrapSelectorWheel(true);
			drinks.setValue(0);
			drinks.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

			alert.show();

		} // ELSE IF IT'S IN MONEY SAVER MODE
		else if (isSaver) {
			final NumberPicker dollars = (NumberPicker) view
					.findViewById(R.id.dollar_picker);

			alert.setView(view).setPositiveButton("Enter",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							maxDollars = dollars.getValue();
							String maxDollarsStr = Integer.toString(maxDollars);
							TinyDB tinydb = new TinyDB(getApplicationContext());
							tinydb.putInt("maxDollars", maxDollars);

							maxNumber.setText("$" + maxDollarsStr + ".00");
						}
					});

			String bucks[] = new String[301];
			for (int a = 0; a < 301; a++)
				bucks[a] = "$" + a + ".00";

			dollars.setDisplayedValues(bucks);
			dollars.setMinValue(0);
			dollars.setMaxValue(20);
			dollars.setWrapSelectorWheel(true);
			dollars.setValue(0);
			dollars.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

			alert.show();
		}
	} // end chooseDialog()

} // end SetGoal Class
