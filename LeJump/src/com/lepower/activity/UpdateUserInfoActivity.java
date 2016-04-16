package com.lepower.activity;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lepower.App;
import com.lepower.R;
import com.lepower.callback.HttpCallback;
import com.lepower.dao.IUserDao;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.MessageObj;
import com.lepower.model.User;
import com.lepower.utils.AddressDialogUtil;
import com.lepower.utils.DateTimePickDialogUtil;
import com.lepower.utils.HttpUtils;
import com.lepower.utils.ImageUtils;
import com.lepower.utils.LeUrls;
import com.lepower.utils.LogUtils;
import com.lepower.utils.ToastUtils;
import com.lepower.utils.Tools;

@ContentView(R.layout.activity_update_user_info)
public class UpdateUserInfoActivity extends BaseActivity {

	@ViewInject(R.id.img_userhead)
	private ImageView headView;

	@ViewInject(R.id.nickName_userInfo)
	private EditText etNickName;// 昵称
	@ViewInject(R.id.radio_group)
	private RadioGroup rgSexRadioGroup;// 性别
	@ViewInject(R.id.radioButton1)
	private RadioButton maleButton;
	@ViewInject(R.id.radioButton2)
	private RadioButton femaleButton;
	@ViewInject(R.id.update_user_info_height)
	private TextView heightTextView;
	private List<String> listHeight;// 身高

	@ViewInject(R.id.update_user_info_weight)
	private TextView weightTextView;
	private List<String> listWeight;// 体重

	@ViewInject(R.id.update_user_info_birthday)
	private TextView birthdayTextView;// 生日布局
	@ViewInject(R.id.update_user_info_address)
	private TextView addressTextView;// 地址布局
	@ViewInject(R.id.tel)
	private TextView txtTel;// 修改手机号码
	@ViewInject(R.id.email)
	private TextView txtEmail;// 修改邮箱
	@ViewInject(R.id.password)
	private TextView txtPwd;// 修改密码

	private View heightLayout;// 身高布局
	private View weightLayout;// 体重布局

	private ListView myHeightListView;// 身高listview
	private ListView myWeightListView;// 体重listview
	private AlertDialog.Builder builder;
	private AlertDialog alertDialogHeight; // 身高对话框
	private AlertDialog alertDialogWeight;// 体重对话框

	private IUserDao userDao;

	private String headUrl;
	private String nickName;
	private String sex;
	private String height;
	private String weight;
	private String birthday;
	private String province;
	private String city;
	private String phoneNum;
	private String email;
	private User user;
	private Button tuichu;

	// 请求码 设置头像
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;

	private static final int UPDATE_PHONE_NUM = 3;
	private static final int UPDATE_EMAIL_NUM = 4;
	private static final int UPDATE_PASSWD = 5;
	private GestureDetector mDetector;

