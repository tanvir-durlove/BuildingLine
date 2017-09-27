package com.codeonce.drawyoureline;

import com.codeonce.drawyoureline.utils.DataHandler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PackType extends Activity implements OnClickListener {

	private Button bbasic, badvanced, btablet;
	private Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.packtype);
		super.onCreate(savedInstanceState);

		bbasic = (Button) findViewById(R.id.bbasic);
		badvanced = (Button) findViewById(R.id.badvanced);
		btablet = (Button) findViewById(R.id.btablet);
		
		((TextView)findViewById(R.id.tvpacktype)).setTypeface(DataHandler.getTypeface(context));

		bbasic.setOnClickListener(this);
		badvanced.setOnClickListener(this);
		btablet.setOnClickListener(this);
		
		bbasic.setTypeface(DataHandler.getTypeface(context));
		badvanced.setTypeface(DataHandler.getTypeface(context));
		btablet.setTypeface(DataHandler.getTypeface(context));
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.badvanced:
			startActivity(new Intent(context, AdvancedPackActivity.class));
			break;
		case R.id.bbasic:
			startActivity(new Intent(context, BasicPackActivity.class));
			break;
		case R.id.btablet:
			startActivity(new Intent(context, TabPackActivity.class));
			break;
		}

	}

}
