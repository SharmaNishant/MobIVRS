package my.ivrs;

import java.io.IOException;
import java.io.OptionalDataException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import rj.ivr.dtmf.DataBlock;
import rj.ivr.dtmf.RecognizerTask;
import rj.ivr.dtmf.RecordTask;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class IVR_Activity extends Activity implements OnInitListener, OnUtteranceCompletedListener {

	private static final String tag = "IVR_Activity";
	private TextView text;
	private TextToSpeech tts;
	private AudioManager am;
	private float SPEECH_RATE = (float) 0.8;
	private RecordTask recordService;
	private RecognizerTask recognitionService;
	private ArrayList<SurveyFile> sampleQuestions;
	private int currentQuestion;
	private static int result;
	private ListView list;
	private ArrayAdapter<String> adapter;
	private AsyncTask<IVR_Activity, Void, Void> timer;
	public static boolean ongoingCall;
	
	public class Timer extends AsyncTask<IVR_Activity, Void, Void> {
		private int seconds = 0;
		private int minutes = 0;
		
		@Override
		protected Void doInBackground(IVR_Activity... params) {
			// TODO Auto-generated method stub
			while(true) {
				try {
					Thread.sleep(1000);					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
				seconds++;
				if(seconds==60) {
					seconds=0;
					minutes++;
				}
				runOnUiThread(new Runnable() {
					private String secString;
					private String minString;
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(seconds<10)
							secString = "0" + seconds;
						else
							secString = "" + seconds;
						if(minutes<10)
							minString = "0" + minutes;
						else
							minString = "" + minutes;
						text.setText("Timer\n" + minString + ":" + secString);
					}					
				});
			}
//			return null;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ivr_);
		
		text = (TextView) this.findViewById(R.id.textView1);
		
		list = (ListView) this.findViewById(R.id.listView1);
		
	
		timer = new Timer();
		timer.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				
		adapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_list_item_1, new ArrayList<String>());
		
//		text.append("\nIncoming Call from : " + CallHandlerService.getPhoneNumber());
		
		
		list.setAdapter(adapter);
		adapter.add("\nIncoming Call from : " + CallHandlerService.getPhoneNumber());
		
		Log.i(tag, "Initializing TTS");
		tts = new TextToSpeech(this, this);
		
		currentQuestion = 0;
		
//		sampleQuestions = new ArrayList<SurveyFile>();
//		String que = "Is the IVR working Properly ?";
//		String[] opt = {"Press 1 for YES", "Press 2 for NO"};
//		sampleQuestions.add(new SurveyFile(que,opt));
//		
//		que = "Is the audio sound clear ?";
//		sampleQuestions.add(new SurveyFile(que,new String[] {"Press 1 for YES", "Press 2 for NO"}));
		
		try {
			sampleQuestions = Result.getQuestion(this);
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
		
		result = -1;
	}

	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		if(status == TextToSpeech.SUCCESS) {
			Log.i(tag, "Successfully Initialized TTS");
			am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
			
			Log.i(tag, "Speakerphone : " + am.isSpeakerphoneOn());
			am.setSpeakerphoneOn(true);
			
			Log.i(tag, "MicrophoneMute : " + am.isMicrophoneMute());
			am.setMicrophoneMute(false);
			
//			tts.addEarcon("[beep]", getPackageName(), R.raw.beep);
			tts.addSpeech("[__beep__]", getPackageName(), R.raw.beep);
			
			tts.setSpeechRate(SPEECH_RATE);
			
			tts.setOnUtteranceCompletedListener(this);
			askQuestion();
						
		}
	}
	
	private void askQuestion() {
		// TODO Auto-generated method stub
		HashMap<String, String> myHash = new HashMap<String,String>();
		
		myHash.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_ALARM));
