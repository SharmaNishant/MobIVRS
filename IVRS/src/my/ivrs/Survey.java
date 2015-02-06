package my.ivrs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.support.v4.app.NavUtils;
/**
 * 
 * @author Nishant
 *
 */
public class Survey extends Activity {
	static int tSize =20;
	static int ques_num;
	static ArrayList<SurveyFile> list = new ArrayList<SurveyFile>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		ToggleButton t = (ToggleButton) findViewById(R.id.toggleButton1);
		try {
			if(Result.getOnOff(this))
			{
				t.setChecked(true);
			}
				
		} catch (OptionalDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setContentView(R.layout.activity_survey);
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
		getMenuInflater().inflate(R.menu.survey, menu);
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

	
	public void NewSurvey(View view)
	{
		ques_num=0;
		list=new ArrayList<SurveyFile>();
		NewQuestion(view);
	}
	
	
	/**
	 * 
	 * @param view
	 */
	public void NewQuestion(View view)
	{
		
		LinearLayout surv = new LinearLayout(this); 
		surv.setOrientation(LinearLayout.VERTICAL);
		ques_num++;
		
		TextView ques = new TextView(this);
		ques.setText("Enter Question Number "+ ques_num + " :" );
		ques.setTextSize(tSize);
		surv.addView(ques);
		
		EditText ques_edit = new EditText(this);
		surv.addView(ques_edit);
		
		TextView op1 = new TextView(this);
		op1.setText("Enter Option Number 1 :" );
		op1.setTextSize(tSize);
		surv.addView(op1);
		
		EditText op1_edit = new EditText(this);
		surv.addView(op1_edit);
		
		TextView op2 = new TextView(this);
		op2.setText("Enter Option Number 2 :" );
		op2.setTextSize(tSize);
		surv.addView(op2);
		
		EditText op2_edit = new EditText(this);
		surv.addView(op2_edit);
		
		Button addOpt = new Button(this);
		addOpt.setText("Add New Option");
		addOpt.setOnClickListener(new addOption());
		surv.addView(addOpt);
		
		Button nextQue = new Button(this);
		nextQue.setText("Next Question");
		nextQue.setOnClickListener(new newQues());
		surv.addView(nextQue);
		
		Button done = new Button(this);
		done.setText("Done");
		done.setOnClickListener(new compSurvey());
		surv.addView(done);
		
		ScrollView t = new ScrollView(this);
		t.setVerticalScrollBarEnabled(true);
		t.setVerticalScrollbarPosition(2);
		t.addView(surv);
		setContentView(t);
		//setContentView(surv);
		
	}
	
	/**
	 * 
	 * @param view
	 */
	public void add_opt(View view)	
	{
		LinearLayout t= (LinearLayout) view.getParent();
		TextView p = new TextView(this);
		p.setTextSize(tSize);
		EditText q = new EditText(this);
		int qn=t.getChildCount();
		qn=qn-9;
		qn=qn/2;
		qn++;
		if(qn>7)
		{
			Toast.makeText(getApplicationContext(), "Options cannot be more than Nine",Toast.LENGTH_LONG).show();
			return;
		}
		p.setText("Enter Option number "+(qn+2)+" :");
		t.addView(p, t.getChildCount()-3);
		t.addView(q, t.getChildCount()-3);
	}
	
	/**
	 * 
	 * @param view
	 * @return
	 * @throws IOException
	 */
	public boolean validater(View view)
	{
		//SurveyFile so = new SurveyFile();
		String que = null;
		String[] opt;
		Class<? extends View> c = null;
		View temp = null;
		LinearLayout t= (LinearLayout) view.getParent();
		int qn=t.getChildCount();
		//que=(EditText)t.getChildAt(2);
		int ct = qn;
		ct=(ct-5)/2;
		opt=new String[ct];
		ct=0;
		for(int i=0 ; i<qn ;i++)
		{
			temp = t.getChildAt(i);
			c = temp.getClass();
			if(c.equals(EditText.class))
			{
				EditText tp = (EditText) temp;
				String check = tp.getText().toString();
				if(check.isEmpty())
				{
					Toast.makeText(getApplicationContext(), "Fields Cannot be Empty",Toast.LENGTH_LONG).show();
					return false;
				}
				if(i==1)
				{
					que=check;
				}
				else
				{
					opt[ct]="press " + (ct+1) + " for " +check;
					ct++;
				}
				
			}
		}
		list.add(new SurveyFile(que,opt));
		return true;
	}

	
	public void goHome()
	{
		startActivity(new Intent(this,HomePage.class));
	}
	
	public class compSurvey implements OnClickListener{
		@Override
		public void onClick(View v) {
			try {
				if(validater(v))
				{
					ArrayFile write = new ArrayFile(list);
					ObjectOutputStream bufferedWriter = new ObjectOutputStream(new FileOutputStream(new File(getFilesDir()+File.separator+"survey.txt")));
					bufferedWriter.writeObject(write);// write(list);
					bufferedWriter.close();
					ques_num=0;
					list=new ArrayList<SurveyFile>();
					File result = new File(getFilesDir()+File.separator+"result.txt");
					result.delete();
					result.createNewFile();
					goHome();
				}
			} catch (IOException e) {
				Toast.makeText(getApplicationContext(), "Unable to Read File",Toast.LENGTH_LONG).show();
				return;
			}
		}
	}
	
	public class addOption implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			add_opt(arg0);
		}
		
	}
	
	public class newQues implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(validater(v))
			{
				NewQuestion(v);
			}
		}
		
	}
	
	public void addopt(View v) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		File serv = new File(getFilesDir()+File.separator+"survey.txt");
		if(!serv.exists())
		{
			Toast.makeText(getApplicationContext(), "Survey Doesn't Exist",Toast.LENGTH_LONG).show();
			return;
		}
		startActivity(new Intent(this, ShowResult.class));
		///getQuestion(v);
	}
	
	public void deleteAll(View v)
	{
		File serv = new File(getFilesDir()+File.separator+"survey.txt");
		File res = new File(getFilesDir()+File.separator+"result.txt");
		File rest = new File(getFilesDir()+File.separator+"surv_setting.txt");
		serv.delete();
		res.delete();
		rest.delete();
		Toast.makeText(getApplicationContext(), "Survey Deleted!",Toast.LENGTH_LONG).show();
	}
	
	public void onOff(View v) throws IOException
	{
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(getFilesDir()+File.separator+"surv_setting.txt")));
		ToggleButton t = new ToggleButton(this);
		if(t.isChecked())
		{
			bufferedWriter.write("1");
			bufferedWriter.close();
		}
		else
		{
			bufferedWriter.write("0");
			bufferedWriter.close();
		
		}
	}
	
	
}
