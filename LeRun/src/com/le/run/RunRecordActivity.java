package com.le.run;

import java.util.ArrayList;

import com.le.run.db.RunData;
import com.le.run.entity.Run;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class RunRecordActivity extends Activity {
	ArrayList<Run> listRun = null;
	View v = null;
       @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.run_record1);
    	
    	
    	RunData runData = new RunData(getApplicationContext());   	
        listRun = runData.getRunData();
    	ListView list_record = (ListView) findViewById(R.id.list_record);
    	list_record.setAdapter(new MyAdapter());
    
    	list_record.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				Run run = listRun.get(position);
				Intent intent = new Intent(RunRecordActivity.this,RunDetialRecordActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("runId", run.getRunId());
				intent.putExtras(bundle);
				startActivity(intent);
				
			}
		});
    	
    }
       class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listRun.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			Run run = listRun.get(position);		
			v = View.inflate(RunRecordActivity.this, R.layout.run_record2, null);
			TextView date = (TextView) v.findViewById(R.id.record_date);
			TextView distance = (TextView) v.findViewById(R.id.record_distance);
			TextView time = (TextView) v.findViewById(R.id.record_time);			
			date.setText(run.getDate());
			distance.setText(""+run.getTotalDistance());
			time.setText(""+run.getTime());
			return v;
		}  	   
       }
}
