package com.diligencedojo.tabsitter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SetPrices extends Activity {
	// TinyDB saved value handles: (string)"beerPrice", (string)"liquorPrice",
	// (string)"shotPrice", (string)"winePrice", (string)"otherPrice"

	Button alarmBtn, lobbyBtn, settingsBtn, startTabBtn, beerBtn, liquorBtn,
			shotBtn, wineBtn, otherBtn, beerBtnNot, liquorBtnNot, shotBtnNot,
			wineBtnNot, otherBtnNot;
	TextView beerPrice, liquorPrice, shotPrice, winePrice, otherPrice;
	String beerPriceStr, liquorPriceStr, shotPriceStr, winePriceStr,
			otherPriceStr;
	boolean priceEntered, beerIsSel, liquorIsSel, shotIsSel, wineIsSel,
			otherIsSel = false;
	EditText enterPrice, inputPrice;
	MoneyWatcher moneyWatcher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_prices_layout);

		beerBtn = (Button) findViewById(R.id.beer_button);
		liquorBtn = (Button) findViewById(R.id.liquor_drink_button);
		shotBtn = (Button) findViewById(R.id.shot_button);
		wineBtn = (Button) findViewById(R.id.wine_button);
		otherBtn = (Button) findViewById(R.id.other_button);
		beerPrice = (TextView) findViewById(R.id.beer_price);
		liquorPrice = (TextView) findViewById(R.id.liquor_price);
		shotPrice = (TextView) findViewById(R.id.shot_price);
		winePrice = (TextView) findViewById(R.id.wine_price);
		otherPrice = (TextView) findViewById(R.id.other_price);
		beerBtnNot = (Button) findViewById(R.id.beer_button_not);
		liquorBtnNot = (Button) findViewById(R.id.liquor_button_not);
		shotBtnNot = (Button) findViewById(R.id.shot_button_not);
		wineBtnNot = (Button) findViewById(R.id.wine_button_not);
		otherBtnNot = (Button) findViewById(R.id.other_button_not);
		alarmBtn = (Button) findViewById(R.id.alarm_button);
		lobbyBtn = (Button) findViewById(R.id.lobby_button);
		settingsBtn = (Button) findViewById(R.id.settings_button);
		startTabBtn = (Button) findViewById(R.id.calculator_button);

		getSavedValues(); // set prices based on saved values

		beerBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				enterScoreDialog("beer");
			}
		});

		liquorBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				enterScoreDialog("liquor");
			}
		});

		shotBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				enterScoreDialog("shot");
			}
		});

		wineBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				enterScoreDialog("wine");
			}
		});

		otherBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				enterScoreDialog("other");
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

		// TO SETTINGS
		settingsBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toSettings = new Intent(v.getContext(), Settings.class);
				startActivity(toSettings);
			}
		});
	} // end onCreate

	public void getSavedValues() {
		TinyDB tinydb = new TinyDB(getApplicationContext());
		beerIsSel = tinydb.getBoolean("beer");
		liquorIsSel = tinydb.getBoolean("liquor");
		shotIsSel = tinydb.getBoolean("shot");
		wineIsSel = tinydb.getBoolean("wine");
		otherIsSel = tinydb.getBoolean("other");
		beerPriceStr = tinydb.getString("beerPrice");
		liquorPriceStr = tinydb.getString("liquorPrice");
		shotPriceStr = tinydb.getString("shotPrice");
		winePriceStr = tinydb.getString("winePrice");
		otherPriceStr = tinydb.getString("otherPrice");

		// whatever kinds of drinks are not activated, put a clear white button
		// over them and set them to where they can't be clicked
		if (!beerIsSel) {
			beerBtnNot.setVisibility(View.VISIBLE);
			beerBtnNot.setTextColor(getResources().getColor(R.color.dark_gray));
			beerBtn.setTextColor(getResources().getColor(R.color.dark_gray));
			beerPrice.setTextColor(getResources().getColor(R.color.dark_gray));
		} else if ((beerIsSel) && (beerPriceStr != null)
				&& (!beerPriceStr.matches("$0.00"))
				&& (!beerPriceStr.isEmpty()))
			beerPrice.setText(beerPriceStr);

		if (!liquorIsSel) {
			liquorBtnNot.setVisibility(View.VISIBLE);
			liquorBtnNot.setTextColor(getResources()
					.getColor(R.color.dark_gray));
			liquorBtn.setTextColor(getResources().getColor(R.color.dark_gray));
			liquorPrice
					.setTextColor(getResources().getColor(R.color.dark_gray));
		} else if ((liquorIsSel) && (liquorPriceStr != null)
				&& (!liquorPriceStr.matches("$0.00"))
				&& (!liquorPriceStr.isEmpty()))
			liquorPrice.setText(liquorPriceStr);

		if (!shotIsSel) {
			shotBtnNot.setVisibility(View.VISIBLE);
			shotBtnNot.setTextColor(getResources().getColor(R.color.dark_gray));
			shotBtn.setTextColor(getResources().getColor(R.color.dark_gray));
			shotPrice.setTextColor(getResources().getColor(R.color.dark_gray));
		} else if ((shotIsSel) && (shotPriceStr != null)
				&& (!shotPriceStr.matches("$0.00"))
				&& (!shotPriceStr.isEmpty()))
			shotPrice.setText(shotPriceStr);

		if (!wineIsSel) {
			wineBtnNot.setVisibility(View.VISIBLE);
			wineBtnNot.setTextColor(getResources().getColor(R.color.dark_gray));
			wineBtn.setTextColor(getResources().getColor(R.color.dark_gray));
			winePrice.setTextColor(getResources().getColor(R.color.dark_gray));
		} else if ((wineIsSel) && (winePriceStr != null)
				&& (!winePriceStr.matches("$0.00"))
				&& (!winePriceStr.isEmpty()))
			winePrice.setText(winePriceStr);

		if (!otherIsSel) {
			otherBtnNot.setVisibility(View.VISIBLE);
			otherBtnNot
					.setTextColor(getResources().getColor(R.color.dark_gray));
			otherBtn.setTextColor(getResources().getColor(R.color.dark_gray));
			otherPrice.setTextColor(getResources().getColor(R.color.dark_gray));
		} else if ((otherIsSel) && (otherPriceStr != null)
				&& (!otherPriceStr.matches("$0.00"))
				&& (!otherPriceStr.isEmpty()))
			otherPrice.setText(otherPriceStr);

	} // end getSavedValues()

	@Override
	public void onBackPressed() {
		TinyDB tinydb = new TinyDB(getApplicationContext());
		String tempPrice;

		tempPrice = beerPrice.getText().toString();
		if ((!tempPrice.matches("")) && (!tempPrice.matches("$0.00")))
			tinydb.putString("beerPrice", tempPrice);

		tempPrice = liquorPrice.getText().toString();
		if ((!tempPrice.matches("")) && (!tempPrice.matches("$0.00")))
			tinydb.putString("liquorPrice", tempPrice);

		tempPrice = shotPrice.getText().toString();
		if ((!tempPrice.matches("")) && (!tempPrice.matches("$0.00")))
			tinydb.putString("shotPrice", tempPrice);

		tempPrice = winePrice.getText().toString();
		if ((!tempPrice.matches("")) && (!tempPrice.matches("$0.00")))
			tinydb.putString("winePrice", tempPrice);

		tempPrice = otherPrice.getText().toString();
		if ((!tempPrice.matches("")) && (!tempPrice.matches("$0.00")))
			tinydb.putString("otherPrice", tempPrice);

		if (checkPrices())
			super.onBackPressed();

	} // end OnBackPressed()

	// CHECK THAT PRICES HAVE BEEN SET FOR ALL ENABLED DRINKS
	public boolean checkPrices() {
		ToastShooter tShoot = new ToastShooter();
		String tempPrice;

		tempPrice = beerPrice.getText().toString();
		if (beerBtnNot.getVisibility() != View.VISIBLE) {
			if (tempPrice.equals("$0.00")) {
				tShoot.displayToast("Set the beer price", this);
				return false;
			}
		}

		tempPrice = liquorPrice.getText().toString();
		if (liquorBtnNot.getVisibility() != View.VISIBLE) {
			if (tempPrice.equals("$0.00")) {
				tShoot.displayToast("Set the liquor drink price", this);
				return false;
			}
		}

		tempPrice = shotPrice.getText().toString();
		if (shotBtnNot.getVisibility() != View.VISIBLE) {
			if (tempPrice.equals("$0.00")) {
				tShoot.displayToast("Set the shot price", this);
				return false;
			}
		}

		tempPrice = winePrice.getText().toString();
		if (wineBtnNot.getVisibility() != View.VISIBLE) {
			if (tempPrice.equals("$0.00")) {
				tShoot.displayToast("Set the wine price", this);
				return false;
			}
		}

		tempPrice = otherPrice.getText().toString();
		if (otherBtnNot.getVisibility() != View.VISIBLE) {
			if (tempPrice.equals("$0.00")) {
				tShoot.displayToast("Set the price for other", this);
				return false;
			}
		}

		return true; // if it gets here, the prices are valid (not $0.00)

	} // end checkPrices()

	// ALERT DIALOG FOR USER ENTERED SCORE
	// **************************************
	@SuppressWarnings("deprecation")
	public void enterScoreDialog(final String pressedBtn) {
		AlertDialog alert = new AlertDialog.Builder(this).create();

		if (pressedBtn.matches("beer"))
			alert.setTitle("Beer Price");
		else if (pressedBtn.matches("liquor"))
			alert.setTitle("Liquor Drink Price");
		else if (pressedBtn.matches("shot"))
			alert.setTitle("Shot Price");
		else if (pressedBtn.matches("wine"))
			alert.setTitle("Wine Price");
		else if (pressedBtn.matches("other"))
			alert.setTitle("Other Price");

		// Set an EditText view to get user input
		inputPrice = new EditText(this);
		inputPrice.setHint(R.string.price_default);
		// only allow digit input
		inputPrice.setInputType(InputType.TYPE_CLASS_NUMBER);

		alert.setView(inputPrice);
		alert.setCancelable(false);

		moneyWatcher = new MoneyWatcher(inputPrice);
		inputPrice.addTextChangedListener(moneyWatcher);

		alert.setButton("Enter Price", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String price = inputPrice.getText().toString();
				inputPrice.removeTextChangedListener(moneyWatcher);

				if ((pressedBtn.matches("beer")) && (!price.matches("")))
					beerPrice.setText(price);
				else if ((pressedBtn.matches("liquor")) && (!price.matches("")))
					liquorPrice.setText(price);
				else if ((pressedBtn.matches("shot")) && (!price.matches("")))
					shotPrice.setText(price);
				else if ((pressedBtn.matches("wine")) && (!price.matches("")))
					winePrice.setText(price);
				else if ((pressedBtn.matches("other")) && (!price.matches("")))
					otherPrice.setText(price);

				// allows user to reset item price to zero
				if ((pressedBtn.matches("beer")) && (price.matches("")))
					beerPrice.setText(getResources().getString(
							R.string.price_default));
				else if ((pressedBtn.matches("liquor")) && (price.matches("")))
					liquorPrice.setText(getResources().getString(
							R.string.price_default));
				else if ((pressedBtn.matches("shot")) && (price.matches("")))
					shotPrice.setText(getResources().getString(
							R.string.price_default));
				else if ((pressedBtn.matches("wine")) && (price.matches("")))
					winePrice.setText(getResources().getString(
							R.string.price_default));
				else if ((pressedBtn.matches("other")) && (price.matches("")))
					otherPrice.setText(getResources().getString(
							R.string.price_default));

				// force keyboard to disappear if still visible
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(inputPrice.getWindowToken(), 0);

				dialog.dismiss();
			}
		});
		alert.show();

		// center title of dialog
		TextView titleView = (TextView) alert.findViewById(getResources()
				.getIdentifier("alertTitle", "id", "android"));
		if (titleView != null)
			titleView.setGravity(Gravity.CENTER);
	}

} // end SetPrices Class
