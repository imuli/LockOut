package li.imu.lockout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class UserPresent extends BroadcastReceiver {
@Override
public void onReceive(Context context, Intent intent) {
	Log.v("UserPresent", "Present");
	if(Util.isEnabled(context)) {
			Util.lockScreen(context);
	}
}

}