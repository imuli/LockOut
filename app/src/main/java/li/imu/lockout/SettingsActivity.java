package li.imu.lockout;

import android.annotation.TargetApi;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;


import java.util.List;




/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends PreferenceActivity {

@Override
protected void onPostCreate(Bundle savedInstanceState) {
	super.onPostCreate(savedInstanceState);
	if(!Util.preventSettings(getApplicationContext()))
		addPreferencesFromResource(R.xml.pref_general);
}

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
	intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
			new ComponentName(this, DeviceAdmin.class));
	intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
			"Lock Out needs this to lock the screen.");
	startActivityForResult(intent, 1);
}

/**
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 */
private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
	@Override
	public boolean onPreferenceChange(Preference preference, Object value) {
		String stringValue = value.toString();
		preference.setSummary(stringValue);
		return true;
	}
};

/**
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 *
 * @see #sBindPreferenceSummaryToValueListener
 */
private static void bindPreferenceSummaryToValue(Preference preference) {
	// Set the listener to watch for value changes.
	preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

	// Trigger the listener immediately with the preference's
	// current value.
	sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
			PreferenceManager
					.getDefaultSharedPreferences(preference.getContext())
					.getString(preference.getKey(), ""));
}

}
