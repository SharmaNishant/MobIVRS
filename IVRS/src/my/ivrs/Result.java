package my.ivrs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.util.ArrayList;

import android.content.Context;
import android.widget.Toast;
/**
 * 
 * @author Nishant
 *
 */
public class Result {

	public int que;
	public int[] opt;
	
	Result(int q,int o)
	{
		que=q;
		opt = new int[o];
		for(int i=0;i<opt.length;i++)
			opt[i]=0;
	}
	
	/**
	 * 
	 * @param qNum
	 * @param res
	 * @param context
	 * @throws IOException
	 */
	public static void setResult(int qNum,int res,Context ct) throws IOException
	{
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(ct.getFilesDir()+File.separator+"result.txt"),true));
		bufferedWriter.write(qNum+":"+res+"\n");
		bufferedWriter.close();
	}
	
	/**
	 * 
	 * @param context
	 * @return arraylist<surveyfile>
	 * @throws OptionalDataException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static ArrayList<SurveyFile> getQuestion(Context ct) throws OptionalDataException, ClassNotFoundException, IOException
	{
		//TextView t = new TextView(this);
		ArrayList<SurveyFile> rList;
		try{
		ObjectInputStream bufferedWriter = new ObjectInputStream(new FileInputStream(new File(ct.getFilesDir()+File.separator+"survey.txt")));
		ArrayFile temp = (ArrayFile) bufferedWriter.readObject();
		rList = temp.list;
		bufferedWriter.close();
		}
		catch(Exception e)
		{
			Toast.makeText(ct, "File Not Found",Toast.LENGTH_LONG).show();
			return new ArrayList<SurveyFile>();
		}
		return rList;
		/*String res="";
		SurveyFile m;
		for(int i=0;i<rList.size();i++)
		{
			m=rList.get(i);
			if(m==null)
				continue;
			System.out.println(m.question);
			res+=m.question+"\n";
			for(int i1=0 ;i1 <m.options.length;i1++)
			{
				res+=m.options[i1]+"\n";
			}
		}
		t.setText(res);
		ScrollView t1 = new ScrollView(this);
		t1.setVerticalScrollBarEnabled(true);
		t1.setVerticalScrollbarPosition(2);
		t1.addView(t);
		setContentView(t1);
		return null;*/
	}
	public static boolean getOnOff(Context ct) throws OptionalDataException, ClassNotFoundException, IOException
	{
		//TextView t = new TextView(this);
		try{
		ObjectInputStream bufferedWriter = new ObjectInputStream(new FileInputStream(new File(ct.getFilesDir()+File.separator+"surv_setting.txt")));
		//if(bu)
		
		char t = bufferedWriter.readChar();
		if(t==('1'))
			return true;
		bufferedWriter.close();
		}
		catch(Exception e)
		{
			Toast.makeText(ct, "File Not Found",Toast.LENGTH_LONG).show();
			return false;
		}
		return false;
	}
}
