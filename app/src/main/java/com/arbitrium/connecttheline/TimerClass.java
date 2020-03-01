package com.arbitrium.connecttheline;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;

public class TimerClass extends TimerTask {

	private Activity activity;
	private int fromX;
	private int fromY;
	private int steps;
	private View view;
	private int toX;
	private int toY;

	public TimerClass(Timer timer, Activity act, View view, int fromX,
			int fromY, int toX, int toY) {
		activity = act;
		this.view = view;
		this.fromX = fromX;
		this.fromY = fromY;
		this.toX = toX;
		this.toY = toY;
		steps = 0;
	}

	public void run() {
		if (steps < 10) {
			++steps;
			activity.runOnUiThread(new Runnable() {
				public void run() {
					LayoutParams params = (LayoutParams) view.getLayoutParams();
					params.leftMargin = (toX - fromX) * steps / 10 + fromX;
					params.topMargin = (toY - fromY) * steps / 10 + fromY;
					view.setLayoutParams(params);
					view.invalidate();
				}
			});
		} else {
			cancel();
		}
	}
}
