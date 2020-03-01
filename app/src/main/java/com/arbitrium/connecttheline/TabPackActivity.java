package com.arbitrium.connecttheline;

import com.arbitrium.connecttheline.utils.DataHandler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TabPackActivity extends Activity implements OnClickListener {

	private Button bpack11, bpack12, bpack13, bpack14;
	private Intent intent;
	private TextView tvselectPack;
	private Context context = this;
	private String packName = "11x11";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabpackscreen);

		tvselectPack = (TextView) findViewById(R.id.tvSelectPack);
		tvselectPack.setTypeface(DataHandler.getTypeface(context));

		bpack11 = (Button) findViewById(R.id.bpack11);
		bpack12 = (Button) findViewById(R.id.bpack12);
		bpack13 = (Button) findViewById(R.id.bpack13);
		bpack14 = (Button) findViewById(R.id.bpack14);

		bpack11.setTypeface(DataHandler.getTypeface(context));
		bpack12.setTypeface(DataHandler.getTypeface(context));
		bpack13.setTypeface(DataHandler.getTypeface(context));
		bpack14.setTypeface(DataHandler.getTypeface(context));
		
		bpack11.setOnClickListener(this);
		bpack12.setOnClickListener(this);
		bpack13.setOnClickListener(this);
		bpack14.setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.bpack11:
			packName = "11x11";
			break;
		case R.id.bpack12:
			packName = "12x12";
			break;
		case R.id.bpack13:
			packName = "13x13";
			break;
		case R.id.bpack14:
			packName = "14x14";
			break;
		}
		intent = new Intent(this, LevelsActivity.class);
		intent.putExtra("packname", packName);
		intent.putExtra("packDisplayName", packName);
		startActivity(intent);
	}

}
