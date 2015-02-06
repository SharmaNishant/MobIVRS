package my.ivrs;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class HomePage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getActionBar().hide();
		getMenuInflater().inflate(R.menu.home_page, menu);
		return true;
		
	}
	
	
	public void survey(View view)
	{
		Intent sur = new Intent(this, Survey.class); 
		startActivity(sur);
	}
	
	public void appoint(View view){

		Intent sur = new Intent(this, SetAppoint.class); 
		startActivity(sur);
	}
}
