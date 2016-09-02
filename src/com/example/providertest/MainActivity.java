package com.example.providertest;

import com.example.providertest.R;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
public class MainActivity extends Activity implements View.OnClickListener{

	private Button btnInsert;
	private Button btnDelete;
	private Button btnUpdate;
	private Button btnQuery;
	private String newId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnInsert=(Button)findViewById(R.id.btn_insert);
		btnDelete=(Button)findViewById(R.id.btn_delete);
		btnUpdate=(Button)findViewById(R.id.btn_update);
		btnQuery=(Button)findViewById(R.id.btn_query);
		btnInsert.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
		btnUpdate.setOnClickListener(this);
		btnQuery.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		Uri uri=null;
		Cursor cursor=null;
		ContentValues values=new ContentValues();
		switch(v.getId()){
		case R.id.btn_insert:
			//添加数据
			uri=Uri.parse("content://com.example.databasetest.provider/Book");
			
			values.put("name", "A Clash of Kings");
			values.put("author", "George Martin");
			values.put("pages", 1024);
			values.put("price", 22.85);
			Uri newUri=getContentResolver().insert(uri, values);
			values.clear();
			newId=newUri.getPathSegments().get(1);
			break;
		case R.id.btn_delete:
			//删除,由于在uri中指定了一个newId，所以只会删除指定的那行数据，其他数据不受影响
			uri=Uri.parse("content://com.example.databasetest.provider/Book/"+newId);
			getContentResolver().delete(uri, null, null);
			break;
		case R.id.btn_update:
			uri=Uri.parse("content://com.example.databasetest.provider/Book/"+newId);
			values.put("name", "A Storm of Swords");
			values.put("pages", 1221);
			values.put("price", 24.21);
			getContentResolver().update(uri, values, null, null);
			break;
		case R.id.btn_query:
			//
			uri=Uri.parse("content://com.example.databasetest.provider/Book");
			cursor=getContentResolver().query(uri, null, null, null, null);
			while(cursor.moveToNext()){
				String name=cursor.getString(cursor.getColumnIndex("name"));
				String author=cursor.getString(cursor.getColumnIndex("author"));
				Log.v("test", "name="+name);
				Log.v("test", "author="+author);
			}
			cursor.close();
			break;
		}
		
	}
}
