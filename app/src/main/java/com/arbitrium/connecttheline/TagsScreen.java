package com.arbitrium.connecttheline;

import com.arbitrium.connecttheline.utils.PrefClass;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;

public class TagsScreen extends Activity implements OnClickListener {

	private RelativeLayout ll1, ll2, ll3, ll4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tag_select);

		PrefClass p1 = new PrefClass(this);

		ll1 = (RelativeLayout) findViewById(R.id.rltag1);
		ll2 = (RelativeLayout) findViewById(R.id.rltag2);
		ll3 = (RelativeLayout) findViewById(R.id.rltag3);
		ll4 = (RelativeLayout) findViewById(R.id.rltag4);

		ll1.setOnClickListener(this);
		ll2.setOnClickListener(this);
		ll3.setOnClickListener(this);
		ll4.setOnClickListener(this);

		setThemeButton();
	}

	private void setThemeButton() {
		int theme = PrefClass.getTheme();
		if (theme == 0)
			ll1.setBackgroundResource(R.drawable.pressed_roundrect_five);
		else if (theme == 1)
			ll2.setBackgroundResource(R.drawable.pressed_roundrect_six);
		else if (theme == 2)
			ll3.setBackgroundResource(R.drawable.pressed_roundrect_seven);
		else if (theme == 3)
			ll4.setBackgroundResource(R.drawable.pressed_roundrect_eight);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rltag1:
			PrefClass.setTheme(0);
			finish();
			break;
		case R.id.rltag2:
			PrefClass.setTheme(1);
			finish();
			break;
		case R.id.rltag3:
			PrefClass.setTheme(2);
			finish();
			break;
		case R.id.rltag4:
			PrefClass.setTheme(3);
			finish();
			break;
		}
		setThemeButton();
	}
}
