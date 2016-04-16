package com.lepower.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.lepower.R;
import com.lepower.dao.IUserDao;
import com.lepower.dao.impl.JumpSQLiteHelper;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.User;
import com.lepower.utils.NetConnect;
import com.lepower.utils.Utils;
import com.lepower.widget.Count;

public class JumpMainActivity extends Activity implements OnClickListener {

	private JumpSQLiteHelper jumpSQLiteHelper;
	private String UserId;
	MyReceiver receiver;
	int jumpCount = 0;
	Count count;
	int[] total = new int[7];
	String[] dayDate;
	boolean flag = false;
	IUserDao userDao;
	final Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			TextView textView = (TextView) findViewById(R.id.count);
			textView.setText("" + msg.obj);
			count.setMax(500);
			count.setJumpCount(jumpCount);
		}
	};
	private ImageView image_stop;
	private ImageView image_setting;
	private GestureDetector mDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userDao = new UserDaoImpl();
		User user = userDao.getUserNow();
		setContentView(R.layout.activity_jumpmain);
		UserId = user.getUserId();
		count = (Count) findViewById(R.id.ts_count);
		image_setting = (ImageView) findViewById(R.id.jump_setting);
		image_stop = (ImageView) findViewById(R.id.startorstop);
		TextView tvdest = (TextView) findViewById(R.id.dest);
		tvdest.setText("" + 500);
		TextView textView = (TextView) findViewById(R.id.count);
		textView.setText("0");

		// �������ݸ�������
		NetConnect.HttpRequest(this, "" + UserId);

		// ��ת��ͳ��ͼҳ��
		ImageView iv_countlist = (ImageView) findViewById(R.id.jump_data);
		iv_countlist.setOnClickListener(this);
		image_setting.setOnClickListener(this);

		mDetector = new GestureDetector(this,
				new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {

						if (e2.getRawX() - e1.getRawX() > 100) {
							Intent intent = new Intent(JumpMainActivity.this,
									DrawPictureActivity.class);
							intent.putExtra("jumpdata", total);
							intent.putExtra("date", dayDate);
							intent.putExtra("userId", UserId);
							startActivity(intent);
							return true;
						} else if (e1.getRawX() - e2.getRawX() > 100) {
							Intent intent2 = new Intent(JumpMainActivity.this,
									UpdateUserInfoActivity.class);
							startActivity(intent2);
							return true;
						}

						return super.onFling(e1, e2, velocityX, velocityY);
					}
				});
	}

	// �㲥��������
	public class MyReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			jumpCount = bundle.getInt("jumpcount");
			Message msg = new Message();
			msg.obj = jumpCount;
			handler.sendMessage(msg);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.jump_data:
			Intent intent = new Intent(JumpMainActivity.this,
					DrawPictureActivity.class);
			intent.putExtra("jumpdata", total);
			intent.putExtra("date", dayDate);
			intent.putExtra("userId", UserId);
			startActivity(intent);
			break;

		case R.id.jump_setting:
			Intent intent2 = new Intent(JumpMainActivity.this,
					UpdateUserInfoActivity.class);
			startActivity(intent2);
			break;
		case R.id.startorstop:
			flag = !flag;// ��ʼֵΪfalse;
			Intent intent1 = new Intent(this, JumpService.class);
			if (flag == true) {
				// ������������
				image_stop.setImageResource(R.drawable.stopjump);
				startService(intent1);
				// ͨ���㲥���������Լ������������
				receiver = new MyReceiver();
				IntentFilter filter = new IntentFilter();
				filter.addAction("com.xufan.JumpCountService");
				JumpMainActivity.this.registerReceiver(receiver, filter);
			} else {
				image_stop.setImageResource(R.drawable.startjump);
				unregisterReceiver(receiver);
				InsertData();
				stopService(intent1);
			}
			break;

		default:
			break;
		}

	}

	@Override
	protected void onDestroy() {
		InsertData();
		super.onDestroy();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	// д���ݵ����ݿ�
	private void InsertData() {
		jumpSQLiteHelper = new JumpSQLiteHelper(this, "JumpData.db", null, 1);
		SQLiteDatabase db = jumpSQLiteHelper.getWritableDatabase();
		int orderId = 1;
		Cursor cursor = db
				.rawQuery(
						"SELECT orderId FROM JumpUser WHERE userId=? order by orderId ASC",
						new String[] { UserId });
		if (cursor.moveToLast()) {

			orderId = cursor.getInt(cursor.getColumnIndex("orderId")) + 1;
		}
		ContentValues values = new ContentValues();

		values.put("userId", UserId);
		values.put("orderId", orderId);
		values.put("jumpCount", jumpCount);
		values.put("date", Utils.getTime());
		values.put("kcal", jumpCount * 0.0916);
		db.insert("JumpUser", null, values);
		db.close();
	}

}
