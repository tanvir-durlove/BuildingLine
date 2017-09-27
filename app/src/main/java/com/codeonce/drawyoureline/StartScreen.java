package com.codeonce.drawyoureline;

import java.util.Random;

import com.codeonce.drawyoureline.utils.DataHandler;
import com.codeonce.drawyoureline.utils.ParticleAcessor;
import com.codeonce.drawyoureline.utils.PrefClass;
import com.vungle.publisher.VunglePub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

public class StartScreen extends Activity implements OnClickListener {

	private Button bstart, bmore, bselectPack, bsettings;
	private Intent intent;
	private Context context = this;
	private TweenManager tm;
	private boolean isAnimationRunning = true;

	private LinearLayout ll1, ll2, ll3, ll4, llmain;

	private VunglePub vunglePub = VunglePub.getInstance();
	private Random rand = new Random();
	private String appId = "";

	private int[] draw = new int[] { R.drawable.pressed_roundrect_five, R.drawable.pressed_roundrect_six, R.drawable.pressed_roundrect_seven,
			R.drawable.pressed_roundrect_eight, R.drawable.pressed_roundrect_nine, R.drawable.pressed_roundrect_ten, R.drawable.pressed_roundrect_eleven,
			R.drawable.pressed_roundrect_twelve, R.drawable.pressed_roundrect_thirteen, R.drawable.pressed_roundrect_fourteen };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startscreen);
		getScreenDimen();

		appId = getResources().getString(R.string.appId);
		vunglePub.init(this, appId);

		PrefClass prefclass = new PrefClass(context);

		AppRater.app_launched(context);

		Tween.registerAccessor(LinearLayout.class, new ParticleAcessor());
		tm = new TweenManager();

		bstart = (Button) findViewById(R.id.bstart);
		bmore = (Button) findViewById(R.id.bmore);
		bselectPack = (Button) findViewById(R.id.bselectPack);
		bsettings = (Button) findViewById(R.id.bstore);

		ll1 = (LinearLayout) findViewById(R.id.ll1);
		ll2 = (LinearLayout) findViewById(R.id.ll2);
		ll3 = (LinearLayout) findViewById(R.id.ll3);
		ll4 = (LinearLayout) findViewById(R.id.ll4);
		llmain = (LinearLayout) findViewById(R.id.llmainheading);

		bstart.setTypeface(DataHandler.getTypeface(context));
		bmore.setTypeface(DataHandler.getTypeface(context));
		bselectPack.setTypeface(DataHandler.getTypeface(context));
		bsettings.setTypeface(DataHandler.getTypeface(context));

		llmain.setBackgroundResource(draw[rand.nextInt(10)]);

		bstart.setOnClickListener(this);
		bmore.setOnClickListener(this);
		bselectPack.setOnClickListener(this);
		bsettings.setOnClickListener(this);

		((TextView) findViewById(R.id.tvheading)).setTypeface(DataHandler.getTypeface(context));

		@SuppressWarnings("unused")
		PrefClass p1 = new PrefClass(context);
		DataHandler.checkAndReturnColor(GameSaver.sharedInstance(context.getApplicationContext()).lastPackSelected, context);

		startTween();
		threadForTween();

	}

	private void threadForTween() {

		new Thread(new Runnable() {
			private long lastMillis = -1;

			@Override
			public void run() {
				while (isAnimationRunning) {
					if (lastMillis > 0) {
						long currentMillis = System.currentTimeMillis();
						final float delta = (currentMillis - lastMillis) / 1000f;

						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								tm.update(delta);
							}
						});
						lastMillis = currentMillis;
					} else {
						lastMillis = System.currentTimeMillis();
					}
					try {
						Thread.sleep(1000 / 60);
					} catch (InterruptedException ex) {
					}
				}
			}
		}).start();
	}

	private void startTween() {
		Timeline.createSequence().beginSequence().push(Tween.set(ll1, ParticleAcessor.POSITION_X).target(-1 * DataHandler.getDevice_width()))
				.push(Tween.set(ll2, ParticleAcessor.POSITION_X).target(-1 * DataHandler.getDevice_width()))
				.push(Tween.set(ll3, ParticleAcessor.POSITION_X).target(-1 * DataHandler.getDevice_width()))
				.push(Tween.set(ll4, ParticleAcessor.POSITION_X).target(-1 * DataHandler.getDevice_width())).pushPause(0.5f)
				.push(Tween.to(ll1, ParticleAcessor.POSITION_X, 0.5f).target(0)).push(Tween.to(ll2, ParticleAcessor.POSITION_X, 0.5f).target(0))
				.push(Tween.to(ll3, ParticleAcessor.POSITION_X, 0.5f).target(0)).push(Tween.to(ll4, ParticleAcessor.POSITION_X, 0.5f).target(0)).end()
				.start(tm);
	}

	private void getScreenDimen() {
		DisplayMetrics dm = getResources().getDisplayMetrics();
		DataHandler.setDevice_width(dm.widthPixels);
		DataHandler.setDevice_height(dm.heightPixels);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bstart:
			intent = new Intent(context, GameActivity.class);
//			intent.putExtra("pack", GameSaver.sharedInstance(context.getApplicationContext()).lastPackSelected);
//			intent.putExtra("pid", GameSaver.sharedInstance(context.getApplicationContext()).lastLevelSlected);
			DataHandler.setCurrentPack(GameSaver.sharedInstance(context.getApplicationContext()).lastPackSelected);
			DataHandler.setCurrentLevel(GameSaver.sharedInstance(context.getApplicationContext()).lastLevelSlected);
			startActivity(intent);
			break;
		case R.id.bmore:
			startActivity(new Intent(context, MoreScreen.class));
			break;
		case R.id.bselectPack:
			startActivity(new Intent(context, PackType.class));
			break;
		case R.id.bstore:
			startActivity(new Intent(context, GetHints.class));
			break;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		vunglePub.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		vunglePub.onResume();
	}
}
