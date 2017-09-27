package com.codeonce.drawyoureline;

import java.util.Hashtable;

import com.codeonce.drawyoureline.utils.DataHandler;
import com.codeonce.drawyoureline.utils.PrefClass;
import com.tapjoy.TapjoyConnect;
import com.tapjoy.TapjoyConnectFlag;
import com.tapjoy.TapjoyConnectNotifier;
import com.tapjoy.TapjoyEarnedPointsNotifier;
import com.tapjoy.TapjoyNotifier;
import com.tapjoy.TapjoySpendPointsNotifier;
import com.vungle.publisher.AdConfig;
import com.vungle.publisher.EventListener;
import com.vungle.publisher.VunglePub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GetHints extends Activity implements OnClickListener {

	private final static String TAG = "DOTS";

	private Context context = this;
	private Button bfree, bshare, bvideo;

	private String tap_appId = "";
	private String tap_appKey = "";
	private String tap_hintId = "";

	// for adColony
	private String V4VC_zone_id = "";

	private Hashtable<String, String> flags;

	private VunglePub vunglePub;
	private String appId = "";
	private AdConfig overrideConfig;

	private int totalTapHints;
	private boolean earnedHints = false;
	private int sharedHints = 5;
	private boolean isShared = false;
	private int earnedTapHints = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.hints_choice);

		sharedHints = getResources().getInteger(R.integer.shareHints);

		bfree = (Button) findViewById(R.id.bfreehints);
		bshare = (Button) findViewById(R.id.bshareHints);
		bvideo = (Button) findViewById(R.id.bvideoHints);

		bshare.setTypeface(DataHandler.getTypeface(context));
		bvideo.setTypeface(DataHandler.getTypeface(context));
		bfree.setTypeface(DataHandler.getTypeface(context));

		bshare.setOnClickListener(this);
		bvideo.setOnClickListener(this);
		bfree.setOnClickListener(this);

		((TextView) findViewById(R.id.tvfreehints)).setTypeface(DataHandler.getTypeface(context));
		((TextView) findViewById(R.id.tvsharehints)).setTypeface(DataHandler.getTypeface(context));
		((TextView) findViewById(R.id.tvvideohints)).setTypeface(DataHandler.getTypeface(context));

		if (DataHandler.tapjoy_ads) {
			tap_appId = getResources().getString(R.string.tap_appId);
			tap_appKey = getResources().getString(R.string.tap_appKey);
			tap_hintId = getResources().getString(R.string.tap_hintId);

			flags = new Hashtable<String, String>();
			flags.put(TapjoyConnectFlag.ENABLE_LOGGING, "true");

			initialiseTapjoy();
		}

		if (DataHandler.video_ads) {
			initialiseVungle();
		}

		if (!DataHandler.video_ads) {
			bvideo.setVisibility(View.GONE);
			((TextView) findViewById(R.id.tvvideohints)).setVisibility(View.GONE);
		}

		if (!DataHandler.tapjoy_ads) {
			bfree.setVisibility(View.GONE);
			((TextView) findViewById(R.id.tvfreehints)).setVisibility(View.GONE);
		}
	}

	private void initialiseTapjoy() {
		TapjoyConnect.requestTapjoyConnect(this, tap_appId, tap_appKey, flags, new TapjoyConnectNotifier() {
			@Override
			public void connectSuccess() {
				onConnectSuccess();
			}

			@Override
			public void connectFail() {
				onConnectFail();
			}
		});

		TapjoyConnect.getTapjoyConnectInstance().getTapPoints(new TapjoyNotifier() {

			@Override
			public void getUpdatePointsFailed(String error) {
				Log.i(TAG, error);
			}

			@Override
			public void getUpdatePoints(String currencyName, int pointTotal) {
				Log.i(TAG, "currencyName: " + currencyName);
				Log.i(TAG, "pointTotal: " + pointTotal);
				if (earnedHints) {
					earnedTapHints = pointTotal;
					spendTapPoints();
					earnedHints = false;
				} else {
				}
			}
		});

	}

	private void initialiseVungle() {
		vunglePub = VunglePub.getInstance();
		vunglePub.setEventListeners(vungleEventListener);
		overrideConfig = new AdConfig();
		overrideConfig.setIncentivized(true);
	}

	private void onConnectSuccess() {
		TapjoyConnect.getTapjoyConnectInstance().setEarnedPointsNotifier(new TapjoyEarnedPointsNotifier() {
			@Override
			public void earnedTapPoints(int amount) {
				earnedHints = true;
				totalTapHints = amount;
				Log.i(TAG, "you have earned " + totalTapHints);
			}
		});
	}

	private void onConnectFail() {
		Log.e(TAG, "Tapjoy connect call failed.");

	}

	private final EventListener vungleEventListener = new EventListener() {

		@Override
		public void onVideoView(boolean arg0, int arg1, int arg2) {
			if (arg0) {
				addHint(getResources().getInteger(R.integer.videoHints));
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						Toast.makeText(context, "5 Hints added", Toast.LENGTH_SHORT).show();
					}
				});
			} else {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						Toast.makeText(context, "Watch Complete video to add hints", Toast.LENGTH_SHORT).show();
					}
				});
			}
		}

		@Override
		public void onAdUnavailable(final String arg0) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(context, arg0, Toast.LENGTH_LONG).show();
				}
			});

		}

		@Override
		public void onAdStart() {

		}

		@Override
		public void onAdEnd(boolean arg0) {
			initialiseVungle();
		}

		@Override
		public void onAdPlayableChanged(boolean arg0) {
			Toast.makeText(context, "You cannot play any ad now. Try after sometime", Toast.LENGTH_LONG).show();
		}
	};

	private void spendTapPoints() {
		TapjoyConnect.getTapjoyConnectInstance().spendTapPoints(earnedTapHints, new TapjoySpendPointsNotifier() {
			@Override
			public void getSpendPointsResponseFailed(String error) {
				Log.i(TAG, error);
			}

			@Override
			public void getSpendPointsResponse(String currencyName, int pointTotal) {
				Log.i(TAG, "spent tap points : " + earnedTapHints);
				addHint(earnedTapHints);
			}
		});

	}

	private void addHint(int value) {
		int hint = PrefClass.getHint();
		hint = hint + value;
		PrefClass p1 = new PrefClass(context);
		PrefClass.saveHint(hint);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.bshareHints:
			shareIt();
			break;
		case R.id.bfreehints:
			TapjoyConnect.getTapjoyConnectInstance().showOffersWithCurrencyID(tap_hintId, true);
			break;
		case R.id.bvideoHints:
			vunglePub.playAd(overrideConfig);
			break;
		}
	}

	private boolean hasDayPassed(long lastSharedTime) {
		boolean passed = false;
		if (lastSharedTime == 0)
			passed = true;
		else if (lastSharedTime + 24 * 60 * 60 * 100 < System.currentTimeMillis()) {
			passed = true;
		}
		return passed;
	}

	private void shareIt() {
		String appPackageName = getPackageName();
		isShared = true;
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=" + appPackageName);
		i.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.shareSubject));
		startActivity(Intent.createChooser(i, "Share"));
	}

	@Override
	protected void onPause() {
		super.onPause();
		TapjoyConnect.getTapjoyConnectInstance().appPause();
		vunglePub.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		vunglePub.onResume();
		TapjoyConnect.getTapjoyConnectInstance().appResume();
		TapjoyConnect.getTapjoyConnectInstance().getTapPoints(new TapjoyNotifier() {
			@Override
			public void getUpdatePointsFailed(String error) {
				Log.i(TAG, error);
			}

			@Override
			public void getUpdatePoints(String currencyName, int pointTotal) {
				Log.i(TAG, "currencyName: " + currencyName);
				Log.i(TAG, "pointTotal: " + pointTotal);

				if (earnedHints) {
					earnedTapHints = pointTotal;
					spendTapPoints();
					earnedHints = false;
				} else {

				}

			}
		});
		if (isShared && hasDayPassed(PrefClass.getLastSharedTime())) {
			PrefClass.setLastSharedTime(System.currentTimeMillis());
			Toast.makeText(context, "Thanks for sharing." + sharedHints + " hints added", Toast.LENGTH_SHORT).show();
			addHint(sharedHints);
			isShared = false;
			this.finish();
		} else if (isShared == true && hasDayPassed(PrefClass.getLastSharedTime()) == false) {
			Toast.makeText(context, "Thanks for sharing.But only 1 share is allowed per day to get hints. Try sharing tommorow", Toast.LENGTH_LONG).show();
			isShared = false;
			this.finish();
		}
	}
}
