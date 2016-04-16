package com.lestep.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lepower.dao.IUserDao;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.User;
import com.lepower.utils.ImageUtils;
import com.lepower.utils.LogUtils;
import com.lestep.App;
import com.lestep.R;
import com.lestep.model.Step;
import com.lestep.utils.NumFormat;
import com.lestep.utils.ShareUtils;
import com.lestep.utils.ToastUtils;

@ContentView(R.layout.activity_share)
public class ShareActivity extends BaseActivity {

	@ViewInject(R.id.rl_share_center)
	View view;
	@ViewInject(R.id.txt_steps)
	TextView todaySteps;
	@ViewInject(R.id.txt_day)
	TextView todayDay;
	@ViewInject(R.id.txt_distance_and_caloria)
	TextView todayDisCal;
	@ViewInject(R.id.txt_arouse)
	TextView todayArouse;
	
	@ViewInject(R.id.ll_share_titile)
	View titleView;
	@ViewInject(R.id.ll_half_above)
	View aboveView;
	@ViewInject(R.id.img_user_head)
	ImageView headImg;
	@ViewInject(R.id.btn_share)
	ImageView btnShare;
	@ViewInject(R.id.txt_nickname)
	TextView nickName;
	
	private IUserDao userDao;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		userDao = new UserDaoImpl();
		user = userDao.getUserNow();
		
		Intent intent = getIntent();
		Step todayStep = (Step) intent.getSerializableExtra("todayStep");
		todaySteps.setText("" + todayStep.getSteps());
		todayDay.setText(convertDayToTitile(todayStep.getDay()));
		todayDisCal.setText("走路"+todayStep.getDistance()+"米，消耗热量"+NumFormat.roundOff(todayStep.getCaloria(), 2)+"千卡");
		nickName.setText(user.getNickName());
		ImageUtils.loadImage(user.getImgURL(), headImg);
		
		if(todayStep.getSteps() >= user.getAim()){
			titleView.setBackgroundColor(getResources().getColor(R.color.md_orange_600));
			aboveView.setBackgroundColor(getResources().getColor(R.color.md_orange_600));
			btnShare.setImageDrawable(getResources().getDrawable(R.drawable.share_orange));
			todayArouse.setText("在漫长的路都是一步步走出来的~");
		}
	}

	
	
	private void screenShot() {
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		try {
			File file = Environment.getExternalStorageDirectory();
			if (file.exists()) {
				OutputStream out = new FileOutputStream(new File(file,
						"temp.jpg"));
				bitmap.compress(CompressFormat.JPEG, 100, out);
				out.flush();
				out.close();
				LogUtils.e(" I lfjslkfjsklfjskljf");
			} else {
				ToastUtils.showShort("您的手机不支持内存卡");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取分享的图片的路径
	 * 
	 * @return
	 */
	private String getFilePath() {
		File file = Environment.getExternalStorageDirectory();
		if (file.exists()) {
			return file.getAbsolutePath() + "/temp.jpg";
		}
		return null;

	}


	@Event(value = { R.id.btn_share,R.id.btn_prev })
	private void share(View view) {
		screenShot();
		switch (view.getId()) {
		case R.id.btn_share:
			ShareUtils.shareImage(this, Uri.parse(getFilePath()), "来自乐计步的分享");
			break;
		case R.id.btn_prev:
			finish();
			overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
			break;
		default:
			break;
		}
	}

	private String convertDayToTitile(String time) {
		String[] strArr = time.split("-");
		int month = Integer.parseInt(strArr[1]);
		int day = Integer.parseInt(strArr[2]);
		return month + "月" + day + "日";
	}
}
