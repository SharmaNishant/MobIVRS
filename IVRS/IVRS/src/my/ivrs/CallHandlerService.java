package my.ivrs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class CallHandlerService extends BroadcastReceiver {

	public static boolean serviceStarted;
	private final static String tag = "CallHandlerService";
	private static String PhoneNumber = "";
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
	    // TODO Auto-generated method stub
		serviceStarted = true;
		
		if(!serviceStarted) {
			Log.i(tag , "Service has not been started");
			return;
		}
		
		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		
		if(telephonyManager.isNetworkRoaming())
			return;
		
		String phone_state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
		Log.i(tag, "Phone State : " + phone_state);
		
		if(phone_state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
			Log.i(tag, "Incoming Call");						
			PhoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
						
			receiveCall(context);			
		}
		else if(phone_state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
			IVR_Activity.ongoingCall = true;
			Log.i(tag, "ongoingCall : " + IVR_Activity.ongoingCall);
			context.startActivity(new Intent(context, IVR_Activity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
		}
		
		else if(phone_state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
			IVR_Activity.ongoingCall = false;
			Log.i(tag, "ongoingCall : " + IVR_Activity.ongoingCall);
			PhoneNumber = "";
		}
	}
	
	public static String getPhoneNumber() {
		return PhoneNumber;
	}
	
	public static void receiveCall(Context context) {
		
		Log.i(tag,"Accepting Call");
		
		Intent buttonDown = new Intent(Intent.ACTION_MEDIA_BUTTON);
	    buttonDown.putExtra(Intent.EXTRA_KEY_EVENT, 
	    		new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_HEADSETHOOK));
	    context.sendOrderedBroadcast(buttonDown, "android.permission.CALL_PRIVILEGED");
	 
	    // froyo and beyond trigger on buttonUp instead of buttonDown
	    Intent buttonUp = new Intent(Intent.ACTION_MEDIA_BUTTON);
	    buttonUp.putExtra(Intent.EXTRA_KEY_EVENT,
	    		new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK));
	    context.sendOrderedBroadcast(buttonUp, "android.permission.CALL_PRIVILEGED");
	    
	    
	    Toast.makeText(context, "MobIVRS receiving incoming call", Toast.LENGTH_LONG).show();
	}
	
	public static void endCall(Context context) {
		
		Log.i(tag,"Ending Call");
		
		Intent buttonDown = new Intent(Intent.ACTION_MEDIA_BUTTON);
	    buttonDown.putExtra(Intent.EXTRA_KEY_EVENT, 
	    		new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_HEADSETHOOK));
	    context.sendOrderedBroadcast(buttonDown, "android.permission.CALL_PRIVILEGED");
	 
	    // froyo and beyond trigger on buttonUp instead of buttonDown
	    Intent buttonUp = new Intent(Intent.ACTION_MEDIA_BUTTON);
	    buttonUp.putExtra(Intent.EXTRA_KEY_EVENT,
	    		new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK));
	    context.sendOrderedBroadcast(buttonUp, "android.permission.CALL_PRIVILEGED");
	}
}
