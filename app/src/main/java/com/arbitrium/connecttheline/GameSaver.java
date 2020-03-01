package com.arbitrium.connecttheline;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class GameSaver {

	private static GameSaver mContext = null;
	private Context appContext = null;
	public boolean isPreviousOpened;
	public int lastLevelSlected;
	public String lastPackSelected;
	public boolean soundOff;

	public static GameSaver sharedInstance(Context context) {
		if (mContext == null) {
			mContext = new GameSaver();
			mContext.appContext = context;
			mContext.loadState();
		}

		return mContext;
	}

	public int getBestMove4PackedBoard(String str) {
		return PreferenceManager.getDefaultSharedPreferences(appContext)
				.getInt("X" + str, 0);
	}

	public void loadState() {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(appContext);
		lastPackSelected = pref.getString("lastSelectedPack", "5x5");
		lastLevelSlected = pref.getInt("lastLevel2Play", 1);
		soundOff = pref.getBoolean("soundOff", false);
		isPreviousOpened = pref.getBoolean("isFirstRun", true);
		if (isPreviousOpened) {
			Editor var2 = pref.edit();
			var2.putBoolean("isFirstRun", false);
			var2.commit();
		}

	}

	public void saveState() {
		Editor edit = PreferenceManager.getDefaultSharedPreferences(appContext)
				.edit();
		edit.putString("lastSelectedPack", lastPackSelected);
		edit.putInt("lastLevel2Play", lastLevelSlected);
		edit.putBoolean("soundOff", soundOff);
		edit.putBoolean("isFirstRun", isPreviousOpened);
		edit.commit();
	}

	public void setBestMove4PackedBoard(String str, int value) {
		Editor edit = PreferenceManager.getDefaultSharedPreferences(appContext)
				.edit();
		edit.putInt("X" + str, value);
		edit.commit();
	}
}
