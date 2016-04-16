package com.lepower.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.lepower.R;
import com.lepower.adapter.EmjImageAdapter;
import com.lepower.adapter.MyEPagerAdapter;
import com.lepower.model.Emoj;
import com.lepower.model.Expressions;
import com.lepower.model.Reply;

public class EmojWindow {
	
	private ImageView image;
	private ViewPager viewPager;
	private List<View> views;
	private Button send;
	private MyEPagerAdapter adapter;
	private LinearLayout container;
	private EditText text;

	private LinearLayout emojVContainer;

	private Button showWindow;

	private LinearLayout parent;


	private boolean isShow = false;

	private static int column = 7;
	private static int row = 3;
	//长宽补丁
	private static int patch = 40;
	//页数
	private static final int PAGESIZE=5;
	//每个表情名字的长度
	private static int faceLen=6;
			
	private Context context;

	private InputMethodManager manager;

	final List<Emoj> emojContainers=new ArrayList<Emoj>();


	private PopupWindow editWindow=null;
	
	private ReplySend replySend;
	
	private Reply reply;
	
	private static EmojWindow emojWindow;
	/**
	 * 私有
	 * @param context
	 * @param replySend
	 */
	private EmojWindow(Context context) {
		this.context=context;
		initPopupWind();
	}
	
	/**
	 * 获取单实例
	 * @param context
	 * @return
	 */
	public static synchronized  EmojWindow getInstance(Context context) {
		if(emojWindow==null){
			emojWindow=new EmojWindow(context);
		}
		return emojWindow;
	}
	/**
	 * 设置回调接口
	 * @param replySend
	 */
	public void setReplySend(ReplySend replySend) {
		this.replySend = replySend;
	}
	
