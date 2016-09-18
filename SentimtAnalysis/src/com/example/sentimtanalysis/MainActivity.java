package com.example.sentimtanalysis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		ImageButton imageButton1 = (ImageButton)findViewById(R.id.imageButton1);
	    imageButton1.setOnClickListener(new OnClickListener(){
	    	public void onClick(View v){
	    		Intent intent = new Intent(MainActivity.this,TwoActivity.class);
	    		startActivity(intent);
	    	}
	    });
	
	}
    
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
