package com.arbitrium.connecttheline.utils;

import com.arbitrium.connecttheline.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class LevelButton extends View {

	Bitmap star_normal, star_filled, star1, star2, star3;
	NinePatchDrawable back;
	Drawable backDraw;
	private int stars, color;
	private Context context;
	private String str = "1";
	private String pack = "5x5";
	private int textSize;
	Canvas canvas;

	public LevelButton(Context context, int stars, String str,String pack, int color,
			Drawable draw, int textSize) {
		super(context);
		setMinimumHeight(DataHandler.getDevice_width() / 6);
		setMinimumWidth(DataHandler.getDevice_width() / 6);
		this.context = context;
		this.str = str;
		this.stars = stars;
		this.color = color;
		this.pack = pack;
		this.textSize = textSize;
		backDraw = draw;
		star_normal = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.star_normal);
		star_filled = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.star_filled);
	}

	public LevelButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LevelButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(getSuggestedMinimumWidth(),
				getSuggestedMinimumHeight());

	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		this.canvas = canvas;
		backDraw.setBounds(0, 0, getWidth(), getHeight());
		backDraw.draw(canvas);

//		setBackgroundColor(isPressed() ? color : Color.TRANSPARENT);
		setBackgroundColor(Color.TRANSPARENT);

		Paint paint = new Paint();
		paint.setStyle(Style.STROKE);
		paint.setTypeface(DataHandler.getTypeface(context));
		paint.setTextSize(textSize);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setColor(Color.WHITE);
		canvas.drawText(str, (int) (canvas.getWidth() / 2), getHeight() * 35
				/ 100 - ((paint.descent() + paint.ascent()) / 2), paint);

		paint.setColor(color);
		
		star1 = star_normal;
		star2 = star_normal;
		star3 = star_normal;
		if (stars == 1) {
			star1 = star_filled;
		} else if (stars == 2) {
			star1 = star_filled;
			star2 = star_filled;
		} else if (stars == 3) {
			star1 = star_filled;
			star2 = star_filled;
			star3 = star_filled;
		}

		canvas.drawBitmap(star1, getWidth() / 4 - star_normal.getWidth() / 2,
				getHeight() * 6 / 10, paint);
		canvas.drawBitmap(star2, getWidth() * 2 / 4 - star_normal.getWidth()
				/ 2, getHeight() * 6 / 10, paint);
		canvas.drawBitmap(star3, getWidth() * 3 / 4 - star_normal.getWidth()
				/ 2, getHeight() * 6 / 10, paint);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			setBackgroundColor(color);
//			invalidate();
			break;
		case MotionEvent.ACTION_UP:
//			invalidate();
			setBackgroundColor(Color.TRANSPARENT);			
			break;
		}
		return super.onTouchEvent(event);
	}

}
