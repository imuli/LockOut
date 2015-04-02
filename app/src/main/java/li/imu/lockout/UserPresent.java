package li.imu.lockout;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;

public class UserPresent extends BroadcastReceiver {
static boolean actually=false;
@Override
public void onReceive(Context context, Intent intent) {
	Log.v("UserPresent", "Present");
	if(isEnabled(context)) {
		actually = !actually;
		if(actually){
			lockScreen(context);
		}
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
	return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("enable", false);
}

}
