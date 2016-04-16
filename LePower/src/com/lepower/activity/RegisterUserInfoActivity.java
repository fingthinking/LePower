package com.lepower.activity;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xutils.x;
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
import android.view.LayoutInflater;
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

import com.lepower.App;
import com.lepower.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lepower.callback.HttpCallback;
import com.lepower.dao.IUserDao;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.MessageObj;
import com.lepower.model.QQUserInfo;
import com.lepower.model.User;
import com.lepower.model.WeiBoUserInfo;
import com.lepower.utils.AddressDialogUtil;
import com.lepower.utils.DateTimePickDialogUtil;
import com.lepower.utils.HttpUtils;
import com.lepower.utils.ImageUtils;
import com.lepower.utils.LeUrls;
import com.lepower.utils.LogUtils;
import com.lepower.utils.ToastUtils;
import com.lepower.utils.Tools;

@ContentView(R.layout.activity_user_info)
public class RegisterUserInfoActivity extends BaseActivity {

	// 头像地址
	private String headUrl;
	private String phoneNum;
	private String email;
	private String userPwd;
	private String nickName;
	private String sex;
	private String height;
	private String weight;
	private String birthday;
	private String province;
	private String city;
	private String qqNum;
	private String weiboNum;
	

	private String[] items = new String[] { "选择本地图片", "拍照" };
	// 头像名称
	private static final String IMAGE_FILE_NAME = "faceImage.jpg";
	// 背景名称
	private static final String IMAGE_FILE_Back_NAME = "backImage.jpg";
	// 请求码 设置头像
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	
	@ViewInject(R.id.img_userhead)
	private ImageView faceImage;// 头像
	@ViewInject(R.id.img_background)
	private ImageView backImage;// 背景
	@ViewInject(R.id.nickName)
	private EditText etNickName;// 昵称
	@ViewInject(R.id.radio_group)
	private RadioGroup sexRadioGroup;// 性别
	@ViewInject(R.id.radioButton1)
	private RadioButton maleRadioButton;// 男
	@ViewInject(R.id.radioButton2)
	private RadioButton femaleRadioButton;// 女
	// private RadioButton rb;
	@ViewInject(R.id.txt_height)
	private TextView heightTextView;
	private List<String> listHeight;// 身高

	@ViewInject(R.id.txt_weight)
	private TextView weightTextView;
	private List<String> listWeight;// 体重

	
	private View heightLayout;// 身高布局
	private View weightLayout;// 体重布局
	@ViewInject(R.id.txt_birthday)
	private TextView birthdayTextView;// 生日布局
	@ViewInject(R.id.txt_address)
	private TextView addressTextView;// 地址布局
	
	private ListView myHeightListView;// 身高listview
	private ListView myWeightListView;// 体重listview
	private AlertDialog.Builder builder;
	private AlertDialog alertDialogHeight; // 身高对话框
	private AlertDialog alertDialogWeight;// 体重对话框
	
	private IUserDao userDao;
	
