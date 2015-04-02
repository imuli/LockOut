package li.imu.lockout;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DeviceAdmin extends DeviceAdminReceiver {
public void onEnabled(Context context, Intent intent){
	Log.v("DeviceAdmin", "onEnabled");
}
}