	/**
	 * 显示窗体
	 * @param v
	 */
	public void showWindow(View v,Reply reply) {
		editWindow.setFocusable(true);

		this.reply=reply;
		// 以下两句不能颠倒
		editWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
		editWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);	
		text.setHint("回复 : " + reply.getReplyName());	
		editWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
		// 显示键盘
		manager = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
		manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		
	}
	
	/**
	 * 初始化弹出界面
	 */
	@SuppressWarnings("deprecation")
	public void initPopupWind() {
		
		LinearLayout popView = (LinearLayout) LayoutInflater.from(context)
				.inflate(R.layout.activity_emj_keyboard, null);

		image = (ImageView) popView.findViewById(R.id.smail);
		send = (Button) popView.findViewById(R.id.send_msg);
		text = (EditText) popView.findViewById(R.id.reply);
		//text 里无数据时   send 按钮为不可点击状态
		text.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s == null || s == "" || s.length() == 0) {
					send.setEnabled(false);
				} else {
					send.setEnabled(true);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		
		//发送评论
		send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String content=text.getText().toString();
				reply.setContent(content);
				replySend.onSend(reply);
				editWindow.dismiss();
				text.setText("");
				emojVContainer.setVisibility(View.GONE);
				manager.hideSoftInputFromWindow(text.getWindowToken(), 0);
			}
		});
		
		parent = (LinearLayout) popView.findViewById(R.id.parent);

		DisplayMetrics outMetrics = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		int width = outMetrics.widthPixels;
		int heght = outMetrics.heightPixels;
		
		viewPager = new ViewPager(context);
		viewPager.setLayoutParams(new LayoutParams(width, (int) (width * 0.46)+10));
		viewPager.setPadding(0, 30, 0, 0);
		viewPager.setBackgroundColor(context.getResources().getColor(R.color.white));

		// ============ GridView =====
		views = new ArrayList<View>();
		for(int i=0;i<PAGESIZE;i++){
			GridView grid=(GridView) getGridView();
			views.add(grid);
		}
		
		//5页 ==== === == ====填充数据源  ==== == ========
		for(int i=1;i<=PAGESIZE;i++){
			
			int j=(i-1)*20;
			
			Emoj emoj=new Emoj();
			emoj.emojIds=new ArrayList<Integer>();
			emoj.emojNames=new ArrayList<String>();
			
			
			for(;j<=i*20;j++){
				emoj.emojIds.add(Expressions.EMOJID[j]);
				emoj.emojNames.add(Expressions.EMOJNAMES[j]);
			}
			
			emojContainers.add(emoj);
		}
		
		//5页 ==== === == ====填充数据源 结束 ==== == ========
		//单个表情长度
		faceLen = Expressions.EMOJNAMES[0].length();
		
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.emoji_00310);

		int emjHeigh = (int) ((width * 1.0) * 0.4);
		int oneWidth = bitmap.getWidth();
		int oneHeigh = bitmap.getHeight();
		int spaceH = (width - oneWidth * 7) / (column + 1);
		int spaceV = (emjHeigh - oneHeigh * 3) / (row + 1);
		
		
		//=== ==== ============== 创建PAGESIZE 个 分页==========
		
		for(int i=0;i<PAGESIZE;i++){
			
			GridView gridView= (GridView) views.get(i);
			gridView.setHorizontalSpacing(spaceH - patch);
			gridView.setVerticalSpacing(spaceV - patch + 32);
			gridView.setNumColumns(column);
			
			Emoj emoj=emojContainers.get(i);
			
			EmjImageAdapter vAdapter = new EmjImageAdapter(emoj.emojIds, emoj.emojNames, context, 1.0f);
			gridView.setAdapter(vAdapter);
			
			gridView.setOnItemClickListener(myEmojItemClick);
		}
		
		
		adapter = new MyEPagerAdapter(views, context);

		viewPager.setAdapter(adapter);

		emojVContainer=new LinearLayout(context);
		emojVContainer.setOrientation(LinearLayout.VERTICAL);
		emojVContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		emojVContainer.setVisibility(View.GONE);
		
		emojVContainer.addView(viewPager);
		
		
		//===================以点作为页的指示器  ==========================
		
		final LinearLayout dotContainer=(LinearLayout) getLinearLayout();
		
		Bitmap dotFs=BitmapFactory.decodeResource(context.
				getResources(), R.drawable.page_indicator_focused);
		Bitmap dotUf=BitmapFactory.decodeResource(context.
				getResources(), R.drawable.page_indicator_unfocused);
		dotContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,dotFs.getWidth()*3));
		
		for(int i=0;i<PAGESIZE;i++){
			ImageView dot=(ImageView) getDotImageView();
			dot.setLayoutParams(new LayoutParams(dotFs.getWidth(), dotFs.getHeight()));
			if(i==0){
				dot.setImageResource(R.drawable.page_indicator_focused);
			}else{
				dot.setImageResource(R.drawable.page_indicator_unfocused);
			}
			
			dotContainer.addView(dot);
			//填充物
			View hSpace=new View(context);
			hSpace.setLayoutParams(new LayoutParams(dotFs.getWidth(), dotFs.getHeight()));
			
			dotContainer.addView(hSpace);
		}

		emojVContainer.addView(dotContainer);
		
		parent.addView(emojVContainer);
		
		viewPager.setTag(0);
		
		// ========================== 页的指示器结束  ==================================
		
		
		//======================   当页面变化时更新指示器 ===================
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				
				int prePage=(Integer) viewPager.getTag();
				
				ImageView dotImage=(ImageView) dotContainer.getChildAt(prePage*2);
				dotImage.setImageResource(R.drawable.page_indicator_unfocused);
				ImageView currentImage=(ImageView)dotContainer.getChildAt(position*2);
				currentImage.setImageResource(R.drawable.page_indicator_focused);
				
				viewPager.setTag(position);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		
		
		// ===========================更新指示器结束  =============================
		
		
		//==================创建窗体=============================
		editWindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		editWindow.setAnimationStyle(R.style.reply_window_anim);
		editWindow.setBackgroundDrawable(context.getResources().getDrawable(R.color.white));
		editWindow.setOutsideTouchable(false);
		
		image.setOnClickListener(new OnClickListener() {
			
			/**
			 * 表情键盘和文字键盘的切换  
			 */
			@Override
			public void onClick(View v) {
				if (emojVContainer.getVisibility() == View.GONE) {

					emojVContainer.setVisibility(View.VISIBLE);
					
					editWindow.setSoftInputMode(
							WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
					manager.hideSoftInputFromWindow(editWindow.getContentView().getWindowToken(), 0);
					
				} else if (emojVContainer.getVisibility() == View.VISIBLE) {
					emojVContainer.setVisibility(View.GONE);
					editWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE|
							WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
					manager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
				}
				
			}
		});

		//测试
		text.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				emojVContainer.setVisibility(View.GONE);
			}
		});

	}
	
	/**
	 * 监听每一个 GridView 的Item点击事件
	 */
	OnItemClickListener myEmojItemClick = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {

			int size=parent.getChildCount();
			//最后一个图片为删除南按钮
			if(position==size-1){
				int selectIndex=text.getSelectionStart();
				if(selectIndex==0)  return ; //光标在文字开头
				
				String content=text.getText().toString();
				if(content!=null&&content.length()>0){
					int len=content.length();
					if(content.charAt(selectIndex-1)!=']'){
						//删除文字
						text.getEditableText().delete(selectIndex-1, selectIndex);
					}else{
						//删除一个表情
						
//						System.out.println(content+"  " + faceLen + "  " + selectIndex);
						
						text.getEditableText().delete(selectIndex-faceLen, selectIndex);
					}
					
//					System.out.println(selectIndex +"  " + content);
				}
				
			}else{
				
				//获取资源id
				EmjImageAdapter.ViewHolder holder = (EmjImageAdapter.ViewHolder) view
						.getTag();

				int cc = parent.getChildCount();

				Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
						holder.resourcesId);
				
				ImageSpan span = new ImageSpan(context, bitmap);

				String name = emojContainers.get(0).emojNames.get(position);

				SpannableStringBuilder sb = new SpannableStringBuilder(name, 0,
						name.length());
				//将字符转换成表情
				sb.setSpan(span, 0, name.length(),
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				
				
				text.append(sb);
			}
		}
	};

	/**
	 * 获取表情网格
	 * @return
	 */
	public View getGridView() {
		return LayoutInflater.from(context).inflate(R.layout.grid_emj, null);
	}
	/**
	 * 点 的指示器容器
	 * @return
	 */
	public View getLinearLayout(){
		return LayoutInflater.from(context).inflate(R.layout.page_indicator_dot_container, null);
	}
	/**
	 * 点的imageView
	 * @return
	 */
	public View getDotImageView(){
		return LayoutInflater.from(context).inflate(R.layout.page_indicator_dot, null);
	}
	
	
	public interface ReplySend{
		String onSend(Reply reply);
	}
}
