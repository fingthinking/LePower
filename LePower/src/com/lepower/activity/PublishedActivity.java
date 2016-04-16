package com.lepower.activity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.lepower.R;
import com.lepower.callback.MyCommonCallback;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.AlbumHelper;
import com.lepower.model.Bimp;
import com.lepower.model.Expressions;
import com.lepower.model.ImageBucket;
import com.lepower.model.User;
import com.lepower.utils.FileUtils;
import com.lepower.utils.LeUrls;
import com.lepower.utils.LogUtils;
import com.lepower.utils.NetUtils;
import com.lepower.utils.ToastUtils;
import com.lepower.widget.TitleBar;

public class PublishedActivity extends Activity {

	private GridView noScrollgridview;
	private GridAdapter adapter;
	private GridView gridView;
	private EditText etMood;
	private TitleBar titleBar;
	private Dialog loadbar = null;
	private int[] expressionImages;
	private String[] expressionImageNames;
	private ImageButton choose;
	private Context context;
	AlbumHelper helper;
	public static final String EXTRA_IMAGE_LIST = "imagelist";
	List<String> imageUrl = new ArrayList<String>();
	List<ImageBucket> dataList;
	ArrayList<GridView> grids;
	private ViewPager viewPager;
	public static Bitmap bimap;
	private UserDaoImpl user;
	String fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
	private InputMethodManager manager;