	private String[] items = new String[] { "选择本地图片", "拍照" };
	// 头像名称
	private static final String IMAGE_FILE_NAME = "faceImage.jpg";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		userDao = new UserDaoImpl();
		initHeightWeight();
		initControls();
		initData();
		mDetector = new GestureDetector(this,
				new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {

						if (e2.getRawX() - e1.getRawX() > 100) {
							Intent intent = new Intent(
									UpdateUserInfoActivity.this,
									JumpMainActivity.class);

							startActivity(intent);
							return true;
						}

						return super.onFling(e1, e2, velocityX, velocityY);
					}
				});
	}

	@Event(value = { R.id.tuichu, R.id.login_reback_btn,
			R.id.update_user_info_address, R.id.update_user_info_birthday,
			R.id.update_user_info_height, R.id.update_user_info_weight,
			R.id.img_userhead, R.id.tel, R.id.password, R.id.email, R.id.finish })
	private void click(View view) {
		switch (view.getId()) {
		case R.id.tuichu:
			userDao.deleteUserNow();
			Intent intent1 = new Intent(this, LoginActivity.class);
			startActivity(intent1);
			finish();
			break;
		case R.id.login_reback_btn: {
			finish();
		}
			break;
		case R.id.update_user_info_address: {
			AddressDialogUtil addressDialog = new AddressDialogUtil(
					UpdateUserInfoActivity.this);
			addressDialog.addressDialog(addressTextView);
		}
			break;
		case R.id.update_user_info_birthday: {
			DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
					UpdateUserInfoActivity.this);
			dateTimePicKDialog.dateTimePicKDialog(birthdayTextView);
		}
			break;
		case R.id.update_user_info_height: {
			heightDialog();
		}
			break;
		case R.id.update_user_info_weight: {
			weightDialog();
		}
			break;
		case R.id.img_userhead: {
			showDialog();
		}
			break;
		case R.id.tel: {
			Intent intent = new Intent(UpdateUserInfoActivity.this,
					UpdateTelActivity.class);
			intent.putExtra("phoneNum", phoneNum);
			startActivityForResult(intent, UPDATE_PHONE_NUM);
		}
			break;
		case R.id.email: {
			Intent intent = new Intent(UpdateUserInfoActivity.this,
					UpdateEmailActivity.class);
			intent.putExtra("email", email);
			startActivityForResult(intent, UPDATE_EMAIL_NUM);
		}
			break;
		case R.id.password: {
			Intent intent = new Intent(UpdateUserInfoActivity.this,
					UpdatePwdActivity.class);
			intent.putExtra("userId", user.getUserId());
			startActivity(intent);
		}
			break;
		case R.id.finish: {
			if (check()) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("userId", user.getUserId());
				params.put("imgURL", headUrl);
				params.put("nickName", nickName);
				params.put("sex", sex);
				params.put("height", height);
				params.put("weight", weight);
				params.put("birthday", birthday);
				params.put("province", province);
				params.put("city", city);
				HttpUtils.post(LeUrls.UPDATE_USER_INFO, params,
						new HttpCallback<String>() {
							@Override
							public void onSuccess(String response) {
								// TODO Auto-generated method stub
								LogUtils.e(response);
								Gson gson = new Gson();
								Type type = new TypeToken<MessageObj<User>>() {
								}.getType();
								MessageObj<User> userMsg = gson.fromJson(
										response, type);
								if (userMsg.getResCode().equals("0")) {
									userDao.saveUserNow(userMsg.getData());
								} else {
									ToastUtils.showShort(userMsg.getResMsg());
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
						});
			}
		}
			break;
		default:
			break;
		}
	}

	// 获得控件
	public void initControls() {

		tuichu = (Button) findViewById(R.id.tuichu);

		heightLayout = LayoutInflater.from(this).inflate(R.layout.list_height,
				null);
		weightLayout = LayoutInflater.from(this).inflate(R.layout.list_weight,
				null);

		myHeightListView = (ListView) heightLayout
				.findViewById(R.id.height_listview);// 获得身高listview

		myHeightListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				heightTextView.setText(listHeight.get(position));
				alertDialogHeight.dismiss();
			}
		});

		myWeightListView = (ListView) weightLayout
				.findViewById(R.id.weight_listview);// 获得体重listview
		myWeightListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				weightTextView.setText(listWeight.get(position));
				alertDialogWeight.dismiss();
			}
		});
	}

	/**
	 * 初始化页面数据
	 */
	private void initData() {
		user = userDao.getUserNow();
		ImageUtils.loadImage(user.getImgURL(), headView);
		headUrl = user.getImgURL();
		System.out.println("==============etNickName================"+etNickName);
		etNickName.setText(user.getNickName());
		if (user.getSex().equals("男")) {
			maleButton.setChecked(true);
		} else {
			femaleButton.setChecked(true);
		}
		heightTextView.setText(user.getHeight());
		weightTextView.setText(user.getWeight());
		birthdayTextView.setText(user.getBirthday());
		addressTextView.setText(user.getProvince() + "," + user.getCity());
		if (TextUtils.isEmpty(user.getPhoneNum())) {
			txtTel.setText("您未绑定手机号，点击可以绑定");
		} else {
			txtTel.setText(user.getPhoneNum());
			phoneNum = user.getPhoneNum();
		}
		if (TextUtils.isEmpty(user.getEmail())) {
			txtEmail.setText("您未绑定邮箱，点击可以绑定");
		} else {
			txtEmail.setText(user.getEmail());
			email = user.getEmail();
		}

	}

	/**
	 * 显示选择对话框
	 */
	private void showDialog() {

		new AlertDialog.Builder(this)
				.setTitle("设置头像")
				.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							Intent intentFromGallery = new Intent();
							intentFromGallery.setType("image/*"); // 设置文件类型
							intentFromGallery
									.setAction(Intent.ACTION_GET_CONTENT);
							startActivityForResult(intentFromGallery,
									IMAGE_REQUEST_CODE);
							break;
						case 1:

							Intent intentFromCapture = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							// 判断存储卡是否可以用，可用进行存储
							if (Tools.hasSdcard()) {

								intentFromCapture.putExtra(
										MediaStore.EXTRA_OUTPUT,
										Uri.fromFile(new File(Environment
												.getExternalStorageDirectory(),
												IMAGE_FILE_NAME)));
							}

							startActivityForResult(intentFromCapture,
									CAMERA_REQUEST_CODE);
							break;
						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 结果码不等于取消时候
		if (resultCode != RESULT_CANCELED) {
			switch (requestCode) {
			case IMAGE_REQUEST_CODE: // 开始裁剪图片
				startPhotoZoom(data.getData(), RESULT_REQUEST_CODE);
				break;
			case CAMERA_REQUEST_CODE: // 存储图片
				if (Tools.hasSdcard()) {
					File tempFile = new File(
							Environment.getExternalStorageDirectory() + "/"
									+ IMAGE_FILE_NAME);
					startPhotoZoom(Uri.fromFile(tempFile), RESULT_REQUEST_CODE);
				} else {
					Toast.makeText(UpdateUserInfoActivity.this,
							"未找到存储卡，无法存储照片！", Toast.LENGTH_LONG).show();
				}

				break;
			case RESULT_REQUEST_CODE: { // 加载图片
				if (data != null) {
					getImageToView(data);
				}
			}
				break;

			case UPDATE_PHONE_NUM: {// 修改手机号\
				String phone = data.getStringExtra("phone");
				txtTel.setText(phone);
			}
				break;
			case UPDATE_EMAIL_NUM: {
				String email = data.getStringExtra("email");
				ToastUtils.showShort(email);
				txtEmail.setText(email);
			}
				break;
			default:
				break;
			}

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri, int requestCode) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, requestCode);
	}

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 */
	private void getImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			headView.setImageBitmap(photo);
			ImageUtils.uploadImage(LeUrls.UPLOAD_IMG, photo,
					new HttpCallback<String>() {
						@Override
						public void onSuccess(String response) {
							// TODO Auto-generated method stub

							headUrl = LeUrls.BASE_IMAGE_URL + response;
							LogUtils.e(headUrl);
							ToastUtils.showShort("headUrl:" + headUrl);
						}

						@Override
						public void onError(Throwable e, boolean arg1) {
							if (!HttpUtils.isNetWork(App.context)) {
								ToastUtils.showShort("请检查网络状况");
							} else {
								LogUtils.e(e.getMessage());
							}
						}
					});
		}
	}

	protected void heightDialog() { // 身高选择对话框
		if (alertDialogHeight == null) {
			ArrayAdapter<String> heightAdapter = new ArrayAdapter<String>(
					UpdateUserInfoActivity.this, R.layout.list_item_view,
					R.id.item_textview, listHeight);
			myHeightListView.setAdapter(heightAdapter);
			builder = new AlertDialog.Builder(UpdateUserInfoActivity.this)
					.setTitle("选择");
			builder.setView(heightLayout);
			alertDialogHeight = builder.create();
		}
		alertDialogHeight.show();

	}

	protected void weightDialog() { // 体重选择对话框
		if (alertDialogWeight == null) {
			ArrayAdapter<String> weightAdapter = new ArrayAdapter<String>(
					UpdateUserInfoActivity.this, R.layout.list_item_view,
					R.id.item_textview, listWeight);
			myWeightListView.setAdapter(weightAdapter);
			builder = new AlertDialog.Builder(UpdateUserInfoActivity.this)
					.setTitle("选择");
			builder.setView(weightLayout);
			alertDialogWeight = builder.create();
		}
		alertDialogWeight.show();

	}

	protected void initHeightWeight() {
		// 初始化身高数据
		listHeight = new ArrayList<String>();
		for (int i = 70; i < 254; i++) {
			listHeight.add(i + "cm");
		}
		// 初始化体重数据
		listWeight = new ArrayList<String>();
		for (int i = 20; i < 151; i++) {
			listWeight.add(i + "kg");
		}

	}

	private boolean check() {
		nickName = etNickName.getText().toString().trim();
		height = heightTextView.getText().toString().trim();
		weight = weightTextView.getText().toString().trim();
		birthday = birthdayTextView.getText().toString().trim();
		String address = addressTextView.getText().toString().trim();
		sex = maleButton.isChecked() ? "男" : "女";
		if (TextUtils.isEmpty(nickName)) {
			ToastUtils.showShort("昵称不能为空");
			return false;
		}
		if (TextUtils.isEmpty(height)) {
			ToastUtils.showShort("身高不能为空");
			return false;
		}
		if (TextUtils.isEmpty(weight)) {
			ToastUtils.showShort("体重不能为空");
			return false;
		}
		if (TextUtils.isEmpty(birthday)) {
			ToastUtils.showShort("生日不能为空");
			return false;
		}
		if (TextUtils.isEmpty(address)) {
			ToastUtils.showShort("地址不能为空");
			return false;
		}
		province = address.split(",")[0];
		city = address.split(",")[1];
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
}
