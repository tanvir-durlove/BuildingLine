package com.codeonce.drawyoureline;

import android.app.Activity;
import android.graphics.Rect;
import android.widget.ImageView;

public class LevelBlock {

	public static int Orig_x = 10;
	public static int Orig_y = 68;
	public static int CELL_HEIGHT = 50;
	public static int CELL_WIDTH = 50;
	public int curx;
	public int cury;
	public int direction;
	public int height;
	public boolean isBlocked;
	public int length;
	public int origx;
	public int origy;
	public ImageView sprite;
	public int width;

	public LevelBlock(int x, int y, int dir, int mLength, boolean isBlkd,
			boolean isDo, Activity act) {
		origx = x;
		origy = y;
		curx = x;
		cury = y;
		direction = dir;
		length = mLength;
		isBlocked = isBlkd;
		sprite = null;
		if (isBlocked) {
			if (isDo) {
				sprite = new ImageView(act);
				sprite.setImageResource(R.drawable.back);
			}

			width = 2 * CELL_WIDTH;
			height = CELL_HEIGHT;
		} else if (direction == 1) {
			if (isDo) {
				sprite = new ImageView(act);
				if (length == 2) {
					sprite.setImageResource(R.drawable.back);
				} else if (length == 3) {
					sprite.setImageResource(R.drawable.back);
				}
			}

			width = CELL_WIDTH * length;
			height = CELL_HEIGHT;
		} else {
			if (isDo) {
				sprite = new ImageView(act);
				if (length == 2) {
					sprite.setImageResource(R.drawable.back);
				} else if (length == 3) {
					sprite.setImageResource(R.drawable.back);
				}
			}

			width = CELL_WIDTH;
			height = CELL_HEIGHT * length;
		}
	}

	public Rect boundRect() {
		Rect rectangle = new Rect();
		rectangle.left = Orig_x + curx * CELL_WIDTH;
		rectangle.bottom = GameActivity.screenHeight
				- (Orig_y + (6 - cury) * CELL_HEIGHT) + height;
		rectangle.right = rectangle.left + width;
		rectangle.top = rectangle.bottom - height;
		return rectangle;
	}

	public void resetState() {
		curx = origx;
		cury = origy;
	}
}
