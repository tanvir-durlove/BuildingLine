package com.codeonce.drawyoureline.utils;

import android.annotation.SuppressLint;
import android.widget.LinearLayout;
import aurelienribon.tweenengine.TweenAccessor;

@SuppressLint("NewApi")
public class ParticleAcessor implements TweenAccessor<LinearLayout> {

	public final static int POSITION_X = 1;
	public final static int POSITION_Y = 2;
	public final static int POSITION_XY = 3;
	public static final int ALPHA = 0;

	@SuppressLint("NewApi")
	@Override
	public int getValues(LinearLayout target, int tweenType,
			float[] returnValues) {
		switch (tweenType) {
		case POSITION_X:
			returnValues[0] = target.getX();
			return 1;
		case POSITION_Y:
			returnValues[0] = target.getY();
			return 1;
		case POSITION_XY:
			returnValues[0] = target.getX();
			returnValues[1] = target.getY();
			return 2;
		default:
			assert false;
			return -1;
		}

	}

	@SuppressLint("NewApi")
	@Override
	public void setValues(LinearLayout target, int tweenType, float[] newValues) {
		switch (tweenType) {
		case POSITION_X:
			target.setX(newValues[0]);
			break;
		case POSITION_Y:
			target.setY(newValues[1]);
			break;
		case POSITION_XY:
			target.setX(newValues[0]);
			target.setY(newValues[1]);
			break;
		default:
			assert false;
			break;
		}
	}
}
