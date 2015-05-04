package li.imu.lockout;

import android.app.admin.DevicePolicyManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.CalendarContract.Instances;
import android.util.Log;

import java.util.TimeZone;

public class Util {

public static void
lockScreen(Context context) {
	Log.v("UserPresent", "Attempting Screen Lock.");
	DevicePolicyManager mDPM;
	mDPM = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
	mDPM.lockNow();
	Log.v("UserPresent", "Screen Locked.");
}

public static boolean
preventSettings(Context context){
	SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
	return preferences.getBoolean("prevent_settings", false) && isEnabled(context);
}

public static boolean
isEnabled(Context context){
	SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
	if(!preferences.getBoolean("enable", false)) return false;
	if(calendarIsEnabled(preferences, context)) return true;

	TimeZone tz = TimeZone.getDefault();

	/* This will be off by a few seconds due to leap seconds. */
	long now = System.currentTimeMillis();
	long offset = tz.getOffset(0) - tz.getOffset(now);
	now = now  % (1000*60*60*24) - offset;

	long start = preferences.getLong("start", 0);
	long stop = preferences.getLong("stop", 0);

	Log.v("UserPresent", "start: " + start + " now: " + now + " stop: " + stop);

	if(start <= stop){
		return start < now && now < stop;
	} else {
		return start < now || now < stop;
	}
}

private static final String[] InstanceFields = new String[] {
		Instances._ID,
};

private static boolean
calendarIsEnabled(SharedPreferences preferences, Context context) {
	String calendarId = preferences.getString("calendar", "-1");
	if(calendarId.equals("-1"))
		return false;

	long now = System.currentTimeMillis();
	ContentResolver cr = context.getContentResolver();
	String selection = Instances.CALENDAR_ID + "= ?";
	String[] selectionArgs = new String[] {calendarId};
	Uri.Builder uri = Instances.CONTENT_URI.buildUpon();
	ContentUris.appendId(uri, now);
	ContentUris.appendId(uri, now);
	Cursor cur = cr.query(uri.build(), InstanceFields, selection, selectionArgs, null);
	return cur.getCount() > 0;
}

}
