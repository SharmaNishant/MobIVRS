//package rj.ivr.dtmf;
//
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.LinkedBlockingQueue;
//
//import rj.dtmf.R;
//import rj.dtmf.R.id;
//import rj.dtmf.R.layout;
//import rj.dtmf.R.menu;
//
//import android.os.Bundle;
//import android.app.Activity;
//import android.util.Log;
//import android.view.Menu;
//import android.widget.TextView;
//
//public class MainActivity extends Activity {
//
//	private RecordTask recordTask;
//	private BlockingQueue<DataBlock> blockingQueue;
//	private RecognizerTask recognizerTask;
//	public static TextView textView;
//
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		
//		textView = (TextView) this.findViewById(R.id.textView1);
//		
//		blockingQueue = new LinkedBlockingQueue<DataBlock>();
//		
//		recordTask = new RecordTask(this, blockingQueue);
//		recognizerTask = new RecognizerTask(this, blockingQueue);
//		
//		recordTask.execute();
//		
//		Log.i("Main Activity", "Record Task started");
//		
//		recognizerTask.execute();
//		
//		Log.i("Main Activity", "Recognizer Task started");
//	}
//	
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
//
//}