//		
//		myHash.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "1");
//		tts.speak("What key do you want to press ?", TextToSpeech.QUEUE_ADD, myHash);
//		text.append("\nSpeaking - " + "What key do you want to press ?");
//		
//		myHash.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "2");
//		tts.speak("1, OR", TextToSpeech.QUEUE_ADD, myHash);
//		text.append("\nSpeaking - " + "1, OR");
//				
//		myHash.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "LAST");
//		tts.speak("2", TextToSpeech.QUEUE_ADD, myHash);
//		text.append("\nSpeaking - " + "2");
		if(!ongoingCall) {
			endActivity();
		}
		
		if(currentQuestion == sampleQuestions.size()) {
			myHash.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "THANKS");
			tts.speak("Thanks for Your Time and Input, BYE!", TextToSpeech.QUEUE_ADD, myHash);
		}
		else {
			SurveyFile toAsk = sampleQuestions.get(currentQuestion);
			
			tts.speak(toAsk.question, TextToSpeech.QUEUE_ADD, myHash);
			for(int i=0 ; i<toAsk.options.length ; i++) {
				if( i == toAsk.options.length-1 )
					myHash.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "LAST");
				tts.speak(toAsk.options[i], TextToSpeech.QUEUE_ADD, myHash);
			}
		}
	}
	
	@Override
	public void onUtteranceCompleted(String utteranceId) {
		// TODO Auto-generated method stub
		if(utteranceId.equals("LAST")) {
			Log.i(tag, "Speaking of Question " + currentQuestion + " Completed");
			
			this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
//					text.append("\nQuestion -\n" + sampleQuestions.get(currentQuestion).question);
					
					adapter.add("Question -\n" + sampleQuestions.get(currentQuestion).question);
//					list.setAdapter(adapter);
				}			
			});
			
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tts.speak("[__beep__]", TextToSpeech.QUEUE_FLUSH, null);
			
			result = GetResponse();
			Log.i(tag, "Displaying Result : " + result);
			
			if(result==-1) {
				try {
					Result.setResult(currentQuestion+1, result, this);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				adapter.add("Response : NO RESPONSE");
			}
			this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
//					text.append("\nResponse - " + result);
					if(result==-1)
						adapter.add("Response : NO RESPONSE");
					else
						adapter.add("Response : " + result);
//					list.setAdapter(adapter);
				}			
			});
			
//			result = -1;
			currentQuestion++;
			askQuestion();
		}
		else if(utteranceId.equals("THANKS")) {
			Log.i(tag, "All Questions Completed");
			
			this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
//					text.append("\nAll Questions Completed");
					adapter.add("All Questions Completed");
//					list.setAdapter(adapter);
				}			
			});
			
			Log.i(tag, "Ending Call");
//			text.append("\nEnding Call");
			
			timer.cancel(true);
			CallHandlerService.endCall(this);
			
			endActivity();
		}
	}
	
	private void endActivity() {
		this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
//				text.append("\nCall Ended");
				adapter.add("Call Ended");
//				list.setAdapter(adapter);
			}			
		});
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.finish();
	}
	
	public void onDestroy() {
		Log.i(tag, "Destroying Activity");
		super.onDestroy();
		
		tts.shutdown();
	}

	private int GetResponse() {
		// TODO Auto-generated method stub
		
//		am.setMicrophoneMute(true);
		
		BlockingQueue<DataBlock> blockingQueue = new LinkedBlockingQueue<DataBlock>();
		recordService = new RecordTask(blockingQueue);
		recognitionService = new RecognizerTask(blockingQueue);

		am.setMicrophoneMute(true);
		am.setSpeakerphoneOn(false);
		
		recordService.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		recognitionService.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		
		long time = System.currentTimeMillis();
		
		while(System.currentTimeMillis() - time < 5000 && !RecognizerTask.recognized) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		recordService.cancel(true);
		recognitionService.cancel(true);
		
		am.setMicrophoneMute(false);
		am.setSpeakerphoneOn(true);
		
		Log.i(tag, "\nInput Recieved : " + RecognizerTask.recognizedKey);

		int ret;
		try {
			ret = Integer.parseInt("" + RecognizerTask.recognizedKey);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			Log.i(tag, "Returning -1");
			ret = -1;
		}
		
		return ret;
	}

}