	@Event(value = { R.id.txt_address, R.id.txt_birthday, R.id.txt_height,
			R.id.txt_weight,R.id.btn_finish,R.id.img_userhead,R.id.login_reback_btn})
	private void click(View view) {
		switch (view.getId()) {
		case R.id.login_reback_btn:{
			finish();
		}
		break;
		case R.id.txt_address: {
			AddressDialogUtil addressDialog = new AddressDialogUtil(
					RegisterUserInfoActivity.this);
			addressDialog.addressDialog(addressTextView);
		}
			break;
		case R.id.txt_birthday: {
			DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
					RegisterUserInfoActivity.this);
			dateTimePicKDialog.dateTimePicKDialog(birthdayTextView);
		}
			break;
		case R.id.txt_height: {
			heightDialog();
		}
			break;
		case R.id.txt_weight: {
			weightDialog();
		}
			break;
		case R.id.img_userhead:{
			showDialog();
		}
		break;
		case R.id.btn_finish:{
			if(basicUserInfo()){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("phoneNum", phoneNum);
				params.put("email", email);
				params.put("userPwd", userPwd);
				nickName = etNickName.getText().toString().trim();
				params.put("nickName", nickName);
				params.put("imgURL", headUrl);
				sex = maleRadioButton.isChecked()?"男":"女";
				params.put("sex",sex);
				height = heightTextView.getText().toString().trim();
				params.put("height", height);
				weight = weightTextView.getText().toString().trim();
				params.put("weight", weight);
				birthday = birthdayTextView.getText().toString().trim();
				params.put("birthday", birthday);
				String[] sp = addressTextView.getText().toString().trim().split(",");
				province = sp[0];
				params.put("province", province);
				city = sp[1];
				params.put("city", city);
				params.put("qqNum", qqNum);
				params.put("weiboNum", weiboNum);
				HttpUtils.post(LeUrls.REGISITER_URL, params, new HttpCallback<String>(){
					@Override
					public void onSuccess(String response) {
						// TODO Auto-generated method stub
						LogUtils.e(response);
						Gson gson = new Gson();
						Type type = new TypeToken<MessageObj<User>>(){}.getType();
						MessageObj<User> userMsg = gson.fromJson(response,type);
						if(userMsg.getResCode().equals("0")){
							userDao.saveUserNow(userMsg.getData());
							LogUtils.e(response);
							Intent intent = new Intent(RegisterUserInfoActivity.this, MainActivity.class);
							startActivity(intent);
							finish();
						}else{
							ToastUtils.showShort(userMsg.getResMsg());
						}
					}
					@Override
					public void onError(Throwable e, boolean arg1) {
						if(!HttpUtils.isNetWork(App.context)){
							ToastUtils.showShort("请检查网络状况");
						}else{
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initControls();// 获得控件
		initHeightWeight(); // 初始化身高体重
//		initDatas();// 用微博/QQ数据初始化控件

	//	x.image().bind(faceImage, headUrl);
		userDao = new UserDaoImpl();
		initData();
		
	}

	// 获得控件
	public void initControls() {

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
			case IMAGE_REQUEST_CODE:
				startPhotoZoom(data.getData(), RESULT_REQUEST_CODE);
				break;
			case CAMERA_REQUEST_CODE:
				if (Tools.hasSdcard()) {
					File tempFile = new File(
							Environment.getExternalStorageDirectory() + "/"
									+ IMAGE_FILE_NAME);
					startPhotoZoom(Uri.fromFile(tempFile), RESULT_REQUEST_CODE);
				} else {
					Toast.makeText(RegisterUserInfoActivity.this,
							"未找到存储卡，无法存储照片！", Toast.LENGTH_LONG).show();
				}

				break;
			case RESULT_REQUEST_CODE:{
				if (data != null) {
					getImageToView(data);
				}
			}
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
			faceImage.setImageBitmap(photo);
			ImageUtils.uploadImage(LeUrls.UPLOAD_IMG, photo, new HttpCallback<String>(){
				@Override
				public void onSuccess(String response) {
					// TODO Auto-generated method stub
					headUrl = LeUrls.BASE_IMAGE_URL+response;
					LogUtils.e(headUrl);
				}
				@Override
				public void onError(Throwable e, boolean arg1) {
					if(!HttpUtils.isNetWork(App.context)){
						ToastUtils.showShort("请检查网络状况");
					}else{
						LogUtils.e(e.getMessage());
					}
				}
			});
		}
	}

	protected void heightDialog() { // 身高选择对话框
		if (alertDialogHeight == null) {
			ArrayAdapter<String> heightAdapter = new ArrayAdapter<String>(
					RegisterUserInfoActivity.this, R.layout.list_item_view, R.id.item_textview,
					listHeight);
			myHeightListView.setAdapter(heightAdapter);
			myHeightListView.setSelection(96);
			builder = new AlertDialog.Builder(RegisterUserInfoActivity.this)
					.setTitle("选择");
			builder.setView(heightLayout);
			alertDialogHeight = builder.create();
		}
		alertDialogHeight.show();

	}

	protected void weightDialog() { // 体重选择对话框
		if (alertDialogWeight == null) {
			ArrayAdapter<String> weightAdapter = new ArrayAdapter<String>(
					RegisterUserInfoActivity.this,R.layout.list_item_view, R.id.item_textview,
					listWeight);
			myWeightListView.setAdapter(weightAdapter);
			myWeightListView.setSelection(36);
			builder = new AlertDialog.Builder(RegisterUserInfoActivity.this)
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

	// 用户基本信息验证
	public boolean basicUserInfo() {
		if (etNickName.getText().toString().trim().equals("")) {
			Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (heightTextView.getText().toString().trim().equals("")) {
			Toast.makeText(this, "身高不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (weightTextView.getText().toString().trim().equals("")) {
			Toast.makeText(this, "体重不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (addressTextView.getText().toString().trim().equals("")) {
			Toast.makeText(this, "地址不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;

	}
	
	
	private void initData(){
		Intent intent = getIntent();
		if(intent.getBooleanExtra("fromTel", false)){
			phoneNum = intent.getStringExtra("phoneNum");
		}else{
			email = intent.getStringExtra("email");
		}
		userPwd = intent.getStringExtra("userPwd");
		if(intent.getBooleanExtra("qq", false)){
		   QQUserInfo qqInfo = userDao.getQQInfo();
		   headUrl = qqInfo.getHeadUrl();
		   // 昵称
		   etNickName.setText(qqInfo.getNickname());
		   // 性别
		   if(qqInfo.getGender().equals("男")){
			   maleRadioButton.setChecked(true);
		   }else{
			   femaleRadioButton.setChecked(true);
		   }
		   birthdayTextView.setText(qqInfo.getYear());
		   addressTextView.setText(qqInfo.getProvince()+","+qqInfo.getCity());
		   qqNum = qqInfo.getOpenid();
		}else if(intent.getBooleanExtra("weibo", false)){
			WeiBoUserInfo weiboInfo = userDao.getWeiboInfo();
			headUrl = weiboInfo.getHeadUrl();
			etNickName.setText(weiboInfo.getNickname());
			if(weiboInfo.getGender().equals("m")){
				maleRadioButton.setChecked(true);
			}else{
				femaleRadioButton.setChecked(true);
			}
			addressTextView.setText(weiboInfo.getLocation().replace(" ", ","));
			weiboNum = weiboInfo.getId();
		}else{
			headUrl = LeUrls.DEFAULT_HEAD_URL;
		}
		ImageUtils.loadImage(headUrl, faceImage);
	}

}
