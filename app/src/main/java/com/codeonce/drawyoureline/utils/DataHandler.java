package com.codeonce.drawyoureline.utils;

import java.util.ArrayList;

import com.codeonce.drawyoureline.GameActivity;
import com.codeonce.drawyoureline.GameSaver;
import com.codeonce.drawyoureline.LevelManager;
import com.codeonce.drawyoureline.R;

import android.content.Context;
import android.graphics.Typeface;

public class DataHandler {

	public static boolean leaderboard_required = true;
	public static boolean video_ads = true;
	public static boolean tapjoy_ads = true;

	private static int device_width = 480;
	private static int device_height = 800;
	private static String current_pack = "5x5";
	private static int current_level = 1;
	private static int totalLevels = 0;
	private static int totalStarsFound = 0;
	private static int totalLevelsCompleted = 0;

	public static int getDevice_width() {
		return device_width;
	}

	public static void setDevice_width(int device_width) {
		DataHandler.device_width = device_width;
	}

	public static Typeface getTypeface(Context context) {
		return Typeface.createFromAsset(context.getAssets(), "font.dat");
	}

	public static int getDevice_height() {
		return device_height;
	}

	public static String getLast_pack() {
		return current_pack;
	}

	public static void setCurrentLevel(int level) {
		DataHandler.current_level = level;
	}

	public static int getCuurrentLevel() {
		return DataHandler.current_level;
	}

	public static void setCurrentPack(String last_pack) {
		DataHandler.current_pack = last_pack;
	}

	public static void setDevice_height(int device_height) {
		DataHandler.device_height = device_height;
	}

	public static int checkAndReturnColor(String packName, Context context) {
		int color = context.getResources().getColor(R.color.pack5);
		if (packName.contentEquals("5x5") || packName.contentEquals("a5x5")) {
			color = context.getResources().getColor(R.color.pack5);
		} else if (packName.contentEquals("6x6") || packName.contentEquals("a6x6")) {
			color = context.getResources().getColor(R.color.pack6);
		} else if (packName.contentEquals("7x7") || packName.contentEquals("a7x7")) {
			color = context.getResources().getColor(R.color.pack7);
		} else if (packName.contentEquals("8x8") || packName.contentEquals("a8x8")) {
			color = context.getResources().getColor(R.color.pack8);
		} else if (packName.contentEquals("9x9") || packName.contentEquals("a9x9")) {
			color = context.getResources().getColor(R.color.pack9);
		} else if (packName.contentEquals("10x10") || packName.contentEquals("a10x10")) {
			color = context.getResources().getColor(R.color.pack10);
		} else if (packName.contentEquals("11x11")) {
			color = context.getResources().getColor(R.color.pack11);
		} else if (packName.contentEquals("12x12")) {
			color = context.getResources().getColor(R.color.pack12);
		} else if (packName.contentEquals("13x13")) {
			color = context.getResources().getColor(R.color.pack13);
		} else if (packName.contentEquals("14x14")) {
			color = context.getResources().getColor(R.color.pack14);
		}

		PrefClass p1 = new PrefClass(context);
		p1.saveColor(color);
		return color;
	}

	public static int starsFoundInPack(String packName, Context context) {
		int totalStarsFound = 0, star = 0;
		int totalLevels = 0, levelsCompleted = 0;
		String[] levelsData;
		ArrayList<String> levelList = LevelManager.sharedInstance(context.getResources()).findAndSelectPack(packName);

		for (int i = 0; i < levelList.size() / 5; i++) {
			for (int j = 0; j < 5; j++) {
				// calculate total levels
				totalLevels++;
				// calculate the number of stars in a level
				levelsData = ((String) levelList.get(i * 5 + j)).split("=");
				if (levelsData.length == 2) {
					String question = levelsData[0];
					int minMoves = (-1 + question.split(";").length) / 2;
					int movesTaken = GameSaver.sharedInstance(context).getBestMove4PackedBoard(question);
					star = 0;
					if (movesTaken > 0)
						star = GameActivity.starRatingFromSteps(movesTaken, minMoves);
				}
				if (star != 0)
					levelsCompleted++;
				totalStarsFound += star;
			}
		}

		return totalStarsFound;
	}

	public static int totalStarsFound(Context context) {
		int totalStarsFound = 0;
		String[] pack = { "5x5", "6x6", "7x7", "8x8", "9x9", "10x10", "11x11", "12x12", "13x13", "14x14", "a5x5",
				"a6x6", "a7x7", "a8x8", "a9x9", "a10x10" };
		for (int i = 0; i < pack.length; i++) {
			int stars = starsFoundInPack(pack[i], context);
			totalStarsFound += stars;
		}

		return totalStarsFound;
	}

}
