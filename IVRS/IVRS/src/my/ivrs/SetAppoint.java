package my.ivrs;

import java.io.File;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class SetAppoint extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_appoint);
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_appoint, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void deletAll(View v)
	{
		File serv = new File(getFilesDir()+File.separator+"today.txt");
		File res = new File(getFilesDir()+File.separator+"appoint.txt");
		File app = new File(getFilesDir()+File.separator+"appointSetting.txt");
		serv.delete();
		res.delete();
		app.delete();
		Toast.makeText(getApplicationContext(), "Appointments Deleted!",Toast.LENGTH_LONG).show();
	}
	
	public void newTimings(View v)
	{
		Toast.makeText(getApplicationContext(), "Future Implementation!",Toast.LENGTH_LONG).show();
	}
	
	public void showToday(View v)
	{
		Toast.makeText(getApplicationContext(), "Future Implementation!",Toast.LENGTH_LONG).show();
	}

}
