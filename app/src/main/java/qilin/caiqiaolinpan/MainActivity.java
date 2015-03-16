package qilin.caiqiaolinpan;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private long lastPressTime = 0;
	private ListView lv;

    // todo: add in profile pic
	int[] imgs = { R.drawable.profile_wangxinggui,
			R.drawable.profile_zhongbingcui, R.drawable.profile_wangbei,
			R.drawable.profile_zhongbingfeng, R.drawable.profile_zhongbingfeng,
			R.drawable.profile_xiaojun, R.drawable.profile_zhongli,
			R.drawable.profile_wangkaifeng, R.drawable.profile_wangjing,
			R.drawable.profile_wangqilin, R.drawable.profile_zhongkehang };

    // todo: SQLite names & numbers
	String[] names = { };
	String[] numbers = {  };

	MyAdapter adapter = null;

	private ListView.OnItemClickListener listener = new ListView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position,
				long id) {
			final String number = numbers[position];

			new AlertDialog.Builder(MainActivity.this)
					.setTitle("ѡ�����:")
					.setNegativeButton("��绰",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									Uri uri = Uri.parse("tel:" + number);
									Intent intent = new Intent(Intent.ACTION_CALL, uri);
									startActivity(intent);
								}
							})
					.setNeutralButton("������",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									Uri uri = Uri.parse("smsto://" + number);
									Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
									startActivity(intent);
								}
							})
					.setPositiveButton("ȡ��",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
								}
							})
					.show();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		lv = (ListView) findViewById(R.id.lv);
		adapter = new MyAdapter(this);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(listener);
	}

	@Override
	public void onBackPressed() {
		if (System.currentTimeMillis() - lastPressTime <= 1000) {
			finish();
		} else {
			lastPressTime = System.currentTimeMillis();
			Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�",
					Toast.LENGTH_SHORT).show();
		}
	}

	public class MyAdapter extends BaseAdapter {
		private LayoutInflater myInflater;

		public MyAdapter(Context c) {
			myInflater = LayoutInflater.from(c);
		}

		@Override
		public int getCount() {
			return names.length;
		}

		@Override
		public Object getItem(int position) {
			return names[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = myInflater.inflate(R.layout.lv_personal_detail, null);

			TextView tv_name = (TextView) convertView
					.findViewById(R.id.tv_name);
			TextView tv_number = (TextView) convertView
					.findViewById(R.id.tv_number);
			ImageView img = (ImageView) convertView.findViewById(R.id.img);

			img.setImageResource(imgs[position]);
			tv_name.setText(names[position]);
			tv_number.setText(numbers[position]);

			return convertView;
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}
}