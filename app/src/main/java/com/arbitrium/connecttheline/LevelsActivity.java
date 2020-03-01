package com.arbitrium.connecttheline;

import java.util.ArrayList;

import com.arbitrium.connecttheline.utils.BlankView;
import com.arbitrium.connecttheline.utils.DataHandler;
import com.arbitrium.connecttheline.utils.LevelButton;
import com.arbitrium.connecttheline.utils.PrefClass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class LevelsActivity extends Activity {

	private Context context = this;
	private TableLayout tbLayout;
	private TableRow tbRow;
	private TableLayout.LayoutParams rowParams;
	private String[] levelsData;
	private int star = 0; // to store the number of stars for a level
	private boolean resumable = false;
	private int margin;
	private int color, totalLevels, levelsCompleted, totalStarsFound;
	private TextView tvpackName, tvleveldetails, tvstardetails;
	private Drawable draw;
	private int textSize = 20;
	public ArrayList<String> levelList = null;
	public String packShowName = "5x5";
	public static String packName = "5x5";

	public void onCreate(Bundle instanceSavedState) {
		super.onCreate(instanceSavedState);
		PrefClass p1 = new PrefClass(context);
		setContentView(R.layout.levelselectscreen);

		if (DataHandler.getDevice_width() >= 800)
			textSize = 25;

		totalLevels = 0;
		levelsCompleted = 0;
		totalStarsFound = 0;

		tbLayout = (TableLayout) findViewById(R.id.tbLayout);

		rowParams = new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		margin = DataHandler.getDevice_width() / 64;
		rowParams.setMargins(margin, margin, margin, 0);

		Bundle bundle = getIntent().getExtras();
		packName = bundle.getString("packname");
		packShowName = bundle.getString("packDisplayName");

		tvpackName = (TextView) findViewById(R.id.tvpackname);
		tvleveldetails = (TextView) findViewById(R.id.tvleveldetails);
		tvstardetails = (TextView) findViewById(R.id.tvstardetails);

		tvpackName.setText(packShowName);
		tvpackName.setTypeface(DataHandler.getTypeface(context));

		tvleveldetails.setTypeface(DataHandler.getTypeface(context));
		tvstardetails.setTypeface(DataHandler.getTypeface(context));

		levelList = LevelManager.sharedInstance(getResources())
				.findAndSelectPack(packName);

		if (packName.contentEquals("5x5") || packName.contentEquals("a5x5")) {
			color = getResources().getColor(R.color.pack5);
			draw = (Drawable) context.getResources().getDrawable(
					R.drawable.levels_rect_five);
		} else if (packName.contentEquals("6x6")
				|| packName.contentEquals("a6x6")) {
			color = getResources().getColor(R.color.pack6);
			draw = (Drawable) context.getResources().getDrawable(
					R.drawable.levels_rect_six);
		} else if (packName.contentEquals("7x7")
				|| packName.contentEquals("a7x7")) {
			color = getResources().getColor(R.color.pack7);
			draw = (Drawable) context.getResources().getDrawable(
					R.drawable.levels_rect_seven);
		} else if (packName.contentEquals("8x8")
				|| packName.contentEquals("a8x8")) {
			color = getResources().getColor(R.color.pack8);
			draw = (Drawable) context.getResources().getDrawable(
					R.drawable.levels_rect_eight);
		} else if (packName.contentEquals("9x9")
				|| packName.contentEquals("a9x9")) {
			draw = (Drawable) context.getResources().getDrawable(
					R.drawable.levels_rect_nine);
			color = getResources().getColor(R.color.pack9);
		} else if (packName.contentEquals("10x10")
				|| packName.contentEquals("a10x10")) {
			draw = (Drawable) context.getResources().getDrawable(
					R.drawable.levels_rect_ten);
			color = getResources().getColor(R.color.pack10);
		} else if (packName.contentEquals("11x11")) {
			draw = (Drawable) context.getResources().getDrawable(
					R.drawable.levels_rect_eleven);
			color = getResources().getColor(R.color.pack11);
		} else if (packName.contentEquals("12x12")) {
			draw = (Drawable) context.getResources().getDrawable(
					R.drawable.levels_rect_twelve);
			color = getResources().getColor(R.color.pack12);
		} else if (packName.contentEquals("13x13")) {
			draw = (Drawable) context.getResources().getDrawable(
					R.drawable.levels_rect_thirteen);
			color = getResources().getColor(R.color.pack13);
		} else if (packName.contentEquals("14x14")) {
			draw = (Drawable) context.getResources().getDrawable(
					R.drawable.levels_rect_fourteen);
			color = getResources().getColor(R.color.pack14);
		}

		tvpackName.setTextColor(color);

		PrefClass.saveColor(color);
		// adding buttons to the layout
		setButtonsAndStars();
		resumable = true;
		tvleveldetails.setText(String.valueOf(levelsCompleted) + "/"
				+ String.valueOf(totalLevels));
		tvstardetails.setText(String.valueOf(totalStarsFound) + "/"
				+ String.valueOf(totalLevels * 3));
	}

	private void setButtonsAndStars() {
		totalLevels = 0;
		levelsCompleted = 0;
		totalStarsFound = 0;
		tbLayout.removeAllViews();
		for (int i = 0; i < levelList.size() / 5; i++) {
			tbRow = new TableRow(context);
			tbRow.removeAllViews();
			for (int j = 0; j < 5; j++) {
				// calculate total levels
				totalLevels++;
				// calculate the number of stars in a level
				levelsData = ((String) levelList.get(i * 5 + j)).split("=");
				if (levelsData.length == 2) {
					String question = levelsData[0];
					int minMoves = (-1 + question.split(";").length) / 2;
					int movesTaken = GameSaver.sharedInstance(this)
							.getBestMove4PackedBoard(question);
					star = 0;
					if (movesTaken > 0)
						star = GameActivity.starRatingFromSteps(movesTaken,
								minMoves);
				}
				if (star != 0)
					levelsCompleted++;
				totalStarsFound += star;
				LevelButton l1 = new LevelButton(context, star,
						String.valueOf(i * 5 + j + 1), packName, color, draw,
						textSize);
				l1.setTag(String.valueOf(i * 5 + j + 1));
				tbRow.addView(l1);
				if ((i * 5 + j + 1) % 5 != 0)
					tbRow.addView(new BlankView(context, 3, margin));
				l1.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						String TAG = (String) v.getTag();
						if (TAG != null) {
							int level = Integer.valueOf(TAG);
							Intent i = new Intent(context, GameActivity.class);
//							i.putExtra("pack", packName);
//							i.putExtra("pid", level);
							DataHandler.setCurrentPack(packName);
							DataHandler.setCurrentLevel(level);
							startActivity(i);
						}

					}
				});
			}
			tbLayout.addView(tbRow, rowParams);
		}
	}

	private void updateStars() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				setButtonsAndStars();
				tvleveldetails.setText(String.valueOf(levelsCompleted) + "/"
						+ String.valueOf(totalLevels));
				tvstardetails.setText(String.valueOf(totalStarsFound) + "/"
						+ String.valueOf(totalLevels * 3));
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (resumable)
			updateStars();
	}

}