	private boolean flag = false; // 来自三方标志

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.saysay);
		this.context = this;
		manager = (InputMethodManager) context
				.getSystemService(context.INPUT_METHOD_SERVICE);
		expression();
		user = new UserDaoImpl();
		if (user.getUserNow() == null) {
			ToastUtils.showShort("您未登录乐动力，请先登录");
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();
			return;
		}
		Init();

	}

	public void Init() {
		// Log.i(TAG,"aaaaa");
		Intent intent = getIntent();
		Uri uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
		if (uri != null) {
			Bimp.drr.add(uri.toString());
			flag = true;
		}
		gridView = (GridView) findViewById(R.id.gridView);
		etMood = (EditText) findViewById(R.id.etMood);
		// allImages.add(BitmapFactory.decodeResource(getResources(),
		// R.drawable.icon_addpic_focused));
		etMood.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				viewPager.setVisibility(View.GONE);
			}
		});
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		noScrollgridview = (GridView) findViewById(R.id.gridView);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		adapter.update();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == Bimp.bmp.size()) {
					new PopupWindows(PublishedActivity.this, noScrollgridview);// 点击最后一个+号增加
				} else {
					Intent intent = new Intent(PublishedActivity.this,
							PhotoActivity.class);// 点击对应图片，打开图片
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		titleBar = (TitleBar) findViewById(R.id.titleBar);
		titleBar.showLeftStrAndRightStr(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}

		}, new OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (!etMood.getText().toString().trim().isEmpty()) {
					// HttpHelper h=new HttpHelper();
					String url = LeUrls.CIRCLE_PUBLISH;

					Map<String, String> map = new HashMap<String, String>();
					map.put("userId", user.getUserNow().getUserId());
					map.put("content", etMood.getText().toString().trim());
					map.put("publishAddr", null);
					if (flag) {
						map.put("scopeFlag", "2");
					} else {
						map.put("scopeFlag", "0");
					}
					// Bimp.revitionImageSize(url);
					// Intent intent=new Intent();
					// intent.getExtras();
					NetUtils.uploadFile(url, map, Bimp.drr,
							new MyCommonCallback<String>() {
								@Override
								public void onSuccess(String result) {
									super.onSuccess(result);
									LogUtils.e("result" + result);
									System.out.println(result);
								}

								@Override
								public void onError(Throwable throwable,
										boolean isOnCallBack) {
									LogUtils.e(throwable.getMessage());
									// super.onError(throwable, isOnCallBack);
								}
							});
					etMood.setText("");
					Bimp.bmp.clear();
					Bimp.drr.clear();
					Bimp.max = 0;
					adapter.notifyDataSetChanged();
					close();
					Intent intent = new Intent(PublishedActivity.this,
							BaseCircleActivity.class);
					setResult(3, intent);
					finish();
					flag = false;
					Toast.makeText(PublishedActivity.this, "已经发表",
							Toast.LENGTH_LONG).show();
					// close();
				} else {
					Toast.makeText(PublishedActivity.this, "亲写点什么吧。。。",
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	public void close() {
		if (loadbar != null) {
			if (loadbar.isShowing()) {
				loadbar.dismiss();
			}
		}
	}

	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater; // 视图容器
		private int selectedPosition = -1;// 选中的位置
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			return (Bimp.bmp.size() + 1);
		}

		public Object getItem(int arg0) {

			return null;
		}

		public long getItemId(int arg0) {

			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		/**
		 * ListView Item设置
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			final int coord = position;
			ViewHolder holder = null;
			if (convertView == null) {

				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == Bimp.bmp.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
				if (position == 9) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(Bimp.bmp.get(position));
			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		// 加載
		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.drr.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							try {
								String path = Bimp.drr.get(Bimp.max);
								System.out.println(path);
								Bitmap bm = Bimp.revitionImageSize(path);
								Bimp.bmp.add(bm);// 加載到bmp里
								String newStr = path.substring(
										path.lastIndexOf("/") + 1,
										path.lastIndexOf("."));
								// Bimp.revitionImageSize()
								FileUtils.saveBitmap(bm, "" + newStr);
								Bimp.max += 1;
								Message message = new Message();
								message.what = 1;
								handler.sendMessage(message);
							} catch (IOException e) {

								e.printStackTrace();
							}
						}
					}
				}
			}).start();
		}
	}

	public String getString(String s) {
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++) {
			s.charAt(i);
		}
		return path;
	}

	protected void onRestart() {
		adapter.update();
		super.onRestart();
	}

	public class PopupWindows extends PopupWindow {

		public PopupWindows(Context mContext, View parent) {
			manager.hideSoftInputFromWindow(etMood.getWindowToken(), 0);
			View view = View
					.inflate(mContext, R.layout.item_popupwindows, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.fade_ins));
			LinearLayout ll_popup = (LinearLayout) view
					.findViewById(R.id.ll_popup);
			ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.push_bottom_in_2));
			viewPager.setVisibility(View.GONE);
			setWidth(LayoutParams.FILL_PARENT);
			setHeight(LayoutParams.FILL_PARENT);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();

			Button bt1 = (Button) view
					.findViewById(R.id.item_popupwindows_camera);
			Button bt2 = (Button) view
					.findViewById(R.id.item_popupwindows_Photo);
			Button bt3 = (Button) view
					.findViewById(R.id.item_popupwindows_cancel);
			bt1.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					photo();
					dismiss();
				}
			});
			bt2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(PublishedActivity.this,
							PictureActivity.class);
					startActivity(intent);
					dismiss();
				}
			});
			bt3.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					dismiss();
				}
			});

		}
	}

	private static final int TAKE_PICTURE = 0x000000;
	private String path = "";

	public void photo() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File file = new File(Environment.getExternalStorageDirectory() + "/",
				String.valueOf(System.currentTimeMillis()) + ".jpg");
		path = file.getPath();
		Uri imageUri = Uri.fromFile(file);
		System.out.println("圖片地址為" + imageUri);
		// Log.i(TAG, "" + imageUri);
		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(openCameraIntent, TAKE_PICTURE);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.e("I am Here", "====");
		switch (requestCode) {
		case TAKE_PICTURE:
			if (Bimp.drr.size() < 9 && resultCode == -1) {
				Bimp.drr.add(path);
				// 加載
				// onRestart();
				// Log.i(TAG, "onRestart");

			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void expression() {
		// Log.i(TAG,"aaaaa");
		expressionImages = Expressions.EMOJID;
		expressionImageNames = Expressions.EMOJNAMES;
		// 创建ViewPager
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		// etMood = (EditText) findViewById(R.id.etMood);
		choose = (ImageButton) findViewById(R.id.choose);
		LayoutInflater inflater = LayoutInflater.from(this);
		grids = new ArrayList<GridView>();
		GridView gView = (GridView) inflater.inflate(R.layout.grid, null);
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		// 生成24个表情
		for (int i = 0; i < expressionImages.length; i++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("image", expressionImages[i]);
			listItems.add(listItem);
		}

		// 适配器表情添加
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
				R.layout.singleexpression, new String[] { "image" },
				new int[] { R.id.image });
		gView.setAdapter(simpleAdapter);
		gView.setNumColumns(6);
		gView.setBackgroundColor(Color.rgb(214, 211, 214));
		gView.setHorizontalSpacing(1);
		gView.setVerticalSpacing(1);
		gView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		gView.setGravity(Gravity.CENTER);
		gView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Bitmap bitmap = null;
				bitmap = BitmapFactory.decodeResource(getResources(),
						expressionImages[arg2 % expressionImages.length]);

				ImageSpan imageSpan = new ImageSpan(PublishedActivity.this,
						bitmap);
				SpannableString spannableString = new SpannableString(
						expressionImageNames[arg2]);
				spannableString.setSpan(imageSpan, 0,
						expressionImageNames[arg2].length(),
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				etMood.append(spannableString);

				choose.setImageDrawable(getResources().getDrawable(
						R.drawable.emo_press));
			}
		});

		grids.add(gView);
		// etMood.setFocusable(true);
		// (etMood).setSoftInputMode();
		// grids.add((GridView) inflater.inflate(R.layout.grid2, null));
		// grids.add((GridView) inflater.inflate(R.layout.grid3, null));
		// manager = (InputMethodManager)
		// context.getSystemService(context.INPUT_METHOD_SERVICE);
		// manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		choose.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (viewPager.getVisibility() == View.GONE) {

					viewPager.setVisibility(View.VISIBLE);
					manager.hideSoftInputFromWindow(etMood.getWindowToken(), 0);

				} else if (viewPager.getVisibility() == View.VISIBLE) {
					viewPager.setVisibility(View.GONE);
					manager.toggleSoftInput(0,
							InputMethodManager.HIDE_NOT_ALWAYS);
				}
			}
		});

		PagerAdapter mPagerAdapter = new PagerAdapter() {
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return grids.size();
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(grids.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(grids.get(position));
				return grids.get(position);
			}
		};
		viewPager.setAdapter(mPagerAdapter);
	}

}
