package com.arbitrium.connecttheline;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.res.Resources;

public class LevelManager {

	private static LevelManager levelManager = null;
	private HashMap<String, ArrayList<String>> mappingData = new HashMap<String, ArrayList<String>>();
	private Resources resources = null;

	public static LevelManager sharedInstance(Resources resources) {
		if (levelManager == null) {
			levelManager = new LevelManager();
			levelManager.resources = resources;
		}

		return levelManager;
	}

	public ArrayList<String> findAndSelectPack(String packName) {
		ArrayList<String> list = mappingData.get(packName);
		if (list == null) {
			loadPack(packName);
			list = mappingData.get(packName);
		}
		return list;
	}

	public int loadPack(String packName) {
		int i = 1;
		int j = 2130968582;
		ArrayList<String> list;
		list = new ArrayList<String>(500);

		mappingData.put(packName, list);

		String str = null;


		if (packName.compareToIgnoreCase("5x5") == 0) {
			j = R.raw.five;
		} else if (packName.compareToIgnoreCase("6x6") == 0) {
			j = R.raw.six;
		} else if (packName.compareToIgnoreCase("7x7") == 0) {
			j = R.raw.seven;
		} else if (packName.compareToIgnoreCase("8x8") == 0) {
			j = R.raw.eight;
		} else if (packName.compareToIgnoreCase("9x9") == 0) {
			j = R.raw.nine;
		} else if (packName.compareToIgnoreCase("10x10") == 0) {
			j = R.raw.ten;
		} else if (packName.compareToIgnoreCase("11x11") == 0) {
			j = R.raw.eleven;
		} else if (packName.compareToIgnoreCase("12x12") == 0) {
			j = R.raw.twelve;
		} else if (packName.compareToIgnoreCase("13x13") == 0) {
			j = R.raw.thirteen;
		} else if (packName.compareToIgnoreCase("14x14") == 0) {
			j = R.raw.fourteen;
		} else if (packName.compareToIgnoreCase("a5x5") == 0) {
			j = R.raw.afive;
		} else if (packName.compareToIgnoreCase("a6x6") == 0) {
			j = R.raw.asix;
		} else if (packName.compareToIgnoreCase("a7x7") == 0) {
			j = R.raw.aseven;
		} else if (packName.compareToIgnoreCase("a8x8") == 0) {
			j = R.raw.aeight;
		} else if (packName.compareToIgnoreCase("a9x9") == 0) {
			j = R.raw.anine;
		} else if (packName.compareToIgnoreCase("a10x10") == 0) {
			j = R.raw.aten;
		}

		try {
			BufferedReader bfReader = new BufferedReader(new InputStreamReader(
					resources.openRawResource(j)));
			str = bfReader.readLine();
			while (str != null) {
				list.add(str);
				str = bfReader.readLine();
			}
			bfReader.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return list.size();

	}

	public String newBoardForPack(String str, int value, boolean isThere,
			Activity activity) {
		ArrayList list = findAndSelectPack(str);
		if (value > 0) {
			value--;
		}
		if (value < list.size()) {
			return (String) list.get(value);
		}
		return null;
	}

	public int getNumberOfPacks(String str) {
		return findAndSelectPack(str).size();
	}
}
