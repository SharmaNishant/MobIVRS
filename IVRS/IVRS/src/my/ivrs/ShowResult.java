package my.ivrs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OptionalDataException;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

/**
 * 
 * @author Nishant
 *
 */
public class ShowResult extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupActionBar();
		int q,o;
		ArrayList<SurveyFile> qList = null;
		ArrayList<Result> rList = new ArrayList<Result>();
		
		
		try {
			qList = Result.getQuestion(this);
			for(int i=0;i<qList.size();i++)
				rList.add(new Result(i,qList.get(i).options.length));
		} catch (OptionalDataException e1) {
			Toast.makeText(getApplicationContext(), e1.getMessage(),Toast.LENGTH_LONG).show();
			return;
		} catch (ClassNotFoundException e1) {
			Toast.makeText(getApplicationContext(), e1.getMessage(),Toast.LENGTH_LONG).show();
			return;
		} catch (IOException e1) {
			Toast.makeText(getApplicationContext(), "Unable to Read File",Toast.LENGTH_LONG).show();
			return;
		}
		
		
		
		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new FileReader(new File(getFilesDir()+File.separator+"result.txt")));
				String t ;
			while((t = bufferedReader.readLine())!=null)
			{
				q=Integer.parseInt(t.split(":")[0]);
				o=Integer.parseInt(t.split(":")[1]);
				if(q<=rList.size())
				{
					if(o<=rList.get(q-1).opt.length)
					{
						rList.get(q-1).opt[o-1]++;
					}
				}
			}
			bufferedReader.close();	
		} catch (FileNotFoundException e) {
			Toast.makeText(getApplicationContext(), "Result File Not Found",Toast.LENGTH_LONG).show();
			return;
		}	
		catch (IOException e) {
			Toast.makeText(getApplicationContext(), "Unable to Read File",Toast.LENGTH_LONG).show();
			return;
		}
		
		
		
		ArrayList<TextView> textViewArray = new ArrayList<TextView>();
		
		String res="";
		SurveyFile surveyFileObject;
		for(int i=0;i<rList.size();i++)
		{
			surveyFileObject=qList.get(i);
			if(surveyFileObject==null)
				continue;
			//System.out.println(m.question);
			//question.setText(surveyFileObject.question);
			textViewArray.add(new TextView(this));
			textViewArray.get(textViewArray.size()-1).setText("Q"+(i+1)+" : "+surveyFileObject.question);
			textViewArray.get(textViewArray.size()-1).setTextSize(25);
			
			//res+=m.question+"\n";
			for(int i1=0 ;i1 <surveyFileObject.options.length;i1++)
			{
				res=surveyFileObject.options[i1];//+" : "+rList.get(i).opt[i1]+"\n";
				res=res.split("for ")[1];
				//option.setText(res+" : "+rList.get(i).opt[i1]);
				textViewArray.add(new TextView(this));
				textViewArray.get(textViewArray.size()-1).setText("Opt " + (i1+1) + " - "+  res+" : "+rList.get(i).opt[i1]);
			}
			textViewArray.add(new TextView(this));
			textViewArray.get(textViewArray.size()-1).setText(" ");
		}
		//t.setText(res);
		
		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		
		for(int k=0;k<textViewArray.size();k++)
		{
			linearLayout.addView(textViewArray.get(k));
		}
		
		ScrollView scrollView = new ScrollView(this);
		scrollView.setVerticalScrollBarEnabled(true);
		scrollView.setVerticalScrollbarPosition(2);
		scrollView.addView(linearLayout);
		setContentView(scrollView);
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
		getMenuInflater().inflate(R.menu.show_result, menu);
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


	
}
