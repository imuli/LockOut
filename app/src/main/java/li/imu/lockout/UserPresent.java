package li.imu.lockout;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.TimeZone;

public class UserPresent extends BroadcastReceiver {
@Override
public void onReceive(Context context, Intent intent) {
	Log.v("UserPresent", "Present");
	if(isEnabled(context)) {
			lockScreen(context);
	}
}

private void lockScreen(Context context) {
	Log.v("UserPresent", "Attempting Screen Lock.");
	DevicePolicyManager mDPM;
	mDPM = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
	mDPM.lockNow();
	Log.v("UserPresent", "Screen Locked.");
}

private boolean isEnabled(Context context){
	SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
	if(!preferences.getBoolean("enable", false)) return false;

	TimeZone tz = TimeZone.getDefault();

	/* This will be off by a few seconds due to leap seconds. */
	long now = System.currentTimeMillis();
	long offset = tz.getOffset(0) - tz.getOffset(now);
	now = now  % (1000*60*60*24) - offset;

	long start = preferences.getLong("start", 0);
	long stop = preferences.getLong("stop", 0);

	Log.v("UserPresent", "start: " + start + " now: " + now + " stop: " + stop);

	if(start < stop){
		return start < now && now < stop;
	} else {
		return start < now || now < stop;
	}
}

}