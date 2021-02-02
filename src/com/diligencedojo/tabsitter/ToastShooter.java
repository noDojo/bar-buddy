package com.diligencedojo.tabsitter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;

import com.github.johnpersano.supertoasts.SuperToast;

public class ToastShooter {

	// DISPLAY TOASTS
	// *****************
	public void displayToast(String message, Activity displayAct) {

		SuperToast superToast = new SuperToast(displayAct);
		superToast = new SuperToast(displayAct);
		superToast.setAnimations(SuperToast.Animations.FLYIN);
		superToast.setText(message);
		superToast.setTextSize(SuperToast.TextSize.MEDIUM);
		superToast.setTypefaceStyle(Typeface.BOLD);
		superToast.setGravity(Gravity.CENTER, 0, 0);
		superToast.setBackground(SuperToast.Background.GREEN);
		superToast.setTextColor(Color.BLACK);
		superToast.setDuration(SuperToast.Duration.VERY_SHORT);
		superToast.show();
	}

	// DISPLAY TOASTS
	// *****************
	public void displayToast(String message, Activity displayAct, String color) {

		SuperToast superToast = new SuperToast(displayAct);
		superToast = new SuperToast(displayAct);
		superToast.setAnimations(SuperToast.Animations.FLYIN);
		superToast.setText(message);
		superToast.setTextSize(SuperToast.TextSize.MEDIUM);
		superToast.setTypefaceStyle(Typeface.BOLD);
		superToast.setGravity(Gravity.CENTER, 0, 0);
		superToast.setBackground(SuperToast.Background.BLUE);
		superToast.setTextColor(Color.GREEN);
		superToast.setDuration(SuperToast.Duration.VERY_SHORT);
		superToast.show();
	}

	// DISPLAY TOASTS W/ ICONS
	// **************************
	public void displayToast(Integer resId, String message, Activity displayAct) {

		SuperToast superToast = new SuperToast(displayAct);
		superToast = new SuperToast(displayAct);
		superToast.setAnimations(SuperToast.Animations.FLYIN);
		superToast.setText(message);
		superToast.setTextSize(SuperToast.TextSize.MEDIUM);
		superToast.setTextColor(Color.BLACK);
		superToast.setTypefaceStyle(Typeface.BOLD);
		superToast.setBackground(SuperToast.Background.GREEN);
		superToast.setGravity(Gravity.CENTER, 0, 0);
		superToast.setIcon(resId, SuperToast.IconPosition.LEFT);
		superToast.setDuration(SuperToast.Duration.SHORT);
		superToast.show();
	}
	
} // end ToastShooter Class
