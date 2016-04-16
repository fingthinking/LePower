package com.lepower.activity;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lepower.App;
import com.lepower.callback.HttpCallback;
import com.lepower.callback.ResultCallback;
import com.lepower.dao.RecordInfoDAO;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.MessageArray;
import com.lepower.model.RecordInfo;
import com.lepower.model.User;
import com.lepower.utils.HttpUtils;
import com.lepower.utils.LeUrls;
import com.lepower.utils.LogUtils;
import com.lepower.utils.ToastUtils;
import com.lepower.view.XListView;
import com.lepower.view.XListView.IXListViewListener;
import com.lesitup.R;

public class RecordListActivity extends Activity implements OnClickListener,
		IXListViewListener {

	public TextView tvDate;
	private XListView listView;
	private RecordinfoAdapter adapter;
	private List<RecordInfo> infoList = new ArrayList<RecordInfo>();
	Calendar calendar;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	RecordInfoDAO infoDao = RecordInfoDAO.getInstance(this);
	String deleteId;
	private Handler handler;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.recrod_list);

		// detector = new GestureDetector(this,this);
		tvDate = (TextView) findViewById(R.id.textView_date);
		// 获取系统时间
		calendar = Calendar.getInstance();
		tvDate.setText(sdf.format(calendar.getTime()));

		listView = (XListView) findViewById(R.id.record_list);
		listView.setPullLoadEnable(true);
		adapter = new RecordinfoAdapter(this, R.layout.record_list_item,
				infoList);

		listView.setAdapter(adapter);
		updateRecordinfo(tvDate.getText().toString());
		LogUtils.e("onCreate" + infoList.size());
		listView.setXListViewListener(this);
		handler = new Handler();

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				deleteId = adapter.getItem(position - 1).getId();
				AlertDialog.Builder dialog = new AlertDialog.Builder(
						RecordListActivity.this);
				dialog.setTitle("删除记录");
				dialog.setMessage("删除此条记录？");
				dialog.setCancelable(true);
				dialog.setPositiveButton("删除",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								infoDao.deleteRecordData(deleteId);
								updateRecordinfo(tvDate.getText().toString());
							}
						});
				dialog.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dailog,
									int which) {
								// TODO Auto-generated method stub

							}
						});
				dialog.show();
				return false;
			}
		});

		Button preButton = (Button) findViewById(R.id.button_pre);
		preButton.setOnClickListener(this);
		Button nextButton = (Button) findViewById(R.id.button_next);
		nextButton.setOnClickListener(this);
	}

	private void getPredateData() {
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		String selectDateString = sdf.format(calendar.getTime());// 2016-03-09
		tvDate.setText(selectDateString);
		updateRecordinfo(tvDate.getText().toString());
	}

	private void getNextdateData() {
		calendar.add(Calendar.DAY_OF_MONTH, +1);
		String selectDateString = sdf.format(calendar.getTime());// 2016-03-09
		tvDate.setText(selectDateString);
		updateRecordinfo(tvDate.getText().toString());
	}

	private void updateRecordinfo(String date) {
		// TODO Auto-generated method stub

		// 更新listView的时候，首先需要将绑定的list清空
		infoList.clear();
		// 然后，需要使用list.addAll()方法，将所有的对象添加进来，这一步很关键
		infoList.addAll(infoDao.getSitUpDataByDateFromDB(date));
		// 最后呢，需要使用adapter.notifyDataChanged()方法
		adapter.notifyDataSetChanged();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button_pre:
			getPredateData();
			break;
		case R.id.button_next:
			getNextdateData();
			break;
		default:
			break;
		}
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub

		infoList.clear();

		final User user = new UserDaoImpl().getUserNow();
		final String date = tvDate.getText().toString();

		final HttpCallback<String> httpCallback = new HttpCallback<String>() {
			public void onSuccess(String response) {
				//infoDao.deleteAll();
				// LogUtils.e(response);
				Gson gson = new Gson();
				Type type = new TypeToken<MessageArray<RecordInfo>>() {
				}.getType();
				MessageArray<RecordInfo> infoMsg = gson
						.fromJson(response, type);
				if (infoMsg.getResCode().equals("0")) {
					List<RecordInfo> infos = infoMsg.getData();
					for (RecordInfo info : infos) {
						LogUtils.e(info);
						infoDao.addRecordInfo(info);
					}

					infoList.addAll(infoDao.getdateFromDB(date));
					adapter.notifyDataSetChanged();
					// 插入数据库完毕，读出来并在UI线程更新
					onLoad();
				} else {
					ToastUtils.showShort(infoMsg.getResMsg());
				}
			}

			@Override
			public void onError(Throwable e, boolean arg1) {
				if (!HttpUtils.isNetWork(App.context)) {
					ToastUtils.showShort("请检查网络状况");
				} else {
					LogUtils.e(e.getMessage());
				}
			}
		};

		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String urlString = "";
				String userId = user.getUserId();
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("userId", userId);
				params.put("date", date);
				HttpUtils.get(LeUrls.GET_SITUP_INFO, params, httpCallback);
			}
		}, 1000);
	}



	protected void onLoad() {
		// TODO Auto-generated method stub
		listView.stopRefresh();
		listView.stopLoadMore();
		Date curDate = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		listView.setRefreshTime(formatter.format(curDate));
	}


	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		/*handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				infoList.clear();
				// geneItems();
				adapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);*/
	}

}
