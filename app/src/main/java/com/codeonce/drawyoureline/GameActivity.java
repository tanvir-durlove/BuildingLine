package com.codeonce.drawyoureline;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

import com.codeonce.drawyoureline.utils.DataHandler;
import com.codeonce.drawyoureline.utils.PrefClass;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.InterstitialAd;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class GameActivity extends Activity implements OnTouchListener, OnClickListener {

	static final int DIALOG_WIN_ID = 1;
	public static final int SOUND_MOVED = 1;
	public static final int SOUND_WIN = 2;
	public static int screenHeight;
	public static int screenWidth;
	private TextView tvhint, tvhintlabel, tvpack, tvlevelLabel, tvlevel, tvbest, tvbestLabel, tvmoves, tvmovesLabel;
	int currentStars;
	int hFactHeight;
	int lFactHeight;
	Point blockPos = new Point();
	Point touchPos = new Point();
	boolean isHintAvailable;
	String board;
	DotsView dtView = null;
	GameState gameState;
	private AudioManager mAudioManager;
	private SoundPool mSoundPool;
	private HashMap<Integer, Integer> mSoundPoolMap;
	public String packName;
	public int puzzleID;
	int stepMoved;
	Timer timer = new Timer();
	boolean tipMode;
	private Context context = this;
	private Dialog completeDialog = null;
	private File picFile;
	private Typeface font;
	private Button bmenu, bback, bnext, bhint, breplay;
	private AbsoluteLayout absLayout;
	private AdView adView;
	private AdRequest adRequest;
	private int path_width;
	private int path_height;
	private String getPack;
	private int getLevel;
	private int adsVar = 0;
	private InterstitialAd interstitialAd;

	// variables for new dots
	private int theme = 1;
	private int levelClreared = 0;
	private static final int REQUEST_WRITE_STORAGE = 11;

	public void onCreate(Bundle bund) {
		super.onCreate(bund);
		@SuppressWarnings("unused")
		PrefClass prefclass = new PrefClass(context);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.gamescreen);

        MobileAds.initialize(this, "ca-app-pub-8389297099602812~6126659691");

        getPack = DataHandler.getLast_pack();
		getLevel = DataHandler.getCuurrentLevel();

		font = DataHandler.getTypeface(context);

		theme = PrefClass.getTheme();

		tvhint = (TextView) findViewById(R.id.tvhint);
		tvhintlabel = (TextView) findViewById(R.id.tvhintlabel);

		tvhint.setTypeface(font);
		tvhintlabel.setTypeface(font);

		bmenu = (Button) findViewById(R.id.bmenu);
		bmenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		bback = (Button) findViewById(R.id.bprevious);
		bback.setOnClickListener(this);
		bhint = (Button) findViewById(R.id.bhint);
		bhint.setOnClickListener(this);
		bnext = (Button) findViewById(R.id.bnext);
		bnext.setOnClickListener(this);
		breplay = (Button) findViewById(R.id.breplay);
		breplay.setOnClickListener(this);

		tvbestLabel = (TextView) findViewById(R.id.tvbestLabel);
		tvbestLabel.setTypeface(font);

		tvlevel = (TextView) findViewById(R.id.tvlevel);
		tvlevel.setTypeface(font);

		tvhintlabel = (TextView) findViewById(R.id.tvhintlabel);
		tvhintlabel.setTypeface(font);

		tvmoves = (TextView) findViewById(R.id.tvmoves);
		tvmoves.setTypeface(font);
		tvpack = (TextView) findViewById(R.id.tvpack);
		tvpack.setTypeface(font);
		tvlevelLabel = (TextView) findViewById(R.id.tvlevelLabel);
		tvlevelLabel.setTypeface(font);
		tvbest = (TextView) findViewById(R.id.tvbest);
		tvbest.setTypeface(font);
		tvmovesLabel = (TextView) findViewById(R.id.tvmovesLabel);
		tvmovesLabel.setTypeface(font);

		screenWidth = DataHandler.getDevice_width();
		screenHeight = DataHandler.getDevice_height();
		float aspectRatio = 1.0F * (float) screenWidth / (float) screenHeight;
		if ((screenWidth != 480 || screenHeight != 800) && (double) aspectRatio != 0.6D) {
			if (screenWidth == 320 && screenHeight == 480) {
				float width = (float) screenWidth / 320.0F;
				float height = (float) screenHeight / 480.0F;
				LevelBlock.Orig_x = (int) (8f * width);
				LevelBlock.Orig_y = (int) (60f * height);
				LevelBlock.CELL_WIDTH = (int) (50.0F * width);
				LevelBlock.CELL_HEIGHT = (int) (50.0F * height);
			} else if (screenWidth == 480 && screenHeight == 854) {
				float width = (float) screenWidth / 320.0F;
				LevelBlock.Orig_x = (int) (10.0F * width);
				LevelBlock.Orig_y = (int) (68.0F * width);
				LevelBlock.CELL_WIDTH = (int) (50.0F * width);
				LevelBlock.CELL_HEIGHT = (int) (50.0F * width);
			} else {
				float width = (float) screenWidth / 320.0F;
				float height = (float) screenHeight / 533.5F;
				LevelBlock.Orig_x = (int) (10.0F * width);
				LevelBlock.Orig_y = (int) (68.0F * height);
				LevelBlock.CELL_WIDTH = (int) (50.0F * width);
				LevelBlock.CELL_HEIGHT = (int) (50.0F * height);
			}
		} else {
			float width = (float) screenWidth / 320.0F;
			LevelBlock.Orig_x = (int) (8f * width);
			LevelBlock.Orig_y = (int) (60f * width);
			LevelBlock.CELL_WIDTH = (int) (50.0F * width);
			LevelBlock.CELL_HEIGHT = (int) (50.0F * width);
		}

		initSounds();
		currentStars = 0;
		absLayout = (AbsoluteLayout) findViewById(R.id.mainGameLayout);
		path_width = -4 + screenWidth;
		path_height = screenHeight - (-4 + path_width + LevelBlock.Orig_y);

		prepareBoard(getPack, getLevel);

		updateUI();

		if (!PrefClass.isPremium())
			loadAds();
		else {
			adView = (AdView) findViewById(R.id.adView);
			adView.setVisibility(View.GONE);
		}
	}

	private void loadAds() {
		adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
		adView = (AdView) findViewById(R.id.adView);
		adView.loadAd(adRequest);

		interstitialAd = new InterstitialAd(context);
		interstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_id));
		interstitialAd.loadAd(adRequest);
	}

	private void showInterstitalAds() {
		if (interstitialAd.isLoaded() && PrefClass.isPremium() == false)
			interstitialAd.show();

		interstitialAd = new InterstitialAd(context);
		interstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_id));
		interstitialAd.loadAd(adRequest);
	}

	private void prepareBoard(String pack, int level) {
		DataHandler.setCurrentPack(pack);
		DataHandler.setCurrentLevel(level);
		DotsView flView = new DotsView(this, path_width, path_width, false,
				DataHandler.checkAndReturnColor(pack, context), theme);
		dtView = flView;
		dtView.gamelayer = this;
		LayoutParams params = new LayoutParams(path_width, path_width, 2, path_height);
		dtView.setLayoutParams(params);
		absLayout.addView(dtView);
		dtView.setupDotsView();

		if (pack == null) {
			pack = "5x5";
			level = 1;
		}

		loadPuzzle(pack, level);
	}

	private void setupDialog(Dialog dialog) {
		dialog.setCanceledOnTouchOutside(false);
		dialog.getWindow().addFlags(Window.FEATURE_NO_TITLE);
		TextView tvtitle = (TextView) dialog.findViewById(R.id.tvtitle);
		tvtitle.setTypeface(font);
		tvtitle.setTextColor(PrefClass.getColor());
		TextView tvCurrentStep = (TextView) dialog.findViewById(R.id.text);
		tvCurrentStep.setTypeface(font);
		String movesLabel = getResources().getString(R.string.movesLabel);
		String bestLabel = getResources().getString(R.string.bestLabel);

		tvCurrentStep.setText(movesLabel + stepMoved);
		TextView tvBestStep = (TextView) dialog.findViewById(R.id.text2);
		tvBestStep.setTypeface(font);
		int bestMoves = GameSaver.sharedInstance(getApplicationContext()).getBestMove4PackedBoard(dtView.problemPart);
		tvBestStep.setText(bestLabel + bestMoves);
		setRateStar(starRatingFromSteps(stepMoved, dtView.getDotCount()), dialog);

		Button bdialogMenu = (Button) dialog.findViewById(R.id.bdialogMenu);
		bdialogMenu.setOnClickListener(this);

		Button bdialogReplay = (Button) dialog.findViewById(R.id.bdialogReplay);
		bdialogReplay.setOnClickListener(this);

		Button bdialogNext = (Button) dialog.findViewById(R.id.bdialogNext);
		bdialogNext.setOnClickListener(this);

		Button bshare = (Button) dialog.findViewById(R.id.bshare);
		bshare.setOnClickListener(this);
	}

	public static int starRatingFromSteps(int steps, int bestSteps) {
		return steps <= bestSteps ? 3 : ((double) steps <= 1.3D * (double) ((float) bestSteps) ? 2 : 1);
	}

	public void clearBoard() {
		stepMoved = 0;
		gameState = GameState.STATE_PLAYING;
	}

	@SuppressLint({ "UseSparseArrays" })
	public void initSounds() {
		mSoundPool = new SoundPool(4, 3, 0);
		mSoundPoolMap = new HashMap<Integer, Integer>();
		mSoundPoolMap.put(Integer.valueOf(1), Integer.valueOf(mSoundPool.load(this, R.raw.dot, 1)));
		mSoundPoolMap.put(Integer.valueOf(2), Integer.valueOf(mSoundPool.load(this, R.raw.complete, 1)));
		mAudioManager = (AudioManager) getSystemService("audio");
	}

	public void levelCleared() {
		GameSaver gameSaver = GameSaver.sharedInstance(this.getApplicationContext());
		int bestMoves = gameSaver.getBestMove4PackedBoard(dtView.problemPart);
		if (bestMoves == 0 || stepMoved < bestMoves) {
			gameSaver.setBestMove4PackedBoard(dtView.problemPart, stepMoved);
		}

		playwinsound();
		updatePanelStatus();
		if (completeDialog != null) {
			setupDialog(completeDialog);
		}
		adsVar = PrefClass.getAdsVar();
		int values = getResources().getInteger(R.integer.interstitial_interval);
		if (adsVar == values - 1 && PrefClass.isPremium() == false)
			showInterstitalAds();
		PrefClass.setAdsVar(++adsVar);
		showDialog(1);
	}

	public void loadPuzzle(String pack, int level) {
		clearBoard();
		board = LevelManager.sharedInstance(getApplication().getResources()).newBoardForPack(pack, level, true, this);
		if (board != null) {
			DataHandler.setCurrentPack(pack);
			stepMoved = 0;
			// moveList.clear();
			dtView.parseData(board);
			dtView.invalidate();
			packName = pack;
			puzzleID = level;
			updatePanelStatus();
			updateUI();
			gameState = GameState.STATE_PLAYING;
			GameSaver gameSaver = GameSaver.sharedInstance(this.getApplicationContext());
			gameSaver.lastPackSelected = pack;
			gameSaver.lastLevelSlected = level;
			gameSaver.saveState();
			if (levelClreared == 1) {
				recreate();
			}
		}
		tvlevel.setTextColor(DataHandler.checkAndReturnColor(packName, context));
		tvlevelLabel.setTextColor(DataHandler.checkAndReturnColor(packName, context));
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.bhint:
			hintButtonPressed();
			break;
		case R.id.breplay:
			dtView.parseData(board);
			dtView.invalidate();
			stepMoved = 0;
			updatePanelStatus();
			updateUI();
			break;
		case R.id.bprevious:
			levelClreared = 1;
			playLevelDirection(-1);
			break;
		case R.id.bnext:
			levelClreared = 1;
			playLevelDirection(1);
			break;
		case R.id.bdialogMenu:
			dismissDialog(1);
			finish();
			break;
		case R.id.bdialogNext:
			levelClreared = 1;
			dismissDialog(1);
			playLevelDirection(1);
			break;
		case R.id.bdialogReplay:
			dismissDialog(1);
			restartLevel();
			break;
		case R.id.bshare:
			if (Build.VERSION.SDK_INT >= 23) {
				if (hasPermission()) {
					shareit();
				} else {
					requestPermissions(new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, REQUEST_WRITE_STORAGE);
				}
			} else {
				shareit();
			}
			break;
		}
	}

	protected Dialog onCreateDialog(int dialogNum) {
		switch (dialogNum) {
		case 1:
			if (completeDialog != null) {
				setupDialog(completeDialog);
				return completeDialog;
			} else {
				completeDialog = new Dialog(this, R.style.creativeDialogTheme);
				completeDialog.setContentView(R.layout.completedialog);
				completeDialog.getWindow().setLayout(-1, -2);
				setupDialog(completeDialog);
			}
		default:
			return completeDialog;
		}
	}

	@SuppressLint("NewApi")
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
			shareit();
		}else{
			Toast.makeText(context, getResources().getString(R.string.perm_denied), Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateUI();
	}

	public boolean onTouch(View view, MotionEvent event) {
		return true;
	}

	public void playLevelDirection(int value) {
		ArrayList<String> list = new ArrayList<String>();
		list.add("5x5");
		list.add("6x6");
		list.add("7x7");
		list.add("8x8");
		list.add("9x9");
		list.add("10x10");
		list.add("a5x5");
		list.add("a6x6");
		list.add("a7x7");
		list.add("a8x8");
		list.add("a9x9");
		list.add("a10x10");
		list.add("11x11");
		list.add("12x12");
		list.add("13x13");
		list.add("14x14");

		LevelManager levelManager = LevelManager.sharedInstance(getApplication().getResources());

		for (int i = 0; i < list.size(); ++i) {
			String pack = (String) list.get(i);
			if (packName.compareToIgnoreCase(pack) == 0) {
				if (value == 1) {
					if (puzzleID < levelManager.getNumberOfPacks(packName)) {
						++puzzleID;
					} else {
						puzzleID = 1;
						packName = (String) list.get((i + 1) % list.size());
					}
				} else if (value == -1) {
					if (puzzleID > 1) {
						puzzleID += -1;
					} else {
						packName = (String) list.get((i - 1 + list.size()) % list.size());
						puzzleID = levelManager.getNumberOfPacks(packName);
					}
				}
				break;
			}
		}

		list.clear();
		prepareBoard(packName, puzzleID);
	}

	public void playsound() {
		playsoundbyid(1);
	}

	public void playsoundbyid(int value) {
		if (PrefClass.getSound()) {
			float soundVar = (float) mAudioManager.getStreamVolume(3) / (float) mAudioManager.getStreamMaxVolume(3);
			int soundMap = ((Integer) mSoundPoolMap.get(Integer.valueOf(value))).intValue();
			mSoundPool.play(soundMap, soundVar, soundVar, 1, 0, 1.0F);
		}
	}

	public void playwinsound() {
		playsoundbyid(2);
	}

	public void restartLevel() {
		loadPuzzle(packName, puzzleID);
	}

	// function to set star rating in dialog
	public void setRateStar(int value, Dialog dialog) {
		ImageView[] imgViewArr = new ImageView[] { (ImageView) dialog.findViewById(R.id.ratingview1),
				(ImageView) dialog.findViewById(R.id.ratingview2), (ImageView) dialog.findViewById(R.id.ratingview3) };

		for (int i = 0; i < value; ++i) {
			imgViewArr[i].setImageResource(R.drawable.star_found);
		}

		for (int i = value; i < 3; ++i) {
			imgViewArr[i].setImageResource(R.drawable.star_lost);
		}

	}

	// function to show hint
	public void hintButtonPressed() {
		if (dtView != null) {
			if ((PrefClass.getHint() == 0)) {
				// Show Activity to get hints
				startActivity(new Intent(context, GetHints.class));
			} else {
				dtView.showHint();
				consumeHint();
				updateUI();
			}

		}

	}

	public void updatePanelStatus() {
		((TextView) findViewById(R.id.tvmoves)).setText(String.valueOf(stepMoved));
		int moves = GameSaver.sharedInstance(getApplicationContext()).getBestMove4PackedBoard(dtView.problemPart);
		if (moves == 0) {
			tvbest.setText("----");
		} else {
			tvbest.setText(String.valueOf(moves));
		}

		((TextView) findViewById(R.id.tvlevel)).setText(String.valueOf(puzzleID));
		((TextView) findViewById(R.id.tvpack)).setText(packName);
		int star = 0;
		if (moves > 0) {
			DotsView mDotsView = dtView;
			star = 0;
			if (mDotsView != null) {
				star = starRatingFromSteps(moves, dtView.getDotCount());
			}
		}
		if (currentStars != star) {
			currentStars = star;
		}

	}

	private void shareit() {

		View view = findViewById(R.id.mainGameLayout);// your layout id
		view.getRootView();
		int fileIndex = PrefClass.getFileIndex();
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			File picDir = new File(
					Environment.getExternalStorageDirectory() + "/" + getResources().getString(R.string.folder_name));
			if (!picDir.exists()) {
				picDir.mkdir();
			}
			view.setDrawingCacheEnabled(true);
			view.buildDrawingCache(true);
			Bitmap bitmap = view.getDrawingCache();
			String fileName = "Screen" + fileIndex + ".jpg";
			picFile = new File(picDir + "/" + fileName);
			try {
				picFile.createNewFile();
				FileOutputStream picOut = new FileOutputStream(picFile);
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), (bitmap.getHeight()));
				boolean saved = bitmap.compress(CompressFormat.JPEG, 100, picOut);
				if (saved) {
					fileIndex++;
					PrefClass.setFileIndex(fileIndex);
				} else {
					Toast.makeText(context, "Error Try Again", Toast.LENGTH_SHORT).show();
					// Error
				}
				picOut.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			view.destroyDrawingCache();
		} else {
			Toast.makeText(context, "Media not mounted", Toast.LENGTH_SHORT).show();
		}
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		sharingIntent.setType("image/jpeg");
		sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(picFile.getAbsolutePath()));
		startActivity(Intent.createChooser(sharingIntent, "Share via"));
	}

	private void consumeHint() {
		int hint = PrefClass.getHint();
		PrefClass.saveHint(--hint);
	}

	@SuppressLint("NewApi")
	private boolean hasPermission() {
		boolean hasPerm = false;
		if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
			hasPerm = true;
		}
		return hasPerm;
	}

	public void updateUI() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				tvhint.setText(String.valueOf(PrefClass.getHint()));
			}
		});
	}

}
