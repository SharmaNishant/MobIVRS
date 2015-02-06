package rj.ivr.dtmf;

import java.util.concurrent.BlockingQueue;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.util.Log;

public class RecordTask extends AsyncTask<Void, Object, Void> {
	
	
	int frequency = 16000;
	int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
	int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;

	int blockSize = 1024;
	
//	MainActivity controller;
	BlockingQueue<DataBlock> blockingQueue;
	private String tag = "Record Task";

	public RecordTask(BlockingQueue<DataBlock> blockingQueue) {
//		this.controller = controller;
		this.blockingQueue = blockingQueue;
		
		Log.i(tag , "Thread initialized");
	}

	@Override
	protected Void doInBackground(Void... params) {

		int bufferSize = AudioRecord.getMinBufferSize(frequency, channelConfiguration, audioEncoding);
		
		AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.VOICE_CALL, frequency, channelConfiguration, audioEncoding, bufferSize);
		
		Log.i(tag, "Thread Started");
		
		try {

			short[] buffer = new short[blockSize];

			audioRecord.startRecording();
			
			Log.i(tag, "Recording Started");
			
			while (true)
			{
				int bufferReadSize = audioRecord.read(buffer, 0, blockSize);
				
				DataBlock dataBlock = new DataBlock(buffer, blockSize, bufferReadSize);
				
				blockingQueue.put(dataBlock);
				
//				Log.i("Record task", "Added to queue");
			}
//			Log.i(tag, "Recording Stopped");
						
			} catch (Throwable t) {
				//Log.e("AudioRecord", "Recording Failed");
			}
		
		audioRecord.stop();

		return null;
	}
}