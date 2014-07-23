package com.cloay.stunningrefreshloadmoredemo;

import com.cloay.stunningrefreshloadmoredemo.R;
import com.cloay.stunningrefreshloadmoredemo.widgets.LoadMoreListView;
import com.cloay.stunningrefreshloadmoredemo.widgets.LoadMoreListView.OnLoadMoreListener;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.os.AsyncTask;
import android.os.Bundle;

/**
 * 
 * @ClassName: MainActivity 
 * @Description: This is a demo 
 * @author cloay Email:shangrody@gmail.com 
 * @date 2014-7-23 ÏÂÎç2:15:03 
 *
 */
public class MainActivity extends ActionBarActivity implements OnRefreshListener, OnLoadMoreListener{
	
    private SwipeRefreshLayout swipeLayout;  
    private LoadMoreListView listView;  
    private MyAdapter adapter;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		swipeLayout = (SwipeRefreshLayout) this.findViewById(R.id.swipe_refresh);  
        swipeLayout.setOnRefreshListener(this);  
          
        // set style  
        swipeLayout.setColorScheme(android.R.color.holo_red_light, android.R.color.holo_green_light,  
                android.R.color.holo_blue_bright, android.R.color.holo_orange_light);
        listView = (LoadMoreListView) this.findViewById(R.id.listview);
        listView.setOnLoadMoreListener(this);
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
	}

	@Override
	public void onRefresh() {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				try {
					Thread.sleep(3*1000); //sleep 3 seconds
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				adapter.count = 15;
				adapter.notifyDataSetChanged();
				swipeLayout.setRefreshing(false);
				listView.setCanLoadMore(adapter.count < 45);
				super.onPostExecute(result);
			}
			
		}.execute();
	}
	
	@Override
	public void onLoadMore() {
			
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				try {
					Thread.sleep(3*1000);	//sleep 3 seconds
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				adapter.count += 15;
				adapter.notifyDataSetChanged();
				listView.setCanLoadMore(adapter.count < 45);
				listView.onLoadMoreComplete();
				super.onPostExecute(result);
			}
			
		}.execute();
	}
	
	
	private class MyAdapter extends BaseAdapter{
		public int count = 15;
		@Override
		public int getCount() {
			return count;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(MainActivity.this).inflate(android.R.layout.simple_list_item_1, null);
				holder.textV = (TextView) convertView.findViewById(android.R.id.text1);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.textV.setText("This is " + (position + 1) + " line.");
			return convertView;
		}
		
		private class ViewHolder{
			TextView textV;
		}
		
	}
	
}
