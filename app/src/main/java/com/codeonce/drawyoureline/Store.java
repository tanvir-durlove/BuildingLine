package com.codeonce.drawyoureline;

import android.app.Activity;

public class Store extends Activity  {
//	
//	private final static String TAG = "Dots";
//	private static int purchaseNumber = 1;
//	private Button bpremium, bhint1, bhint2, bhint3;
//	private TextView tvstore;
//	private Context context = this;
//	private static String SKU_PREMIUM, SKU_HINT1, SKU_HINT2, SKU_HINT3;
//	private IabHelper mHelper;
//	private static String billkey = "";
//	@SuppressWarnings("unused")
//	private PrefClass prefclass;
//	private int[] color = { R.color.main1, R.color.main2, R.color.main3,
//			R.color.main4 };
//	private Random rand;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		prefclass = new PrefClass(context);
//		setContentView(R.layout.storescreen);
//
//		rand = new Random();
//
//		billkey = getResources().getString(R.string.bill_key);
//
//		SKU_PREMIUM = getResources().getString(R.string.purchase_premium);
//		SKU_HINT1 = getResources().getString(R.string.purchase_hint1);
//		SKU_HINT2 = getResources().getString(R.string.purchase_hint2);
//		SKU_HINT3 = getResources().getString(R.string.purchase_hint3);
//
//		bpremium = (Button) findViewById(R.id.bpremium);
//		bhint1 = (Button) findViewById(R.id.bhint1);
//		bhint2 = (Button) findViewById(R.id.bhint2);
//		bhint3 = (Button) findViewById(R.id.bhint3);
//
//		tvstore = (TextView) findViewById(R.id.tvStore);
//		tvstore.setTextColor(getResources().getColor(color[rand.nextInt(4)]));
//
//		bpremium.setOnClickListener(this);
//		bhint1.setOnClickListener(this);
//		bhint2.setOnClickListener(this);
//		bhint3.setOnClickListener(this);
//
//		bpremium.setTypeface(DataHandler.getTypeface(context));
//		bhint1.setTypeface(DataHandler.getTypeface(context));
//		bhint2.setTypeface(DataHandler.getTypeface(context));
//		bhint3.setTypeface(DataHandler.getTypeface(context));
//		tvstore.setTypeface(DataHandler.getTypeface(context));
//
//		mHelper = new IabHelper(context, billkey);
//		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
//			public void onIabSetupFinished(IabResult result) {
//				if (!result.isSuccess()) {
//					// Oh no, there was a problem.
//					Log.d(TAG, "Problem setting up In-app Billing: " + result);
//				}
//				if (mHelper == null)
//					return;
//				if (result.isSuccess()) {
//					Log.d(TAG, "In-app Billing is set up OK");
//					Log.d(TAG, "Setup successful. Querying inventory.");
//				}
//
//			}
//		});
//
//	}
//
//	IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
//		public void onIabPurchaseFinished(IabResult result, Purchase info) {
//
//			if (result.isFailure()) {
//				Log.d(TAG, "Error purchasing: " + result);
//				Toast.makeText(context, "Error in purchasing :" + result,
//						Toast.LENGTH_SHORT).show();
//
//				return;
//			}
//
//			Log.d(TAG, "Purchase successful.");
//
//			if (info.getSku().equals(SKU_PREMIUM)) {
//				Toast.makeText(context,
//						"Enjoy the premium version. Please Restart!!",
//						Toast.LENGTH_LONG).show();
//				fullVersion();
//				Store.this.finish();
//			} else if (info.getSku().equals(SKU_HINT1)) {
//				mHelper.consumeAsync(info, mConsumeFinishedListener);
//				purchaseNumber = 1;
//			} else if (info.getSku().equals(SKU_HINT2)) {
//				mHelper.consumeAsync(info, mConsumeFinishedListener);
//				purchaseNumber = 2;
//			} else if (info.getSku().equals(SKU_HINT3)) {
//				mHelper.consumeAsync(info, mConsumeFinishedListener);
//				purchaseNumber = 3;
//			}
//
//		}
//	};
//
//	// Called when consumption is complete
//	IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
//		public void onConsumeFinished(Purchase purchase, IabResult result) {
//			Log.d(TAG, "Consumption finished. Purchase: " + purchase
//					+ ", result: " + result);
//
//			if (mHelper == null)
//				return;
//
//			if (result.isSuccess()) {
//				Log.d(TAG, "Consumption successful. Provisioning.");
//				if (purchaseNumber == 1) {
//					Toast.makeText(context, "Hints added.", Toast.LENGTH_LONG)
//							.show();
//					addHint(getResources().getInteger(R.integer.hint_1));
//					Store.this.finish();
//				} else if (purchaseNumber == 2) {
//					Toast.makeText(context, "Hints added.", Toast.LENGTH_LONG)
//							.show();
//					addHint(getResources().getInteger(R.integer.hint_2));
//					Store.this.finish();
//				} else if (purchaseNumber == 3) {
//					Toast.makeText(context, "Hints added.", Toast.LENGTH_LONG)
//							.show();
//					addHint(getResources().getInteger(R.integer.hint_3));
//					Store.this.finish();
//				}
//
//			} else {
//				Toast.makeText(context, "Error in purchasing",
//						Toast.LENGTH_SHORT).show();
//			}
//
//			Log.d(TAG, "End consumption dots.");
//		}
//	};
//
//	private void fullVersion() {
//		SharedPreferences pref = getSharedPreferences("SETTINGS", MODE_PRIVATE);
//		SharedPreferences.Editor edit = pref.edit();
//		edit.putBoolean("ISPREMIUM", true);
//		edit.commit();
//	}
//
//	private void addHint(int value) {
//		int hint = PrefClass.getHint();
//		hint = hint + value;
//		PrefClass.saveHint(hint);
//	}
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
//			super.onActivityResult(requestCode, resultCode, data);
//		}
//	}
//
//	@Override
//	public void onClick(View v) {
//		String payload = "";
//		switch (v.getId()) {
//		case R.id.bpremium:
//			mHelper.launchPurchaseDots(Store.this, SKU_PREMIUM, 10001,
//					mPurchaseFinishedListener, payload);
//			addHint(200);
//			break;
//		case R.id.bhint1:
//			mHelper.launchPurchaseDots(Store.this, SKU_HINT1, 10001,
//					mPurchaseFinishedListener, payload);
//			break;
//		case R.id.bhint2:
//			mHelper.launchPurchaseDots(Store.this, SKU_HINT2, 10001,
//					mPurchaseFinishedListener, payload);
//			break;
//		case R.id.bhint3:
//			mHelper.launchPurchaseDots(Store.this, SKU_HINT3, 10001,
//					mPurchaseFinishedListener, payload);
//			break;
//		}
//	}
//
//	@Override
//	public void onDestroy() {
//		super.onDestroy();
//		if (mHelper != null)
//			mHelper.dispose();
//		mHelper = null;
//	}
//
}
