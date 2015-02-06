package rj.ivr.dtmf;

import java.util.concurrent.BlockingQueue;

import android.os.AsyncTask;
import android.util.Log;

public class RecognizerTask extends AsyncTask<Void, Object, Void> {
	
	private BlockingQueue<DataBlock> blockingQueue;
	
	private Recognizer recognizer;
	
	public static boolean recognized;
	public static Character recognizedKey;

	public RecognizerTask(BlockingQueue<DataBlock> blockingQueue) 
	{
		this.blockingQueue = blockingQueue;
		
		recognizer = new Recognizer();
		
		recognized = false;
		recognizedKey = null;
		
		Log.i("Recognizer Task", "Thread initialized");
	}

	@Override
	protected Void doInBackground(Void... params)
	{
		Log.i("Recognizer Task", "Thread Started");
		
		while(true)
		{
			try {
				DataBlock dataBlock = blockingQueue.take();
				
//				Log.i("Recognizer Task", "Got datablock from Queue");
								
				Spectrum spectrum = dataBlock.FFT();
				
				spectrum.normalize();				
				
				StatelessRecognizer statelessRecognizer = new StatelessRecognizer(spectrum);
				
//				Character key = recognizer.getRecognizedKey(statelessRecognizer.getRecognizedKey());
				
				Character key = statelessRecognizer.getRecognizedKey();
				
//				Log.i("Recognizer Task", "Key Pressed : " + key);
				
				if(key != ' ') {
					Log.i("Recognizer Task", "Key Recognized");
					recognized = true;
					recognizedKey = key;
				}
				
				
//				Log.i("Recognizer Task", "key : " + key);
				
//				publishProgress(spectrum, key);
								
//				SpectrumFragment spectrumFragment = new SpectrumFragment(75, 100, spectrum);
//				publishProgress(spectrum, spectrumFragment.getMax());
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
		}

//		return null;
	}

//	protected void onProgressUpdate(Object... progress) 
//	{
//		Log.i("RecognizerTask", "Key Pressed is : " + progress[1]);
//		
//		if(!("" + progress[1]).matches(" ")) {
////			Log.i("RecognizerTask", "Key Pressed is : " + progress[1]);
////			MainActivity.textView.append("Key Pressed is : " + progress[1] + "\n");
//		}
//
////		Integer key = (Integer)progress[1];
////		controller.debug(key.toString());
//    }
}
