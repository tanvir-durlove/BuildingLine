package com.codeonce.drawyoureline.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public class BlankView extends View {

	public BlankView(Context context, int minHeight, int minWidth) {
		super(context);
		setMinimumHeight(minHeight);
		setMinimumWidth(minWidth);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(getSuggestedMinimumWidth(),
				getSuggestedMinimumHeight());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

}
