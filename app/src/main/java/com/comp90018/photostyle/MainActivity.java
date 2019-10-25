package com.comp90018.photostyle;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.pixplicity.easyprefs.library.Prefs;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
	private String[] classNames;
	private static final Class<?>[] CLASSES = new Class<?>[]{
			ImageActivity.class,
	};



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Boolean loggedIn= Prefs.getBoolean("loggedIn",false);
		if(loggedIn){
			setContentView(R.layout.activity_main);
			classNames = getResources().getStringArray(R.array.class_name);
			ListView listView = findViewById(R.id.list_view);
			ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, classNames);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(this);
		}
		else{
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();
		}



	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		Intent intent = new Intent(this, CLASSES[position]);
		intent.putExtra(BaseActivity.ACTION_BAR_TITLE, classNames[position]);
		startActivity(intent);
	}
}
