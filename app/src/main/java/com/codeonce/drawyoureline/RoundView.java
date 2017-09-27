package com.codeonce.drawyoureline;

import android.content.Context;
import android.graphics.drawable.PaintDrawable;
import android.view.View;

public class RoundView extends View {

	public int color = -16777216;
	PaintDrawable paintDrawable = new PaintDrawable();

	public RoundView(Context context, int rad) {
		super(context);
		paintDrawable.getPaint().setColor(color);
		paintDrawable.setAlpha(102);
		paintDrawable.setCornerRadius((float) ((double) rad / 2.0D));
		setBackgroundDrawable(paintDrawable);
	}

	public void changeColor(int color) {
		this.color = color;
		paintDrawable.getPaint().setColor(color);
	}

	public void updateCornerRadius(float rad) {
		paintDrawable.setCornerRadius(rad);
	}
}
